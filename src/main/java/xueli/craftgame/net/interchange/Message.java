package xueli.craftgame.net.interchange;

import java.io.Serializable;

import io.netty.channel.ChannelHandlerContext;
import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.server.Server;

public class Message implements Serializable {

	private static final long serialVersionUID = -5044213292903062056L;

	private String code;
	private Object data;

	public Message(String code, Object data) {
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

	public void onRecieve(Server server, ChannelHandlerContext ctx) {
		
	}

	public void onRecieve(Client client) {
		
	}

	@Override
	public String toString() {
		return "Message [code=" + code + ", data=" + data + "]";
	}

}
