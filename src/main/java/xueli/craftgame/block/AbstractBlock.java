package xueli.craftgame.block;

import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

import xueli.craftgame.model.CubeDrawer;
import xueli.craftgame.state.StateWorld;
import xueli.craftgame.world.Dimension;
import xueli.craftgame.world.FakeAOBaker;
import xueli.game.utils.FloatList;
import xueli.game.utils.texture.AtlasTextureHolder;

public class AbstractBlock extends BlockBase {

	private static final float[] ALL_WHITE = { 1, 1, 1, 1 };

	protected String[] textureNames;
	private AtlasTextureHolder[] holders;

	public AbstractBlock(String namespace, String nameInternational, String... textureNames) {
		super(namespace);

		// TODO: Dynamic Loading for Language File
		this.nameInternational = nameInternational;

		this.textureNames = textureNames;
		try {
			initModel();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initModel() throws Exception {
		this.holders = new AtlasTextureHolder[6];
		if (textureNames.length == 6)
			for (int i = 0; i < 6; i++) {
				holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[i]);
			}
		else if (textureNames.length == 1)
			for (int i = 0; i < 6; i++) {
				holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[0]);
			}
		else
			throw new Exception("Not supported texture count: " + textureNames.length);

	}

	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Dimension dimension) {
		AtlasTextureHolder texHolder = this.holders[face];

		float[] aoColor = dimension == null ? ALL_WHITE : FakeAOBaker.bake(x, y, z, face, dimension);
		Vector4f c1 = new Vector4f(aoColor[0], aoColor[0], aoColor[0], 1.0f),
				c2 = new Vector4f(aoColor[1], aoColor[1], aoColor[1], 1.0f),
				c3 = new Vector4f(aoColor[2], aoColor[2], aoColor[2], 1.0f),
				c4 = new Vector4f(aoColor[3], aoColor[3], aoColor[3], 1.0f);

		switch (face) {
		case BlockFace.FRONT: {
			CubeDrawer.drawQuadFacingFront(buffer, new Vector3f(x, y, z), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y, z), texHolder.p_right_down, c2, new Vector3f(x, y + 1, z),
					texHolder.p_left_top, c3, new Vector3f(x + 1, y + 1, z), texHolder.p_right_top, c4);
			break;
		}
		case BlockFace.RIGHT: {
			CubeDrawer.drawQuadFacingRight(buffer, new Vector3f(x + 1, y, z), texHolder.p_right_down, c1,
					new Vector3f(x + 1, y + 1, z), texHolder.p_right_top, c2, new Vector3f(x + 1, y, z + 1),
					texHolder.p_left_down, c3, new Vector3f(x + 1, y + 1, z + 1), texHolder.p_left_top, c4);
			break;
		}
		case BlockFace.BACK: {
			CubeDrawer.drawQuadFacingBack(buffer, new Vector3f(x, y, z + 1), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y, z + 1), texHolder.p_right_down, c2, new Vector3f(x, y + 1, z + 1),
					texHolder.p_left_top, c3, new Vector3f(x + 1, y + 1, z + 1), texHolder.p_right_top, c4);
			break;
		}
		case BlockFace.LEFT: {
			CubeDrawer.drawQuadFacingLeft(buffer, new Vector3f(x, y, z), texHolder.p_left_down, c1,
					new Vector3f(x, y + 1, z), texHolder.p_left_top, c2, new Vector3f(x, y, z + 1),
					texHolder.p_right_down, c3, new Vector3f(x, y + 1, z + 1), texHolder.p_right_top, c4);
			break;
		}
		case BlockFace.TOP: {
			CubeDrawer.drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y + 1, z), texHolder.p_left_top, c2, new Vector3f(x, y + 1, z + 1),
					texHolder.p_right_down, c3, new Vector3f(x + 1, y + 1, z + 1), texHolder.p_right_top, c4);
			break;
		}
		case BlockFace.BOTTOM: {
			CubeDrawer.drawQuadFacingBottom(buffer, new Vector3f(x, y, z), texHolder.p_left_down, c1,
					new Vector3f(x + 1, y, z), texHolder.p_left_top, c2, new Vector3f(x, y, z + 1),
					texHolder.p_right_down, c3, new Vector3f(x + 1, y, z + 1), texHolder.p_right_top, c4);
			break;
		}
		}

		return 6;
	}

}
