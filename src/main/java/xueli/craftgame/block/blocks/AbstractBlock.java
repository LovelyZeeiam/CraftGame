package xueli.craftgame.block.blocks;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.renderer.model.TexturedModelBuilder;
import xueli.craftgame.state.StateWorld;
import xueli.game.utils.texture.AtlasTextureHolder;

public class AbstractBlock extends BlockBase {
	
	private String[] textureNames;
	
	public AbstractBlock(String namespace, String nameInternational, String... textureNames) {
		super(namespace);

		// TODO: Dynamic Loading for Language File
		this.nameInternational = nameInternational;
		
		this.textureNames = textureNames;
		try {
			initModel();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void initModel() {
		AtlasTextureHolder[] holders = new AtlasTextureHolder[textureNames.length];
		for (int i = 0; i < textureNames.length; i++) {
			holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[i]);
		}
		
		model = new TexturedModelBuilder(StateWorld.getInstance().getModels().getModule("cg:little_cube")).add("cube", holders).build("cg:little_cube");
		
	}

}
