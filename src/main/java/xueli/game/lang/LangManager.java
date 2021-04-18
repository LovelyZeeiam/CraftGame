package xueli.game.lang;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.logging.Logger;

public class LangManager {

	private String pathString;

	private static HashMap<String, LanguageFile> langFileMap = new HashMap<String, LanguageFile>();
	private LanguageFile currentLanguageFile;

	public LangManager(String pathString) {
		this.pathString = pathString;

	}

	public void loadLang() {
		langFileMap.clear();

		// language
		FilenameFilter langFilenameFilter = (dir, name) -> name.endsWith(".lang");

		File[] langFiles = new File(pathString + "/lang/").listFiles(langFilenameFilter);
		Logger.getLogger(getClass().getName()).info("[Lang] find Lang File: " + langFiles.length);
		for (File langFile : langFiles) {
			String name = langFile.getName();
			LanguageFile languageFile = new LanguageFile(langFile);
			languageFile.read();
			languageFile.close();
			langFileMap.put(name, languageFile);
			Logger.getLogger(getClass().getName()).finer("[Lang] read Lang File: " + name);

		}

		setLang("zh-ch.lang");

	}

	public void setLang(String name) {
		LanguageFile languageFile = langFileMap.get(name);
		if (languageFile == null)
			Logger.getLogger(getClass().getName()).finer("[Lang] Failed to set Lang File: " + name + " Keep origin.");
		else {
			Logger.getLogger(getClass().getName()).finer("[Lang] set Lang File: " + name);
			this.currentLanguageFile = languageFile;
		}
	}

	public String getStringFromLangMap(String name) {
		if (currentLanguageFile == null || !name.startsWith("#"))
			return name;
		String valueString = currentLanguageFile.getLang(name.substring(1));
		return valueString == null ? name : valueString;
	}

}
