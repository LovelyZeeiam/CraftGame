package xueli.tests;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.DbLock;
import org.iq80.leveldb.impl.Filename;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.iq80.leveldb.table.BlockEntry;
import org.iq80.leveldb.table.BlockHandle;
import org.iq80.leveldb.table.TableBuilder;
import org.iq80.leveldb.util.*;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

@SuppressWarnings("unused")
public class TrySaveLevelTest {

	private static final Options options = new Options();
	static {
		options.createIfMissing(false);
		options.compressionType(CompressionType.SNAPPY);
	}

	private static final File testDBFolder = new File("D:/temp/New folder (2)/db4/");

	public static void main(String[] args) throws IOException {
		File lockFile = new File(testDBFolder, Filename.lockFileName());
		DbLock lock = new DbLock(lockFile);

//		File currentFile = new File(testDBFolder, Filename.currentFileName());
//		String currentManifestName = Files.toString(currentFile, StandardCharsets.UTF_8);
//		if (currentManifestName.isEmpty() || currentManifestName.charAt(currentManifestName.length() - 1) != '\n') {
//			throw new IllegalStateException("CURRENT file does not end with newline");
//		}
//		currentManifestName = currentManifestName.substring(0, currentManifestName.length() - 1);

//		HashMultimap<Integer, FileMetaData> sstableFiles = HashMultimap.create();

//		try (FileInputStream fis = new FileInputStream(new File(testDBFolder, currentManifestName));
//			 FileChannel fileChannel = fis.getChannel()) {
//			LogReader reader = new LogReader(fileChannel, LogMonitors.throwExceptionMonitor(), true, 0);
//			for (Slice record = reader.readRecord(); record != null; record = reader.readRecord()) {
//				VersionEdit edit = new VersionEdit(record);
//				sstableFiles.putAll(edit.getNewFiles());
//			}
//		}

		File[] allFiles = testDBFolder.listFiles((dir, name) -> name.endsWith(".sst"));

		for (File file : allFiles) {
//			File file = new File(testDBFolder, Filename.tableFileName(sstFileMeta.getNumber()));
//			if(!file.exists()) {
//				System.err.println("ERR Table Not Exist: " + sstFileMeta.getNumber());
//				continue;
//			}

//			if(file.length() != sstFileMeta.getFileSize()) {
//				System.err.println("ERR Table Size: " + sstFileMeta.getNumber());
//				continue;
//			}
			long fileSize = file.length();

			try (FileInputStream fis = new FileInputStream(file); FileChannel fc = fis.getChannel()) {
				// Try read its footer
				ByteBuffer footerBuf = ByteBuffer.allocate(48);
				fc.read(footerBuf, fileSize - 48);
				footerBuf.flip();
				Slice slice = Slices.copiedBuffer(footerBuf);
				SliceInput sliceInput = slice.input();

				BlockHandle metaIndexBlockHandle = BlockHandle.readBlockHandle(sliceInput);
				BlockHandle indexBlockHandle = BlockHandle.readBlockHandle(sliceInput);

				sliceInput.setPosition(40);
				long magicNumber = sliceInput.readUnsignedInt() | (sliceInput.readUnsignedInt() << 32);
				if (magicNumber != TableBuilder.TABLE_MAGIC_NUMBER) {
					System.err.println("ERR Table Magic Number: " + file.getName());
					continue;
				}

				System.out.println("==== INFO Read Table: " + file.getName());

//				Slice metaBlock = readBlock(metaIndexBlockHandle, fc);
				Slice indexBlock = readBlock(indexBlockHandle, fc);
				SliceInput indexBlockInput = indexBlock.input();

				BlockEntry lastIndexBlockEntry = null;
				do {
					int sharedKeyLength = VariableLengthQuantity.readVariableLengthInt(indexBlockInput);
					int nonSharedKeyLength = VariableLengthQuantity.readVariableLengthInt(indexBlockInput);
					int valueLength = VariableLengthQuantity.readVariableLengthInt(indexBlockInput);

					Slice key;
					if(sharedKeyLength > 0) {
						Assertions.assertNotNull(lastIndexBlockEntry);

						key = Slices.allocate(sharedKeyLength + nonSharedKeyLength);
						SliceOutput sliceOutput = key.output();

						sliceOutput.writeBytes(lastIndexBlockEntry.getKey(), 0, sharedKeyLength);
						sliceOutput.writeBytes(indexBlockInput, nonSharedKeyLength);

					} else {
						key = indexBlockInput.readSlice(nonSharedKeyLength);
					}
					Slice value = indexBlockInput.readSlice(valueLength);

					BlockEntry entry = new BlockEntry(key, value);
					lastIndexBlockEntry = entry;
					System.out.println(entry);

				} while(indexBlockInput.isReadable());


			}
		}

		lock.release();

	}

	private static Slice readBlock(BlockHandle handle, FileChannel fc) throws IOException {
		ByteBuffer trailerBuf = ByteBuffer.allocate(5);
		fc.read(trailerBuf, handle.getOffset() + handle.getDataSize());
		trailerBuf.flip();

		Slice trailerSlice = Slices.copiedBuffer(trailerBuf);
		SliceInput trailerInput = trailerSlice.input();
		int compressTypeId = trailerInput.readUnsignedByte();

		CompressionType compressType;
		try {
			compressType = CompressionType.getCompressionTypeByPersistentId(compressTypeId);
		} catch (IllegalArgumentException e) {
			compressType = CompressionType.SNAPPY;
		}
		System.out.println("Compress Type: " + compressTypeId);
//		int crc32c = trailerInput.readInt();

		ByteBuffer blockDataBuf = ByteBuffer.allocate(handle.getDataSize());
		fc.read(blockDataBuf, handle.getOffset());
		blockDataBuf.flip();

		Slice uncompressedData;
		if (compressType == CompressionType.SNAPPY) {
			int uncompressedLength = blockDataBuf.duplicate().getInt();
			ByteBuffer buf = ByteBuffer.allocate(uncompressedLength);
			Snappy.uncompress(blockDataBuf, buf);
			buf.flip();
			blockDataBuf = buf;
		}

		uncompressedData = Slices.copiedBuffer(blockDataBuf);
		return uncompressedData;
	}

	@org.junit.jupiter.api.Test
	public void renameFiles() throws IOException {
		File[] files = new File("C:\\Users\\22598\\appdata\\Roaming\\MinecraftPE_Netease\\minecraftWorlds\\bbcc62f9-8ec6-4d31-89bf-ca6e23c46ad3\\db2\\").listFiles((dir, n) -> n.endsWith(".ldb"));
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			File parent = file.getParentFile();
			String fileName = file.getName();
			file.renameTo(new File(parent, fileName.substring(0, fileName.length() - 4) + ".sst"));
		}

	}

	@org.junit.jupiter.api.Test
	public void flushDatabase() throws IOException {
		DB db = null;
		try {
			db = new Iq80DBFactory().open(new File("C:\\Users\\22598\\appdata\\Roaming\\MinecraftPE_Netease\\minecraftWorlds\\bbcc62f9-8ec6-4d31-89bf-ca6e23c46ad3\\db2\\"), options);
			db.forEach(e -> {
				byte[] key = e.getKey();

				if(key[0] == '~' || (key[0] >= 'A' && key[0] <= 'z')) {
					String keyStr = new String(key);
					System.out.println(keyStr);
				} else {
					System.out.println(Arrays.toString(key));
				}

			});
//			db.get("~local_player".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(db != null) {
				System.out.println("Close");
				db.close();

			}
		}
	}
	
}
