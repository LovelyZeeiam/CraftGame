package xueli.mcremake.level;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteOrder;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.Tag;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;

public class LevelInfo implements AutoCloseable {
    
    private final File file;

    private final int version;
    private final CompoundMap map;

    public LevelInfo(File file) throws Exception {
        this.file = file;

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        version = in.readInt();
        in.readInt();
        NBTInputStream nbtIn = new NBTInputStream(in, false, ByteOrder.LITTLE_ENDIAN);
        CompoundTag tag = (CompoundTag) nbtIn.readTag();
        this.map = tag.getValue();
        in.close();

    }

    public Tag<?> get(String tag) {
        return map.get(tag);
    }

    public void write(Tag<?> tag) {
        map.put(tag);
    }

    @Override
    public void close() throws Exception {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        out.writeInt(version);

        ByteArrayOutputStream nbtSectionOut = new ByteArrayOutputStream();
        NBTOutputStream nbtOut = new NBTOutputStream(nbtSectionOut, false, ByteOrder.LITTLE_ENDIAN);
        nbtOut.writeTag(new CompoundTag("", map));
        nbtOut.close();

        out.writeInt(nbtSectionOut.size());
        out.write(nbtSectionOut.toByteArray());

        out.close();

    }

    @Override
    public String toString() {
        return new CompoundTag("", map).toString();
    }

}
