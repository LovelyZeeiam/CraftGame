package xueli.craftgame.server;

import xueli.craftgame.message.MessageNotAssociatedWithWorld;
import xueli.craftgame.message.MessageObject;

public class MessagePlayerInfo extends MessageObject<PlayerInfo> implements MessageNotAssociatedWithWorld {

	public MessagePlayerInfo(PlayerInfo playerInfo) {
		super(playerInfo);
	}

}
