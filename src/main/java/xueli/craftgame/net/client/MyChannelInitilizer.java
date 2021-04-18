package xueli.craftgame.net.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MyChannelInitilizer extends ChannelInitializer<SocketChannel> {

	private Client client;

	public MyChannelInitilizer(Client client) {
		this.client = client;

	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
		pipeline.addLast("encoder", new ObjectEncoder());
		pipeline.addLast("handler", client);

	}

}
