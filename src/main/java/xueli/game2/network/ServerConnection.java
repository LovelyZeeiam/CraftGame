package xueli.game2.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xueli.game2.network.pipeline.PacketDecoder;
import xueli.game2.network.pipeline.PacketEncoder;
import xueli.game2.network.pipeline.PacketSizeDecodeHandler;
import xueli.game2.network.pipeline.PacketSizePrefixer;
import xueli.game2.network.processor.PacketProcessor;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class ServerConnection {

	private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private int port;

	private final Protocol clientboundProtocol, serverboundProtocol;
	private final Function<ClientConnection, PacketProcessor> processor;

	private final ArrayList<ClientConnection> connections = new ArrayList<>();

	public ServerConnection(int port, Function<ClientConnection, PacketProcessor> processor, Protocol clientboundProtocol, Protocol serverboundProtocol) {
		this.port = port;

		this.clientboundProtocol = clientboundProtocol;
		this.serverboundProtocol = serverboundProtocol;

		this.processor = processor;

	}

	private ChannelFuture serverFuture;

	public void startServer() {
		this.serverFuture = new ServerBootstrap()
				.group(workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ClientConnection clientConnection = new ClientConnection();
						clientConnection.setPacketProcessor(processor.apply(clientConnection));

						ch.pipeline()
								.addLast(new PacketSizePrefixer())
								.addLast(new PacketEncoder(serverboundProtocol))

								.addLast(new PacketSizeDecodeHandler())
								.addLast(new PacketDecoder(clientboundProtocol))
								.addLast(clientConnection);

					}
				}).bind(port).syncUninterruptibly();

	}

	public void stopServer() {
		if(serverFuture != null) {
			try {
				serverFuture.channel().close().sync();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

}
