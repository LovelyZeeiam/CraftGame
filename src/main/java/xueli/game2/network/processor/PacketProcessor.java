package xueli.game2.network.processor;

import java.util.HashMap;
import java.util.function.Consumer;

import xueli.game2.network.Packet;

public class PacketProcessor {

	private final HashMap<String, Consumer<Packet>> packetProcessors = new HashMap<>();

	public PacketProcessor() {
	}

	@SuppressWarnings("unchecked")
	public <T extends Packet> void addProcessor(String name, Consumer<T> c) {
		packetProcessors.put(name, p -> c.accept((T) p));

	}

	public void doProcess(String name, Packet packet) {
		Consumer<Packet> consumer = packetProcessors.get(name);
		if(consumer != null) {
			consumer.accept(packet);
		}

	}

}
