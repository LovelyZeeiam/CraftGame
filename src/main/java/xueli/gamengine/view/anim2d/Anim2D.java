package xueli.gamengine.view.anim2d;

import xueli.gamengine.data.Author;

import java.util.ArrayList;
import java.util.HashMap;

public class Anim2D {

	String year;
	ArrayList<Author> authors = new ArrayList<>();

	HashMap<String, Element2D> scene = new HashMap<>();
	HashMap<Integer, ArrayList<AnimComponent>> components = new HashMap<>();

}
