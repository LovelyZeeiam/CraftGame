package xueli.craftgame.server;

import java.io.Serializable;

public class PlayerInfo implements Serializable {

	private String name;

	public PlayerInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
