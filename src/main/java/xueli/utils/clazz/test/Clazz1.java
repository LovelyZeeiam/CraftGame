package xueli.utils.clazz.test;

@Test(name = "clazz1", value = 114514)
public class Clazz1 {

	public Clazz1() {
		
	}
	
	public void invoke1(@Param(name = "param1") String p1) {}
	
	public void invoke2(@Param(name = "param1") String p1, @Param(name = "param2") String p2) {}

}
