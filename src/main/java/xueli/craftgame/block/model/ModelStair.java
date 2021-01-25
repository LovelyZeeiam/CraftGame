package xueli.craftgame.block.model;

import com.google.gson.JsonObject;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.block.data.SlabAndStairData;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

import java.util.ArrayList;

public class ModelStair extends IModel {

    public ModelStair(JsonObject renderArgs) {
        super(renderArgs);
    }

    @Override
    public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face, TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
    	Vector2s textureVector2s = data.getTextures()[face];
    	
    	float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
        float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
        float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
        float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
        float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
        float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;
    	
        if(params == null) {
        	switch (face) {
			case BlockFace.FRONT:
				buffer.put(u1).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z);
                buffer.put(u3).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z);
                
                buffer.put(u1).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z + 0.5f);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 0.5f);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 0.5f);
                buffer.put(u3).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
                
                return 12;
			case BlockFace.BACK:
				buffer.put(u1).put(v3);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u2).put(v3);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u2).put(v3);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z + 1);
                return 6;
			case BlockFace.LEFT:
				buffer.put(u1).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y).put(z);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u3).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z + 1);
                
                buffer.put(u1).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z + 0.5f);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 0.5f);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 0.5f);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 0.5f).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 1);
                
				return 12;
			case BlockFace.RIGHT:
				buffer.put(u1).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z);
                buffer.put(u3).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                buffer.put(u3).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z + 1);
                
                buffer.put(u1).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                buffer.put(u2).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                
				return 12;
			case BlockFace.TOP:
				buffer.put(u2).put(v3);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 1).put(z + 0.5f);
                buffer.put(u3).put(v3);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                buffer.put(u2).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                buffer.put(u3).put(v3);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u3).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 1).put(z + 1);
                
                buffer.put(u2).put(v3);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 0.5f).put(z);
                buffer.put(u3).put(v3);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 0.5f).put(z + 0.5f);
                buffer.put(u2).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 0.5f).put(z);
                buffer.put(u2).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 0.5f).put(z);
                buffer.put(u3).put(v3);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 0.5f).put(z + 0.5f);
                buffer.put(u3).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
				
				return 12;
			case BlockFace.BOTTOM:
				buffer.put(u1).put(v3);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u3).put(v3);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u3).put(v1);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u3).put(v3);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x).put(y).put(z + 1);
                
				return 6;
			}
        	
        }
        else if(params.slabOrStairData == SlabAndStairData.DOWN) {
    		switch (params.faceTo) {
			case BlockFace.FRONT:
				switch (face) {
				case BlockFace.FRONT:
					buffer.put(u1).put(v2);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x).put(y).put(z);
	                buffer.put(u1).put(v1);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x).put(y + 0.5f).put(z);
	                buffer.put(u3).put(v2);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x + 1).put(y).put(z);
	                buffer.put(u1).put(v1);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x).put(y + 0.5f).put(z);
	                buffer.put(u3).put(v1);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x + 1).put(y + 0.5f).put(z);
	                buffer.put(u3).put(v2);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x + 1).put(y).put(z);
	                
	                buffer.put(u1).put(v2);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x).put(y + 0.5f).put(z + 0.5f);
	                buffer.put(u1).put(v1);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x).put(y + 1).put(z + 0.5f);
	                buffer.put(u3).put(v2);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
	                buffer.put(u1).put(v1);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x).put(y + 1).put(z + 0.5f);
	                buffer.put(u3).put(v1);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x + 1).put(y + 1).put(z + 0.5f);
	                buffer.put(u3).put(v2);
	                buffer.put(0.7f).put(0.7f).put(0.7f);
	                buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
	                
	                return 12;
				case BlockFace.BACK:
					buffer.put(u1).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u2).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1).put(z + 1);
                    buffer.put(u2).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u2).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1).put(z + 1);
	                return 6;
				case BlockFace.LEFT:
					buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u3).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 0.5f);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1).put(z + 0.5f);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1).put(z + 0.5f);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u2).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1).put(z + 1);
                    
					return 12;
				case BlockFace.RIGHT:
					buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                    buffer.put(u2).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1).put(z + 1);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    
					return 12;
				case BlockFace.TOP:
					buffer.put(u2).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 1).put(z + 0.5f);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 1).put(z + 1);
                    buffer.put(u2).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                    buffer.put(u2).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 1).put(z + 0.5f);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 1).put(z + 1);
                    buffer.put(u3).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 1).put(z + 1);
                    
                    buffer.put(u2).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 0.5f).put(z + 0.5f);
                    buffer.put(u2).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u2).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 0.5f).put(z + 0.5f);
                    buffer.put(u3).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
					
					return 12;
				case BlockFace.BOTTOM:
					buffer.put(u1).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y).put(z);
                    buffer.put(u3).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u3).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y).put(z + 1);
                    
					return 6;
				}
				break;
				
			
			}
    	} else if(params.slabOrStairData == SlabAndStairData.UP) {
    		switch (face) {
			case BlockFace.FRONT:
				
				break;
			case BlockFace.BACK:
				
				break;
			case BlockFace.LEFT:
				
				break;
			case BlockFace.RIGHT:
				
				break;
			case BlockFace.TOP:
				
				break;
			case BlockFace.BOTTOM:
				
				break;
			}
    	}
    	
    	return 0;
    }
    
    @Override
    public boolean isAlpha(World world) {
    	return false;
    }
    
    @Override
    public boolean isCompleteBlock(World world) {
    	return false;
    }

    @Override
    public ArrayList<AABB> getAabbs(BlockParameters params) {
        ArrayList<AABB> aabbs = new ArrayList<>();

        if (params.slabOrStairData == SlabAndStairData.DOWN) {
            aabbs.add(new AABB(0, 1, 0, 0.5f, 0, 1));
            switch (params.faceTo) {
                case BlockFace.FRONT:
                    aabbs.add(new AABB(0,1,0.5f, 1, 0.5f, 1));
                    break;
                case BlockFace.BACK:
                    aabbs.add(new AABB(0,1,0.5f, 1, 0, 0.5f));
                    break;
                case BlockFace.LEFT:
                    aabbs.add(new AABB(0.5f,1,0.5f,1,0,1));
                    break;
                case BlockFace.RIGHT:
                    aabbs.add(new AABB(0,0.5f,0.5f,1,0,1));
                    break;
            }
        } else if (params.slabOrStairData == SlabAndStairData.UP) {
            aabbs.add(new AABB(0, 1, 0.5f, 1, 0, 1));
            switch (params.faceTo) {
                case BlockFace.FRONT:
                    aabbs.add(new AABB(0,1,0, 0.5f, 0.5f, 1));
                    break;
                case BlockFace.BACK:
                    aabbs.add(new AABB(0,1,0, 0.5f, 0, 0.5f));
                    break;
                case BlockFace.LEFT:
                    aabbs.add(new AABB(0.5f,1,0, 0.5f,0,1));
                    break;
                case BlockFace.RIGHT:
                    aabbs.add(new AABB(0,0.5f,0, 0.5f,0,1));
                    break;
            }
        }

        return aabbs;
    }

}
