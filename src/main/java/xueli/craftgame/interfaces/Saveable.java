package xueli.craftgame.interfaces;

import com.flowpowered.nbt.CompoundTag;

public interface Saveable {

    public CompoundTag getSaveData();
    public void setSaveData(CompoundTag data);

}
