package xueLi.gamengine.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import xueLi.gamengine.gui.GUIProgressBar;
import xueLi.gamengine.gui.GUITextView;

public class Options extends IResource {

	private String real_path;

	private HashMap<String, JsonElement> options = new HashMap<String, JsonElement>();
	private Type type = new TypeToken<Map<String, JsonElement>>() {
	}.getType();

	public Options(String pathString) {
		super(pathString);
		this.real_path = pathString + "options/";

	}

	public void load() {
		File file = new File(real_path);
		for (File f : file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".json");
			}
		})) {
			try {
				options.putAll(gson.fromJson(new FileReader(f.getPath()), type));
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void load(GUITextView textView, GUIProgressBar progressBar, float startValue, float endValue) {
		File[] files = new File(real_path).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".json");
			}
		});

		int count = 0;
		float progressPerElement = (endValue - startValue) / files.length;

		String loading_messageString = textView.getText();

		for (File f : files) {
			textView.setText(loading_messageString + " - " + f.getName());
			try {
				options.putAll(gson.fromJson(new FileReader(f.getPath()), type));
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				e.printStackTrace();
			}

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

		}

		progressBar.setProgress(endValue);

	}

	public JsonElement get(String key) {
		return options.get(key);
	}

	@Override
	public void close() throws IOException {

	}

}
