package xueLi.craftGame;

import java.io.File;
import java.util.ArrayList;

import xueLi.gamengine.resource.IResource;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.GUITextView;

public class BlockResource extends IResource {
	
	private String real_path;
	
	
	
	public BlockResource(String pathString) {
		super(pathString);
		this.real_path = pathString + "blocks/";
		
	}
	
	private void load(File file) {
		
		
	}
	
	public void load(GUITextView textView, GUIProgressBar progressBar, float startValue, float endValue) {
		ArrayList<File> guiFiles = findAllFiles(new File(real_path));
		
		int count = 0;
		float progressPerElement = (endValue - startValue) / guiFiles.size();

		String loading_textString = textView.getText();
		
		for (File f : guiFiles) {
			load(f);

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

			textView.setText(loading_textString + " - " + f.getName());

		}

		progressBar.setProgress(endValue);
		
		
	}

	@Override
	public void close() {
		
		
	}

}
