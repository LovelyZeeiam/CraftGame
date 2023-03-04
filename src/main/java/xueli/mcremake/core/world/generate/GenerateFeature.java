package xueli.mcremake.core.world.generate;

import java.util.Random;

import xueli.mcremake.core.world.WorldAccessible;

public interface GenerateFeature {
	
	public void doFeature(Random random, WorldAccessible world);
	
}
