package xueli.utils.clazz;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import xueli.utils.io.Files;

public class ClazzUtils {

	private ClazzUtils() {
	}

	public static List<Class<?>> getAllAnnotatedClass(Class<? extends Annotation> annotation) {
		List<Class<?>> clazzes = new ArrayList<>();

		List<Class<?>> all = getAllClasses();
		clazzes = all.stream().filter(c -> {
			if (c.getAnnotation(annotation) != null)
				return true;
			return false;
		}).collect(Collectors.toList());

		return clazzes;
	}

	public static <T> List<Class<? extends T>> getClassExtendedBy(Class<T> c) {
		List<Class<? extends T>> clazzes = new ArrayList<>();

		List<Class<?>> all = getAllClasses();
		for (Class<?> clazz : all) {
			try {
				Class<? extends T> extended = clazz.asSubclass(c);
				if (!extended.getName().equals(c.getName()))
					clazzes.add(extended);
			} catch (ClassCastException e) {
				continue;
			}
		}

		return clazzes;
	}

	public static List<Class<?>> getAllClasses() {
		ArrayList<Class<?>> clazzes = new ArrayList<>();

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String path = loader.getResource("./").getPath();
		ArrayList<File> allFiles = Files.getAllFiles(path);

		for (File file : allFiles) {
			if (!file.exists())
				continue;
			if (!file.getPath().endsWith(".class"))
				continue;

			String realPath = file.getPath().substring(path.length() - 1);
			// the file path separater might be only useful in Windows
			String packageName = realPath.substring(0, realPath.length() - ".class".length()).replace('\\', '.');

			Class<?> clazz = null;
			try {
				clazz = Class.forName(packageName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				continue;
			}
			clazzes.add(clazz);

		}

		return clazzes;
	}

}
