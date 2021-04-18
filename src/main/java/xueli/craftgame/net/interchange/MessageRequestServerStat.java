package xueli.craftgame.net.interchange;

import io.netty.channel.ChannelHandlerContext;
import xueli.craftgame.net.server.Server;

public class MessageRequestServerStat extends Message {

	private static final long serialVersionUID = -1422558937019418408L;

	public MessageRequestServerStat() {
		super("REQUEST_STAT", null);
	}

	@Override
	public void onRecieve(Server server, ChannelHandlerContext ctx) {
		ctx.writeAndFlush(new MessageServerBean(server.getBean()));

	}

}
