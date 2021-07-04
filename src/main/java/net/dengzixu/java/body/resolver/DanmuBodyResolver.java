package net.dengzixu.java.body.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.message.FansMedal;
import net.dengzixu.java.message.Message;
import net.dengzixu.java.message.UserInfo;

public class DanmuBodyResolver extends BodyResolver {
	private static final BodyCommandEnum BODY_COMMAND = BodyCommandEnum.DANMU_MSG;

	public DanmuBodyResolver(Map<String, Object> payloadMap) {
		super(payloadMap);

		if (!BODY_COMMAND.toString().equals(this.payloadCmd)) {
			throw new ErrorCmdException();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Message resolve() {
		Message message = new Message();
		message.setBodyCommand(BODY_COMMAND);

		// ["info"] 弹幕内容
		final ArrayList<Object> infoList = (ArrayList) this.payloadMap.get("info");

		message.setContent(new HashMap<>() {
			private static final long serialVersionUID = 2675283830949520146L;
			{
				put("danmu", infoList.get(1));
			}
		});

		// 用户信息
		final ArrayList<Object> userInfoList = (ArrayList) infoList.get(2);
		message.setUserInfo(new UserInfo() {
			{
				setUid((int) userInfoList.get(0));
				setUsername((String) userInfoList.get(1));
			}
		});

		// 粉丝牌信息
		final ArrayList<Object> fansMedalList = (ArrayList) infoList.get(3);
		if (!fansMedalList.isEmpty()) {
			message.setFansMedal(new FansMedal() {
				{
					setMedalLevel((int) fansMedalList.get(0));
					setMedalName((String) fansMedalList.get(1));
					setMedalColor((int) fansMedalList.get(4));
					setMedalColorBorder((int) fansMedalList.get(7));
					setMedalColorStart((int) fansMedalList.get(8));
					setMedalColorEnd((int) fansMedalList.get(9));
					setLighted((int) fansMedalList.get(11) == 1);
				}
			});
		}

		return message;
	}
}
