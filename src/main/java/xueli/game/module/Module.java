package xueli.game.module;

public abstract class Module {
	
	private String namespace;

	public Module(String namespace) {
		this.namespace = namespace;
		
	}

	public abstract boolean checkInvaild();
	
	public String getNamespace() {
		return namespace;
	}

	@Override
	public String toString() {
		return "Module [namespace=" + namespace + "]";
	}

}
