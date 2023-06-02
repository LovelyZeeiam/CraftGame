package xueli.utils.logger;

// Used to observe the method invoke
public class InvokeDaemon {
	
	private final Class<?> clazz;
	private boolean enable = false;
	
	public InvokeDaemon(Class<?> clazz) {
		this.clazz = clazz;
		
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public boolean isEnable() {
		return enable;
	}
	
	public void announce() {
		if(!enable) return;
		
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		StackTraceElement methodSource = es[2];
		StackTraceElement invoker = es[3];
		
		if(invoker.getClassName().equals(clazz.getName())) return;
		
		System.out.println("[" + clazz.getSimpleName() + "] " + methodSource.getMethodName() + " from " + invoker.getFileName() + ":" + invoker.getLineNumber());
		
	}

}
