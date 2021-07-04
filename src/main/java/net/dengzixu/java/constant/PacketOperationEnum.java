package net.dengzixu.java.constant;

import java.util.HashMap;
import java.util.Map;

public enum PacketOperationEnum {
	/**
	 * 操作类型 2: 客户端 (空) 心跳 不发送心跳包，70 秒之后会断开连接，通常每 30 秒发送 1 次 3: 服务器 Int 32 Big Endian
	 * 心跳回应 Body 内容为房间人气值 5: 服务器 JSON 通知 弹幕、广播等全部信息 7: 客户端 JSON 进房 WebSocket
	 * 连接成功后的发送的第一个数据包，发送要进入房间 ID 8: 服务器 (空) 进房回应
	 */
	OPERATION_2(2), OPERATION_3(3), OPERATION_5(5), OPERATION_7(7), OPERATION_8(8),

	OPERATION_UNKNOWN(-1);

	private final Integer packetOperation;

	private static final Map<Integer, PacketOperationEnum> enumMap = new HashMap<>();

	static {
		for (PacketOperationEnum e : values()) {
			enumMap.put(e.operation(), e);
		}
	}

	PacketOperationEnum(Integer operation) {
		this.packetOperation = operation;
	}

	public static PacketOperationEnum getEnum(int operation) {
		return null == enumMap.get(operation) ? OPERATION_UNKNOWN : enumMap.get(operation);
	}

	public Integer operation() {
		return this.packetOperation;
	}

	@Override
	public String toString() {
		return String.valueOf(this.packetOperation);
	}
}
