package xueli.game2.network.pipeline;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xueli.game2.network.ByteBufReadable;
import xueli.game2.network.PrimitiveCodec;

public class PacketSizeDecodeHandler extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();

		ByteBufReadable readable = new ByteBufReadable(in);
		int read = PrimitiveCodec.VAR_INT.read(readable);

		if (read <= in.readableBytes()) {
			out.add(in.readBytes(read));
		} else {
			in.resetReaderIndex();
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

}
