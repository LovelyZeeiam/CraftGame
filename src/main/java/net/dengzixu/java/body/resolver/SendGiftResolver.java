package net.dengzixu.java.body.resolver;

import java.util.HashMap;
import java.util.Map;

import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.message.FansMedal;
import net.dengzixu.java.message.Message;
import net.dengzixu.java.message.UserInfo;

public class SendGiftResolver extends BodyResolver {
	private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.SEND_GIFT;

	public SendGiftResolver(Map<String, Object> payloadMap) {
		super(payloadMap);

		if (!BODY_COMMAND.toString().equals(this.payloadCmd)) {
			throw new ErrorCmdException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Message resolve() {
		Message message = new Message();
		message.setBodyCommand(BODY_COMMAND);

		final Map<String, Object> dataMap = (Map<String, Object>) payloadMap.get("data");

		// 礼物信息
		message.setContent(new HashMap<>() {
			private static final long serialVersionUID = 2836703446357571152L;

			{
				put("coin_type", dataMap.get("coin_type"));
				put("gift_id", dataMap.get("giftId"));
				put("gift_name", dataMap.get("giftName"));
				put("gift_type", dataMap.get("giftType"));
				put("num", dataMap.get("num"));
				put("price", dataMap.get("price"));
			}
		});

		// 用户信息
		message.setUserInfo(new UserInfo() {
			{
				setUsername((String) dataMap.get("uname"));
				setUid((int) dataMap.get("uid"));
				setFace((String) dataMap.get("face"));
			}
		});

		// 粉丝牌信息
		final Map<String, Object> fansMedalMap = (Map<String, Object>) dataMap.get("medal_info");
		message.setFansMedal(new FansMedal() {
			{
				setMedalLevel((int) fansMedalMap.get("medal_level"));
				setMedalName((String) fansMedalMap.get("medal_name"));
				setMedalColor((int) fansMedalMap.get("medal_color"));
				setMedalColorBorder((int) fansMedalMap.get("medal_color_border"));
				setMedalColorStart((int) fansMedalMap.get("medal_color_start"));
				setMedalColorEnd((int) fansMedalMap.get("medal_color_end"));
				setLighted((int) fansMedalMap.get("is_lighted") == 1);
			}
		});

		return message;
	}
}
