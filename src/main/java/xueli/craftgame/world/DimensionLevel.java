package xueli.craftgame.world;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBException;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

@SuppressWarnings("unused")
public class DimensionLevel {

	private static Logger logger = Logger.getLogger(DimensionLevel.class.getName());

	private static Options options = new Options();
	static {
		options.createIfMissing(true);
		options.blockSize(1024 * 1024 * 4);
		options.compressionType(CompressionType.NONE);

	}

	private Dimension dimension;

	private String path;

	private DB db;

	public DimensionLevel(String path, Dimension dimension) {
		this.path = path;
		this.dimension = dimension;

		try {
			this.db = new Iq80DBFactory().open(new File(path), options);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private StringBuilder builder = new StringBuilder();

	private byte[] genChunkKey(int x, int y, int z) {
		builder.delete(0, builder.length());
		builder.append(x).append("_").append(y).append("_").append(z);
		return builder.toString().getBytes();
	}

	public boolean checkChunkExist(int x, int y, int z) {
		return db.get(genChunkKey(x, y, z)) != null;
	}

	public Chunk getChunk(int x, int y, int z) {
		try {
			return ChunkIO.readChunk(db.get(genChunkKey(x, y, z)), dimension);
		} catch (DBException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void writeChunk(Chunk chunk) {
		try {
			db.put(genChunkKey(chunk.getChunkX(), chunk.getChunkY(), chunk.getChunkZ()), ChunkIO.writeChunk(chunk));
		} catch (DBException | IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			this.db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
