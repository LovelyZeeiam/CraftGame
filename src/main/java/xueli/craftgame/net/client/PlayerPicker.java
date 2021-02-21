package xueli.craftgame.net.client;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.CraftGame;
import xueli.craftgame.block.Block;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.block.data.LeftClick;
import xueli.craftgame.block.data.RightClick;
import xueli.craftgame.net.event.EventSetblock;
import xueli.craftgame.net.message.Message;
import xueli.craftgame.world.World;
import xueli.gamengine.utils.math.MousePicker;

public class PlayerPicker {

	private ClientPlayer player;
	private World world;

	private Vector3f lastTimeRayEnd;
	private Vector3i selected_block_pos = new Vector3i(0, 0, 0), last_time_selected_block_pos = new Vector3i(0, 0, 0);
	private byte place_block_face_to;

	private PlayerPickerTimer timer;

	public PlayerPicker(ClientPlayer player) {
		this.player = player;
		this.world = player.getClient().getWorld();
		this.timer = new PlayerPickerTimer();

	}

	public void pickTick() {
		selected_block_pos = null;
		MousePicker picker = new MousePicker(player.getClientPlayerPos(),
				player.getLogic().getWorldRenderer().getProjMatrix(),
				player.getLogic().getWorldRenderer().getViewMatrix());
		for (float distance = 0.0f; distance < 8.0f; distance += 0.05f) {
			Vector3f rayEnd = picker.getPointOnRay(distance);
			Vector3i rayEndBlock = new Vector3i(rayEnd);
			if (world.hasBlock(rayEndBlock.getX(), rayEndBlock.getY(), rayEndBlock.getZ())) {
				selected_block_pos = rayEndBlock;

				float delta_x = lastTimeRayEnd.getX() - rayEnd.getX();
				float delta_z = lastTimeRayEnd.getZ() - rayEnd.getZ();
				int delta_x_int = last_time_selected_block_pos.getX() - rayEndBlock.getX();
				int delta_z_int = last_time_selected_block_pos.getZ() - rayEndBlock.getZ();

				if (delta_x_int > 0 && delta_z_int == 0)
					place_block_face_to = BlockFace.RIGHT;
				else if (delta_x_int < 0 && delta_z_int == 0)
					place_block_face_to = BlockFace.LEFT;
				else if (delta_z_int > 0 && delta_x_int == 0)
					place_block_face_to = BlockFace.BACK;
				else if (delta_z_int < 0 && delta_x_int == 0)
					place_block_face_to = BlockFace.FRONT;
				else if (delta_x - Math.abs(delta_z) > 0)
					place_block_face_to = BlockFace.RIGHT;
				else if (delta_x + Math.abs(delta_z) < 0)
					place_block_face_to = BlockFace.LEFT;
				else if (delta_z - Math.abs(delta_x) > 0)
					place_block_face_to = BlockFace.BACK;
				else if (delta_z + Math.abs(delta_x) < 0)
					place_block_face_to = BlockFace.FRONT;
				else
					place_block_face_to = BlockFace.FRONT;

				break;
			}

			last_time_selected_block_pos = rayEndBlock;
			lastTimeRayEnd = rayEnd;

		}

		timer.tick();

	}

	public void onRightClick() {
		if (selected_block_pos != null) {
			Block block = world.getBlock(selected_block_pos.getX(), selected_block_pos.getY(),
					selected_block_pos.getZ());
			if (timer.canPlaceBlock()
					&& block.getData().getListener().onRightClick(selected_block_pos.getX(), selected_block_pos.getY(),
							selected_block_pos.getZ(), world) == RightClick.PLACE_BLOCK_WHEN_RIGHT_CLICK) {
				EventSetblock event = new EventSetblock(last_time_selected_block_pos.getX(),
						last_time_selected_block_pos.getY(), last_time_selected_block_pos.getZ(),
						new Block("craftgame:stone"), this.player.getClient().getId(),
						CraftGame.INSTANCE_CRAFT_GAME.getPlayerStat());
				player.getClient().send(Message.generateEventMessage(event));

			}

		}

	}

	public void onLeftClick() {
		if (selected_block_pos != null) {
			Block block = world.getBlock(selected_block_pos.getX(), selected_block_pos.getY(),
					selected_block_pos.getZ());
			if (timer.canPlaceBlock()
					&& block.getData().getListener().onLeftClick(selected_block_pos.getX(), selected_block_pos.getY(),
							selected_block_pos.getZ(), world) == LeftClick.DESTROY_BLOCK_WHEN_LEFT_CLICK) {
				EventSetblock event = new EventSetblock(selected_block_pos.getX(), selected_block_pos.getY(),
						selected_block_pos.getZ(), null, this.player.getClient().getId(),
						CraftGame.INSTANCE_CRAFT_GAME.getPlayerStat());
				player.getClient().send(Message.generateEventMessage(event));

			}

		}

	}

}
