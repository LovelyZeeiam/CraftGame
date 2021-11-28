package xueli.craftgame.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

public class CraftgameServer {

	public CraftgameServer() {

	}

	public void run() {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap();

	}

	public static void main(String[] args) {
		new CraftgameServer().run();
	}

}
