package xueli.game2.network.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xueli.game2.network.ByteBufWritable;
import xueli.game2.network.PrimitiveCodec;

public class PacketSizePrefixer extends MessageToByteEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int byteCount = msg.readableBytes();
		ByteBufWritable writable = new ByteBufWritable(out);
		PrimitiveCodec.VAR_INT.write(byteCount, writable);
		out.writeBytes(msg, msg.readerIndex(), byteCount);

	}

}
