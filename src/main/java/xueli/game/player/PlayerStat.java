package xueli.game.player;

public class PlayerStat {

	private String name, iconPath;

	public PlayerStat() {
		this.name = System.getProperty("player.name");
		this.iconPath = System.getProperty("player.icon");

		if (this.name == null) {
			this.name = "Steve";
		}
		if (this.iconPath == null) {
			this.iconPath = "res/gui/player_icon.jpg";
		}

	}

	public PlayerStat(String name, String iconPath) {
		this.name = name;
		this.iconPath = iconPath;
	}



	public String getName() {
		return name;
	}

	public String getIconPath() {
		return iconPath;
	}

	@Override
	public String toString() {
		return "PlayerStat [name=" + name + ", iconPath=" + iconPath + "]";
	}

}
