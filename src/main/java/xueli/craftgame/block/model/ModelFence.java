package xueli.craftgame.block.model;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import com.google.gson.JsonObject;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

public class ModelFence extends IModel {
	
	private static float fence_bridge_x = 0.45f;

    public ModelFence(JsonObject renderArgs) {
        super(renderArgs);
    }

    @Override
    public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face, TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
    	 int vertCount = 0;
    	 
    	 vertCount += modelFenceBasic.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
    	 
    	 if(world != null) {
	    	 if(world.hasBlock(new Vector3i(x, y, z - 1)) && (world.getBlock(x, y, z - 1).getData().getNamespace().endsWith(".fence") ||world.getBlock(x, y, z - 1).getModel().isCompleteBlock(world))) {
	    		 vertCount += modelFenceFrontAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
	    	 }
	    	 if(world.hasBlock(new Vector3i(x, y, z + 1)) && (world.getBlock(x, y, z + 1).getData().getNamespace().endsWith(".fence") ||world.getBlock(x, y, z + 1).getModel().isCompleteBlock(world))) {
	    		 vertCount += modelFenceBackAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
	    	 }
	    	 if(world.hasBlock(new Vector3i(x - 1, y, z)) && (world.getBlock(x - 1, y, z).getData().getNamespace().endsWith(".fence") ||world.getBlock(x - 1, y, z).getModel().isCompleteBlock(world))) {
	    		 vertCount += modelFenceLeftAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
	    	 }
	    	 if(world.hasBlock(new Vector3i(x + 1, y, z)) && (world.getBlock(x + 1, y, z).getData().getNamespace().endsWith(".fence") ||world.getBlock(x + 1, y, z).getModel().isCompleteBlock(world))) {
	    		 vertCount += modelFenceLeftAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
	    	 }
    	 }
    		 
