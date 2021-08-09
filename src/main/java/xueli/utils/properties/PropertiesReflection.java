package xueli.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import xueli.utils.logger.MyLogger;

public class PropertiesReflection {

	private static HashMap<Class<?>, Parsable<?>> parsers = new HashMap<>();

	public static <T> void registerParser(Parsable<T> parsable, Class<T> clazz) {
		parsers.put(clazz, parsable);

	}

	public static void reflect(Object obj, File properties) throws Exception {
		MyLogger.getInstance().pushState("Reflection");

		Properties p = new Properties();
		p.load(new FileInputStream(properties));

		boolean instance = !(obj instanceof Class<?>);
		Class<?> objClazz = instance ? obj.getClass() : (Class<?>) obj;
		HashMap<String, ArrayList<Field>> annotations = new HashMap<>();
		for (Field f : objClazz.getDeclaredFields()) {
			Property property = f.getAnnotation(Property.class);
			if (property == null)
				continue;
			String pName = property.value();

			if (!annotations.containsKey(pName))
				annotations.put(pName, new ArrayList<>());
			annotations.get(pName).add(f);

			f.setAccessible(true);

		}

		Object modifyTarget = instance ? obj : null;

		for (Map.Entry<Object, Object> entry : p.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();

			ArrayList<Field> fields = annotations.get(key);
			if (fields == null)
				continue;

			for (Field field : fields) {
				Class<?> fieldClazz = field.getType();
				if (String.class.equals(fieldClazz)) {
					field.set(modifyTarget, value);
				} else if (int.class.equals(fieldClazz)) {
					field.setInt(modifyTarget, Integer.parseInt(value));
				} else if (long.class.equals(fieldClazz)) {
					field.setLong(modifyTarget, Long.parseLong(value));
				} else if (float.class.equals(fieldClazz)) {
					field.setFloat(modifyTarget, Float.parseFloat(value));
				} else if (double.class.equals(fieldClazz)) {
					field.setDouble(modifyTarget, Double.parseDouble(value));
				} else if (boolean.class.equals(fieldClazz)) {
					field.setBoolean(modifyTarget, Boolean.parseBoolean(value));
				} else if (short.class.equals(fieldClazz)) {
					field.setShort(modifyTarget, Short.parseShort(value));
				} else if (byte.class.equals(fieldClazz)) {
					field.setByte(modifyTarget, Byte.parseByte(value));
				} else if (parsers.containsKey(fieldClazz)) {
					Parsable<?> parser = parsers.get(fieldClazz);
					field.set(modifyTarget, parser.parse(value));
				} else {
					MyLogger.getInstance().warning("Not supported field type \"" + fieldClazz.getName()
							+ "\" when setting key \"" + key + "\" in field \"" + field.getName() + "\"");
				}

			}

			MyLogger.getInstance().popState();

		}

	}

}
