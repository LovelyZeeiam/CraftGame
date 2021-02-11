package xueli.craftgame.interfaces;

import com.flowpowered.nbt.CompoundTag;

import xueli.craftgame.WorldLogic;

public interface Saveable {

    public CompoundTag getSaveData(WorldLogic logic);
    public void setSaveData(CompoundTag data, WorldLogic logic);

}
