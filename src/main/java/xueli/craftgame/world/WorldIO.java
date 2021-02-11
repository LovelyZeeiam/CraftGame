package xueli.craftgame.world;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;

import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.math.MathUtils;
import xueli.utils.Files;

public class WorldIO {

    private static final String path = "level/";

    private World world;

    private WorldCheckAndSaveThread thread = new WorldCheckAndSaveThread();

    public WorldIO(World world) {
        this.world = world;

    }

    private String getChunkSaveName(int chunkX, int chunkZ) {
        return "chunk_" + chunkX + "_" + chunkZ + ".dat";
    }

    private ExecutorService service = Executors.newFixedThreadPool(10);

    public void readChunk(int x, int z) {
        service.submit(() -> {
            try {
                NBTInputStream in = new NBTInputStream(new FileInputStream(path + getChunkSaveName(x,z)), false, ByteOrder.LITTLE_ENDIAN);
                CompoundTag tag = (CompoundTag) in.readTag();
                in.close();

                world.chunks.put(MathUtils.vert2ToLong(x,z), new Chunk(x,z,world,tag));

            } catch (Throwable e) {
            	Logger.warn(e.getClass().getName()+": "+e.getMessage());

                // 生成新的区块
                world.chunks.put(MathUtils.vert2ToLong(x,z), world.getGen().superflat(x,z));

            } finally {
				System.gc();
				
			}
        });

    }

    public void readChunkSync(int x, int z){
        try {
            NBTInputStream in = new NBTInputStream(new FileInputStream(path + getChunkSaveName(x,z)), false, ByteOrder.LITTLE_ENDIAN);
            CompoundTag tag = (CompoundTag) in.readTag();
            in.close();

            world.chunks.put(MathUtils.vert2ToLong(x,z), new Chunk(x,z,world,tag));

        } catch (Exception e) {
            Logger.warn(e.getClass().getName()+": "+e.getMessage());

            // 生成新的区块
            world.chunks.put(MathUtils.vert2ToLong(x,z), world.getGen().superflat(x,z));

        }
    }

    public void saveChunk(Chunk chunk) {
        // 生成区块保存标签
        CompoundTag tag = chunk.getSaveData(world.getWorldLogic());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            NBTOutputStream nbtout = new NBTOutputStream(out, false, ByteOrder.LITTLE_ENDIAN);
            nbtout.writeTag(tag);
            nbtout.flush();
            nbtout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!new File("level/").exists())
            new File("level/").mkdir();

        try {
            Files.fileOutput(path + getChunkSaveName(chunk.chunkX, chunk.chunkZ), out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkSave() {
        if(thread.isAlive())
            return;
        thread.run();

    }

    public void close() {
        thread.close();

    }

    private class WorldCheckAndSaveThread extends Thread {

        private ExecutorService service = Executors.newSingleThreadExecutor();

        @Override
        public void run() {
            world.chunks.forEach((l, c) -> {
                if(Time.thisTime - c.getLastUsedTime() > 10000) {
                    service.submit(() -> {saveChunk(c);world.chunkThatNeedRemove.add(l);});
                }
            });
            System.gc();
            System.runFinalization();

        }

        public void close() {
        	service.shutdown();
            service.shutdownNow();

        }

    }

}
