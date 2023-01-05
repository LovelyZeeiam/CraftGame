package xueli.game2.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import xueli.game2.lifecycle.RunnableLifeCycle;
import xueli.game2.network.pipeline.PacketDecoder;
import xueli.game2.network.pipeline.PacketEncoder;
import xueli.game2.network.pipeline.PacketSizeDecodeHandler;
import xueli.game2.network.pipeline.PacketSizePrefixer;

public class Server<T extends ServerClientConnection> implements RunnableLifeCycle {

	private final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private int port;

	private final Protocol clientboundProtocol, serverboundProtocol;
	private final Supplier<T> connFunc;

	private final ArrayList<T> connections = new ArrayList<>();

	public Server(int port, Supplier<T> connFunc, Protocol clientboundProtocol, Protocol serverboundProtocol) {
		this.port = port;

		this.clientboundProtocol = clientboundProtocol;
		this.serverboundProtocol = serverboundProtocol;

		this.connFunc = connFunc;

	}

	private ChannelFuture serverFuture;
	private boolean isRunning = false;

	@Override
	public void init() {
		this.serverFuture = new ServerBootstrap()
				.group(workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						T conn = connFunc.get();
						connections.add(conn);

						ch.pipeline()
								.addLast(new PacketSizePrefixer())
								.addLast(new PacketEncoder(clientboundProtocol))

								.addLast(new PacketSizeDecodeHandler())
								.addLast(new PacketDecoder(serverboundProtocol))
								.addLast(conn);

					}
				}).bind(port).syncUninterruptibly();
		this.isRunning = true;

	}
	
	@Override
	public void gameLoop() {
		// we can get an iterator of the list and we can remove it immediately, learnt from source code of Minecraft
		Iterator<T> iterable = connections.iterator();
		while(iterable.hasNext()) {
			T t = iterable.next();
			t.tick();
			if(!t.isConnected()) {
				iterable.remove();
			}
		}
		
	}
	
	public void broadcast(Object obj) {
		this.connections.forEach(l -> l.writeAndFlush(obj));
		
	}

	@Override
	public void release() {
		this.isRunning = false;
		if(serverFuture != null) {
			try {
				serverFuture.channel().close().sync();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		workerGroup.shutdownGracefully();

	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

}
