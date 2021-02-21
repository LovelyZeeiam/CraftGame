package xueli.craftgame.net.message;

import java.nio.charset.Charset;

public class HandshakeMessages {

	// 13

	public static final Charset STANDARD_CHARSET = Charset.forName("UTF-8");

	/**
	 * 握手专用指令 所以在Server里面的方法是已经写死了的
	 */
	public static final byte PREPARE_SPAWN_POINT = 0;
	public static final byte PREPARE_SPAWN_POINT_OK = 1;

	public static final byte PLAYER_CONNECT = 2;
	public static final byte PLAYER_LOST_CONNECTION = 3;
	public static final byte PLAYER_RECONNECT = 4;
	public static final byte PLAYER_DISCONNECT = 5;

	public static final byte HANDSHAKE_CLIENT_REQUEST_PLAYER_POSITION = 10;
	public static final byte HANDSHAKE_SERVER_ANSWER_PLAYER_POSITION = 11;

	public static final byte EVENT = 9;

	public static final byte CLIENT_ENTER_GAMEPLAY = 12;
	public static final byte CLIENT_END_GAMEPLAY = 13;

	public static final byte SERVER_CLOSE = 8;

	private HandshakeMessages() {
	}

}
