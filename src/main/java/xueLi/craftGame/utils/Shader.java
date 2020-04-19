package xueLi.craftGame.utils;

import java.io.IOException;

public class Shader {
	
	private int id;
	
	public Shader(String shaderPath,String name) {
		String vertCode,fragCode;
		try {
			vertCode = FileIO.readAllToString(shaderPath + "/" + name + ".vert");
			fragCode = FileIO.readAllToString(shaderPath + "/" + name + ".frag");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
