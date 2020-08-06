package xueLi.gamengine.resource;

public class Texture {

	public int id;
	public boolean isPreloading = false;
	public boolean isGUITexture = false;

	public Texture(int id, boolean isPreloading, boolean isGuiTexture) {
		this.id = id;
		this.isPreloading = isPreloading;
		this.isGUITexture = isGuiTexture;

	}

}
