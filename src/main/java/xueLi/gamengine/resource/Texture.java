package xueLi.gamengine.resource;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

	public int id;
	public boolean isPreloading = false;
	public boolean isGUITexture = false;

	public Texture(int id, boolean isPreloading, boolean isGuiTexture) {
		this.id = id;
		this.isPreloading = isPreloading;
		this.isGUITexture = isGuiTexture;

	}

	public void bind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

	}

}
