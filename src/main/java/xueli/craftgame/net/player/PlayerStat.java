package xueli.craftgame.net.player;

import java.io.Serializable;

public class PlayerStat implements Serializable {

	private static final long serialVersionUID = -3317283467437134144L;

	private String name;
	private int iconTexture;

	public PlayerStat(String name, int iconTexture) {
		this.name = name;
		this.iconTexture = iconTexture;

	}

	public String getName() {
		return name;
	}

	public int getIconTexture() {
		return iconTexture;
	}

}
