package net.dengzixu.java.body.resolver;

import java.util.HashMap;
import java.util.Map;

import net.dengzixu.java.constant.BodyCommandEnum;
import net.dengzixu.java.message.Message;

public class UnknownBodyResolver extends BodyResolver {
	public UnknownBodyResolver(Map<String, Object> payloadMap) {
		super(new HashMap<String, Object>() {
			private static final long serialVersionUID = 5971472071131258563L;

			{
				put("cmd", "unknown");
			}
		});
	}

	@Override
	public Message resolve() {
		return new Message() {
			{
				setBodyCommand(BodyCommandEnum.UNKNOWN);
			}
		};
	}
}
