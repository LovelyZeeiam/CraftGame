package xueli.craftgame.world.generate;

import java.util.ArrayList;

import xueli.craftgame.block.Tile;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.utils.vector.Vector2i;

public class GeneratorForest implements IChunkGenerator {

	@Override
	public void generate(Chunk chunk) {

	}

	@Override
	public void postGenerate(World world, Chunk chunk) {
		// 高度图
		int[][] heightMap = chunk.heightMap.clone();

		// 生成树的地方们
		ArrayList<Vector2i> treePoses = new ArrayList<>();

		for (int i = 0; i < 14; i++) {
			int posX = (int) (1 + 14 * Math.random());
			int posZ = (int) (1 + 14 * Math.random());

			// 检测这个地方是不是已经生成过了树木
			boolean hasGenerateTree = false;

			if (treePoses.contains(new Vector2i(posX, posZ)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX, posZ + 1)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX, posZ - 1)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX + 1, posZ)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX + 1, posZ + 1)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX + 1, posZ - 1)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX - 1, posZ)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX - 1, posZ + 1)))
				hasGenerateTree = true;
			else if (treePoses.contains(new Vector2i(posX - 1, posZ - 1)))
				hasGenerateTree = true;
			if (hasGenerateTree) {
				i--;
				continue;
			}

			treePoses.add(new Vector2i(posX, posZ));

			// 生成树木
			int rootY = heightMap[posX][posZ];

			if (chunk.getBlock(posX, rootY - 1, posZ) != null
					&& chunk.getBlock(posX, rootY - 1, posZ).getData().getNamespace().equals("craftgame:water"))
				continue;

			chunk.setBlock(posX, rootY + 1, posZ, new Tile("craftgame:oak_log", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 2, posZ, new Tile("craftgame:oak_log", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 3, posZ, new Tile("craftgame:oak_log", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 4, posZ, new Tile("craftgame:oak_log", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 5, posZ, new Tile("craftgame:oak_log", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 6, posZ, new Tile("craftgame:oak_log", world.getWorldLogic()));

			chunk.setBlock(posX, rootY + 7, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 7, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 7, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 1, rootY + 7, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 7, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));

			chunk.setBlock(posX + 1, rootY + 6, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 6, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 6, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 1, rootY + 6, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 6, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 1, rootY + 6, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 6, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 6, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));

			chunk.setBlock(posX + 1, rootY + 5, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 5, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 5, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 1, rootY + 5, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 5, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 1, rootY + 5, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 5, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 5, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));

			chunk.setBlock(posX + 2, rootY + 5, posZ + 2, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 2, rootY + 5, posZ + 2, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 5, posZ + 2, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 2, rootY + 5, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 2, rootY + 5, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 2, rootY + 5, posZ - 2, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 2, rootY + 5, posZ - 2, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 5, posZ - 2, new Tile("craftgame:oak_leave", world.getWorldLogic()));

			chunk.setBlock(posX + 1, rootY + 4, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 4, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 4, posZ + 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 1, rootY + 4, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 4, posZ, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX + 1, rootY + 4, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX - 1, rootY + 4, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));
			chunk.setBlock(posX, rootY + 4, posZ - 1, new Tile("craftgame:oak_leave", world.getWorldLogic()));

		}

	}

}
