package xueli.game2.network;

import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.Tag;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lwjgl.utils.vector.Vector3i;

import java.io.IOException;
import java.util.UUID;

public interface PrimitiveCodec<T> {

	public T read(Readable r) throws IOException;

	public void write(T t, Writable w) throws IOException;

//	default T[] readArr(Readable r) throws IOException {
//		int count = VAR_INT.read(r);
//		Object[] objs = new Object[count];
//		for (int i = 0; i < count; i++) {
//			objs[i] = read(r);
//		}
//		return (T[]) objs;
//	}
//
//	default void writeArr(T[] arr, Writable w) throws IOException {
//		int count = arr.length;
//		VAR_INT.write(count, w);
//		for (int i = 0; i < count; i++) {
//			write(arr[i], w);
//		}
//	}

	PrimitiveCodec<Integer> INT = new PrimitiveCodec<>() {
		@Override
		public Integer read(Readable r) throws IOException {
			return r.readInteger();
		}

		@Override
		public void write(Integer integer, Writable w) throws IOException {
			w.writeInteger(integer);
		}
	};

	PrimitiveCodec<Byte> BYTE = new PrimitiveCodec<>() {
		@Override
		public Byte read(Readable r) throws IOException {
			return r.readByte();
		}

		@Override
		public void write(Byte aByte, Writable w) throws IOException {
			w.writeByte(aByte);
		}
	};

	PrimitiveCodec<Short> SHORT = new PrimitiveCodec<>() {
		@Override
		public Short read(Readable r) throws IOException {
			return r.readShort();
		}

		@Override
		public void write(Short aShort, Writable w) throws IOException {
			w.writeShort(aShort);
		}
	};

	PrimitiveCodec<Long> LONG = new PrimitiveCodec<>() {
		@Override
		public Long read(Readable r) throws IOException {
			return r.readLong();
		}

		@Override
		public void write(Long aLong, Writable w) throws IOException {
			w.writeLong(aLong);
		}
	};

	PrimitiveCodec<Integer> UNSIGNED_BYTE = new PrimitiveCodec<>() {
		@Override
		public Integer read(Readable r) throws IOException {
			return Byte.toUnsignedInt(BYTE.read(r));
		}

		@Override
		public void write(Integer integer, Writable w) throws IOException {
			BYTE.write(integer.byteValue(), w);
		}
	};

	PrimitiveCodec<Integer> UNSIGNED_SHORT = new PrimitiveCodec<>() {
		@Override
		public Integer read(Readable r) throws IOException {
			return Short.toUnsignedInt(SHORT.read(r));
		}

		@Override
		public void write(Integer integer, Writable w) throws IOException {
			SHORT.write(integer.shortValue(), w);
		}
	};

	PrimitiveCodec<Float> FLOAT = new PrimitiveCodec<>() {
		@Override
		public Float read(Readable r) throws IOException {
			return r.readFloat();
		}

		@Override
		public void write(Float aFloat, Writable w) throws IOException {
			w.writeFloat(aFloat);
		}
	};

	PrimitiveCodec<Double> DOUBLE = new PrimitiveCodec<>() {
		@Override
		public Double read(Readable r) throws IOException {
			return r.readDouble();
		}

		@Override
		public void write(Double d, Writable w) throws IOException {
			w.writeDouble(d);
		}
	};

	PrimitiveCodec<Boolean> BOOLEAN = new PrimitiveCodec<>() {
		@Override
		public Boolean read(Readable r) throws IOException {
			return r.readBoolean();
		}

		@Override
		public void write(Boolean aBoolean, Writable w) throws IOException {
			w.writeBoolean(aBoolean);
		}
	};

	PrimitiveCodec<Integer> VAR_INT = new PrimitiveCodec<>() {

		@Override
		public Integer read(Readable r) throws IOException {
			byte readByte;
			int loopCount = 0;
			int result = 0;

			while (true) {
				readByte = r.readByte();
				result |= (((int) readByte) & (0b1111111)) << (loopCount * 7);

				if ((readByte & (1 << 7)) == 0)
					break;
				loopCount++;

			}

			return result;
		}

		@Override
		public void write(Integer integer, Writable w) throws IOException {
			int i = integer;
			int nextI;

			boolean flag;
			byte thisByte;

			while (true) {
				thisByte = (byte) (i & 0b1111111);

				nextI = i >>> 7;
				flag = nextI != 0;
				if (flag) {
					thisByte |= (1 << 7);
				}

				w.writeByte(thisByte);

				if (!flag) break;
				i = nextI;

			}
		}

	};

