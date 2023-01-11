package xueli.game2.ecs;

import java.util.ArrayList;
import java.util.Arrays;

public class SparseSet {
	
	private final int sparsePageSize;
	private final ArrayList<Integer> data = new ArrayList<>();
	private final ArrayList<int[]> sparse = new ArrayList<>();
	
	public SparseSet() {
		this(32);
	}
	
	public SparseSet(int sparsePageSize) {
		this.sparsePageSize = sparsePageSize;
	}
	
	/**
	 * Add an element to this set
	 * @param i The element to be added to the set
	 * @return Whether the operation ends successfully
	 */
	public boolean add(int i) {
		// Because the initial value of array is 0, so we add one to the real data to avoid some problems.
		if(!setSparse(i, data.size() + 1, false))
			return false;
		data.add(i);
		return true;
	}
	
	/**
	 * Get the index of an element.
	 * When the value is 0, then this element isn't in the set. So the index actually starts at 1.
	 * @param i 
	 * @return The index of element
	 */
	public int indexOf(int i) {
		return getSparse(i);
	}
	
	// To save the cost, I duplicate some codes.
	public boolean remove(int i) {
		// Get sparse data of the element to be removed and its page array.
		int yInSparse = i / sparsePageSize;
		if(yInSparse > sparse.size())
			return false;
		int[] targetPage = this.sparse.get(yInSparse);
		int xInSparse = i % sparsePageSize;
		// Remember this index is started from 1. We have to subtract the number by one to get its real index.
		int dataIndex = targetPage[xInSparse];
		if(dataIndex <= 0)
			return false;
		
		// To save the cost of moving every element backward,
		// First we exchange the position of the element to be removed and the last element.
		// But we don't have to literally set the last element to the element to be removed.
		int lastElementIndex = this.data.size() - 1;
		int lastElement = this.data.get(lastElementIndex);
		setSparse(lastElement, dataIndex, true);
		this.data.set(dataIndex - 1, lastElement);
		// Then we "pop" the last element
		this.data.remove(lastElementIndex);
		targetPage[xInSparse] = 0;
		
		return true;
	}
	
	public boolean contains(int i) {
		return this.getSparse(i) > 0;
	}
	
	private boolean setSparse(int index, int data, boolean force) {
		int yInSparse = index / sparsePageSize;
		int xInSparse = index % sparsePageSize;
		
		int[] targetPage;
		int sparseOriginSize = sparse.size();
		if(yInSparse >= sparseOriginSize) {
			targetPage = new int[this.sparsePageSize];
			
			int needGrowCount = yInSparse - sparseOriginSize;
			for (int i = 0; i < needGrowCount; i++) {
				this.sparse.add(null);
			}
			this.sparse.add(targetPage);
			
		} else {
			targetPage = this.sparse.get(yInSparse);
			if(targetPage == null) {
				this.sparse.set(yInSparse, targetPage = new int[this.sparsePageSize]);
			}
		}
		
		// This element is at the list, so we ignore it
		if(targetPage[xInSparse] > 0 && !force) return false;
		
		targetPage[xInSparse] = data;
		return true;
	}
	
	private int getSparse(int index) {
		int yInSparse = index / sparsePageSize;
		if(yInSparse > sparse.size())
			// Because when creating an array, Java sets all its elements to zero, so we set it to zero
			return 0;
		
		int[] targetPage = this.sparse.get(yInSparse);
		int xInSparse = index % sparsePageSize;
		return targetPage[xInSparse];
	}
	
	void debugPrint() {
		System.out.println("-- Data --");
		System.out.println(this.data.toString());
		System.out.println("-- Sparse --");
		this.sparse.forEach(e -> System.out.println(Arrays.toString(e)));
		
	}
	
}
