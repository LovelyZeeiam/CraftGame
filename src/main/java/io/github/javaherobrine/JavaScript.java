package io.github.javaherobrine;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.stream.Stream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScript {
    public static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

    static {
        try {
            engine.eval("function json(json){return json}");
        } catch (ScriptException e) {
        }
    }

    public static Map parse(String json) throws ScriptException {
        return (Map) engine.eval("json(" + json + ")");
    }

    public static String json(Object object) {
        StringBuilder sb = new StringBuilder("{\n");
        return json(object, sb, object.getClass());
    }

    public static String json(Object object, StringBuilder sb, Class clazz) {
        if (clazz.getSuperclass() != null) {
            sb.append(json(object, sb, clazz.getSuperclass()));
        }
        Stream.of(object.getClass().getFields()).filter(field -> {
            return !Modifier.isFinal(field.getModifiers()) && (Modifier.isStatic(field.getModifiers()) || field.isAccessible() || Modifier.isTransient(field.getModifiers()));
        }).forEach(field -> {
            try {
                sb.append("\"" + field.getName() + "\"" + ":");
                Object thisFie = field.get(object);
                if (thisFie instanceof Number) {
                    sb.append(thisFie.toString() + ",\n");
                } else if (thisFie instanceof String) {
                    sb.append("\"" + thisFie + ",\"");
                } else {
                    sb.append("\"" + json(thisFie) + ",\n");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
            }
        });
        sb.delete(sb.length() - 2, sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    public static String jsonWithoutExtends(Object object) {
        final StringBuilder sb = new StringBuilder("{");
        Stream.of(object.getClass().getFields()).filter(field -> {
            return !Modifier.isFinal(field.getModifiers()) && (Modifier.isStatic(field.getModifiers()) || field.isAccessible() || Modifier.isTransient(field.getModifiers()));
        }).forEach(field -> {
            try {
                sb.append("\"" + field.getName() + "\"" + ":");
                Object thisFie = field.get(object);
                if (thisFie instanceof Number) {
                    sb.append(thisFie.toString() + ",\n");
                } else if (thisFie instanceof String) {
                    sb.append("\"" + thisFie + ",\"");
                } else {
                    sb.append("\"" + json(thisFie) + ",\n");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
            }
        });
        sb.delete(sb.length() - 2, sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }
}