         return vertCount;
    }
    
    @Override
    public boolean isCompleteBlock(World world) {
    	return false;
    }
	
	private static IModel modelFenceFrontAttach = new IModel(null) {
		
		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x + fence_bridge_x) / blockTextureAtlas.width;
            float v1 = (float) (textureVector2s.y + 0.4f) / blockTextureAtlas.height;
            float u2 = (float) (textureVector2s.x + 1 - fence_bridge_x) / blockTextureAtlas.width;
            float v2 = (float) (textureVector2s.y + 0.55f) / blockTextureAtlas.height;
            
            float u3 = (float) (textureVector2s.x) / blockTextureAtlas.width;
            float u4 = (float) (textureVector2s.x + 0.4f) / blockTextureAtlas.width;
            
            float v4 = (float) (textureVector2s.y + 0.05f) / blockTextureAtlas.height;
            float v5 = (float) (textureVector2s.y + 0.2f) / blockTextureAtlas.height;
            
            float v6 = (float) (textureVector2s.y + fence_bridge_x) / blockTextureAtlas.height;
            float v7 = (float) (textureVector2s.y + 1 - fence_bridge_x) / blockTextureAtlas.height;
            
            switch (face) {
            case BlockFace.FRONT:
                drawQuadFacingFrontOrLeft(buffer,
                		new Vector3f(x + fence_bridge_x, y + 0.45f, z), new Vector2f(u1, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.45f, z), new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.6f, z), new Vector2f(u1, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.6f, z), new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                drawQuadFacingFrontOrLeft(buffer,
                		new Vector3f(x + fence_bridge_x, y + 0.8f, z), new Vector2f(u1, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.8f, z), new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.95f, z), new Vector2f(u1, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.95f, z), new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f));
            case BlockFace.RIGHT:
            	drawQuadFacingBackOrRight(buffer,
            			new Vector3f(x + 1 - fence_bridge_x, y + 0.45f, z), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.6f, z), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.45f, z + 0.4f), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.6f, z + 0.4f), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f));
            	drawQuadFacingBackOrRight(buffer,
            			new Vector3f(x + 1 - fence_bridge_x, y + 0.8f, z), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.95f, z), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.8f, z + 0.4f), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 1 - fence_bridge_x, y + 0.95f, z + 0.4f), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                break;
            case BlockFace.BACK:
                return 0;
            case BlockFace.LEFT:
            	drawQuadFacingFrontOrLeft(buffer,
            			new Vector3f(x + fence_bridge_x, y + 0.45f, z), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.6f, z), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.45f, z + 0.4f), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.6f, z + 0.4f), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f));
            	drawQuadFacingFrontOrLeft(buffer,
            			new Vector3f(x + fence_bridge_x, y + 0.8f, z), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.95f, z), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.8f, z + 0.4f), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + fence_bridge_x, y + 0.95f, z + 0.4f), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                break;
            case BlockFace.TOP:
            	drawQuadFacingTop(buffer,
                 		new Vector3f(x + fence_bridge_x, y + 0.95f, z), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.95f, z), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + fence_bridge_x, y + 0.95f, z + 0.4f), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.95f, z + 0.4f), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
            	drawQuadFacingTop(buffer,
                 		new Vector3f(x + fence_bridge_x, y + 0.6f, z), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.6f, z), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + fence_bridge_x, y + 0.6f, z + 0.4f), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.6f, z + 0.4f), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                break;
            case BlockFace.BOTTOM:
            	drawQuadFacingBottom(buffer,
                 		new Vector3f(x + fence_bridge_x, y + 0.8f, z), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.8f, z), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + fence_bridge_x, y + 0.8f, z + 0.4f), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.8f, z + 0.4f), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f));
            	drawQuadFacingBottom(buffer,
                 		new Vector3f(x + fence_bridge_x, y + 0.45f, z), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.45f, z), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + fence_bridge_x, y + 0.45f, z + 0.4f), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 1 - fence_bridge_x, y + 0.45f, z + 0.4f), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f));
                break;
            }
            
	        return 12;
		}
		
	};
    
	private static IModel modelFenceBackAttach = new IModel(null) {
		
		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			Vector2s textureVector2s = data.getTextures()[face];

            float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
            float u2 = (float) (textureVector2s.x + 0.4f) / blockTextureAtlas.width;
            float v2 = (float) (textureVector2s.y + 0.4f) / blockTextureAtlas.height;
            float u3 = (float) (textureVector2s.x + 0.6f) / blockTextureAtlas.width;
            float v3 = (float) (textureVector2s.y + 0.6f) / blockTextureAtlas.height;
            float v4 = (float) (textureVector2s.y + 1.0f) / blockTextureAtlas.height;
            
            
	        return 0;
		}
		
	};
	
	private static IModel modelFenceLeftAttach = new IModel(null) {
	
		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			Vector2s textureVector2s = data.getTextures()[face];

            float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
            float u2 = (float) (textureVector2s.x + 0.4f) / blockTextureAtlas.width;
            float v2 = (float) (textureVector2s.y + 0.4f) / blockTextureAtlas.height;
            float u3 = (float) (textureVector2s.x + 0.6f) / blockTextureAtlas.width;
            float v3 = (float) (textureVector2s.y + 0.6f) / blockTextureAtlas.height;
            float v4 = (float) (textureVector2s.y + 1.0f) / blockTextureAtlas.height;
            
            
	        return 0;
		}
		
	};
	
	private static IModel modelFenceRightAttach = new IModel(null) {
		
		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			Vector2s textureVector2s = data.getTextures()[face];

            float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
            float u2 = (float) (textureVector2s.x + 0.4f) / blockTextureAtlas.width;
            float v2 = (float) (textureVector2s.y + 0.4f) / blockTextureAtlas.height;
            float u3 = (float) (textureVector2s.x + 0.6f) / blockTextureAtlas.width;
            float v3 = (float) (textureVector2s.y + 0.6f) / blockTextureAtlas.height;
            float v4 = (float) (textureVector2s.y + 1.0f) / blockTextureAtlas.height;
            
            
	        return 0;
		}
		
	};
	
    private static IModel modelFenceBasic = new IModel(null) {
    	
    	@Override
    	public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
    			TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
    		Vector2s textureVector2s = data.getTextures()[face];

            float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
            float u2 = (float) (textureVector2s.x + 0.4f) / blockTextureAtlas.width;
            float v2 = (float) (textureVector2s.y + 0.4f) / blockTextureAtlas.height;
            float u3 = (float) (textureVector2s.x + 0.6f) / blockTextureAtlas.width;
            float v3 = (float) (textureVector2s.y + 0.6f) / blockTextureAtlas.height;
            float v4 = (float) (textureVector2s.y + 1.0f) / blockTextureAtlas.height;
            
            switch (face) {
            case BlockFace.FRONT:
                drawQuadFacingFrontOrLeft(buffer,
                		new Vector3f(x + 0.4f, y, z + 0.4f), new Vector2f(u2, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 0.6f, y, z + 0.4f), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 0.4f, y + 1, z + 0.4f), new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                		new Vector3f(x + 0.6f, y + 1, z + 0.4f), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                break;
            case BlockFace.RIGHT:
            	drawQuadFacingBackOrRight(buffer,
                 		new Vector3f(x + 0.6f, y, z + 0.4f), new Vector2f(u3, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                 		new Vector3f(x + 0.6f, y + 1, z + 0.4f), new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f),
                 		new Vector3f(x + 0.6f, y, z + 0.6f), new Vector2f(u2, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                 		new Vector3f(x + 0.6f, y + 1, z + 0.6f), new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f));
                break;
            case BlockFace.BACK:
            	drawQuadFacingBackOrRight(buffer,
                		new Vector3f(x + 0.4f, y, z + 0.6f), new Vector2f(u2, v4), new Vector3f(0.5f, 0.5f, 0.5f),
                		new Vector3f(x + 0.6f, y, z + 0.6f), new Vector2f(u3, v4), new Vector3f(0.5f, 0.5f, 0.5f),
                		new Vector3f(x + 0.4f, y + 1, z + 0.6f), new Vector2f(u2, v1), new Vector3f(0.5f, 0.5f, 0.5f),
                		new Vector3f(x + 0.6f, y + 1, z + 0.6f), new Vector2f(u3, v1), new Vector3f(0.5f, 0.5f, 0.5f));
                break;
            case BlockFace.LEFT:
            	drawQuadFacingFrontOrLeft(buffer,
                 		new Vector3f(x + 0.4f, y, z + 0.4f), new Vector2f(u2, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                 		new Vector3f(x + 0.4f, y + 1, z + 0.4f), new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f),
                 		new Vector3f(x + 0.4f, y, z + 0.6f), new Vector2f(u3, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                 		new Vector3f(x + 0.4f, y + 1, z + 0.6f), new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
                break;
            case BlockFace.TOP:
            	drawQuadFacingTop(buffer,
                 		new Vector3f(x + 0.4f, y + 1, z + 0.4f), new Vector2f(u2, v2), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 0.6f, y + 1, z + 0.4f), new Vector2f(u2, v3), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 0.4f, y + 1, z + 0.6f), new Vector2f(u3, v2), new Vector3f(1.0f, 1.0f, 1.0f),
                 		new Vector3f(x + 0.6f, y + 1, z + 0.6f), new Vector2f(u3, v3), new Vector3f(1.0f, 1.0f, 1.0f));
                break;
            case BlockFace.BOTTOM:
            	drawQuadFacingBottom(buffer,
                 		new Vector3f(x + 0.4f, y, z + 0.4f), new Vector2f(u2, v3), new Vector3f(0.4f, 0.4f, 0.4f),
                 		new Vector3f(x + 0.6f, y, z + 0.4f), new Vector2f(u2, v2), new Vector3f(0.4f, 0.4f, 0.4f),
                 		new Vector3f(x + 0.4f, y, z + 0.6f), new Vector2f(u3, v3), new Vector3f(0.4f, 0.4f, 0.4f),
                 		new Vector3f(x + 0.6f, y, z + 0.6f), new Vector2f(u3, v2), new Vector3f(0.4f, 0.4f, 0.4f));
                break;
            }
            
            return 6;
    	}
    	
    };

}
