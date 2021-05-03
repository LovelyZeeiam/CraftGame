package xueli.craftgame.block;

public class AbstractAlphaBlock extends AbstractBlock {

	public AbstractAlphaBlock(String namespace, String nameInternational, String... textureNames) {
		super(namespace, nameInternational, textureNames);
		
		isAlpha = true;
		
	}

}
