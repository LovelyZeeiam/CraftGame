package xueli.daw;

import java.util.ArrayList;

public class Channel {
	
	private final DawContext context;
	
	private Generator generator;
	private final ArrayList<Plugin> pluginChain = new ArrayList<>();
	
	private final int[] buffer1 = new int[DawContext.BUFFER_SIZE];
	private final int[] buffer2 = new int[DawContext.BUFFER_SIZE];
	
	public Channel(DawContext context) {
		this.context = context;
	}
	
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
	
	public Generator getGenerator() {
		return generator;
	}
	
	// Can plugins be singleton? No
	public void addPlugin(Plugin plugin) {
		this.pluginChain.add(plugin);
	}
	
	public void removePlugin(Plugin plugin) {
		this.pluginChain.remove(plugin);
	}
	
	// Notice that the array yielded can be changed outside
	
	public int[] tick(double time) {
		if(generator == null) return null;
		
		// Double buffer
		int[] src = buffer1;
		int[] dest = buffer2;
		
		generator.progress(dest, time, context);
		
		for(Plugin p : pluginChain) {
			int[] temp = src;
			src = dest;
			dest = temp;
			
			p.progress(src, dest, time, context);
			
		}
		
		return dest;
	}
	
}
