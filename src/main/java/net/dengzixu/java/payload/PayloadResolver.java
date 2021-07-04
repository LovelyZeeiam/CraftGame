package net.dengzixu.java.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.dengzixu.java.body.resolver.BodyResolver;
import net.dengzixu.java.body.resolver.DanmuBodyResolver;
import net.dengzixu.java.body.resolver.LiveStartResolver;
import net.dengzixu.java.body.resolver.SendGiftResolver;
import net.dengzixu.java.body.resolver.UnknownBodyResolver;
import net.dengzixu.java.body.resolver.WelcomeBodyResolver;
import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.constant.PacketOperationEnum;
import net.dengzixu.java.message.Message;

public class PayloadResolver {
	private final byte[] payload;
	private final PacketOperationEnum operation;

	public PayloadResolver(@NotNull String payload, PacketOperationEnum packetOperationEnum) {
		this(payload.getBytes(StandardCharsets.UTF_8), packetOperationEnum);
	}

	public PayloadResolver(byte[] payload, PacketOperationEnum packetOperationEnum) {
		this.payload = payload;
		this.operation = packetOperationEnum;
	}

	@SuppressWarnings("unchecked")
	public Message resolve() {
		Message message = new Message();

		switch (operation) {
		case OPERATION_3: {
			ByteBuffer byteBuffer = ByteBuffer.allocate(payload.length).put(payload);
			message.setBodyCommand(BodyCommandEnum.POPULARITY);
			message.setContent(new HashMap<>() {
				private static final long serialVersionUID = 4063561289525488887L;

				{
					put("popularity", byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt(0));
				}
			});

			break;
		}
		case OPERATION_5: {
			Map<String, Object> payloadMap;
			try {
				// 反序列化
				payloadMap = new ObjectMapper().readValue(new String(payload), Map.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				break;
			}

			// 根据 cmd 解析
			BodyResolver bodyResolver;

			switch (BodyCommandEnum.getEnum((String) payloadMap.get("cmd"))) {
			case DANMU_MSG:
				bodyResolver = new DanmuBodyResolver(payloadMap);
				break;
			case INTERACT_WORD:
				bodyResolver = new WelcomeBodyResolver(payloadMap);
				break;
			case SEND_GIFT:
				bodyResolver = new SendGiftResolver(payloadMap);
				break;
			case LIVE_START:
				bodyResolver = new LiveStartResolver(payloadMap);
				break;
			case STOP_LIVE_ROOM_LIST:
			default:
				bodyResolver = new UnknownBodyResolver(null);
			}
			try {
				message = bodyResolver.resolve();
			} catch (Exception e) {
				message = new Message() {
					{
						setBodyCommand(BodyCommandEnum.UNKNOWN);
					}
				};
				e.printStackTrace();
			}
			break;
		}
		case OPERATION_8: {
			Map<String, Object> payloadMap;
			try {
				// 反序列化
				payloadMap = new ObjectMapper().readValue(new String(payload), Map.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				break;
			}
			// 特殊处理
			try {
				if ((int) payloadMap.get("code") == 0) {
					message = new Message() {
						{
							setBodyCommand(BodyCommandEnum.AUTH_SUCCESS);
						}
					};
				}
			} catch (Exception ignored) {
			}
			break;
		}
		default:
			message = new Message() {
				{
					setBodyCommand(BodyCommandEnum.UNKNOWN);
				}
			};

		}
		return message;
	}
}
