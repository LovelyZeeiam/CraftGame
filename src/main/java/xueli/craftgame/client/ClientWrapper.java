package xueli.craftgame.client;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import xueli.craftgame.message.Message;
import xueli.craftgame.message.MessageNotAssociatedWithWorld;
import xueli.craftgame.server.MessageServerSideAuthentication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientWrapper implements IoHandler, AutoCloseable {

	private static final int CONNECT_TIMEOUT = 5000;

	private String hostname;
	private int port;

	private IoSession session;
	private NioSocketConnector connector;

	private boolean authentiated = false;
	private ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<>();

	public ClientWrapper(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;

	}

	public void start() throws IOException {
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		connector.getSessionConfig().setReadBufferSize(2048 * 2048);

		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		connector.getFilterChain().addLast("logger", new LoggingFilter());

		connector.setHandler(this);

		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(hostname, port));
			future.awaitUninterruptibly();
			this.session = future.getSession();
		} catch (RuntimeIoException e) {
			throw new IOException(e);
		}

	}

	public Message popMessage() {
		return queue.poll();
	}

	public WriteFuture sendMessage(Message msg) {
		return session.write(msg);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
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
			// System.out.println(msg);
			if (msg instanceof MessageNotAssociatedWithWorld) {
				processMessageClientside(session, msg);
			} else {
				queue.add(msg);
			}
		}

	}

	private void processMessageClientside(IoSession session, Message msg) {
		if(msg instanceof MessageServerSideAuthentication) {
			this.authentiated = true;
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}

	public boolean isAuthentiated() {
		return authentiated;
	}

	public void close() {
		session.close(true).awaitUninterruptibly();
		connector.dispose();

	}

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

}
