package xueli.game2.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xueli.game2.network.Readable;
import xueli.game2.network.*;
import xueli.utils.logger.MyLogger;

import java.util.List;
import java.util.function.Function;

public class PacketDecoder extends ByteToMessageDecoder {

	private static final MyLogger LOGGER = new MyLogger() {
		{
			pushState("PacketDecoder");
		}
	};

	private PacketSourceSide side;

	public PacketDecoder(PacketSourceSide side) {
		this.side = side;

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			Readable readable = new ByteBufReadable(in);
			int id = PrimitiveCodec.VAR_INT.read(readable);

			Function<Readable, Packet> deserializer = side.getProtocol().getDeserializerById(id);
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
