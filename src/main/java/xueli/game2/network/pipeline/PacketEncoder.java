package xueli.game2.network.pipeline;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xueli.game2.network.ByteBufWritable;
import xueli.game2.network.Packet;
import xueli.game2.network.PrimitiveCodec;
import xueli.game2.network.Protocol;
import xueli.game2.network.Writable;
import xueli.utils.logger.Logger;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	private static final Logger LOGGER = new Logger();

	private Protocol protocol;

	public PacketEncoder(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		int id = protocol.getIdByClazz(msg.getClass());
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
