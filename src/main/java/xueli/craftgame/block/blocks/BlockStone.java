package xueli.craftgame.block.blocks;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockDefination;
import xueli.craftgame.renderer.model.TexturedModel;
import xueli.craftgame.state.StateWorld;

@BlockDefination
public class BlockStone extends BlockBase {

	public BlockStone() {
		this.namespace = "craftgame:stone";

		// TODO: Dynamic Loading for Language File
		this.nameInternational = "Stone";
		
		model = TexturedModel.getFullCubeModel(
				StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder("cg:stone")
		);
		
	}

}
