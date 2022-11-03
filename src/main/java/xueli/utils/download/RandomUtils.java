package xueli.utils.download;

import java.util.ArrayList;
import java.util.Random;

public class RandomUtils {

	private static final String CHARS = "qwertyuiopasdfghjklzxcvbnm1234567890";
	private static final Random RANDOM = new Random();

	private static final ArrayList<String> hasRandomStrings = new ArrayList<>();

	public static String genRandomStr(int length) {
		StringBuffer buf = new StringBuffer(length);
		RANDOM.ints(length).forEach(i -> buf.append(CHARS.charAt(Math.floorMod(i, CHARS.length()))));
		String s = buf.toString();
		if (hasRandomStrings.contains(s)) {
			return genRandomStr(length);
		}
		hasRandomStrings.add(s);
		return s;
	}

}
