package xueli.utils;

import java.util.ArrayList;

public class Strings {

	public static String[] splitToken(String target, String... tokens) {
		ArrayList<String> ts = new ArrayList<>();
		ts.add(target);

		for (String token : tokens) {
			ArrayList<String> newTokens = new ArrayList<>();

			for (int i = 0; i < ts.size(); i++) {
				String string = ts.get(i);

				int indexOf = 0;
				ArrayList<String> strings = new ArrayList<>();

				while (true) {
					int thisIndexOf = string.indexOf(token, indexOf);
					if (thisIndexOf == -1) {
						if (indexOf < string.length())
							strings.add(string.substring(indexOf));
						break;
					}
					String sub = string.substring(indexOf, thisIndexOf);
					if (thisIndexOf != 0)
						strings.add(sub);
					strings.add(token);
					indexOf = thisIndexOf + token.length();

				}

				newTokens.addAll(strings);

			}

			ts = newTokens;

		}

		return ts.toArray(new String[0]);
	}

	public static String[] split(String target, String noMoreRegex) {
		int indexOf = 0;
		ArrayList<String> strings = new ArrayList<>();

		while (true) {
			int thisIndexOf = target.indexOf(noMoreRegex, indexOf);
			if (thisIndexOf == -1) {
				strings.add(target.substring(indexOf));
				break;
			}
			String sub = target.substring(indexOf, thisIndexOf);
			strings.add(sub);
			indexOf = thisIndexOf + 1;

		}

		return strings.toArray(new String[0]);
	}
	
	public static String padLeft(String origin, int length, char ch) {
		int origin_length = origin.length();
		int need_pad_length = length - origin_length;
		if(need_pad_length <= 0) return origin.substring(0, length);
		return Character.toString(ch).repeat(need_pad_length) + origin;
	}
	
	public static String padRight(String origin, int length, char ch) {
		int origin_length = origin.length();
		int need_pad_length = length - origin_length;
		if(need_pad_length <= 0) return origin.substring(0, length);
		return origin + Character.toString(ch).repeat(need_pad_length);
	}
	
}
