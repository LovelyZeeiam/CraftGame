package xueli.craftgame.player;

public class PlayerStat {
	
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
