package xueli.game.modding;

public abstract class IMod {

	protected String name;
	protected String resFolder;
	protected String workingDirectory;

	public IMod(String name, String resourceFolder, String workingDirectory) {
		this.resFolder = resourceFolder;
		this.name = name;
		this.workingDirectory = workingDirectory;

	}

	public void onInit() {

	}

	public void onTick() {

	}

	public void onRelease() {

	}

	public String getName() {
		return name;
	}

	public String getResFolder() {
		return resFolder;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

}
