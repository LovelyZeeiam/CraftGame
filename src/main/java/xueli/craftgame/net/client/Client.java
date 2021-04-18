package xueli.craftgame.net.client;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import xueli.craftgame.net.interchange.Message;
import xueli.craftgame.net.interchange.MessageChat;
import xueli.craftgame.net.interchange.MessageClose;
import xueli.craftgame.net.server.Server;

@Sharable
public class Client extends SimpleChannelInboundHandler<Message> {

	private static Logger logger = Logger.getLogger(Client.class.getName());
	
	private String addr;
	private int port;
	
	public Client(String addr, int port) {
		this.addr = addr;
		this.port = port;
		
	}

	public String getAddr() {
		return addr;
	}

	public int getPort() {
		return port;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		System.out.println(msg);
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
		closeClient();
		
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		closeClient();
	}
	
	private Channel channel;
	public boolean running = true;
	private BlockingQueue<Message> messagesToBeSent = new LinkedBlockingDeque<>();
	
	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyChannelInitilizer(this));
            channel = bootstrap.connect(addr, port).sync().channel();
            
            logger.info("Connected Server: " + addr + ":" + port);
            
            while(running) {
				Message message = messagesToBeSent.take();
				if(message instanceof MessageClose)
					if(running) continue; else break;
				channel.writeAndFlush(message);
				
			}
            
            logger.info("Closing connect: " + addr + ":" + port);
            channel.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            logger.info("Client close gracefully.");
            
        }
		
	}
	
	public void sendMessage(Message message) {
		messagesToBeSent.add(message);
		
	}
	
	public void closeClient() {
		running = false;
		messagesToBeSent.add(new MessageClose());
		
	}
	
	public static void main(String[] args) {
		Client client = new Client("localhost", Server.PORT);
		Thread thread = new Thread(() -> {
			try {
				client.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();
		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			String input = scanner.nextLine();
			if("q".equals(input)) break;
			client.sendMessage(new MessageChat(input));
			
		}
		scanner.close();
		
		client.closeClient();
		
	}

}
