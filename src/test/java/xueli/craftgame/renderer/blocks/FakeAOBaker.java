package xueli.craftgame.renderer.blocks;

import xueli.craftgame.block.BlockFace;
import xueli.craftgame.world.World;

public class FakeAOBaker {

	private static final float[] COVERS = { 1.0f, 0.7f, 0.55f };

	public static float[] bake(int x, int y, int z, byte face, World dimension) {
		float[] values = new float[4];
		switch (face) {
		case BlockFace.TOP -> {
			boolean flag1 = dimension.getBlock(x, y + 1, z - 1) != null;
			boolean flag2 = dimension.getBlock(x - 1, y + 1, z) != null;
			boolean flag3 = dimension.getBlock(x + 1, y + 1, z) != null;
			boolean flag4 = dimension.getBlock(x, y + 1, z + 1) != null;
			
			boolean flag5 = dimension.getBlock(x - 1, y + 1, z - 1) != null;
			boolean flag6 = dimension.getBlock(x + 1, y + 1, z - 1) != null;
			boolean flag7 = dimension.getBlock(x - 1, y + 1, z + 1) != null;
			boolean flag8 = dimension.getBlock(x + 1, y + 1, z + 1) != null;

			values[0] = COVERS[Math.max(trueCount(flag1, flag2), trueCount(flag5))];
			values[1] = COVERS[Math.max(trueCount(flag1, flag3), trueCount(flag6))];
			values[2] = COVERS[Math.max(trueCount(flag2, flag4), trueCount(flag7))];
			values[3] = COVERS[Math.max(trueCount(flag3, flag4), trueCount(flag8))];

		}
		case BlockFace.BOTTOM -> {
			boolean flag1 = dimension.getBlock(x, y - 1, z - 1) != null;
			boolean flag2 = dimension.getBlock(x - 1, y - 1, z) != null;
			boolean flag3 = dimension.getBlock(x + 1, y - 1, z) != null;
			boolean flag4 = dimension.getBlock(x, y - 1, z + 1) != null;
			
			boolean flag5 = dimension.getBlock(x - 1, y - 1, z - 1) != null;
			boolean flag6 = dimension.getBlock(x + 1, y - 1, z - 1) != null;
			boolean flag7 = dimension.getBlock(x - 1, y - 1, z + 1) != null;
			boolean flag8 = dimension.getBlock(x + 1, y - 1, z + 1) != null;

			values[0] = COVERS[Math.max(trueCount(flag1, flag2), trueCount(flag5))];
			values[1] = COVERS[Math.max(trueCount(flag1, flag3), trueCount(flag6))];
			values[2] = COVERS[Math.max(trueCount(flag4, flag2), trueCount(flag7))];
			values[3] = COVERS[Math.max(trueCount(flag4, flag3), trueCount(flag8))];

		}
		case BlockFace.FRONT -> {
			boolean flag1 = dimension.getBlock(x - 1, y, z - 1) != null;
			boolean flag2 = dimension.getBlock(x + 1, y, z - 1) != null;
			boolean flag3 = dimension.getBlock(x, y - 1, z - 1) != null;
			boolean flag4 = dimension.getBlock(x, y + 1, z - 1) != null;
			
			boolean flag5 = dimension.getBlock(x - 1, y - 1, z - 1) != null;
			boolean flag6 = dimension.getBlock(x + 1, y - 1, z - 1) != null;
			boolean flag7 = dimension.getBlock(x - 1, y + 1, z - 1) != null;
			boolean flag8 = dimension.getBlock(x + 1, y + 1, z - 1) != null;

			values[0] = COVERS[Math.max(trueCount(flag1, flag3), trueCount(flag5))];
			values[1] = COVERS[Math.max(trueCount(flag2, flag3), trueCount(flag6))];
			values[2] = COVERS[Math.max(trueCount(flag1, flag4), trueCount(flag7))];
			values[3] = COVERS[Math.max(trueCount(flag2, flag4), trueCount(flag8))];

		}
		case BlockFace.BACK -> {
			boolean flag1 = dimension.getBlock(x - 1, y, z + 1) != null;
			boolean flag2 = dimension.getBlock(x + 1, y, z + 1) != null;
			boolean flag3 = dimension.getBlock(x, y - 1, z + 1) != null;
			boolean flag4 = dimension.getBlock(x, y + 1, z + 1) != null;

			boolean flag5 = dimension.getBlock(x - 1, y - 1, z + 1) != null;
			boolean flag6 = dimension.getBlock(x + 1, y - 1, z + 1) != null;
			boolean flag7 = dimension.getBlock(x - 1, y + 1, z + 1) != null;
			boolean flag8 = dimension.getBlock(x + 1, y + 1, z + 1) != null;
			
			values[0] = COVERS[Math.max(trueCount(flag1, flag3), trueCount(flag5))];
			values[1] = COVERS[Math.max(trueCount(flag2, flag3), trueCount(flag6))];
			values[2] = COVERS[Math.max(trueCount(flag1, flag4), trueCount(flag7))];
			values[3] = COVERS[Math.max(trueCount(flag2, flag4), trueCount(flag8))];

		}
		case BlockFace.LEFT -> {
			boolean flag1 = dimension.getBlock(x - 1, y, z - 1) != null;
			boolean flag2 = dimension.getBlock(x - 1, y, z + 1) != null;
			boolean flag3 = dimension.getBlock(x - 1, y - 1, z) != null;
			boolean flag4 = dimension.getBlock(x - 1, y + 1, z) != null;
			
			boolean flag5 = dimension.getBlock(x - 1, y - 1, z - 1) != null;
			boolean flag6 = dimension.getBlock(x - 1, y - 1, z + 1) != null;
			boolean flag7 = dimension.getBlock(x - 1, y + 1, z - 1) != null;
			boolean flag8 = dimension.getBlock(x - 1, y + 1, z + 1) != null;

			values[0] = COVERS[Math.max(trueCount(flag1, flag3), trueCount(flag5))];
			values[1] = COVERS[Math.max(trueCount(flag1, flag4), trueCount(flag7))];
			values[2] = COVERS[Math.max(trueCount(flag2, flag3), trueCount(flag6))];
			values[3] = COVERS[Math.max(trueCount(flag2, flag4), trueCount(flag8))];

		}
		case BlockFace.RIGHT -> {
			boolean flag1 = dimension.getBlock(x + 1, y, z - 1) != null;
			boolean flag2 = dimension.getBlock(x + 1, y, z + 1) != null;
			boolean flag3 = dimension.getBlock(x + 1, y - 1, z) != null;
			boolean flag4 = dimension.getBlock(x + 1, y + 1, z) != null;
			
			boolean flag5 = dimension.getBlock(x + 1, y - 1, z - 1) != null;
			boolean flag6 = dimension.getBlock(x + 1, y - 1, z + 1) != null;
			boolean flag7 = dimension.getBlock(x + 1, y + 1, z - 1) != null;
			boolean flag8 = dimension.getBlock(x + 1, y + 1, z + 1) != null;

			values[0] = COVERS[Math.max(trueCount(flag1, flag3), trueCount(flag5))];
			values[1] = COVERS[Math.max(trueCount(flag1, flag4), trueCount(flag7))];
			values[2] = COVERS[Math.max(trueCount(flag2, flag3), trueCount(flag6))];
			values[3] = COVERS[Math.max(trueCount(flag2, flag4), trueCount(flag8))];

		}

		};

		return values;
	}

	private static int trueCount(boolean... bs) {
		int count = 0;
		for (int i = 0; i < bs.length; i++) {
			boolean b = bs[i];
			if (b)
				count++;
		}
		return count;
	}

}
