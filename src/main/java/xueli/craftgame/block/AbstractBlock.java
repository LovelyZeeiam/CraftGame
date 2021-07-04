package xueli.craftgame.block;

import xueli.craftgame.model.TexturedModelBuilder;
import xueli.craftgame.state.StateWorld;
import xueli.game.utils.texture.AtlasTextureHolder;

public class AbstractBlock extends BlockBase {

	protected String[] textureNames;

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

		model = new TexturedModelBuilder(StateWorld.getInstance().getModels().getModule("cg:cube")).add("cube", holders)
				.build("cg:cube");

	}

}
