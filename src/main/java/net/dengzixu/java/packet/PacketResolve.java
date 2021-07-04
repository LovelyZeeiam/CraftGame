package net.dengzixu.java.packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import net.dengzixu.java.constant.PacketProtocolVersionEnum;
import net.dengzixu.java.exception.UnknownProtocolVersionException;
import net.dengzixu.java.utils.BrotliUtil;
import net.dengzixu.java.utils.ZlibUtil;

public class PacketResolve {
	private final byte[] rawData;
	private final int rawDataLength;

	private int currentOffset;

	private List<Packet> resultPacketList = new ArrayList<>();

	public PacketResolve(byte[] rawData) {
		if (null == rawData) {
			throw new NullPointerException();
		}
		this.rawData = rawData;
		this.rawDataLength = rawData.length;
	}

	public List<Packet> getPacketList() {
		// 根据传入的数据分配空间
		final ByteBuffer byteBuffer = ByteBuffer.allocate(rawDataLength);

		// 数据装入
		byteBuffer.put(rawData);

		// 需要判断一下数据包里是不是只有一条数据
		while (currentOffset < rawDataLength) {
			Packet resolvedPacket = new Packet() {
				{
					setPacketLength(byteBuffer.getInt(currentOffset));
					setHeaderLength(byteBuffer.getShort(currentOffset + 4));
					setProtocolVersion(byteBuffer.getShort(currentOffset + 6));
					setOperation(byteBuffer.getInt(currentOffset + 8));
					setSequenceId(byteBuffer.getInt(currentOffset + 12));
					byte[] bodyBytes = new byte[getPacketLength() - 16];

					for (int i = 0; i < bodyBytes.length; i++) {
						bodyBytes[i] = byteBuffer.get(currentOffset + 16 + i);
					}

					setPayload(bodyBytes);
				}
			};
			resultPacketList.add(resolvedPacket);
			currentOffset += resolvedPacket.getPacketLength();
		}

		// TODO 猜测 如果大于 1 个数据包 数据包就不再可能是压缩的
		if (resultPacketList.size() > 1) {
			return resultPacketList;
		}

		// 根据 Protocol Version 进行处理
		switch (PacketProtocolVersionEnum.getEnum(resultPacketList.get(0).getProtocolVersion())) {
		// 如果协议版本为 0 或 1 直接返回
		case PROTOCOL_VERSION_0:
		case PROTOCOL_VERSION_1:
			break;
		// 如果协议版本为 2 就解压一下
		case PROTOCOL_VERSION_2: {
			byte[] compressedData = ZlibUtil.inflate(resultPacketList.get(0).getPayload());
			// 递归一把梭
			resultPacketList = new PacketResolve(compressedData).getPacketList();
			break;
		}
		case PROTOCOL_VERSION_3: {// System.out.println("PROTOCOL_VERSION_3 无法处理");
			byte[] compressedData = BrotliUtil.unCompress(resultPacketList.get(0).getPayload());
			// 递归一把梭
			System.out.println(new String(compressedData));
			resultPacketList = new PacketResolve(compressedData).getPacketList();
			break;
		}
		default:
			throw new UnknownProtocolVersionException();
		}
		return resultPacketList;
	}
}
