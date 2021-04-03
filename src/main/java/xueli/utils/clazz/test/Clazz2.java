package xueli.utils.clazz.test;

@Test(name = "clazz2", value = 1919180)
public class Clazz2 {

	public Clazz2() {
		
	}
	
	public void invoke1(@Test(name = "param1", value = 233) String p1) {}
	
	public void invoke2(@Test(name = "param1", value = 455) String p1, @Param(name = "param2") String p2) {}

}
