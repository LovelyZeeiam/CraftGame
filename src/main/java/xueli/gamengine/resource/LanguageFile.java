package xueli.gamengine.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import xueli.gamengine.utils.Logger;

public class LanguageFile {

	private File file;
	private BufferedReader inputStream;

	private HashMap<String, String> langMap = new HashMap<String, String>();

	public LanguageFile(String path) {
		try {
			this.file = new File(path);
			this.inputStream = new BufferedReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public LanguageFile(File file) {
		try {
			this.file = file;
			this.inputStream = new BufferedReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void read() {
		String line;
		int lineNum = 0;
		try {
			while ((line = inputStream.readLine()) != null) {
				if (line.startsWith("#"))
					// 是注释
					continue;
				if (line.isEmpty())
					// 是空行
					continue;
				int findEqual = line.indexOf('=');
				if (findEqual == -1) {
					// 本条数据无效
					Logger.warn("Lang: File " + file.getName() + ": " + lineNum + " Data Error! Ignore.");
					continue;
				}
				String key = line.substring(0, findEqual);
				String value = line.substring(findEqual + 1);
				langMap.put(key, value);
				lineNum++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLang(String regex) {
		return langMap.get(regex);
	}

}
