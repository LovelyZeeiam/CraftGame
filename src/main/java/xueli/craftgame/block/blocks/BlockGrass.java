package xueli.craftgame.block.blocks;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockDefination;
import xueli.craftgame.renderer.model.TexturedModel;
import xueli.craftgame.state.StateWorld;

@BlockDefination
public class BlockGrass extends BlockBase {

	public BlockGrass() {
		this.namespace = "craftgame:grass";

		// TODO: Dynamic Loading for Language File
		this.nameInternational = "Grass Block";

		model = TexturedModel.getFullCubeModel(
				StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:grass_block_side"),
				StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:grass_block_side"),
				StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:grass_block_side"),
				StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:grass_block_side"),
				StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:grass_block_top"),
				StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:dirt"));

	}

}
