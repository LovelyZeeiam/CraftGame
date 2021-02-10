package xueli.gamengine.resource;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public abstract class IResource implements Closeable {

	public static Gson gson = new Gson();
	protected String pathString;

	public IResource(String pathString) {
		this.pathString = pathString;

	}

	// Get all files including its folder's files
	public static ArrayList<File> findAllFiles(File file) {
		if (!file.exists()) {
			return null;
		}
		ArrayList<File> files = new ArrayList<File>();
		if (file.isFile()) {
			files.add(file);
			return files;
		} else if (file.isDirectory()) {
			File[] inFiles = file.listFiles();
			if (inFiles.length == 0)
				return null;
			else
				for (File f : inFiles)
					files.addAll(findAllFiles(f));
		}
		return files;
	}

	public static String readAllToString(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String lineString;
			while ((lineString = reader.readLine()) != null) {
				builder.append(lineString).append("\n");
			}
			reader.close();
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getPathString() {
		return pathString;
	}

}
