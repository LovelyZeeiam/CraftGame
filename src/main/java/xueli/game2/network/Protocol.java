package xueli.game2.network;

import java.util.HashMap;
import java.util.function.Function;

public class Protocol {

	private final HashMap<Integer, Function<Readable, Packet>> deserializers = new HashMap<>();
	private final HashMap<Class<? extends Packet>, Integer> packets = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T extends Packet> Protocol register(int id, Class<? extends T> clazz, Function<Readable, T> deserializer) {
		this.deserializers.put(id, (Function<Readable, Packet>) deserializer);
		this.packets.put(clazz, id);
		return this;
	}

	public Function<Readable, Packet> getDeserializerById(int id) {
		return this.deserializers.get(id);
	}

	public int getIdByClazz(Class<? extends Packet> clazz) {
		return this.packets.get(clazz);
	}

}
