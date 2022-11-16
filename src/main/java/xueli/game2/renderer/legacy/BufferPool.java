package xueli.game2.renderer.legacy;

import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;

import java.util.LinkedList;

/*
  Some Exceptions natively will be thrown:
  <code>
  Current thread (0x000001532bcffd70):  JavaThread "main" [_thread_in_native, id=11436, stack(0x00000004dc400000,0x00000004dc500000)]

  Stack: [0x00000004dc400000,0x00000004dc500000],  sp=0x00000004dc4fe710,  free space=1017k
  Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
  C  [ntdll.dll+0xa6d98]
  C  [ntdll.dll+0x406e1]
  C  [AcLayers.DLL+0x72af]
  C  [atio6axx.dll+0x1af18bc]

  Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)
  J 1274  org.lwjgl.opengl.GL15C.nglBufferData(IJJI)V (0 bytes) @ 0x000001533b0985f6 [0x000001533b0985a0+0x0000000000000056]
  j  org.lwjgl.opengl.GL15C.glBufferData(ILjava/nio/ByteBuffer;I)V+11
  j  org.lwjgl.opengl.GL15.glBufferData(ILjava/nio/ByteBuffer;I)V+3
  j  xueli.game2.renderer.legacy.buffer.AttributeBuffer.lambda$tick$0()V+8
  j  xueli.game2.renderer.legacy.buffer.AttributeBuffer$$Lambda$92+0x0000000800cc78f0.run()V+4
  J 1275 c1 xueli.game2.renderer.legacy.buffer.Bindable.bind(Ljava/lang/Runnable;)V (19 bytes) @ 0x0000015333d238a4 [0x0000015333d236e0+0x00000000000001c4]
  j  xueli.game2.renderer.legacy.buffer.AttributeBuffer.tick()V+21
  j  xueli.game2.renderer.legacy.buffer.VertexAttribute$$Lambda$91+0x0000000800cc76c8.accept(Ljava/lang/Object;)V+4
  J 1279 c1 java.util.HashMap$Values.forEach(Ljava/util/function/Consumer;)V java.base@17-ea (119 bytes) @ 0x0000015333d2491c [0x0000015333d24700+0x000000000000021c]
  j  xueli.game2.renderer.legacy.buffer.VertexAttribute.render(I)V+16
  j  xueli.game2.renderer.legacy.VertexAttributeRenderBuffer.render()V+8
  j  xueli.mcremake.classic.client.renderer.gui.RenderTypeTexture2D.lambda$render$1(Ljava/lang/Integer;Lxueli/game2/renderer/legacy/RenderBuffer;)V+16
  j  xueli.mcremake.classic.client.renderer.gui.RenderTypeTexture2D$$Lambda$90+0x0000000800cc74a0.accept(Ljava/lang/Object;Ljava/lang/Object;)V+8
  J 1242 c1 java.util.HashMap.forEach(Ljava/util/function/BiConsumer;)V java.base@17-ea (112 bytes) @ 0x0000015333d15ba4 [0x0000015333d15980+0x0000000000000224]
  j  xueli.mcremake.classic.client.renderer.gui.RenderTypeTexture2D.render()V+16
  j  xueli.mcremake.classic.client.renderer.gui.MyFont.tick()V+54
  j  xueli.game2.renderer.ui.MyGui.tick()V+4
  j  xueli.mcremake.classic.client.CraftGameClient.render()V+22
  j  xueli.game2.display.GameDisplay.tick()V+72
  j  xueli.game2.lifecycle.RunnableLifeCycle.run()V+16
  j  xueli.mcremake.classic.client.CraftGameMain.main([Ljava/lang/String;)V+7
  v  ~StubRoutines::call_stub

  siginfo: EXCEPTION_ACCESS_VIOLATION (0xc0000005), reading address 0xffffffffffffffff
  </code>
*/
@Deprecated
public class BufferPool {

	private final LinkedList<LotsOfByteBuffer> bufferPool = new LinkedList<>();
	private final int maxCount;
	private final int allocateSizeAccordingToBefore;

	public BufferPool(int maxCount, int allocateSizeAccordingToBefore) {
		this.maxCount = maxCount;
		this.allocateSizeAccordingToBefore = allocateSizeAccordingToBefore;
	}

	public LotsOfByteBuffer newBuffer() {
		if(bufferPool.size() > this.maxCount) {
			LotsOfByteBuffer buf = bufferPool.pop();
			buf.release();
		}

		LotsOfByteBuffer buf;
		if(allocateSizeAccordingToBefore > 0) {
			int accordCount = Math.min(bufferPool.size(), this.allocateSizeAccordingToBefore);
			int sizeSum = 0;

			for (int i = 1; i <= accordCount; i++) {
				LotsOfByteBuffer lastBuf = bufferPool.get(bufferPool.size() - i);
				sizeSum += lastBuf.getSize();
			}

			if(sizeSum > 0) {
				buf = new LotsOfByteBuffer(sizeSum / accordCount);
			} else {
				buf = new LotsOfByteBuffer();
			}

		} else {
			buf = new LotsOfByteBuffer();
		}
		bufferPool.addLast(buf);

//		System.out.println(this.bufferPool);

		return buf;
	}

}
