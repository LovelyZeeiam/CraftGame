package xueli.craftgame.net.interchange;

import xueli.craftgame.net.server.ServerBean;

public class MessageServerBean extends Message {

	private static final long serialVersionUID = 6212479347017083313L;

	public MessageServerBean(ServerBean bean) {
		super("SERVER_BEAN", bean);

	}

}
