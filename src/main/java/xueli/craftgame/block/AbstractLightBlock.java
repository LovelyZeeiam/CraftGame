package xueli.craftgame.block;

import java.awt.Color;

public class AbstractLightBlock extends AbstractBlock {

	private Color lightColor;
	
	public AbstractLightBlock(String namespace, String nameInternational, Color lightColor, String... textureNames) {
		super(namespace, nameInternational, textureNames);
		
		getTags().add(BlockTags.LIGHT_BLOCK);
		this.lightColor = lightColor;
		
	}
	
	public Color getLightColor() {
		return lightColor;
	}

}
