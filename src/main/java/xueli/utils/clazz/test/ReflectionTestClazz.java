package xueli.utils.clazz.test;

import xueli.utils.clazz.ClazzUtils;

public class ReflectionTestClazz {

	public static void main(String[] args) {
		for (Class<?> clazz : ClazzUtils.getAllAnnotatedClass(Test.class)) {
			System.out.println(clazz);

		}

	}

}
