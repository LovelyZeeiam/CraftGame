package net.dengzixu.java.websocket;

import net.dengzixu.java.message.Message;

public interface LiveMessageCallback {

	public void onMessage(Message m) throws Exception;

}
