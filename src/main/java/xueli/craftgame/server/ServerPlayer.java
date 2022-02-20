package xueli.craftgame.server;

import org.apache.mina.core.session.IoSession;

public class ServerPlayer {

	private IoSession session;
	private PlayerInfo info;

	public ServerPlayer(IoSession session, PlayerInfo info) {
		this.session = session;
		this.info = info;
	}

	public IoSession getSession() {
		return session;
	}

	public PlayerInfo getInfo() {
		return info;
	}

}
