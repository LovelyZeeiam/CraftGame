package xueli.gamengine.view.anim2d;

import java.util.ArrayList;
import java.util.HashMap;

import xueli.gamengine.data.Author;

public class Anim2D {

	String year;
	ArrayList<Author> authors = new ArrayList<>();

	HashMap<String, Element2D> scene = new HashMap<>();
	HashMap<Integer, ArrayList<AnimComponent>> components = new HashMap<>();

}
