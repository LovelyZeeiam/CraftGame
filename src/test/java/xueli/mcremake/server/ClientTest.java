package xueli.mcremake.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.UUID;

import xueli.game2.network.ClientConnection;
import xueli.mcremake.classic.network.PacketSourceSide;
import xueli.mcremake.classic.network.protocol.C00HelloPacket;

public class ClientTest {

	public static void main(String[] args) throws IOException {
		ClientConnection conn = ClientConnection.connectToServer(PacketSourceSide.FROM_SERVER.getProtocol(), PacketSourceSide.FROM_CLIENT.getProtocol(), new InetSocketAddress(8000));
		conn.writeAndFlush(new C00HelloPacket("lovelizeeiam", UUID.randomUUID()), () -> {
			conn.close();
		});
		
		
	}

}
