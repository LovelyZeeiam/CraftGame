package net.dengzixu.java.body.resolver;

import java.util.Map;

import net.dengzixu.java.exception.ErrorCmdException;
import net.dengzixu.java.message.Message;

public abstract class BodyResolver {
	protected Map<String, Object> payloadMap;
	protected String payloadCmd;

	public BodyResolver(Map<String, Object> payloadMap) {
		this.payloadMap = payloadMap;

		if (null == payloadMap) {
			throw new NullPointerException();
		}

		try {
			payloadCmd = (String) payloadMap.get("cmd");
		} catch (Exception e) {
			throw new ErrorCmdException();
		}

		if (null == payloadCmd) {
			throw new ErrorCmdException();
		}
	}

	public abstract Message resolve();
}
