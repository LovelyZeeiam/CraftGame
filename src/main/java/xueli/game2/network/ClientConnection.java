package xueli.game2.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import xueli.game2.network.pipeline.PacketDecoder;
import xueli.game2.network.pipeline.PacketEncoder;
import xueli.game2.network.pipeline.PacketSizeDecodeHandler;
import xueli.game2.network.pipeline.PacketSizePrefixer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientConnection extends SimpleChannelInboundHandler<Packet> {

	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(1);

	private ChannelHandlerContext channel;

	private String hostname;
	private int port;

	private ClientConnection() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx;
		super.channelActive(ctx);

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		// TODO: Process Packet

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
		super.exceptionCaught(ctx, cause);

	}

	public void writeAndFlush(Object p) {
		this.writeAndFlush(p, true);
	}

	public void writeAndFlush(Object p, boolean async) {
		ChannelFuture future = this.channel.writeAndFlush(p);
		if (!async) {
			try {
				future.sync();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	public void close() {
		if (this.channel != null) {
			this.channel.close().syncUninterruptibly();
		}

	}

	public static ClientConnection connectToServer(InetSocketAddress addr) throws IOException {
		ClientConnection c = new ClientConnection();
		c.hostname = addr.getHostName();
		c.port = addr.getPort();

		new Bootstrap()
				.group(workerGroup)
				.handler(new ChannelInitializer<>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline()
								.addLast(new PacketSizePrefixer())
								.addLast(new PacketEncoder(PacketSourceSide.FROM_CLIENT))

								.addLast(new PacketSizeDecodeHandler())
								.addLast(new PacketDecoder(PacketSourceSide.FROM_SERVER))
								.addLast(c);
					}
				}).channel(NioSocketChannel.class).connect(addr).syncUninterruptibly();
		return c;
	}

	public static void shutdown() {
		workerGroup.shutdownGracefully();
	}

}
