package xueli.game2.network;

import java.io.IOException;
import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import xueli.game2.network.pipeline.PacketDecoder;
import xueli.game2.network.pipeline.PacketEncoder;
import xueli.game2.network.pipeline.PacketSizeDecodeHandler;
import xueli.game2.network.pipeline.PacketSizePrefixer;

public class ClientConnection extends SimpleChannelInboundHandler<Packet> {

	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(1);

	private ChannelHandlerContext channel;

	private String hostname;
	private int port;

	private boolean connected = true;
	
	ClientConnection() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx;
		super.channelActive(ctx);

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		this.packetRead(msg);
	}

	protected void packetRead(Packet msg) {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
		super.exceptionCaught(ctx, cause);

	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		this.connected = false;
		
	}

	public void writeAndFlush(Object p) {
		this.writeAndFlush(p, true);
	}
	
	public void writeAndFlush(Object p, PacketListener listener) {
		ChannelFuture future = this.channel.writeAndFlush(p);
		future.addListener(f -> {
			if(f.isDone()) {
				listener.onPacketSentSuccessfully();
			} else {
				Packet failurePacket = listener.onPacketSendFailure();
				if(failurePacket != null) {
					this.channel.writeAndFlush(failurePacket)
						.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
				}
			}
			
		});
		
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

	public void tick() {
	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	public void close() {
		if (this.channel != null) {
			this.channel.close().awaitUninterruptibly();
			this.connected = false;
		}

	}
	
	public boolean isConnected() {
		return connected;
	}

	public static ClientConnection connectToServer(Protocol clientboundProtocol, Protocol serverboundProtocol, InetSocketAddress addr) throws IOException {
		ClientConnection c = new ClientConnection();
		c.hostname = addr.getHostName();
		c.port = addr.getPort();

		new Bootstrap()
				.group(workerGroup)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<>() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline()
								.addLast(new PacketSizePrefixer())
								.addLast(new PacketEncoder(serverboundProtocol))

								.addLast(new PacketSizeDecodeHandler())
								.addLast(new PacketDecoder(clientboundProtocol))
								.addLast(c);
					}
				}).connect(addr).syncUninterruptibly();
		return c;
	}

	public static void shutdown() {
		workerGroup.shutdownGracefully();
	}

}
