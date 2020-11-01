package xueLi.gamengine.resource;

import xueLi.gamengine.utils.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

public class LangManager extends IResource {

	private HashMap<String, LanguageFile> langFileMap = new HashMap<String, LanguageFile>();
	private LanguageFile currentLanguageFile;

	public LangManager(String pathString) {
		super(pathString);

	}

	public void loadLang() {
		langFileMap.clear();

		// language
		FilenameFilter langFilenameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".lang");
			}
		};

		File[] langFiles = new File(pathString + "lang/").listFiles(langFilenameFilter);
		Logger.info("Lang: find Lang File: " + langFiles.length);
		for (File langFile : langFiles) {
			String name = langFile.getName();
			LanguageFile languageFile = new LanguageFile(langFile);
			languageFile.read();
			languageFile.close();
			langFileMap.put(name, languageFile);
			Logger.info("Lang: read Lang File: " + name);

		}

	}

	public void setLang(String name) {
		LanguageFile languageFile = langFileMap.get(name);
		if (languageFile == null)
			Logger.info("Lang: Failed to set Lang File: " + name + " Keep origin.");
		else {
			Logger.info("Lang: set Lang File: " + name);
			this.currentLanguageFile = languageFile;
		}
	}

	public String getStringFromLangMap(String name) {
		if (currentLanguageFile == null || !name.startsWith("#"))
			return name;
		String valueString = currentLanguageFile.getLang(name.substring(1));
		return valueString == null ? name : valueString;
	}

	@Override
	public void close() {

	}

}
