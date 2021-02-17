package xueli.gamengine.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class DataResource extends IResource {

	private static String extraPathString = "data/";

	public DataResource(String pathString) {
		super(pathString);
	}

	public JsonObject readJson(String name) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		return gson.fromJson(new FileReader(new File(pathString + extraPathString + name)), JsonObject.class);
	}

}
