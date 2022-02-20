package xueli.craftgame.entity;

import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;
import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockFace;
import xueli.craftgame.world.Dimension;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.math.MousePicker;

public class PlayerPicker {

	private float maxTouchDistance;
	private Player player;

	private Dimension dimension;

	private Vector3i lastSelectedBlock = null, selectedBlock = null;
	private byte faceTo = -1, blockPart = -1;
	private Vector3f lastRayVector = null;

	public PlayerPicker(Player player, float maxTouchDistance) {
		this.player = player;
		this.maxTouchDistance = maxTouchDistance;
		this.dimension = player.getDimension();

	}

	public void tick() {
		selectedBlock = null;
		lastSelectedBlock = null;

		MousePicker picker = new MousePicker(player.pos, MatrixHelper.lastTimeProjMatrix,
				MatrixHelper.lastTimeViewMatrix);
		for (float d = 0; d <= maxTouchDistance; d += 0.1f) {
			Vector3f p = picker.getPointOnRay(d);
			Vector3i pb = new Vector3i(p);

			if (dimension.getBlock(pb.getX(), pb.getY(), pb.getZ()) != null) {
				selectedBlock = new Vector3i(p);
				lastSelectedBlock = lastRayVector != null ? new Vector3i(lastRayVector) : null;

				BlockBase tile = dimension.getBlock(selectedBlock.getX(), selectedBlock.getY(), selectedBlock.getZ());
				tile.getListener().onLookAt(selectedBlock.getX(), selectedBlock.getY(), selectedBlock.getZ(),
						tile, dimension, player);

				if (lastRayVector != null) {
					if (lastRayVector.getY() - lastSelectedBlock.getY() > 0.5f)
						blockPart = BlockFace.PART_UP;
					else
						blockPart = BlockFace.PART_DOWN;
				}

				break;
			}
			lastRayVector = p;
		}

		if (player.pos.rotY >= 45 && player.pos.rotY <= 135)
			faceTo = BlockFace.LEFT;
		else if (player.pos.rotY > 135 && player.pos.rotY < 225)
			faceTo = BlockFace.FRONT;
		else if (player.pos.rotY >= 225 && player.pos.rotY <= 315)
			faceTo = BlockFace.RIGHT;
		else
			faceTo = BlockFace.BACK;

	}

	public float getMaxTouchDistance() {
		return maxTouchDistance;
	}

	public Player getPlayer() {
		return player;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Vector3i getLastSelectedBlock() {
		return lastSelectedBlock;
	}

	public Vector3i getSelectedBlock() {
		return selectedBlock;
	}

	public byte getFaceTo() {
		return faceTo;
	}

	public byte getBlockPart() {
		return blockPart;
	}

	public Vector3f getLastRayVector() {
		return lastRayVector;
	}

}
