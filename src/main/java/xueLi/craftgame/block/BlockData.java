package xueLi.craftgame.block;

import xueLi.gamengine.utils.vector.Vector2s;

public class BlockData {

    private String blockName;
    private BlockType type;
    private long destroyTime;
    private Vector2s[] textures;

    private BlockListener listener = new BlockListener();

    public BlockData(String blockName, BlockType type, long destroyTime, Vector2s[] textures) {
        this.blockName = blockName;
        this.type = type;
        this.destroyTime = destroyTime;
        this.textures = textures;
    }

    public String getBlockName() {
        return blockName;
    }

    public BlockType getType() {
        return type;
    }

    public long getDestroyTime() {
        return destroyTime;
    }

    public Vector2s[] getTextures() {
        return textures;
    }

    public BlockListener getListener() {
        return listener;
    }

    public void setListener(BlockListener listener) {
        this.listener = listener;

    }

}
