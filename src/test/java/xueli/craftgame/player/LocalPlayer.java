package xueli.craftgame.player;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.BlockFace;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.block.Blocks;
import xueli.craftgame.event.EventSetBlock;
import xueli.craftgame.server.PlayerInfo;
import xueli.craftgame.utils.OperationDuration;
import xueli.craftgame.world.World;
import xueli.game.player.FirstPersonCamera;
import xueli.game.utils.math.MousePicker;
import xueli.game.vector.Vector;

// TODO: Extend a entity class
public class LocalPlayer {

	private CraftGameContext ctx;
	private PlayerInfo info;

	private FirstPersonCamera player;
	private IViewPerspective viewController = ViewPerspectives.FIRST_PERSON.getPerspective();
	private Vector camPos = new Vector();
	
	private Float fov = 90.0f;
	private float maxTouchDistance = 8.0f;
	
	public LocalPlayer(FirstPersonCamera player, CraftGameContext ctx) {
		this.ctx = ctx;
		this.player = player;
		
		this.info = ctx.getPlayerInfo();
		
	}
	
	// Player Operation "LeFt AnD RiGhT"
	private OperationDuration leftClickTimer = new OperationDuration(200), rightClickTimer = new OperationDuration(200);
	private Vector3i selectedBlock, lastSelectedBlock;
	private Vector3f lastRayVector;
	private byte blockPart, faceTo;
	
	public void tick() {
		player.tick();
		camPos = viewController.getViewPerspective(this);
		
		mousePick();
		
		if (ctx.getDisplay().isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			if(leftClickTimer.check()) {
				leftMouseClick();
			}
		} else {
			leftClickTimer.relieve();
		}
		
		if (ctx.getDisplay().isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
			if(rightClickTimer.check()) {
				rightMouseClick();
			}
		} else {
			rightClickTimer.relieve();
		}
		
	}
	
	private void leftMouseClick() {
		if(selectedBlock != null)
			ctx.submitEventToMainTicker(new EventSetBlock(selectedBlock.x, selectedBlock.y, selectedBlock.z, (BlockType) null));
		
	}
	
	private void rightMouseClick() {
		if(lastSelectedBlock != null)
			ctx.submitEventToMainTicker(new EventSetBlock(lastSelectedBlock.x, lastSelectedBlock.y, lastSelectedBlock.z, Blocks.BLOCK_STONE));
		
	}
	
	private void mousePick() {
		World dimension = ctx.getWorld();
		MousePicker picker = new MousePicker(player.getPos(), ctx.getWorldRenderer().getProjMatrix(), ctx.getWorldRenderer().getViewMatrix());
		
		selectedBlock = lastSelectedBlock = null;
		
		for (float d = 0; d <= maxTouchDistance; d += 0.1f) {
			Vector3f p = picker.getPointOnRay(d);
			Vector3i pb = new Vector3i(p);

			if (dimension.getBlock(pb.getX(), pb.getY(), pb.getZ()) != null) {
				selectedBlock = new Vector3i(p);
				lastSelectedBlock = lastRayVector != null ? new Vector3i(lastRayVector) : null;
				
				// TODO: Block Listener
				/*
				 * BlockType tile = dimension.getBlock(selectedBlock.getX(),
				 * selectedBlock.getY(), selectedBlock.getZ());
				 * tile.getListener().onLookAt(selectedBlock.getX(), selectedBlock.getY(),
				 * selectedBlock.getZ(), tile, dimension, player);
				 */
				
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
		
		if(selectedBlock == null)
			lastSelectedBlock = null;
		
		if (player.getPos().rotY >= 45 && player.getPos().rotY <= 135)
			faceTo = BlockFace.LEFT;
		else if (player.getPos().rotY > 135 && player.getPos().rotY < 225)
			faceTo = BlockFace.FRONT;
		else if (player.getPos().rotY >= 225 && player.getPos().rotY <= 315)
			faceTo = BlockFace.RIGHT;
		else
			faceTo = BlockFace.BACK;
		
	}

	public FirstPersonCamera getPlayer() {
		return player;
	}

	public Vector getCamera() {
		return camPos;
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

	public Float getFov() {
		return fov;
	}

}
