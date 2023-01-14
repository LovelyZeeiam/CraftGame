package xueli.mcremake.registry;

import java.util.HashMap;

import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2i;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game2.math.MatrixHelper;
import xueli.game2.renderer.legacy.FrameBuffer;
import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.renderer.world.ChunkRenderBuildManager;
import xueli.mcremake.client.renderer.world.ChunkRenderType;
import xueli.mcremake.core.block.BlockType;

public class BlockIconGenerator implements ResourceHolder {
	
	public static final int FRAME_BUFFER_SIZE = 256;
	
	private static final Matrix4f viewMatrix, projMatrix;

	static {
		projMatrix = MatrixHelper.ortho(-0.9f, 0.9f, -0.9f, 0.9f, 0.01f, 1000000.0f);
		viewMatrix = MatrixHelper.lookAt(new Vector3f(4 + (float) (Math.random() * 2.0f - 1.0f) * 0.4f,
				3.5f + (float) (Math.random() * 2.0f - 1.0f) * 0.4f, 4 + (float) (Math.random() * 2.0f - 1.0f) * 0.4f),
				new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0, -1, 0));
	}
	
	private final CraftGameClient ctx;
	
	private final HashMap<ResourceLocation, FrameBuffer> icons = new HashMap<>();
	
	public BlockIconGenerator(CraftGameClient ctx) {
		this.ctx = ctx;
		
	}

	@Override
	public void reload() {
		this.clear();
		this.genBlockIcons();
	}
	
	private void clear() {
		icons.values().forEach(b -> {
			b.delete();
		});
		icons.clear();
	}
	
	private void genBlockIcons() {
		BlockRenderTypes renderTypes = new BlockRenderTypes(ctx.getRenderResources());
		
		for (ChunkRenderType type : renderTypes.values()) {
			type.applyMatrix("viewMatrix", viewMatrix);
			type.applyMatrix("projMatrix", projMatrix);
		}
		
		GameRegistry.BUILTIN_BLOCK_REGISTRY.getAllContainTag(GameRegistry.TAG_GENERIC_BLOCK).forEach(name -> {
			BlockType block = GameRegistry.BUILTIN_BLOCK_REGISTRY.getByName(name);
			ChunkRenderBuildManager manager = new ChunkRenderBuildManager(new Vector2i(0, 0)) {
				@Override
				public <T extends ChunkRenderType> T getRenderType(Class<T> clazz) {
					return renderTypes.get(clazz);
				}
			};
			block.renderer().renderIcon(manager);
			manager.flip();
			
			FrameBuffer frameBuffer = new FrameBuffer(FRAME_BUFFER_SIZE, FRAME_BUFFER_SIZE);
			frameBuffer.bind();
			for (ChunkRenderType type : renderTypes.values()) {
				type.render();
				type.clear();
			}
			frameBuffer.unbind();
//			frameBuffer.save("./" + name.location() + ".png");
			
		});
		
	}
	
}
