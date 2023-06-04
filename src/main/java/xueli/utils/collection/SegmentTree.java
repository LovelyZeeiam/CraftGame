package xueli.utils.collection;

import java.util.Arrays;
import java.util.Objects;

// Who put it here? —— XueLi
public class SegmentTree {

	private TreeNode[] nodes;

	public SegmentTree(int[] list) {
		Objects.requireNonNull(list);
		this.nodes = new TreeNode[list.length * list.length * list.length];
		this.buildTree(1, 1, list.length, list);
		System.out.println(Arrays.toString(this.nodes));

	}

	private void buildTree(int nodeIndex, int left, int right, int[] list) {
		TreeNode node = this.nodes[nodeIndex] = new TreeNode();
		node.left = left;
		node.right = right;
		if (left == right) {
			node.value = list[left - 1];
			return;
		}
		int middle = (left + right) >> 1;
//		int middle = (int) Math.sqrt(left * right);
		int leftIndex = nodeIndex << 1, rightIndex = leftIndex | 1;
		this.buildTree(leftIndex, left, middle, list);
		this.buildTree(rightIndex, middle + 1, right, list);
		node.value = this.nodes[leftIndex].value + this.nodes[rightIndex].value;
	}

	public int sum(int left, int right) {
		return this.sum(1, left, right);
	}

	private int sum(int nodeIndex, int left, int right) {
		TreeNode node = this.nodes[nodeIndex];
		if (left <= node.left && right >= node.right)
			return node.value;
		if ((left <= node.right && left >= node.left) || (right <= node.right && right >= node.left)) {
			int s = 0;
			int leftIndex = nodeIndex << 1, rightIndex = leftIndex | 1;
			s += sum(leftIndex, left, right);
			s += sum(rightIndex, left, right);
			return s;
		}
		return 0;
	}

	private static class TreeNode {
		public int value = 0;
		public int left, right;
		public int lazy = 0;

		@Override
		public String toString() {
			return String.format("{%s, %s, %s, %s}", value, lazy, left, right);
		}

	}

	public static void main(String[] args) {
		var tree = new SegmentTree(new int[] { 1, 5, 2, 5, 3, 1, 3, 4, 6, 6, 3, 2, 4, 5, 6, 5, 2, 3 });
		System.out.println(tree.sum(2, 21));

	}

}
