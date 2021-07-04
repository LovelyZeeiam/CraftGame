package net.dengzixu.java.body.resolver;

import java.util.HashMap;
import java.util.Map;

import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.message.Message;

public class LiveStartResolver extends BodyResolver {
	public LiveStartResolver(Map<String, Object> payloadMap) {
		super(new HashMap<String, Object>() {
			private static final long serialVersionUID = 5971472071131258563L;

			{
				put("cmd", BodyCommandEnum.LIVE_START);
			}
		});
	}

	@Override
	public Message resolve() {
		return new Message() {
			{
				setBodyCommand(BodyCommandEnum.LIVE_START);
			}
		};
	}
}
