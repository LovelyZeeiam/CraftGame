package xueli.craftgame.world;

import java.io.File;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;
import org.iq80.leveldb.impl.Iq80DBFactory;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.init.Blocks;

public class ChunkIOTest {

	private static class BlockDirt extends BlockBase {

		public BlockDirt() {
			this.namespace = "craftgame:dirt";

			// TODO: Dynamic Loading for Language File
			this.nameInternational = "Dirt";

		}

	}

	private static class BlockGrass extends BlockBase {

		public BlockGrass() {
			this.namespace = "craftgame:grass";

			// TODO: Dynamic Loading for Language File
			this.nameInternational = "Grass Block";

		}

	}

	private static class BlockStone extends BlockBase {

		public BlockStone() {
			this.namespace = "craftgame:stone";

			// TODO: Dynamic Loading for Language File
			this.nameInternational = "Stone";

		}

	}

	public static void main(String[] args) {
		try {
			Blocks blocks = new Blocks();
			blocks.addBlock(new BlockDirt());
			blocks.addBlock(new BlockGrass());
			blocks.addBlock(new BlockStone());

			Dimension dimension = new Dimension(false, blocks);
			ChunkGenerator generator = new ChunkGenerator(dimension);

			Options options = new Options();
			options.createIfMissing(true);
			options.blockSize(1024 * 4);
			options.compressionType(CompressionType.NONE);

			DB db = new Iq80DBFactory().open(new File("temp/chunks"), options);

			WriteBatch batch = db.createWriteBatch();

			long time1 = System.currentTimeMillis();
			StringBuilder builder = new StringBuilder();
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					for (int z = 0; z < 10; z++) {
						builder.delete(0, builder.length());
						builder.append(x).append("_").append(y).append("_").append("z");
						batch.put(builder.toString().getBytes(), ChunkIO.writeChunk(generator.genChunk(x, y, z)));
					}
				}
			}
			db.write(batch);
			long time2 = System.currentTimeMillis();
			System.out.println(time2 - time1);

			time1 = System.currentTimeMillis();
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					for (int z = 0; z < 10; z++) {
						builder.delete(0, builder.length());
						builder.append(x).append("_").append(y).append("_").append("z");
						byte[] data = db.get(builder.toString().getBytes());
						ChunkIO.readChunk(data, dimension);
					}
				}
			}
			time2 = System.currentTimeMillis();
			System.out.println(time2 - time1);

			db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
