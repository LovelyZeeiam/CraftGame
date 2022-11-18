package xueli.mcremake.classic.client.gui.universal;

import xueli.game2.resource.submanager.render.texture.TextureLoaderLegacy;

import static org.lwjgl.opengl.GL30.*;

public class UniversalBackgroundTextureLoader extends TextureLoaderLegacy {

	@Override
	public int createTexture() {
		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glBindTexture(GL_TEXTURE_2D, 0);
		return id;
	}

}
