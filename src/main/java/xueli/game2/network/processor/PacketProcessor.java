package xueli.game2.network.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import xueli.game2.network.Packet;

public class PacketProcessor {

	private final HashMap<Class<? extends Packet>, ArrayList<Consumer<? extends Packet>>> packetProcessors = new HashMap<>();

	public PacketProcessor() {
	}

	public <T extends Packet> void addProcessor(Class<T> clazz, Consumer<T> processor) {
		ArrayList<Consumer<? extends Packet>> list = packetProcessors.computeIfAbsent(clazz, c -> new ArrayList<>());
		list.add(processor);

	}

	@SuppressWarnings("unchecked")
	public <T extends Packet> void doProcess(T packet) {
		Class<? extends Packet> clazz = packet.getClass();
		ArrayList<Consumer<? extends Packet>> list = packetProcessors.get(clazz);
		if(list == null) {
			System.err.println("Bugs? No Packet Processor for " + packet);
			return;
		}

		list.forEach(consumer -> {
			((Consumer<T>) consumer).accept(packet);
		});

	}

}
