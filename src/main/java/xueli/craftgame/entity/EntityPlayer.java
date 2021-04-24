package xueli.craftgame.entity;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import xueli.game.utils.FloatBufferWrapper;
import xueli.game.utils.Shader;
import xueli.game.utils.texture.Texture;
import xueli.game.vector.Vector;
import xueli.utils.mojang.SkinGetter;

public class EntityPlayer extends Entity {
	
	private String name;

	private Texture texture;
	
	public EntityPlayer(String name) {
		super();
		this.name = name;
		
		getTexture();

	}

	public EntityPlayer(Vector position, String name) {
		super(position);
		this.name = name;
		
		getTexture();
		
	}
	
	private void getTexture() {
		String skinPath = "temp/skins/" + name + ".png";
		if(!SkinGetter.saveSkin(name, skinPath)) {
			texture = Texture.NULL;
		} else {
			try {
				texture = Texture.loadTexture(skinPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected void storeBuffer(EntityRenderer renderer) {
		this.pointer = new VertexPointerEntity(8192, GL15.GL_STATIC_DRAW);
		this.pointer.initDraw();
		FloatBuffer buffer = this.pointer.mapBuffer().asFloatBuffer();
		FloatBufferWrapper wrapper = new FloatBufferWrapper(buffer);

		// head
		{
			// left
			wrapper.putVector3f(-4.1f, 8.2f, -4.1f);
			wrapper.putVector2f(0.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(-4.1f, 0.0f, -4.1f);
			wrapper.putVector2f(0.0f / 64.0f, 16.0f / 64.1f);
			wrapper.putVector3f(-4.1f, 8.2f, 4.1f);
			wrapper.putVector2f(8.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(-4.1f, 0.0f, 4.1f);
			wrapper.putVector2f(8.0f / 64.0f, 16.0f / 64.1f);
			// front
			wrapper.putVector3f(4.1f, 8.2f, 4.1f);
			wrapper.putVector2f(16.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f, 0.0f, 4.1f);
			wrapper.putVector2f(16.0f / 64.0f, 16.0f / 64.1f);
			// right
			wrapper.putVector3f(4.1f, 8.2f, -4.1f);
			wrapper.putVector2f(24.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f, 0.0f, -4.1f);
			wrapper.putVector2f(24.0f / 64.0f, 16.0f / 64.1f);
			// back
			wrapper.putVector3f(-4.1f, 8.2f, -4.1f);
			wrapper.putVector2f(32.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(-4.1f, 0.0f, -4.1f);
			wrapper.putVector2f(32.0f / 64.0f, 16.0f / 64.1f);

			// top
			wrapper.putVector3f(-4.1f, 8.2f, -4.1f);
			wrapper.putVector2f(8.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(-4.1f, 8.2f, 4.1f);
			wrapper.putVector2f(8.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f, 8.2f, -4.1f);
			wrapper.putVector2f(16.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(4.1f, 8.2f, 4.1f);
			wrapper.putVector2f(16.0f / 64.0f, 8.0f / 64.1f);

			// bottom
			wrapper.putVector3f(-4.1f, 0.0f, -4.1f);
			wrapper.putVector2f(16.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(4.1f, 0.0f, -4.1f);
			wrapper.putVector2f(24.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(-4.1f, 0.0f, 4.1f);
			wrapper.putVector2f(16.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f, 0.0f, 4.1f);
			wrapper.putVector2f(24.0f / 64.0f, 8.0f / 64.1f);

			// left wear
			wrapper.putVector3f(-4.1f * 1.1f, 4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(32.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(-4.1f * 1.1f, -4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(32.0f / 64.0f, 16.0f / 64.1f);
			wrapper.putVector3f(-4.1f * 1.1f, 4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(-4.1f * 1.1f, -4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 16.0f / 64.1f);
			// front wear
			wrapper.putVector3f(4.1f * 1.1f, 4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f * 1.1f, -4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 16.0f / 64.1f);
			// right wear
			wrapper.putVector3f(4.1f * 1.1f, 4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(56.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f * 1.1f, -4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(56.0f / 64.0f, 16.0f / 64.1f);
			// back wear
			wrapper.putVector3f(-4.1f * 1.1f, 4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(64.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(-4.1f * 1.1f, -4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(64.0f / 64.0f, 16.0f / 64.1f);

			// top wear
			wrapper.putVector3f(-4.1f * 1.1f, 4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(-4.1f * 1.1f, 4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f * 1.1f, 4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(4.1f * 1.1f, 4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 8.0f / 64.1f);

			// bottom wear
			wrapper.putVector3f(-4.1f * 1.1f, -4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(4.1f * 1.1f, -4.1f * 1.1f + 4.1f, -4.1f * 1.1f);
			wrapper.putVector2f(56.0f / 64.0f, 0.0f / 64.1f);
			wrapper.putVector3f(-4.1f * 1.1f, -4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 8.0f / 64.1f);
			wrapper.putVector3f(4.1f * 1.1f, -4.1f * 1.1f + 4.1f, 4.1f * 1.1f);
			wrapper.putVector2f(56.0f / 64.0f, 8.0f / 64.1f);

		}

		// body
		{
			// left
			wrapper.putVector3f(-4.0f, 12.0f, -2.02f);
			wrapper.putVector2f(16.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-4.0f, 0.0f, -2.02f);
			wrapper.putVector2f(16.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-4.0f, 12.0f, 2.02f);
			wrapper.putVector2f(20.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-4.0f, 0.0f, 2.02f);
			wrapper.putVector2f(20.0f / 64.0f, 32.0f / 64.0f);
			// front
			wrapper.putVector3f(4.0f, 12.0f, 2.02f);
			wrapper.putVector2f(28.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(4.0f, 0.0f, 2.02f);
			wrapper.putVector2f(28.0f / 64.0f, 32.0f / 64.0f);
			// right
			wrapper.putVector3f(4.0f, 12.0f, -2.02f);
			wrapper.putVector2f(32.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(4.0f, 0.0f, -2.02f);
			wrapper.putVector2f(32.0f / 64.0f, 32.0f / 64.0f);
			// back
			wrapper.putVector3f(-4.0f, 12.0f, -2.02f);
			wrapper.putVector2f(40.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-4.0f, 0.0f, -2.02f);
			wrapper.putVector2f(40.0f / 64.0f, 32.0f / 64.0f);

			// top
			wrapper.putVector3f(-4.0f, 12.0f, -2.02f);
			wrapper.putVector2f(20.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(-4.0f, 12.0f, 2.02f);
			wrapper.putVector2f(20.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(4.0f, 12.0f, -2.02f);
			wrapper.putVector2f(28.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(4.0f, 12.0f, 2.02f);
			wrapper.putVector2f(28.0f / 64.0f, 20.0f / 64.0f);

			// bottom
			wrapper.putVector3f(-4.0f, 0.0f, -2.02f);
			wrapper.putVector2f(28.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(4.0f, 0.0f, -2.02f);
			wrapper.putVector2f(36.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(-4.0f, 0.0f, 2.02f);
			wrapper.putVector2f(28.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(4.0f, 0.0f, 2.02f);
			wrapper.putVector2f(36.0f / 64.0f, 20.0f / 64.0f);

			// left wear
			wrapper.putVector3f(-4.0f * 1.1f, 6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(16.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-4.0f * 1.1f, -6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(16.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-4.0f * 1.1f, 6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(20.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-4.0f * 1.1f, -6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(20.0f / 64.0f, 48.0f / 64.0f);
			// front wear
			wrapper.putVector3f(4.0f * 1.1f, 6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(28.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(4.0f * 1.1f, -6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(28.0f / 64.0f, 48.0f / 64.0f);
			// right wear
			wrapper.putVector3f(4.0f * 1.1f, 6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(32.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(4.0f * 1.1f, -6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(32.0f / 64.0f, 48.0f / 64.0f);
			// back wear
			wrapper.putVector3f(-4.0f * 1.1f, 6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-4.0f * 1.1f, -6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 48.0f / 64.0f);

			// top wear
			wrapper.putVector3f(-4.0f * 1.1f, 6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(20.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-4.0f * 1.1f, 6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(20.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(4.0f * 1.1f, 6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(28.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(4.0f * 1.1f, 6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(28.0f / 64.0f, 36.0f / 64.0f);

			// bottom wear
			wrapper.putVector3f(-4.0f * 1.1f, -6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(28.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(4.0f * 1.1f, -6.0f * 1.1f + 6.0f, -2.02f * 1.1f);
			wrapper.putVector2f(36.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-4.0f * 1.1f, -6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(28.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(4.0f * 1.1f, -6.0f * 1.1f + 6.0f, 2.02f * 1.1f);
			wrapper.putVector2f(36.0f / 64.0f, 36.0f / 64.0f);

		}

		// right arm
		{
			// left
			wrapper.putVector3f(-1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(40.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(40.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(44.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(44.0f / 64.0f, 32.0f / 64.0f);
			// front
			wrapper.putVector3f(1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(47.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(47.0f / 64.0f, 32.0f / 64.0f);
			// right
			wrapper.putVector3f(1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(50.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(50.0f / 64.0f, 32.0f / 64.0f);
			// back
			wrapper.putVector3f(-1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(54.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(54.0f / 64.0f, 32.0f / 64.0f);

			// top
			wrapper.putVector3f(-1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(44.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(-1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(44.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(47.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(47.0f / 64.0f, 20.0f / 64.0f);

			// bottom
			wrapper.putVector3f(-1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(47.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(50.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(47.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(50.0f / 64.0f, 20.0f / 64.0f);

			// left wear
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(40.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(44.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(44.0f / 64.0f, 48.0f / 64.0f);
			// front wear
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(47.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(47.0f / 64.0f, 48.0f / 64.0f);
			// right wear
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(50.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(50.0f / 64.0f, 48.0f / 64.0f);
			// back wear
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(54.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(54.0f / 64.0f, 48.0f / 64.0f);

			// top wear
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(44.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(44.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(47.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(47.0f / 64.0f, 36.0f / 64.0f);

			// bottom wear
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(47.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(50.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(47.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(50.0f / 64.0f, 36.0f / 64.0f);

		}

		// left arm
		{
			// left
			wrapper.putVector3f(-1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(32.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(32.0f / 64.0f, 64.0f / 64.0f);
			wrapper.putVector3f(-1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(36.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(36.0f / 64.0f, 64.0f / 64.0f);
			// front
			wrapper.putVector3f(1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(39.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(39.0f / 64.0f, 64.0f / 64.0f);
			// right
			wrapper.putVector3f(1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(42.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(42.0f / 64.0f, 64.0f / 64.0f);
			// back
			wrapper.putVector3f(-1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(46.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(46.0f / 64.0f, 64.0f / 64.0f);

			// top
			wrapper.putVector3f(-1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(36.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(36.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f, 2.0f, -1.5f);
			wrapper.putVector2f(39.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(1.5f, 2.0f, 1.5f);
			wrapper.putVector2f(39.0f / 64.0f, 52.0f / 64.0f);

			// bottom
			wrapper.putVector3f(-1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(39.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, -1.5f);
			wrapper.putVector2f(42.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(39.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f, -10.0f, 1.5f);
			wrapper.putVector2f(42.0f / 64.0f, 52.0f / 64.0f);

			// left wear
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(48.0f / 64.0f, 64.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(52.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(52.0f / 64.0f, 64.0f / 64.0f);
			// front wear
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(55.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(55.0f / 64.0f, 64.0f / 64.0f);
			// right wear
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(59.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(59.0f / 64.0f, 64.0f / 64.0f);
			// back wear
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(62.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(62.0f / 64.0f, 64.0f / 64.0f);

			// top wear
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(52.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(52.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(55.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(55.0f / 64.0f, 52.0f / 64.0f);

			// bottom wear
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(55.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(58.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(55.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(1.5f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(58.0f / 64.0f, 52.0f / 64.0f);

		}

		// right leg
		{
			// left
			wrapper.putVector3f(-2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(0.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(0.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(4.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(4.0f / 64.0f, 32.0f / 64.0f);
			// front
			wrapper.putVector3f(2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(8.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(8.0f / 64.0f, 32.0f / 64.0f);
			// right
			wrapper.putVector3f(2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(12.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(12.0f / 64.0f, 32.0f / 64.0f);
			// back
			wrapper.putVector3f(-2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(16.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(16.0f / 64.0f, 32.0f / 64.0f);

			// top
			wrapper.putVector3f(-2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(4.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(-2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(4.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(8.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(8.0f / 64.0f, 20.0f / 64.0f);

			// bottom
			wrapper.putVector3f(-2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(8.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(12.0f / 64.0f, 16.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(8.0f / 64.0f, 20.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(12.0f / 64.0f, 20.0f / 64.0f);

			// left wear
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(0.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(0.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 48.0f / 64.0f);
			// front wear
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 48.0f / 64.0f);
			// right wear
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 48.0f / 64.0f);
			// back wear
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(16.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(16.0f / 64.0f, 48.0f / 64.0f);

			// top wear
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 36.0f / 64.0f);

			// bottom wear
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 32.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 36.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 36.0f / 64.0f);

		}

		// left leg
		{
			// left
			wrapper.putVector3f(-2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(16.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(16.0f / 64.0f, 64.0f / 64.0f);
			wrapper.putVector3f(-2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(20.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(20.0f / 64.0f, 64.0f / 64.0f);
			// front
			wrapper.putVector3f(2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(24.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(24.0f / 64.0f, 64.0f / 64.0f);
			// right
			wrapper.putVector3f(2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(28.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(28.0f / 64.0f, 64.0f / 64.0f);
			// back
			wrapper.putVector3f(-2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(32.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(32.0f / 64.0f, 64.0f / 64.0f);

			// top
			wrapper.putVector3f(-2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(20.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(20.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f, 2.0f, -2.0f);
			wrapper.putVector2f(24.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(2.0f, 2.0f, 2.0f);
			wrapper.putVector2f(24.0f / 64.0f, 52.0f / 64.0f);

			// bottom
			wrapper.putVector3f(-2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(24.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, -2.0f);
			wrapper.putVector2f(28.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(24.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f, -10.0f, 2.0f);
			wrapper.putVector2f(28.0f / 64.0f, 52.0f / 64.0f);

			// left wear
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(0.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(0.0f / 64.0f, 64.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 64.0f / 64.0f);
			// front wear
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 64.0f / 64.0f);
			// right wear
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 64.0f / 64.0f);
			// back wear
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(16.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(16.0f / 64.0f, 64.0f / 64.0f);

			// top wear
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(4.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, 6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 52.0f / 64.0f);

			// bottom wear
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, -2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 48.0f / 64.0f);
			wrapper.putVector3f(-2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(8.0f / 64.0f, 52.0f / 64.0f);
			wrapper.putVector3f(2.0f * 1.1f, -6.0f * 1.1f - 4.0f, 2.0f * 1.1f);
			wrapper.putVector2f(12.0f / 64.0f, 52.0f / 64.0f);

		}

		this.pointer.unmap();
		this.pointer.postDraw();

		this.boneParameters.put("body_rot_x", 0.0f);
		this.boneParameters.put("body_rot_y", 0.0f);
		this.boneParameters.put("body_rot_z", 0.0f);

		this.boneParameters.put("head_rot_x", 0.0f);
		this.boneParameters.put("head_rot_y", 0.0f);
		this.boneParameters.put("head_rot_z", 0.0f);

		this.boneParameters.put("right_arm_rot_x", 0.0f);
		this.boneParameters.put("right_arm_rot_y", 0.0f);
		this.boneParameters.put("right_arm_rot_z", 0.0f);

		this.boneParameters.put("left_arm_rot_x", 0.0f);
		this.boneParameters.put("left_arm_rot_y", 0.0f);
		this.boneParameters.put("left_arm_rot_z", 0.0f);

		this.boneParameters.put("right_leg_rot_x", 0.0f);
		this.boneParameters.put("right_leg_rot_y", 0.0f);
		this.boneParameters.put("right_leg_rot_z", 0.0f);

		this.boneParameters.put("left_leg_rot_x", 0.0f);
		this.boneParameters.put("left_leg_rot_y", 0.0f);
		this.boneParameters.put("left_leg_rot_z", 0.0f);
		
		this.boneParameters.put("body_offset_x", 0.0f);
		this.boneParameters.put("body_offset_y", 0.0f);
		this.boneParameters.put("body_offset_z", 0.0f);

		this.boneParameters.put("head_offset_x", 0.0f);
		this.boneParameters.put("head_offset_y", 0.0f);
		this.boneParameters.put("head_offset_z", 0.0f);

		this.boneParameters.put("right_arm_offset_x", 0.0f);
		this.boneParameters.put("right_arm_offset_y", 0.0f);
		this.boneParameters.put("right_arm_offset_z", 0.0f);

		this.boneParameters.put("left_arm_offset_x", 0.0f);
		this.boneParameters.put("left_arm_offset_y", 0.0f);
		this.boneParameters.put("left_arm_offset_z", 0.0f);

		this.boneParameters.put("right_leg_offset_x", 0.0f);
		this.boneParameters.put("right_leg_offset_y", 0.0f);
		this.boneParameters.put("right_leg_offset_z", 0.0f);

		this.boneParameters.put("left_leg_offset_x", 0.0f);
		this.boneParameters.put("left_leg_offset_y", 0.0f);
		this.boneParameters.put("left_leg_offset_z", 0.0f);

	}

	@Override
	protected void render(EntityRenderer renderer) {
		Shader shader = renderer.getShader();
		int loc_transMatrix = shader.getUnifromLocation("transMatrix");
		int loc_boneMatrix = shader.getUnifromLocation("boneMatrix");

		this.pointer.initDraw();
		texture.bind();

		// 玩家的位置与朝向
		Matrix4f transMatrix = new Matrix4f();
		transMatrix.setIdentity();
		Matrix4f.translate(new Vector3f(position.x, position.y, position.z), transMatrix, transMatrix);
		Matrix4f.rotate(position.rotX, new Vector3f(1, 0, 0), transMatrix, transMatrix);
		Matrix4f.rotate(position.rotY, new Vector3f(0, 1, 0), transMatrix, transMatrix);
		Matrix4f.rotate(position.rotZ, new Vector3f(0, 0, 1), transMatrix, transMatrix);
		shader.setUniformMatrix(loc_transMatrix, transMatrix);

		Matrix4f bodyBoneMatrix = new Matrix4f();
		bodyBoneMatrix.setIdentity();
		Matrix4f.translate(new Vector3f(boneParameters.get("body_offset_x"), boneParameters.get("body_offset_y"), boneParameters.get("body_offset_z")), bodyBoneMatrix, bodyBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_x")), new Vector3f(1, 0, 0), bodyBoneMatrix,
				bodyBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_y")), new Vector3f(0, 1, 0), bodyBoneMatrix,
				bodyBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_z")), new Vector3f(0, 0, 1), bodyBoneMatrix,
				bodyBoneMatrix);

		Matrix4f headBoneMatrix = new Matrix4f();
		headBoneMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_x")), new Vector3f(1, 0, 0), headBoneMatrix,
				headBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_y")), new Vector3f(0, 1, 0), headBoneMatrix,
				headBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_z")), new Vector3f(0, 0, 1), headBoneMatrix,
				headBoneMatrix);
		Matrix4f.translate(new Vector3f(0, 12, 0), headBoneMatrix, headBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("head_rot_x")), new Vector3f(1, 0, 0), headBoneMatrix,
				headBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("head_rot_y")), new Vector3f(0, 1, 0), headBoneMatrix,
				headBoneMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("head_rot_z")), new Vector3f(0, 0, 1), headBoneMatrix,
				headBoneMatrix);
		shader.setUniformMatrix(loc_boneMatrix, headBoneMatrix);

		Matrix4f rightArmMatrix = new Matrix4f();
		rightArmMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_x")), new Vector3f(1, 0, 0), rightArmMatrix,
				rightArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_y")), new Vector3f(0, 1, 0), rightArmMatrix,
				rightArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_z")), new Vector3f(0, 0, 1), rightArmMatrix,
				rightArmMatrix);
		Matrix4f.translate(new Vector3f(-5.5f, 10, 0), rightArmMatrix, rightArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("right_arm_rot_x")), new Vector3f(1, 0, 0),
				rightArmMatrix, rightArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("right_arm_rot_y")), new Vector3f(0, 1, 0),
				rightArmMatrix, rightArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("right_arm_rot_z")), new Vector3f(0, 0, 1),
				rightArmMatrix, rightArmMatrix);

		Matrix4f leftArmMatrix = new Matrix4f();
		leftArmMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_x")), new Vector3f(1, 0, 0), leftArmMatrix,
				leftArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_y")), new Vector3f(0, 1, 0), leftArmMatrix,
				leftArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("body_rot_z")), new Vector3f(0, 0, 1), leftArmMatrix,
				leftArmMatrix);
		Matrix4f.translate(new Vector3f(5.5f, 10, 0), leftArmMatrix, leftArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("left_arm_rot_x")), new Vector3f(1, 0, 0),
				leftArmMatrix, leftArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("left_arm_rot_y")), new Vector3f(0, 1, 0),
				leftArmMatrix, leftArmMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("left_arm_rot_z")), new Vector3f(0, 0, 1),
				leftArmMatrix, leftArmMatrix);

		Matrix4f rightLegMatrix = new Matrix4f();
		rightLegMatrix.setIdentity();
		Matrix4f.translate(new Vector3f(-2.0f, -2.0f, 0), rightLegMatrix, rightLegMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("right_leg_rot_x")), new Vector3f(1, 0, 0),
				rightLegMatrix, rightLegMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("right_leg_rot_y")), new Vector3f(0, 1, 0),
				rightLegMatrix, rightLegMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("right_leg_rot_z")), new Vector3f(0, 0, 1),
				rightLegMatrix, rightLegMatrix);

		Matrix4f leftLegMatrix = new Matrix4f();
		leftLegMatrix.setIdentity();
		Matrix4f.translate(new Vector3f(2.0f, -2.0f, 0), leftLegMatrix, leftLegMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("left_leg_rot_x")), new Vector3f(1, 0, 0),
				leftLegMatrix, leftLegMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("left_leg_rot_y")), new Vector3f(0, 1, 0),
				leftLegMatrix, leftLegMatrix);
		Matrix4f.rotate((float) Math.toRadians(boneParameters.get("left_leg_rot_z")), new Vector3f(0, 0, 1),
				leftLegMatrix, leftLegMatrix);

		// body
		shader.setUniformMatrix(loc_boneMatrix, bodyBoneMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 36, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 46, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 50, 4);

		// head
		shader.setUniformMatrix(loc_boneMatrix, headBoneMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 10, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 14, 4);

		// right arm
		shader.setUniformMatrix(loc_boneMatrix, rightArmMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 72, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 82, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 86, 4);

		// left arm
		shader.setUniformMatrix(loc_boneMatrix, leftArmMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 108, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 118, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 122, 4);

		// right leg
		shader.setUniformMatrix(loc_boneMatrix, rightLegMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 144, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 154, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 158, 4);

		// left leg
		shader.setUniformMatrix(loc_boneMatrix, leftLegMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 180, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 190, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 194, 4);

		// body wear
		shader.setUniformMatrix(loc_boneMatrix, bodyBoneMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 54, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 64, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 68, 4);

		// head wear
		shader.setUniformMatrix(loc_boneMatrix, headBoneMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 18, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 28, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 32, 4);

		// right arm wear
		shader.setUniformMatrix(loc_boneMatrix, rightArmMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 90, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 100, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 104, 4);

		// left arm wear
		shader.setUniformMatrix(loc_boneMatrix, leftArmMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 126, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 136, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 140, 4);

		// right leg wear
		shader.setUniformMatrix(loc_boneMatrix, rightLegMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 162, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 172, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 176, 4);

		// left leg wear
		shader.setUniformMatrix(loc_boneMatrix, leftLegMatrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 198, 10);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 208, 4);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 212, 4);

		texture.unbind();
		
		this.pointer.postDraw();

	}

}
