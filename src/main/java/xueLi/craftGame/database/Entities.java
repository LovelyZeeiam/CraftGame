package xueLi.craftGame.database;

import java.io.FileNotFoundException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import xueLi.craftGame.template.entity.AttributeEntity;
import xueLi.craftGame.utils.FileIO;

public class Entities {

	public static AttributeEntity mWarma;
	
	static {
		try {
			mWarma = FileIO.readToEntityData("res/entities/Warma.json");
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
