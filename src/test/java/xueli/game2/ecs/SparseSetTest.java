package xueli.game2.ecs;

public class SparseSetTest {

	public static void main(String[] args) {
		SparseSet set = new SparseSet();
		set.add(1);
		set.debugPrint();
		set.add(3);
		set.debugPrint();
		set.add(5);
		set.debugPrint();
		
		set.remove(3);
		set.debugPrint();
		set.add(80);
		set.debugPrint();
		set.remove(70);
		set.debugPrint();
		set.add(75);
		set.debugPrint();
		set.remove(80);
		set.debugPrint();
		
		set.remove(1);
		set.debugPrint();
		
		set.remove(3);
		set.debugPrint();
		
		set.remove(5);
		set.debugPrint();
		
	}

}
