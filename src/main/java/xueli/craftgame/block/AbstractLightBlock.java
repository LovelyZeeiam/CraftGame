package xueli.craftgame.block;

import xueli.game.vector.Vector3b;

public class AbstractLightBlock extends AbstractBlock {

	private Vector3b lightRGB;
	
	public AbstractLightBlock(String namespace, String nameInternational, Vector3b lightRGB, String... textureNames) {
		super(namespace, nameInternational, textureNames);
		
		getTags().add(BlockTags.LIGHT_BLOCK);
		this.lightRGB = lightRGB;
		
	}
	
	public Vector3b getLightRGB() {
		return lightRGB;
	}

}
