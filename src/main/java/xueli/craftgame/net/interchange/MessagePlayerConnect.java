package xueli.craftgame.net.interchange;

import io.netty.channel.ChannelHandlerContext;
import xueli.craftgame.net.server.Server;

public class MessagePlayerConnect extends Message {

	private static final long serialVersionUID = 366750710127711143L;

	public MessagePlayerConnect(String playerName) {
		super("PLAYER_STAT_TO_SERVER", playerName);
	}
	
	@Override
	public void onRecieve(Server server, ChannelHandlerContext ctx) {
		server.addPlayer((String) getData(), ctx.channel().remoteAddress());
		
	}

}
