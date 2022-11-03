package xueli.game2.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xueli.game2.network.*;
import xueli.utils.logger.MyLogger;

import java.io.IOException;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private static final MyLogger LOGGER = new MyLogger() {
		{
			pushState("PacketEncoder");
		}
	};

	private PacketSourceSide side;

	public PacketEncoder(PacketSourceSide side) {
		this.side = side;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		int id = side.getProtocol().getIdByClazz(msg.getClass());
		Writable writable = new ByteBufWritable(out) {
			@Override
			public void writeByte(byte b) throws IOException {
				super.writeByte(b);
//				System.out.println(b);
			}
		};

		PrimitiveCodec.VAR_INT.write(id, writable);
		msg.write(writable);

		LOGGER.info("Write Packet: " + msg);

	}

}
