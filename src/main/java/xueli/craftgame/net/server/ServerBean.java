package xueli.craftgame.net.server;

import java.io.Serializable;

public class ServerBean implements Serializable {

	private static final long serialVersionUID = -116485240098759476L;

	private String name;
	private String description;

	public ServerBean(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "ServerBean [name=" + name + ", description=" + description + "]";
	}

}
