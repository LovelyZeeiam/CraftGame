package xueli.craftgame.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Logger;

import xueli.craftgame.block.AbstractLightBlock;
import xueli.craftgame.block.BlockBase;
import xueli.game.vector.Vector3b;

public class LightingEngine {

	private final static Logger logger = Logger.getLogger(LightingEngine.class.getName());

	public static void light(int x, int y, int z, Dimension dimension) {
		Tile tile = dimension.getBlock(x, y, z);
		if (tile == null) {
			logger.info("[Light] Error: Air block! " + x + ", " + y + ", " + z);
			return;
		}

		BlockBase base = tile.getBase();
		if (!(base instanceof AbstractLightBlock)) {
			logger.info("[Light] Error: Not Light block! " + x + ", " + y + ", " + z);
			return;
		}
		AbstractLightBlock lightBlock = (AbstractLightBlock) base;
		Vector3b initialRGB = lightBlock.getLightRGB();

		// BFS
		HashSet<Chunk> referredChunks = new HashSet<>();

		ArrayList<LightNode> hasCalcR = new ArrayList<>();
		LinkedList<LightNode> nodesR = new LinkedList<>();

		nodesR.add(new LightNode(x, y, z, initialRGB.x));

		while (true) {
			if (nodesR.isEmpty())
				break;

			LightNode nodeR = nodesR.poll();
			if (nodeR.value != 0) {
				if (!hasCalcR.contains(new LightNode(nodeR.x - 1, nodeR.y, nodeR.z, (byte) 0))) {
					nodesR.add(new LightNode(nodeR.x - 1, nodeR.y, nodeR.z, (byte) (nodeR.value - 1)));
					hasCalcR.add(new LightNode(nodeR.x - 1, nodeR.y, nodeR.z, (byte) (nodeR.value - 1)));
				}
				if (!hasCalcR.contains(new LightNode(nodeR.x + 1, nodeR.y, nodeR.z, (byte) 0))) {
					nodesR.add(new LightNode(nodeR.x + 1, nodeR.y, nodeR.z, (byte) (nodeR.value - 1)));
					hasCalcR.add(new LightNode(nodeR.x + 1, nodeR.y, nodeR.z, (byte) (nodeR.value - 1)));
				}
				if (!hasCalcR.contains(new LightNode(nodeR.x, nodeR.y - 1, nodeR.z, (byte) 0))) {
					nodesR.add(new LightNode(nodeR.x, nodeR.y - 1, nodeR.z, (byte) (nodeR.value - 1)));
					hasCalcR.add(new LightNode(nodeR.x, nodeR.y - 1, nodeR.z, (byte) (nodeR.value - 1)));
				}
				if (!hasCalcR.contains(new LightNode(nodeR.x, nodeR.y + 1, nodeR.z, (byte) 0))) {
					nodesR.add(new LightNode(nodeR.x, nodeR.y + 1, nodeR.z, (byte) (nodeR.value - 1)));
					hasCalcR.add(new LightNode(nodeR.x, nodeR.y + 1, nodeR.z, (byte) (nodeR.value - 1)));
				}
				if (!hasCalcR.contains(new LightNode(nodeR.x, nodeR.y, nodeR.z - 1, (byte) 0))) {
					nodesR.add(new LightNode(nodeR.x, nodeR.y, nodeR.z - 1, (byte) (nodeR.value - 1)));
					hasCalcR.add(new LightNode(nodeR.x, nodeR.y, nodeR.z - 1, (byte) (nodeR.value - 1)));
				}
				if (!hasCalcR.contains(new LightNode(nodeR.x, nodeR.y, nodeR.z + 1, (byte) 0))) {
					nodesR.add(new LightNode(nodeR.x, nodeR.y, nodeR.z + 1, (byte) (nodeR.value - 1)));
					hasCalcR.add(new LightNode(nodeR.x, nodeR.y, nodeR.z + 1, (byte) (nodeR.value - 1)));
				}

			}

		}

		hasCalcR.forEach(a -> {
			if (dimension.getLight(a.x, a.y, a.z) != null)
				dimension.getLight(a.x, a.y, a.z).setR(a.value);
			referredChunks.add(dimension.getChunk(a.x >> 4, a.y >> 4, a.z >> 4));
		});

		referredChunks.forEach(c -> {
			if (c != null)
				c.getBuffer().reportRebuild();
		});

	}

	private static class LightNode {

		private int x, y, z;
		private byte value;

		public LightNode(int x, int y, int z, byte value) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LightNode other = (LightNode) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			if (z != other.z)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "LightNode [x=" + x + ", y=" + y + ", z=" + z + ", value=" + value + "]";
		}

	}

}
