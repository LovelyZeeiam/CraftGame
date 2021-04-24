package xueli.craftgame.block.blocks;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockDefination;
import xueli.craftgame.renderer.model.TexturedModel;
import xueli.craftgame.state.StateWorld;

@BlockDefination
public class BlockDirt extends BlockBase {

	public BlockDirt() {
		this.namespace = "craftgame:dirt";

		// TODO: Dynamic Loading for Language File
		this.nameInternational = "Dirt";

		model = TexturedModel
				.getFullCubeModel(StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:dirt"));

	}

}
