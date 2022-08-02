package xueli.craftgame.player;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.BlockFace;
import xueli.craftgame.block.BlockListener.Type;
import xueli.craftgame.client.renderer.display.KeyInputListener;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.event.EventBlockListener;
import xueli.craftgame.event.EventInventorySlotChosenMove;
import xueli.craftgame.event.EventLocalPlayerPosSync;
import xueli.craftgame.event.EventRaiseGUI;
import xueli.craftgame.event.EventSetBlock;
import xueli.craftgame.renderer.view.InventoryView;
import xueli.craftgame.utils.OperationDuration;
import xueli.craftgame.world.World;
import xueli.game.input.InputHolder;
import xueli.game.input.InputManager;
import xueli.game.player.FirstPersonCamera;
import xueli.game.utils.math.MousePicker;
import xueli.game.vector.Vector;

// TODO: Extend a entity class
public class LocalPlayer implements KeyInputListener {

	private CraftGameContext ctx;
	private InputHolder inputHolder;
	private PlayerInfo info;

	private FirstPersonCamera player;
	private IViewPerspective viewController = ViewPerspectives.FIRST_PERSON.getPerspective();
	private Vector camPos = new Vector();

	private Float fov = 90.0f;
	private float maxTouchDistance = 8.0f;

	private Inventory inventory;

	public LocalPlayer(CraftGameContext ctx) {
		this.ctx = ctx;

		this.info = ctx.getPlayerInfo();
		this.inputHolder = ctx.getInputManager().createInputHolder(InputManager.WEIGH_PLAYER_CONTROL);
		this.player = new FirstPersonCamera(0, 0, 0, inputHolder);

		this.inventory = new Inventory(this);

		this.inputHolder.addListener(this);

	}

	// Player Operation "LeFt AnD RiGhT"
	private OperationDuration leftClickTimer = new OperationDuration(200), rightClickTimer = new OperationDuration(200);
	private Vector3i selectedBlock, lastSelectedBlock;
	private Vector3f lastRayVector;
	private byte blockPart, faceTo;

	private OperationDuration positionSyncTimer = new OperationDuration(50);

	public void tick() {
		player.tick();
		camPos = viewController.getViewPerspective(this);

		mousePick();

		if (inputHolder.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			if (leftClickTimer.check()) {
				leftMouseClick();
			}
		} else {
			leftClickTimer.relieve();
		}

		if (inputHolder.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
			if (rightClickTimer.check()) {
				rightMouseClick();
			}
		} else {
			rightClickTimer.relieve();
		}

		int wheelDelta = (int) inputHolder.getWheelDelta();
		if (wheelDelta > 0)
			ctx.submitEvent(new EventInventorySlotChosenMove(inventory.getFrontInventoryChosen() - 1));
		else if (wheelDelta < 0)
			ctx.submitEvent(new EventInventorySlotChosenMove(inventory.getFrontInventoryChosen() + 1));

		if (positionSyncTimer.check()) {
			Vector playerPos = player.getPos();
			ctx.submitEventToMainTicker(new EventLocalPlayerPosSync(playerPos.x, playerPos.y, playerPos.z));
		}

		if (inputHolder.isKeyDownOnce(GLFW.GLFW_KEY_E)) {
			if (!ctx.getViewRenderer().isViewRaised(InventoryView.class)) {
				ctx.submitEvent(new EventRaiseGUI(InventoryView.class));
			}
		}

	}

	private void leftMouseClick() {
		if (inventory.leftClick()) {
			if (selectedBlock != null)
				ctx.submitEventToMainTicker(
						new EventSetBlock(selectedBlock.x, selectedBlock.y, selectedBlock.z, (BlockType) null, null));
		}
	}

	private void rightMouseClick() {
		inventory.rightClick();
	}

	private boolean shouldCallLookAtToListener = true;

	private void mousePick() {
		World dimension = ctx.getWorld();
		MousePicker picker = new MousePicker(player.getPos(), ctx.getWorldRenderer().getProjMatrix(),
				ctx.getWorldRenderer().getViewMatrix());

		// Store the last selected block to tell whether the targeted block changed
		Vector3i selectedBlockPrevious = this.selectedBlock;

		selectedBlock = lastSelectedBlock = null;

		if (picker.valid()) {
			for (float d = 0; d <= maxTouchDistance; d += 0.1f) {
				Vector3f p = picker.getPointOnRay(d);
				Vector3i pb = new Vector3i(p);

				if (dimension.getBlock(pb.getX(), pb.getY(), pb.getZ()) != null) {
					selectedBlock = new Vector3i(p);
					lastSelectedBlock = lastRayVector != null ? new Vector3i(lastRayVector) : null;

					if (shouldCallLookAtToListener) {
						ctx.submitEventToMainTicker(
								new EventBlockListener(pb.getX(), pb.getY(), pb.getZ(), Type.LOOK_AT, null));
						shouldCallLookAtToListener = false;
					}

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

			if (selectedBlock == null)
				lastSelectedBlock = null;

			if (selectedBlockPrevious != null && !selectedBlockPrevious.equals(this.selectedBlock)) {
				shouldCallLookAtToListener = true;
			}

			if (player.getPos().rotY >= 45 && player.getPos().rotY <= 135)
				faceTo = BlockFace.LEFT;
			else if (player.getPos().rotY > 135 && player.getPos().rotY < 225)
				faceTo = BlockFace.FRONT;
			else if (player.getPos().rotY >= 225 && player.getPos().rotY <= 315)
				faceTo = BlockFace.RIGHT;
			else
				faceTo = BlockFace.BACK;

		}

	}

	@Override
	public void onInput(int key, int scancode, int action, int mods) {
		if (action == GLFW.GLFW_PRESS) {
			int keyNumDetect = key - GLFW.GLFW_KEY_0;
			if (keyNumDetect > 0 && keyNumDetect <= Inventory.FRONT_INVENTORY) {
				ctx.submitEvent(new EventInventorySlotChosenMove(keyNumDetect - 1));
			}
		}

	}

	public void onInventorySlotChosenMove(EventInventorySlotChosenMove event) {
		int destSlot = event.get();
		destSlot = Math.floorMod(destSlot, Inventory.SLOT_COUNT);
		inventory.setFrontInventoryChosen(Math.floorMod(destSlot, Inventory.FRONT_INVENTORY));

	}

	public FirstPersonCamera getPlayer() {
		return player;
	}

	public Vector getCamera() {
		return camPos;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public CraftGameContext getContext() {
		return ctx;
	}

	public PlayerInfo getInfo() {
		return info;
	}

	public Vector3i getSelectedBlock() {
		return selectedBlock;
	}

	public byte getPlaceBlockPart() {
		return blockPart;
	}

	public byte getPlaceFaceTo() {
		return faceTo;
	}

	public Vector3i getLastSelectedBlock() {
		return lastSelectedBlock;
	}

	public Float getFov() {
		return fov;
	}

}
