package xueli.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesReflection {

	private static final Logger logger = Logger.getLogger(PropertiesReflection.class.getName());

	public static void reflect(Object obj, File properties) throws Exception {
		Properties p = new Properties();
		p.load(new FileInputStream(properties));

		Class<?> objClazz = obj.getClass();
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

		for (Map.Entry<Object, Object> entry : p.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();

			ArrayList<Field> fields = annotations.get(key);
			if (fields == null)
				continue;

			for (Field field : fields) {
				Class<?> fieldClazz = field.getType();
				if (String.class.equals(fieldClazz)) {
					field.set(obj, value);
				} else if (int.class.equals(fieldClazz)) {
					field.setInt(obj, Integer.parseInt(value));
				} else if (long.class.equals(fieldClazz)) {
					field.setLong(obj, Long.parseLong(value));
				} else if (float.class.equals(fieldClazz)) {
					field.setFloat(obj, Float.parseFloat(value));
				} else if (double.class.equals(fieldClazz)) {
					field.setDouble(obj, Double.parseDouble(value));
				} else if (boolean.class.equals(fieldClazz)) {
					field.setBoolean(obj, Boolean.parseBoolean(value));
				} else if (short.class.equals(fieldClazz)) {
					field.setShort(obj, Short.parseShort(value));
				} else if (byte.class.equals(fieldClazz)) {
					field.setByte(obj, Byte.parseByte(value));
				} else if (Parsable.class.equals(fieldClazz) && (obj instanceof Parsable<?>)) {
					Parsable<?> parser = (Parsable<?>) obj;
					field.set(obj, parser.parse(value));
				} else {
					logger.warning("Not supported field type \"" + fieldClazz.getName() + "\" when setting key \"" + key
							+ "\" in field \"" + field.getName() + "\"");
				}

			}

		}

	}

}
