package xueli.craftgame.block.rendercontrol.model;

import com.google.gson.JsonObject;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

public class ModelFence extends IModel {

    private static float fence_x = 0.35f;
    private static float fence_bridge_x = 0.4f;
    private static float[] fence_bridge_y = new float[]{0.35f, 0.55f, 0.75f, 0.95f};

    public ModelFence(JsonObject renderArgs) {
        super(renderArgs);
    }

    @Override
    public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face, TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
        int vertCount = 0;

        vertCount += modelFenceBasic.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);

        if (world != null) {
            if (world.hasBlock(new Vector3i(x, y, z - 1)) && (world.getBlock(x, y, z - 1).getData().getNamespace().endsWith(".fence") || world.getBlock(x, y, z - 1).getModel().isCompleteBlock(world))) {
                vertCount += modelFenceFrontAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
            }
            if (world.hasBlock(new Vector3i(x, y, z + 1)) && (world.getBlock(x, y, z + 1).getData().getNamespace().endsWith(".fence") || world.getBlock(x, y, z + 1).getModel().isCompleteBlock(world))) {
                vertCount += modelFenceBackAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
            }
            if (world.hasBlock(new Vector3i(x - 1, y, z)) && (world.getBlock(x - 1, y, z).getData().getNamespace().endsWith(".fence") || world.getBlock(x - 1, y, z).getModel().isCompleteBlock(world))) {
                vertCount += modelFenceLeftAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
            }
            if (world.hasBlock(new Vector3i(x + 1, y, z)) && (world.getBlock(x + 1, y, z).getData().getNamespace().endsWith(".fence") || world.getBlock(x + 1, y, z).getModel().isCompleteBlock(world))) {
                vertCount += modelFenceRightAttach.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
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

            float v1 = (float) (textureVector2s.y + 1 - fence_bridge_y[1]) / blockTextureAtlas.height;
            float v2 = (float) (textureVector2s.y + 1 - fence_bridge_y[0]) / blockTextureAtlas.height;

            float u3 = (float) (textureVector2s.x) / blockTextureAtlas.width;
            float u4 = (float) (textureVector2s.x + 1 - fence_bridge_y[1]) / blockTextureAtlas.width;

            float v4 = (float) (textureVector2s.y + 1 - fence_bridge_y[3]) / blockTextureAtlas.height;
            float v5 = (float) (textureVector2s.y + 1 - fence_bridge_y[2]) / blockTextureAtlas.height;

            float v6 = (float) (textureVector2s.y + fence_bridge_x) / blockTextureAtlas.height;
            float v7 = (float) (textureVector2s.y + 1 - fence_bridge_x) / blockTextureAtlas.height;

            switch (face) {
                case BlockFace.FRONT:
                    return 0;
                case BlockFace.RIGHT:
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_y[1]), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_y[1]), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_y[1]), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_y[1]), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                    break;
                case BlockFace.BACK:
                    return 0;
                case BlockFace.LEFT:
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                    break;
                case BlockFace.TOP:
                    drawQuadFacingTop(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    drawQuadFacingTop(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    break;
                case BlockFace.BOTTOM:
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z), new Vector2f(u3, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z), new Vector2f(u3, v6), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v6), new Vector3f(0.4f, 0.4f, 0.4f));
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z), new Vector2f(u3, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z), new Vector2f(u3, v6), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_y[1]), new Vector2f(u4, v6), new Vector3f(0.4f, 0.4f, 0.4f));
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

            float v1 = (float) (textureVector2s.y + 1 - fence_bridge_y[1]) / blockTextureAtlas.height;
            float v2 = (float) (textureVector2s.y + 1 - fence_bridge_y[0]) / blockTextureAtlas.height;

            float u3 = (float) (textureVector2s.x) / blockTextureAtlas.width;
            float u4 = (float) (textureVector2s.x + 1 - fence_bridge_y[1]) / blockTextureAtlas.width;

            float v4 = (float) (textureVector2s.y + 1 - fence_bridge_y[3]) / blockTextureAtlas.height;
            float v5 = (float) (textureVector2s.y + 1 - fence_bridge_y[2]) / blockTextureAtlas.height;

            float v6 = (float) (textureVector2s.y + fence_bridge_x) / blockTextureAtlas.height;
            float v7 = (float) (textureVector2s.y + 1 - fence_bridge_x) / blockTextureAtlas.height;

            switch (face) {
                case BlockFace.FRONT:
                    return 0;
                case BlockFace.RIGHT:
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_y[1]), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_y[1]), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + 1), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + 1), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_y[1]), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_y[1]), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + 1), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + 1), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                    break;
                case BlockFace.BACK:
                    return 0;
                case BlockFace.LEFT:
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_y[1]), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_y[1]), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + 1), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + 1), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_y[1]), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_y[1]), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + 1), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + 1), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                    break;
                case BlockFace.TOP:
                    drawQuadFacingTop(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_y[1]), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_y[1]), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + 1), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + 1), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    drawQuadFacingTop(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_y[1]), new Vector2f(u3, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_y[1]), new Vector2f(u3, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + 1), new Vector2f(u4, v6), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + 1), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    break;
                case BlockFace.BOTTOM:
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_y[1]), new Vector2f(u3, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_y[1]), new Vector2f(u3, v6), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + 1), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + 1), new Vector2f(u4, v6), new Vector3f(0.4f, 0.4f, 0.4f));
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_y[1]), new Vector2f(u3, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_y[1]), new Vector2f(u3, v6), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + 1), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + 1), new Vector2f(u4, v6), new Vector3f(0.4f, 0.4f, 0.4f));
                    break;
            }

            return 12;
        }

    };

    private static IModel modelFenceLeftAttach = new IModel(null) {

        @Override
        public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                     TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
            Vector2s textureVector2s = data.getTextures()[face];

            float v1 = (float) (textureVector2s.y + 1 - fence_bridge_y[1]) / blockTextureAtlas.height;
            float v2 = (float) (textureVector2s.y + 1 - fence_bridge_y[0]) / blockTextureAtlas.height;

            float u3 = (float) (textureVector2s.x) / blockTextureAtlas.width;
            float u4 = (float) (textureVector2s.x + 1 - fence_bridge_y[1]) / blockTextureAtlas.width;

            float v4 = (float) (textureVector2s.y + 1 - fence_bridge_y[3]) / blockTextureAtlas.height;
            float v5 = (float) (textureVector2s.y + 1 - fence_bridge_y[2]) / blockTextureAtlas.height;

            float v7 = (float) (textureVector2s.y + 1 - fence_bridge_x) / blockTextureAtlas.height;

            float u8 = (float) (textureVector2s.x + 1 - fence_bridge_x) / blockTextureAtlas.width;
            float v8 = (float) (textureVector2s.y + fence_bridge_x) / blockTextureAtlas.height;

            switch (face) {
                case BlockFace.FRONT:
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                    break;
                case BlockFace.RIGHT:
                    return 0;
                case BlockFace.BACK:
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u3, v2), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u4, v2), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u3, v1), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u4, v1), new Vector3f(0.5f, 0.5f, 0.5f));
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u3, v5), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u4, v5), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u3, v4), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u4, v4), new Vector3f(0.5f, 0.5f, 0.5f));
                case BlockFace.LEFT:
                    return 0;
                case BlockFace.TOP:
                    drawQuadFacingTop(buffer,
                            new Vector3f(x, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    drawQuadFacingTop(buffer,
                            new Vector3f(x, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    break;
                case BlockFace.BOTTOM:
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f));
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f));
                    break;
            }

            return 12;
        }

    };

    private static IModel modelFenceRightAttach = new IModel(null) {

        @Override
        public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                     TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
            Vector2s textureVector2s = data.getTextures()[face];

            float v1 = (float) (textureVector2s.y + 1 - fence_bridge_y[1]) / blockTextureAtlas.height;
            float v2 = (float) (textureVector2s.y + 1 - fence_bridge_y[0]) / blockTextureAtlas.height;

            float u3 = (float) (textureVector2s.x) / blockTextureAtlas.width;
            float u4 = (float) (textureVector2s.x + 1 - fence_bridge_y[1]) / blockTextureAtlas.width;

            float v4 = (float) (textureVector2s.y + 1 - fence_bridge_y[3]) / blockTextureAtlas.height;
            float v5 = (float) (textureVector2s.y + 1 - fence_bridge_y[2]) / blockTextureAtlas.height;

            float v7 = (float) (textureVector2s.y + 1 - fence_bridge_x) / blockTextureAtlas.height;

            float u8 = (float) (textureVector2s.x + 1 - fence_bridge_x) / blockTextureAtlas.width;
            float v8 = (float) (textureVector2s.y + fence_bridge_x) / blockTextureAtlas.height;

            switch (face) {
                case BlockFace.FRONT:
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u4, v2), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u4, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u3, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u4, v5), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u4, v4), new Vector3f(0.7f, 0.7f, 0.7f));
                    break;
                case BlockFace.RIGHT:
                    return 0;
                case BlockFace.BACK:
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u3, v2), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u4, v2), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u3, v1), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u4, v1), new Vector3f(0.5f, 0.5f, 0.5f));
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u3, v5), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u4, v5), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u3, v4), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u4, v4), new Vector3f(0.5f, 0.5f, 0.5f));
                case BlockFace.LEFT:
                    return 0;
                case BlockFace.TOP:
                    drawQuadFacingTop(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1, y + fence_bridge_y[1], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1, y + fence_bridge_y[1], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    drawQuadFacingTop(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1, y + fence_bridge_y[3], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1, y + fence_bridge_y[3], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(1.0f, 1.0f, 1.0f));
                    break;
                case BlockFace.BOTTOM:
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1, y + fence_bridge_y[0], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1, y + fence_bridge_y[0], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f));
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u8, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1, y + fence_bridge_y[2], z + fence_bridge_x), new Vector2f(u4, v8), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_bridge_x, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u8, v7), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1, y + fence_bridge_y[2], z + 1 - fence_bridge_x), new Vector2f(u4, v7), new Vector3f(0.4f, 0.4f, 0.4f));
                    break;
            }

            return 12;
        }

    };

    private static IModel modelFenceBasic = new IModel(null) {

        @Override
        public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                     TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
            Vector2s textureVector2s = data.getTextures()[face];

            float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
            float u2 = (float) (textureVector2s.x + fence_x) / blockTextureAtlas.width;
            float v2 = (float) (textureVector2s.y + fence_x) / blockTextureAtlas.height;
            float u3 = (float) (textureVector2s.x + 1 - fence_x) / blockTextureAtlas.width;
            float v3 = (float) (textureVector2s.y + 1 - fence_x) / blockTextureAtlas.height;
            float v4 = (float) (textureVector2s.y + 1.0f) / blockTextureAtlas.height;

            switch (face) {
                case BlockFace.FRONT:
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + fence_x, y, z + fence_x), new Vector2f(u2, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_x, y, z + fence_x), new Vector2f(u3, v4), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + fence_x, y + 1, z + fence_x), new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f),
                            new Vector3f(x + 1 - fence_x, y + 1, z + fence_x), new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f));
                    break;
                case BlockFace.RIGHT:
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + 1 - fence_x, y, z + fence_x), new Vector2f(u3, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                            new Vector3f(x + 1 - fence_x, y + 1, z + fence_x), new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f),
                            new Vector3f(x + 1 - fence_x, y, z + 1 - fence_x), new Vector2f(u2, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                            new Vector3f(x + 1 - fence_x, y + 1, z + 1 - fence_x), new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f));
                    break;
                case BlockFace.BACK:
                    drawQuadFacingBackOrRight(buffer,
                            new Vector3f(x + fence_x, y, z + 1 - fence_x), new Vector2f(u2, v4), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1 - fence_x, y, z + 1 - fence_x), new Vector2f(u3, v4), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + fence_x, y + 1, z + 1 - fence_x), new Vector2f(u2, v1), new Vector3f(0.5f, 0.5f, 0.5f),
                            new Vector3f(x + 1 - fence_x, y + 1, z + 1 - fence_x), new Vector2f(u3, v1), new Vector3f(0.5f, 0.5f, 0.5f));
                    break;
                case BlockFace.LEFT:
                    drawQuadFacingFrontOrLeft(buffer,
                            new Vector3f(x + fence_x, y, z + fence_x), new Vector2f(u2, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                            new Vector3f(x + fence_x, y + 1, z + fence_x), new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f),
                            new Vector3f(x + fence_x, y, z + 1 - fence_x), new Vector2f(u3, v4), new Vector3f(0.6f, 0.6f, 0.6f),
                            new Vector3f(x + fence_x, y + 1, z + 1 - fence_x), new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
                    break;
                case BlockFace.TOP:
                    drawQuadFacingTop(buffer,
                            new Vector3f(x + fence_x, y + 1, z + fence_x), new Vector2f(u2, v2), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_x, y + 1, z + fence_x), new Vector2f(u2, v3), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + fence_x, y + 1, z + 1 - fence_x), new Vector2f(u3, v2), new Vector3f(1.0f, 1.0f, 1.0f),
                            new Vector3f(x + 1 - fence_x, y + 1, z + 1 - fence_x), new Vector2f(u3, v3), new Vector3f(1.0f, 1.0f, 1.0f));
                    break;
                case BlockFace.BOTTOM:
                    drawQuadFacingBottom(buffer,
                            new Vector3f(x + fence_x, y, z + fence_x), new Vector2f(u2, v3), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_x, y, z + fence_x), new Vector2f(u2, v2), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + fence_x, y, z + 1 - fence_x), new Vector2f(u3, v3), new Vector3f(0.4f, 0.4f, 0.4f),
                            new Vector3f(x + 1 - fence_x, y, z + 1 - fence_x), new Vector2f(u3, v2), new Vector3f(0.4f, 0.4f, 0.4f));
                    break;
            }

            return 6;
        }

    };

}
