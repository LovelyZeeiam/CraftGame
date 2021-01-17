package xueli.craftgame.world.generate;

import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;

public interface IChunkGenerator {

	public void generate(Chunk chunk);

	public void postGenerate(World world, Chunk chunk);

}
