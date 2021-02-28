package xueli.game.modding;

import xueli.game.lang.LangManager;

public abstract class IMod {

	protected String name;
	protected String resFolder;
	protected String workingDirectory;

	protected LangManager lang;

	public IMod(String name, String resourceFolder, String workingDirectory) {
		this.resFolder = resourceFolder;
		this.name = name;
		this.workingDirectory = workingDirectory;

		this.lang = new LangManager(resourceFolder);
		this.lang.loadLang();
		this.lang.setLang("zh-ch.lang");

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

	public LangManager getLangManager() {
		return lang;
	}

}
