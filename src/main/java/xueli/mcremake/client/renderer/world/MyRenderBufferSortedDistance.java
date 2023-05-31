package xueli.mcremake.client.renderer.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import xueli.game2.Vector;
import xueli.game2.renderer.legacy.buffer.ElementBuffer;
import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;

public class MyRenderBufferSortedDistance extends MyRenderBuffer {

	private final ElementBuffer ebo = new ElementBuffer();
	private LotsOfByteBuffer elementBuffer;

	@Override
	public void setVertexCount(int count) {
		super.setVertexCount(count);

		if (elementBuffer != null) {
			elementBuffer.release();
		}
		elementBuffer = new LotsOfByteBuffer(count * Integer.BYTES);
		ebo.updateBuffer(elementBuffer);

	}

	public void renderSorted(Vector camVector) {
//		int vertCount = this.getVertCount();
//		AttributeBuffer vertBuffer = this.attr.getAttributeBuffer(ATTR_VERTEX);
//		var latestVertBuffer = vertBuffer.getLatestBuffer();
//		if(latestVertBuffer != null) {
//			FloatBuffer vertRawBuf = latestVertBuffer.asFloatBuffer();
//			
//			elementBuffer.setReadWrite(false);
//			elementBuffer.clear();
//			SortTask.genSortedElementBuffer(vertRawBuf, vertCount, elementBuffer);
//			elementBuffer.setReadWrite(true);
//			ebo.updateBuffer(elementBuffer);
//			
//			ebo.tick();
//			this.attr.renderElement(ebo, vertCount);
//		}
		this.render();

	}

	@Override
	public void release() {
		ebo.release();
		super.release();

	}

}

class SortTask {

	public static void genSortedElementBuffer(FloatBuffer vertRawBuf, int vertCount, LotsOfByteBuffer elementBuffer) {
		int triangleCount = vertCount / 3;

		for (int i = 0; i < vertCount; i++) {
			elementBuffer.put(i);
		}
		ArrayList<Integer> selectedList = new ArrayList<>();
		for (int i = 0; i < triangleCount; i++) {
			selectedList.add(i);
			writeElementBuffer(i, elementBuffer);
		}
		sortElementBuffer(vertRawBuf, selectedList, elementBuffer);

	}

//	public static void dumpElementBuffer(LotsOfByteBuffer buf) {
//		IntBuffer raw = buf.getBuffer().asIntBuffer();
//		System.out.print("[");
//		for(int i = 0; i < raw.limit(); i++) {
//			System.out.print(raw.get(i));
//			System.out.print(", ");
//		}
//		System.out.println("]");
//		
//	}

	private static void sortElementBuffer(FloatBuffer vertRawBuf, List<Integer> selected,
			LotsOfByteBuffer elementBuffer) {
		if (selected.size() < 1)
			return;
		if (selected.size() == 1) {
			writeElementBuffer(selected.get(0), elementBuffer);
			return;
		}

		double middle = getDistance(vertRawBuf, selected.get(0));
		ArrayList<Integer> left = new ArrayList<>();
		ArrayList<Integer> right = new ArrayList<>();

		for (int i = 1; i < selected.size(); i++) {
			double val = getDistance(vertRawBuf, selected.get(i));
			if (val < middle) {
				left.add(selected.get(i));
			} else {
				right.add(selected.get(i));
			}
		}

		sortElementBuffer(vertRawBuf, left, elementBuffer);
		writeElementBuffer(selected.get(0), elementBuffer);
		sortElementBuffer(vertRawBuf, right, elementBuffer);

	}

	private static void writeElementBuffer(int i, LotsOfByteBuffer buf) {
		buf.put(i * 3);
		buf.put(i * 3 + 1);
		buf.put(i * 3 + 2);

	}

	private static double getDistance(FloatBuffer vertRawBuf, int offset) {
		double d1 = Math.sqrt(Math.pow(vertRawBuf.get(offset * 9), 2) + Math.pow(vertRawBuf.get(offset * 9 + 1), 2)
				+ Math.pow(vertRawBuf.get(offset * 9 + 2), 2));
		double d2 = Math.sqrt(Math.pow(vertRawBuf.get(offset * 9 + 3), 2) + Math.pow(vertRawBuf.get(offset * 9 + 4), 2)
				+ Math.pow(vertRawBuf.get(offset * 9 + 5), 2));
		double d3 = Math.sqrt(Math.pow(vertRawBuf.get(offset * 9 + 6), 2) + Math.pow(vertRawBuf.get(offset * 9 + 7), 2)
				+ Math.pow(vertRawBuf.get(offset * 9 + 8), 2));
		return d1 + d2 + d3;
	}

}
