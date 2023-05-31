package xueli.game2.network.pipeline;

import java.util.List;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xueli.game2.network.ByteBufReadable;
import xueli.game2.network.Packet;
import xueli.game2.network.PrimitiveCodec;
import xueli.game2.network.Protocol;
import xueli.game2.network.Readable;
import xueli.utils.logger.Logger;

public class PacketDecoder extends ByteToMessageDecoder {

	private static final Logger LOGGER = new Logger();

	private Protocol protocol;

	public PacketDecoder(Protocol protocol) {
		this.protocol = protocol;

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			Readable readable = new ByteBufReadable(in);
			int id = PrimitiveCodec.VAR_INT.read(readable);

			Function<Readable, Packet> deserializer = protocol.getDeserializerById(id);
			if (deserializer != null) {
				Packet packet = deserializer.apply(readable);
				out.add(packet);

				LOGGER.info("Receive Packet: " + packet);

			} else {
				LOGGER.info("Receive Packet Error: " + id);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
