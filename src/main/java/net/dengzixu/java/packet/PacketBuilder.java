package net.dengzixu.java.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import net.dengzixu.java.constant.PacketOperationEnum;
import net.dengzixu.java.constant.PacketProtocolVersionEnum;

public class PacketBuilder {
	private final Packet packet;

	public PacketBuilder(short protocolVersion, int operation) {
		this(16, (short) 16, protocolVersion, operation, 1, null);
	}

	public PacketBuilder(short protocolVersion, int operation, byte[] payload) {
		this(16 + payload.length, (short) 16, protocolVersion, operation, 1, payload);
	}

	public PacketBuilder(PacketProtocolVersionEnum packetProtocolVersion, PacketOperationEnum packetOperation,
			String payloadString) {
		this(16 + payloadString.getBytes(StandardCharsets.UTF_8).length, (short) 16, packetProtocolVersion.version(),
				packetOperation.operation(), 1, payloadString.getBytes(StandardCharsets.UTF_8));
	}

	public PacketBuilder(short protocolVersion, int operation, String payloadString) {
		this(16 + payloadString.getBytes(StandardCharsets.UTF_8).length, (short) 16, protocolVersion, operation, 1,
				payloadString.getBytes(StandardCharsets.UTF_8));
	}

	public PacketBuilder(int packetLength, short headerLength, short protocolVersion, int operation, int sequenceId,
			byte[] payload) {

		this.packet = new Packet() {
			{
				setPacketLength(packetLength);
				setHeaderLength(headerLength);
				setProtocolVersion(protocolVersion);
				setOperation(operation);
				setSequenceId(sequenceId);
				setPayload(payload);
			}
		};
	}

	public byte[] buildArrays() {
		ByteBuffer head = ByteBuffer.allocate(packet.getPacketLength());
		try {
			// Packet Length
			head.put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(packet.getPacketLength()).array());

			// Header Length
			head.put(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(packet.getHeaderLength()).array());

			// Protocol Version
			head.put(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(packet.getProtocolVersion()).array());

			// Operation
			head.put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(packet.getOperation()).array());

			// Sequence Id
			head.put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(packet.getProtocolVersion()).array());

			if (null != packet.getPayload()) {
				head.put(packet.getPayload());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return head.array();
	}
}
