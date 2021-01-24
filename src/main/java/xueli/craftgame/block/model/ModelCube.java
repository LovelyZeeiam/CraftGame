package xueli.craftgame.block.model;

import com.google.gson.JsonObject;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.view.GuiColor;

public class ModelCube extends IModel {

    public ModelCube(JsonObject renderArgs) {
        super(renderArgs);

    }

    @Override
    public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                 TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
        return getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, chunk, world, GuiColor.WHITE);
    }

}
