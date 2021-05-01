package xueli.craftgame.block.blocks;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.renderer.model.TexturedModel;
import xueli.craftgame.state.StateWorld;
import xueli.game.utils.texture.AtlasTextureHolder;

public class AbstractBlock extends BlockBase {
	
	public AbstractBlock(String namespace, String nameInternational, String... textureNames) {
		this.namespace = namespace;

		// TODO: Dynamic Loading for Language File
		this.nameInternational = nameInternational;
		
		AtlasTextureHolder[] holders = new AtlasTextureHolder[textureNames.length];
		for (int i = 0; i < textureNames.length; i++) {
			holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[i]);
		}
		
		model = TexturedModel.getFullCubeModel(holders);

	}

}
