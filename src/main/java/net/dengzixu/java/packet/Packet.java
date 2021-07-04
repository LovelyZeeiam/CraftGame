package net.dengzixu.java.packet;

public class Packet {
	/**
	 * 数据包长度 Packet Length
	 */
	private int packetLength;

	/**
	 * 数据包头部长度 (固定为 16) Header Length
	 */
	private short headerLength;

	/**
	 * 协议版本 Protocol Version
	 */
	private short protocolVersion;

	/**
	 * 操作类型 Operation
	 */
	private int operation;

	/**
	 * 数据包头部长度 (固定为 1) Sequence Id
	 */
	private int sequenceId;

	/**
	 * 数据包主体
	 */
	private byte[] payload;

	public int getPacketLength() {
		return packetLength;
	}

	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}

	public short getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(short headerLength) {
		this.headerLength = headerLength;
	}

	public short getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(short protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public int getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Packet{" + "packetLength=" + packetLength + ", headerLength=" + headerLength + ", protocolVersion="
				+ protocolVersion + ", operation=" + operation + ", sequenceId=" + sequenceId + ", payload="
				+ new String(payload) + '}';
	}

}