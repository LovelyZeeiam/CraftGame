package xueli.craftgame.block;

import java.awt.Color;

import xueli.craftgame.world.Dimension;
import xueli.game.utils.FloatList;

public class AbstractAlphaBlock extends AbstractBlock {

	public AbstractAlphaBlock(String namespace, String nameInternational, String... textureNames) {
		super(namespace, nameInternational, textureNames);

		isAlpha = true;

	}

	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Color color, Dimension dimension) {
		for (byte i = 0; i < 6; i++)
			super.getRenderCubeData(buffer, x, y, z, i, color, dimension);
		return 36;
	}

}