	PrimitiveCodec<Long> VAR_LONG = new PrimitiveCodec<>() {
		@Override
		public Long read(Readable r) throws IOException {
			byte readByte;
			int loopCount = 0;
			long result = 0;

			while (true) {
				readByte = r.readByte();
				result |= (((long) readByte) & (0b1111111)) << (loopCount * 7);

				if ((readByte & (1 << 7)) == 0)
					break;
				loopCount++;

			}

			return result;
		}

		@Override
		public void write(Long aLong, Writable w) throws IOException {
			long i = aLong;
			long nextI;

			boolean flag;
			byte thisByte;

			while (true) {
				thisByte = (byte) (i & 0b1111111);

				nextI = i >>> 7;
				flag = nextI != 0;
				if (flag) {
					thisByte |= (1 << 7);
				}

				w.writeByte(thisByte);

				if (!flag) break;
				i = nextI;

			}

		}
	};

	PrimitiveCodec<String> STRING = new PrimitiveCodec<>() {
		@Override
		public String read(Readable r) throws IOException {
			int size = VAR_INT.read(r);
			return r.readString(size);
		}

		@Override
		public void write(String s, Writable w) throws IOException {
			byte[] bytes = s.getBytes(CodecConstants.STRING_CHARSET);
			VAR_INT.write(s.length(), w);
			w.writeBytes(bytes);
		}
	};

	PrimitiveCodec<UUID> UUID = new PrimitiveCodec<>() {
		@Override
		public java.util.UUID read(Readable r) throws IOException {
			long mostSigBits = r.readLong();
			long leaseSigBits = r.readLong();
			return new UUID(mostSigBits, leaseSigBits);
		}

		@Override
		public void write(java.util.UUID uuid, Writable w) throws IOException {
			w.writeLong(uuid.getMostSignificantBits());
			w.writeLong(uuid.getLeastSignificantBits());

		}
	};

	PrimitiveCodec<JsonObject> JSON = new PrimitiveCodec<>() {

		private static final Gson GSON = new Gson();

		@Override
		public JsonObject read(Readable r) throws IOException {
			String str = STRING.read(r);
			return GSON.fromJson(str, JsonObject.class);
		}

		@Override
		public void write(JsonObject jsonElement, Writable w) throws IOException {
			String str = jsonElement.toString();
			STRING.write(str, w);

		}
	};

	PrimitiveCodec<CompoundTag> NBT_NOT_COMPRESSED = new PrimitiveCodec<>() {
		@Override
		public CompoundTag read(Readable r) throws IOException {
			NBTInputStream nbtIn = new NBTInputStream(r.toInputStream(), false);
			Tag<?> tag = nbtIn.readTag();
			nbtIn.close();
			return (CompoundTag) tag;
		}

		@Override
		public void write(CompoundTag compoundTag, Writable w) throws IOException {
			NBTOutputStream nbtOut = new NBTOutputStream(w.toOutputStream(), false);
			nbtOut.writeTag(compoundTag);
			nbtOut.flush();
			nbtOut.close();

		}
	};
	
	PrimitiveCodec<Vector3i> BLOCK_POS = new PrimitiveCodec<>() {
		
		@Override
		public Vector3i read(Readable r) throws IOException {
			long longVal = LONG.read(r);
			int x = (int) (longVal >> 38);
			int z = (int) ((longVal << 52) >> 52);
			int y = (int) ((longVal << 26) >> 38);
			return new Vector3i(x, y, z);
		}
		
		public void write(Vector3i t, Writable w) throws IOException {
			long val = 0L;
			val |= ((long) (t.getX() & 0x3FFFFFF) << 38);
			val |= ((long) (t.getZ() & 0x3FFFFFF) << 12);
			val |= (t.getY() & 0xFFF);
			LONG.write(val, w);
			
		};
		
	};

}
