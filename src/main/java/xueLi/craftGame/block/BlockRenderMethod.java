package xueLi.craftGame.block;

import java.nio.FloatBuffer;

public class BlockRenderMethod {
	
	//为材质包工作
	public static float TEXTURES_SIZE = 16;

	/**
	 * 仅绘制方块形状 不上材质
	 */
	public static void drawDefaultBlockToBuffer(FloatBuffer buffer, int x, int y, int z, int face) {
		switch (face) {
		case 0:
			buffer.put(x).put(y).put(z);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(x + 1).put(y + 1).put(z);
			break;
		case 1:
			buffer.put(x + 1).put(y).put(z);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			break;
		case 2:
			buffer.put(x).put(y).put(z + 1);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			break;
		case 3:
			buffer.put(x).put(y).put(z);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(x).put(y + 1).put(z + 1);
			break;
		case 4:
			buffer.put(x).put(y + 1).put(z);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			break;
		case 5:
			buffer.put(x).put(y).put(z);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(x + 1).put(y).put(z + 1);
			break;
		}

	}

	//绘制方块边框
	public static void drawDefaultBlockFrame(FloatBuffer buffer, int x, int y, int z) {
		buffer.put(x).put(y).put(z);
		buffer.put(x).put(y + 1).put(z);
		buffer.put(x + 1).put(y).put(z);
		buffer.put(x + 1).put(y + 1).put(z);

		buffer.put(x + 1).put(y).put(z);
		buffer.put(x + 1).put(y + 1).put(z);
		buffer.put(x + 1).put(y).put(z + 1);
		buffer.put(x + 1).put(y + 1).put(z + 1);

		buffer.put(x).put(y).put(z + 1);
		buffer.put(x).put(y + 1).put(z + 1);
		buffer.put(x + 1).put(y).put(z + 1);
		buffer.put(x + 1).put(y + 1).put(z + 1);

		buffer.put(x).put(y).put(z);
		buffer.put(x).put(y + 1).put(z);
		buffer.put(x).put(y).put(z + 1);
		buffer.put(x).put(y + 1).put(z + 1);

		buffer.put(x).put(y + 1).put(z);
		buffer.put(x + 1).put(y + 1).put(z);
		buffer.put(x).put(y + 1).put(z + 1);
		buffer.put(x + 1).put(y + 1).put(z + 1);

		buffer.put(x).put(y).put(z);
		buffer.put(x + 1).put(y).put(z);
		buffer.put(x).put(y).put(z + 1);
		buffer.put(x + 1).put(y).put(z + 1);
	}

	// 此处的x，y是从0开始数的              不会改（笑哭）
	public static int bindDefaultToBuffer(FloatBuffer buffer, int xInTexture, int yInTexture, int x, int y, int z, int face) {
		float u1 = (float) xInTexture / TEXTURES_SIZE;
		float v1 = (float) yInTexture / TEXTURES_SIZE;
		float u2 = (float) (xInTexture + 1) / TEXTURES_SIZE;
		float v2 = (float) (yInTexture + 1) / TEXTURES_SIZE;

		switch (face) {
		case 0:
			buffer.put(u1).put(v2);
			// buffer.put(-1).put(-1).put(-1);
			buffer.put(x).put(y).put(z);
			buffer.put(u1).put(v1);
			// buffer.put(-1).put(1).put(-1);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			// buffer.put(1).put(-1).put(-1);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u1).put(v1);
			// buffer.put(-1).put(1).put(-1);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v1);
			// buffer.put(1).put(1).put(-1);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			// buffer.put(1).put(-1).put(-1);
			buffer.put(x + 1).put(y).put(z);
			break;
		case 1:
			buffer.put(u1).put(v2);
			// buffer.put(1).put(-1).put(-1);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u1).put(v1);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v1);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(x + 1).put(y).put(z + 1);
			break;
		case 2:
			buffer.put(u1).put(v2);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u2).put(v1);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			break;
		case 3:
			buffer.put(u1).put(v2);
			buffer.put(x).put(y).put(z);
			buffer.put(u2).put(v2);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u1).put(v1);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u2).put(v1);
			buffer.put(x).put(y + 1).put(z + 1);
			break;
		case 4:
			buffer.put(u1).put(v2);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u1).put(v1);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u2).put(v1);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			break;
		case 5:
			buffer.put(u1).put(v2);
			buffer.put(x).put(y).put(z);
			buffer.put(u1).put(v1);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u2).put(v2);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u2).put(v1);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(x).put(y).put(z + 1);
			break;
		}

		return 6;
	}

}
