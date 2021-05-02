package xueli.craftgame.world;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.IntArrayTag;
import com.flowpowered.nbt.IntTag;
import com.flowpowered.nbt.ListTag;
import com.flowpowered.nbt.StringTag;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;

import xueli.craftgame.block.Tile;

public class ChunkIO {

	private static final String AIR = "air";
	private static final boolean compressed = true;

	public ChunkIO() {

	}

	public static Chunk readChunk(byte[] data, Dimension dimension) throws IOException {
		NBTInputStream in = new NBTInputStream(new ByteArrayInputStream(data), compressed);
		CompoundTag tag = (CompoundTag) in.readTag();
		CompoundMap map = tag.getValue();
		in.close();

		int version = ((IntTag) map.get("version")).getValue();
		return switch (version) {
		case 0: {
			yield readChunk0(map, dimension);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + version);
		};
	}

	private static Chunk readChunk0(CompoundMap map, Dimension dimension) {
		int chunkx = ((IntTag) map.get("x")).getValue();
		int chunky = ((IntTag) map.get("y")).getValue();
		int chunkz = ((IntTag) map.get("z")).getValue();

		CompoundMap chunkMap = ((CompoundTag) map.get("chunkMap")).getValue();
		int[] grid = ((IntArrayTag) chunkMap.get("grid")).getValue();
		int[] heightmap = ((IntArrayTag) chunkMap.get("heightmap")).getValue();
		List<?> namespaces = ((ListTag<?>) chunkMap.get("namespaces")).getValue();

		Chunk chunk = new Chunk(chunkx, chunky, chunkz, dimension);
		int count = 0;
		int count2 = 0;
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				chunk.heightmap[x][z] = heightmap[count2];
				count2++;
				for (int y = 0; y < 16; y++) {
					int index = grid[count];
					String namespace = ((StringTag) namespaces.get(index)).getValue();

					Tile tile = namespace.equals(AIR) ? null : new Tile(dimension.blocks.getModule(namespace));

					chunk.grid[x][y][z] = tile;

					count++;
				}
			}
		}

		return chunk;
	}

	public static byte[] writeChunk(Chunk chunk) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		CompoundMap map = new CompoundMap();
		map.put(new IntTag("version", 0));

		map.put(new IntTag("x", chunk.getChunkX()));
		map.put(new IntTag("y", chunk.getChunkY()));
		map.put(new IntTag("z", chunk.getChunkZ()));

		int[] grid = new int[16 * 16 * 16];
		int[] heightMap = new int[16 * 16];
		ArrayList<String> namespaces = new ArrayList<>();

		int count = 0;
		int count2 = 0;
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				heightMap[count2] = chunk.heightmap[x][z];
				count2++;
				for (int y = 0; y < 16; y++) {
					Tile tile = chunk.grid[x][y][z];
					String namespace = tile != null ? tile.getBase().getNamespace() : AIR;

					int index = namespaces.indexOf(namespace);
					if (index < 0) {
						index = namespaces.size();
						namespaces.add(namespace);
					}

					grid[count] = index;

					count++;
				}
			}
		}

		CompoundMap gridMap = new CompoundMap();
		gridMap.put(new IntArrayTag("grid", grid));
		gridMap.put(new IntArrayTag("heightmap", heightMap));

		ArrayList<StringTag> namespaceTags = new ArrayList<>();
		for (String namespace : namespaces) {
			namespaceTags.add(new StringTag("", namespace));
		}
		gridMap.put(new ListTag<StringTag>("namespaces", StringTag.class, namespaceTags));

		map.put(new CompoundTag("chunkMap", gridMap));

		NBTOutputStream nbtout = new NBTOutputStream(out, compressed);
		nbtout.writeTag(new CompoundTag("", map));
		nbtout.flush();
		nbtout.close();

		return out.toByteArray();
	}

}
