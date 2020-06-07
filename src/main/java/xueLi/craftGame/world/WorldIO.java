package xueLi.craftGame.world;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.utils.ChunkPos;

public class WorldIO {
	
	private static DBFactory factory = new Iq80DBFactory();
	private static Options options = new Options();
	private DB db;
	
	static {
		options.blockSize(4096);
		options.compressionType(CompressionType.SNAPPY);
		options.createIfMissing(true);
		
		
	}

	public WorldIO(String path) {
		try {
			db = factory.open(new File(path), options);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private static ArrayList<ChunkPos> chunksThatHasBeenLoadingOrLoaded = new ArrayList<ChunkPos>();
	
	public void save(Chunk chunk) throws IOException {
		chunksThatHasBeenLoadingOrLoaded.remove(new ChunkPos(chunk.chunkX, chunk.chunkZ));
		
		int chunkX = chunk.chunkX;
		int chunkZ = chunk.chunkZ;
		
		byte[] heightMapKey = new byte[] { (byte) (chunkX & 0xff), (byte) ((chunkX >> 8) & 0xff), (byte) ((chunkX >> 16) & 0xff),
				(byte) ((chunkX >> 24) & 0xff), (byte) (chunkZ & 0xff), (byte) ((chunkZ >> 8) & 0xff),
				(byte) ((chunkZ >> 16) & 0xff), (byte) ((chunkZ >> 24) & 0xff), 'h' };
		byte[] chunkDataKey = new byte[] { (byte) (chunkX & 0xff), (byte) ((chunkX >> 8) & 0xff), (byte) ((chunkX >> 16) & 0xff),
				(byte) ((chunkX >> 24) & 0xff), (byte) (chunkZ & 0xff), (byte) ((chunkZ >> 8) & 0xff),
				(byte) ((chunkZ >> 16) & 0xff), (byte) ((chunkZ >> 24) & 0xff), 'd' };
		
		ByteArrayOutputStream heightMapByteArrayOutputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzipHeightData = new GZIPOutputStream(heightMapByteArrayOutputStream);
		DataOutputStream heightDataOutputStream = new DataOutputStream(gzipHeightData);
		
		ByteArrayOutputStream chunkDataByteArrayOutputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzipChunkDataByteArratOutputStream = new GZIPOutputStream(chunkDataByteArrayOutputStream);
		DataOutputStream chunkDataOutputStream = new DataOutputStream(gzipChunkDataByteArratOutputStream);
		
		int[][] heightMap = chunk.heightMap;
		Block[][][] blockGrid = chunk.blockState;
		
		for (int xInChunk = 0; xInChunk < Chunk.size; xInChunk++) {
			for (int zInChunk = 0; zInChunk < Chunk.size; zInChunk++) {
				heightDataOutputStream.writeInt(heightMap[xInChunk][zInChunk]);
				for(int y = 0;y < Chunk.height;y++) {
					Block block = blockGrid[xInChunk][y][zInChunk];
					if(block == null) {
						chunkDataOutputStream.writeInt(0);
						chunkDataOutputStream.writeInt(0);
					}else {
						chunkDataOutputStream.writeInt(block.getID());
						chunkDataOutputStream.writeInt(block.dataValue);
					}
				}
			}
		}
		
		gzipHeightData.finish();
		gzipChunkDataByteArratOutputStream.finish();
		
		db.put(chunkDataKey, chunkDataByteArrayOutputStream.toByteArray());
		db.put(heightMapKey, heightMapByteArrayOutputStream.toByteArray());
		
	}
	
	public Chunk load(int chunkX,int chunkZ, World world) {
		byte[] heightMapKey = new byte[] { (byte) (chunkX & 0xff), (byte) ((chunkX >> 8) & 0xff), (byte) ((chunkX >> 16) & 0xff),
				(byte) ((chunkX >> 24) & 0xff), (byte) (chunkZ & 0xff), (byte) ((chunkZ >> 8) & 0xff),
				(byte) ((chunkZ >> 16) & 0xff), (byte) ((chunkZ >> 24) & 0xff), 'h' };
		byte[] chunkDataKey = new byte[] { (byte) (chunkX & 0xff), (byte) ((chunkX >> 8) & 0xff), (byte) ((chunkX >> 16) & 0xff),
				(byte) ((chunkX >> 24) & 0xff), (byte) (chunkZ & 0xff), (byte) ((chunkZ >> 8) & 0xff),
				(byte) ((chunkZ >> 16) & 0xff), (byte) ((chunkZ >> 24) & 0xff), 'd' };
		Chunk chunk = new Chunk(chunkX, chunkZ);
		
		byte[] heightData = db.get(heightMapKey);
		byte[] chunkData = db.get(chunkDataKey);
		
		if(chunkData == null || heightData == null) {
			return ChunkGenerator.gen(chunkX, chunkZ, world);
		}
		
		try {
			DataInputStream heightDataInputStream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(heightData)));
			DataInputStream chunkDataInputStream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(chunkData)));
			for (int xInChunk = 0; xInChunk < Chunk.size; xInChunk++) {
				for (int zInChunk = 0; zInChunk < Chunk.size; zInChunk++) {
					chunk.heightMap[xInChunk][zInChunk] = heightDataInputStream.readInt();
					for(int y = 0;y < Chunk.height;y++) {
						int id = chunkDataInputStream.readInt();
						int dataValue = chunkDataInputStream.readInt();
						if(id != 0)
							chunk.blockState[xInChunk][y][zInChunk] = new Block(id, dataValue);
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return chunk;
	}
	
	public void close() {
		try {
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
