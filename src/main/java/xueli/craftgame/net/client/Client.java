package xueli.craftgame.net.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
import xueli.craftgame.net.interchange.MessageClose;
import xueli.craftgame.net.interchange.MessagePlayerConnect;
import xueli.game.player.PlayerStat;

@Sharable
public class Client extends SimpleChannelInboundHandler<Message> {

	private static Logger logger = Logger.getLogger(Client.class.getName());
	
	private String addr;
	private int port;
	
	private PlayerStat stat;
	
	public Client(String addr, int port, PlayerStat stat) {
		this.addr = addr;
		this.port = port;
		this.stat = stat;
		
	}

	public String getAddr() {
		return addr;
	}

	public int getPort() {
		return port;
	}
	
	private ConcurrentLinkedQueue<Message> messagesToBeProcessed = new ConcurrentLinkedQueue<>();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		logger.info("Recieve Event: " + msg.toString());
		messagesToBeProcessed.add(msg);
		
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
            
            sendMessage(new MessagePlayerConnect(stat.getName()));
            
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
	
	public void updateWhenMainThread() {
		while(!messagesToBeProcessed.isEmpty()) {
			messagesToBeProcessed.poll().onRecieve(this);
			
		}
		
	}
	
	public void sendMessage(Message message) {
		messagesToBeSent.add(message);
		
	}
	
	public void closeClient() {
		running = false;
		messagesToBeSent.add(new MessageClose());
		
	}
	
	public boolean connected() {
		return channel != null && channel.isActive();
	}

}
