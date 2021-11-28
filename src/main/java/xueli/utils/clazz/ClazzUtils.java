package xueli.utils.clazz;

import xueli.utils.io.Files;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

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

		// process current module
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

		// process loaded JAR pack
		ArrayList<File> allJarFiles = new ArrayList<>();

		try {
			String trigger = "META-INF";
			Enumeration<URL> paths = loader.getResources("META-INF");

			while (paths.hasMoreElements()) {
				URL u = paths.nextElement();
				URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { u },
						Thread.currentThread().getContextClassLoader());

				String p = u.getPath();
				String jarpath = new File(p).getParent();
				jarpath = jarpath.substring(!jarpath.contains("file:\\") ? 0 : "file:\\".length(),
						!jarpath.endsWith("!") ? jarpath.length() : jarpath.length() - 1);

				JarFile file = new JarFile(jarpath);
				Enumeration<JarEntry> entries = file.entries();
				while (entries.hasMoreElements()) {
					JarEntry e = entries.nextElement();
					if (e.isDirectory())
						continue;

					String name = e.getName();
					if (name.contains("META-INF"))
						continue;
					if (name.contains("module-info"))
						continue;

					if (name.endsWith(".class")) {
						String className = name.substring(0, name.length() - ".class".length());
						className = className.replaceAll("/", ".");
						clazzes.add(urlClassLoader.loadClass(className));

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return clazzes;
	}

	public static Field[] getAllFields(Class<?> c) {
		ArrayList<Field> fields = new ArrayList<>();
		fields.addAll(List.of(c.getDeclaredFields()));

		Class<?> s = c.getSuperclass();
		if (s != null)
			fields.addAll(List.of(getAllFields(s)));

		return fields.toArray(new Field[0]);
	}

	public static void printAllSuperClass(Class<?> c) {
		do {
			System.out.println(c.getName());
			c = c.getSuperclass();
		} while (c != null);
	}

}
