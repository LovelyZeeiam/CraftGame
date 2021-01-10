package xueli.gamengine.resource;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataResource extends IResource {

    private static String extraPathString = "data/";

    public DataResource(String pathString) {
        super(pathString);
    }

    public JsonObject readJson(String name) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        return gson.fromJson(new FileReader(new File(pathString + extraPathString + name)), JsonObject.class);
    }

    @Override
    public void close() throws IOException {

    }

}
