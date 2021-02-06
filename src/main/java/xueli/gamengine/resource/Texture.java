package xueli.gamengine.resource;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

	public int id;
	public boolean isPreloading = false;
	public boolean isGUITexture = false;

	/**
	 * Only enabled when not nvg texture
	 */
	public int width,height;

	public Texture(int id, boolean isPreloading, boolean isGuiTexture) {
		this.id = id;
		this.isPreloading = isPreloading;
		this.isGUITexture = isGuiTexture;

	}

	public void bind() {
		bind(GL13.GL_TEXTURE0);

	}
	
	public void bind(int textureID) {
		GL13.glActiveTexture(textureID);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		
	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

	}

}
