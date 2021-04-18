package xueli.craftgame.net.server;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import xueli.craftgame.net.interchange.Message;
import xueli.craftgame.net.interchange.MessageClose;
import xueli.craftgame.net.interchange.MessagePlayerConnect;

@Sharable
public abstract class Server extends SimpleChannelInboundHandler<Message> implements Runnable {

	public static final int PORT = 19100;
	
	private static Logger logger = Logger.getLogger(Server.class.getName());

	private ServerBean bean;
	public boolean running = false;

	public Server() {
		this.bean = new ServerBean("Test Server", "This is a server being tested out~");

	}

	public ServerBean getBean() {
		return bean;
	}
	
	private HashMap<SocketAddress, String> playerNames = new HashMap<>();
	
	public void addPlayer(String name, SocketAddress addr) {
		playerNames.put(addr, name);
		
	}
	
	private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public static ChannelGroup getChannels() {
		return channels;
	}
	
	private EventLoopGroup bossGroup, workerGroup;
	private ChannelFuture future;
	
	private void initServer() {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		
	}
	
	private void startServer() throws Exception {
		ServerBootstrap b = new ServerBootstrap(); // (2)
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new MyChannelInitilizer(this))
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		future = b.bind(PORT);
		
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		channels.add(ctx.channel());

		logger.info("Connected: " + incoming.remoteAddress().toString());

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		logger.info("Recieve Event: " + msg.toString());
		msg.onRecieve(this, ctx);

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		logger.info("Active: " + incoming.remoteAddress().toString());

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		logger.info("Inactive: " + incoming.remoteAddress().toString());

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();

	}

	private void sendToAll(Message message) {
		for (Channel channel : channels) {
			channel.writeAndFlush(message);
		}

	}
	
	private void stopServer() throws Exception {
		future.channel().close().sync();
		
	}
	
	private void shutdownGracefully() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		
	}
	
	private final BlockingQueue<Message> messageToBeSentForAll = new LinkedBlockingQueue<>();

	private void runServer() throws Exception {
		initServer();
		try {
			startServer();
			logger.info("Server start in " + PORT + ".");
			running = true;
			
			while(running) {
				Message message = messageToBeSentForAll.take();
				if(message instanceof MessageClose)
					if(running) continue; else break;
				sendToAll(message);
				
			}
			
			stopServer();
			logger.info("Server stopped.");
			
		} finally {
			shutdownGracefully();
			
		}

	}
	
	public void closeServer() {
		try {
			for(Channel channel : channels) {
				channel.close().sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		running = false;
		messageToBeSentForAll.add(new MessageClose());
		
		
	}
	
	public void addMessageToAll(Message message) {
		messageToBeSentForAll.add(message);
		
	}
	
	@Override
	public void run() {
		Thread thread = new Thread(() -> {
			try {
				runServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		onInit();
		thread.start();
		
		while(running) {
			onUpdate();
		}
		
		onExit();
		
	}
	
	public abstract void onInit();
	
	public abstract void onUpdate();
	
	public abstract void onExit();

}
