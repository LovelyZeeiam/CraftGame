package xueli.craftgame.message;

import xueli.craftgame.client.CraftGameClient;
import xueli.craftgame.server.PlayerInfo;

public class MessagePlayerState extends MessageObject<PlayerInfo> {

	private State state;

	public MessagePlayerState(PlayerInfo playerInfo, State state) {
		super(playerInfo);
		this.state = state;
	}

	@Override
	public void processClient(CraftGameClient client) {
		System.out.println("<" + getValue().getName() + " joined the game>");
	}

	@Override
	public String toString() {
		return "MessagePlayerState{" +
				"info='" + getValue() + '\'' +
				", state=" + state +
				'}';
	}

	public State getState() {
		return state;
	}

	public static enum State {
		JOIN, EXIT;
	}

}
