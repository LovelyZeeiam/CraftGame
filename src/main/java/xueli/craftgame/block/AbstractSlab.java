package xueli.craftgame.block;

import org.lwjgl.utils.vector.Vector2f;

import xueli.craftgame.init.Models;
import xueli.craftgame.model.TexturedModel;
import xueli.craftgame.model.TexturedModelBuilder;
import xueli.craftgame.state.StateWorld;
import xueli.craftgame.world.Dimension;
import xueli.craftgame.world.Tile;
import xueli.game.utils.FloatList;
import xueli.game.utils.Light;
import xueli.game.utils.texture.AtlasTextureHolder;

public class AbstractSlab extends AbstractBlock {

	private TexturedModel up, down;

	public AbstractSlab(String namespace, String nameInternational, String... textureNames) {
		super(namespace, nameInternational, textureNames);

		getTags().add(BlockTags.HAS_PART_UP_AND_PART_DOWN);
		isComplete = false;

		init(textureNames);

	}

	public void init(String[] textureNames) {
		AtlasTextureHolder[] holders = new AtlasTextureHolder[6];
		if (textureNames.length == 6)
			for (int i = 0; i < textureNames.length; i++) {
				holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[i]);
			}
		else if (textureNames.length == 1)
			for (int i = 0; i < 6; i++) {
				holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[0]);
			}
		else {
			throw new RuntimeException("Please input 6 vertices or 1 vertex for all faces!");
		}

		Models models = StateWorld.getInstance().getModels();
		down = new TexturedModelBuilder(models.getModule("cg:slab_down"))
				.add("slab", holders[0].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)), holders[4], holders[5])
				.build("cg:slab_down");
		up = new TexturedModelBuilder(models.getModule("cg:slab_up"))
				.add("slab", holders[0].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)), holders[4], holders[5])
				.build("cg:slab_up");

	}

	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Dimension dimension) {
		Light light = dimension.getLight(x, y, z);

		Tile tile = dimension.getBlock(x, y, z);
		byte part = (byte) tile.getTags().get(BlockTags.TAG_NAME_PART).getValue();

		if (part == BlockFace.PART_DOWN)
			return down.getRenderData(x, y, z, face, buffer);
		else if (part == BlockFace.PART_UP)
			return up.getRenderData(x, y, z, face, buffer);
		return 0;
	}

	@Override
	public int getRenderModelViewData(FloatList buffer) {
		int v = 0;
		for (byte f = 0; f < 6; f++) {
			v += down.getRenderData(0, 0.5f, 0, f, buffer);
		}
		return v;
	}

}
