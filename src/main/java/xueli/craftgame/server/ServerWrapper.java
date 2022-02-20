package xueli.craftgame.server;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import xueli.craftgame.message.Message;
import xueli.craftgame.message.MessageNotAssociatedWithWorld;
import xueli.craftgame.message.MessagePlayerState;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerWrapper implements IoHandler, AutoCloseable {

	private int port;
	private IoAcceptor acceptor;

	private HashMap<SocketAddress,ServerPlayer> players = new HashMap<>();
	private ConcurrentLinkedQueue<WrappedMessage> queue = new ConcurrentLinkedQueue<>();

	public ServerWrapper(int port) {
		this.port = port;
	}

	public void start() throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048 * 2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
		acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new ObjectSerializationCodecFactory()));

		acceptor.setHandler(this);

		acceptor.bind(new InetSocketAddress(port));

	}

	public WrappedMessage popMessage() {
		return queue.poll();
	}

	public void sendMessage(Message msg, boolean awaitUninterruptibly) {
		sendMessage(msg, players.values(), awaitUninterruptibly);
	}

	public void sendMessage(Message msg, Collection<ServerPlayer> targets, boolean awaitUninterruptibly) {
		Collection<ServerPlayer> realTargets = targets == null ? this.players.values() : targets;
		for (ServerPlayer target : realTargets) {
			WriteFuture writeFuture = target.getSession().write(msg);
			if(awaitUninterruptibly) {
				writeFuture.awaitUninterruptibly();
			}
		}
	}

	public void close() throws Exception {
		acceptor.unbind();
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		ServerPlayer from = players.get(session.getRemoteAddress());
		if(from == null) {
			return;
		}

		players.remove(session.getRemoteAddress());

		sendMessage(new MessagePlayerState(from.getInfo(), MessagePlayerState.State.EXIT), players.values(), false);

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if(message instanceof Message msg) {
			if (msg instanceof MessageNotAssociatedWithWorld) {
				// System.out.println(msg);
				processMessageServerside(session, msg);
			} else {
				ServerPlayer from = players.get(session.getRemoteAddress());
				if(from == null) {
					return;
				}
				queue.add(new WrappedMessage(msg, from));
			}
		}

	}

	private void processMessageServerside(IoSession session, Message msg) {
		if(msg instanceof MessagePlayerInfo info) {
			if(!players.containsKey(session.getRemoteAddress())) {
				PlayerInfo playerInfo = info.getValue();
				ServerPlayer serverPlayer = new ServerPlayer(session, playerInfo);
				players.put(session.getRemoteAddress(), serverPlayer);

				sendMessage(new MessageServerSideAuthentication(), new ArrayList<>() {
					{
						add(serverPlayer);
					}
				}, false);
				sendMessage(new MessagePlayerState(playerInfo, MessagePlayerState.State.JOIN), false);
			}
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}

	public HashMap<SocketAddress, ServerPlayer> getPlayers() {
		return players;
	}

	public int getPort() {
		return port;
	}

}
