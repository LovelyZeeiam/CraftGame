package xueli.craftgame.block.model;

import com.google.gson.JsonObject;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.view.GuiColor;

import java.util.ArrayList;

public class ModelLeaf extends IModel {

    public ModelLeaf(JsonObject renderArgs) {
        super(renderArgs);

    }

    @Override
    public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                 TextureAtlas blockTextureAtlas, Chunk chunk, World world) {
        return getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, chunk, world, chunk == null ? GuiColor.GREEN : chunk.getBiome().getLeaves_color_nvg());
    }

    @Override
    public ArrayList<AABB> getAabbs() {
        return new ArrayList<>();
    }

}
