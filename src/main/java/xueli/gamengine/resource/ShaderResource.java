package xueli.gamengine.resource;

import xueli.gamengine.utils.Shader;
import xueli.gamengine.view.GUIProgressBar;
import xueli.gamengine.view.GUITextView;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

public class ShaderResource extends IResource {

    private String real_path;
    private HashMap<String, Shader> shaders = new HashMap<String, Shader>();

    public ShaderResource(String pathString) {
        super(pathString);

        real_path = pathString + "shaders/";

    }

    public void load() {
        File[] folders = new File(real_path).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        for (File f : folders) {
            load(f.getName(), new File(f.getPath() + "/geo.txt").exists());

        }

    }

    public void load(GUIProgressBar progressBar, float startValue, float endValue) {
        File[] folders = new File(real_path).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        int count = 0;
        float progressPerElement = (endValue - startValue) / folders.length;

        for (File f : folders) {
            load(f.getName(), new File(f.getPath() + "/geo.txt").exists());

            ++count;
            progressBar.setProgress(startValue + progressPerElement * count);

        }

        progressBar.setProgress(endValue);

    }

    public void load(GUITextView textView, GUIProgressBar progressBar, float startValue, float endValue) {
        File[] folders = new File(real_path).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        int count = 0;
        float progressPerElement = (endValue - startValue) / folders.length;

        String loading_textString = textView.getText();

        for (File f : folders) {
            load(f.getName(), new File(f.getPath() + "/geo.txt").exists());

            ++count;
            progressBar.setProgress(startValue + progressPerElement * count);

            textView.setText(loading_textString + " - " + f.getName());

        }

        progressBar.setProgress(endValue);

    }

    public Shader load(String name, boolean hasGeometryShader) {
        if (shaders.containsKey(name))
            return shaders.get(name);
        Shader shader = null;
        if (!hasGeometryShader)
            shader = new Shader(real_path + name + "/vert.txt", real_path + name + "/frag.txt");
        else
            shader = new Shader(real_path + name + "/vert.txt", real_path + name + "/geo.txt",
                    real_path + name + "/frag.txt");
        shaders.put(name, shader);
        return shader;
    }

    public Shader get(String key) {
        return shaders.get(key);
    }

    @Override
    public void close() {
        for (Shader s : shaders.values()) {
            s.release();

        }
    }

}