package xueli.game.lang;

import xueli.utils.logger.MyLogger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.logging.Logger;

public class FiledLangManager {

	private String pathString;

	private static HashMap<String, LanguageFile> langFileMap = new HashMap<String, LanguageFile>();
	private LanguageFile currentLanguageFile;

	public FiledLangManager(String pathString) {
		this.pathString = pathString;

	}

	public void loadLang() {
		langFileMap.clear();

		// language
		FilenameFilter langFilenameFilter = (dir, name) -> name.endsWith(".lang");

		File[] langFiles = new File(pathString + "/lang/").listFiles(langFilenameFilter);
		MyLogger.getInstance().info("find Lang File: " + langFiles.length);
		for (File langFile : langFiles) {
			String name = langFile.getName();
			LanguageFile languageFile = new LanguageFile(langFile);
			languageFile.read();
			languageFile.close();
			langFileMap.put(name, languageFile);
			MyLogger.getInstance().info("read Lang File: " + name);

		}

		setLang("zh-ch.lang");

	}

	public void setLang(String name) {
		LanguageFile languageFile = langFileMap.get(name);
		MyLogger.getInstance().pushState("Lang");
		if (languageFile == null)
			MyLogger.getInstance().warning("Failed to set Lang File: " + name + " Keep origin.");
		else {
			MyLogger.getInstance().info("Set Lang File: " + name);
			this.currentLanguageFile = languageFile;
		}
		MyLogger.getInstance().popState();
	}

	public String getStringFromLangMap(String name) {
		if (currentLanguageFile == null || !name.startsWith("#"))
			return name;
		String valueString = currentLanguageFile.getLang(name.substring(1));
		return valueString == null ? name : valueString;
	}

}
