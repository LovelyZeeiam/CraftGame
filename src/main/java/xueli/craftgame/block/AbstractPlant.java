package xueli.craftgame.block;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.renderer.model.CubeDrawer;
import xueli.craftgame.state.StateWorld;
import xueli.craftgame.world.Dimension;
import xueli.game.utils.FloatList;
import xueli.game.utils.Light;
import xueli.game.utils.texture.AtlasTextureHolder;

public class AbstractPlant extends AbstractBlock {

	private AtlasTextureHolder holder;

	public AbstractPlant(String namespace, String nameInternational, String textureName) {
		super(namespace, nameInternational,
				new String[] { textureName, textureName, textureName, textureName, textureName, textureName });

		isComplete = false;
		isAlpha = true;

		this.holder = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureName);

	}

	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Dimension dimension) {
		Light light = dimension == null ? Light.FULL_LIGHT : dimension.getLight(x, y, z);
		CubeDrawer.drawQuad(buffer,new Vector3f(-1, 0, 1), new Vector3f(x + 0.2f, y + 0.8f, z + 0.2f), holder.p_left_top,
				light.getLightBuffer(),
				new Vector3f(x + 0.8f, y + 0.8f, z + 0.8f), holder.p_right_top,
				light.getLightBuffer(),
				new Vector3f(x + 0.2f, y + 0, z + 0.2f), holder.p_left_down,
				light.getLightBuffer(),
				new Vector3f(x + 0.8f, y + 0, z + 0.8f), holder.p_right_down,
				light.getLightBuffer());
		CubeDrawer.drawQuad(buffer,new Vector3f(1, 0, 1), new Vector3f(x + 0.8f, y + 0.8f, z + 0.2f), holder.p_left_top,
				light.getLightBuffer(),
				new Vector3f(x + 0.2f, y + 0.8f, z + 0.8f), holder.p_right_top,
				light.getLightBuffer(),
				new Vector3f(x + 0.8f, y + 0, z + 0.2f), holder.p_left_down,
				light.getLightBuffer(),
				new Vector3f(x + 0.2f, y + 0, z + 0.8f), holder.p_right_down,
				light.getLightBuffer());
		return 12;
	}

}
