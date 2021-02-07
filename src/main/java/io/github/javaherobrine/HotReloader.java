package io.github.javaherobrine;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.stream.Stream;

public final class HotReloader {
    private HotReloader() {
    }

    public static final void load(File modF, ModLoader ml) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        if (modF.isFile()) {
            JarClassLoader jcl = new JarClassLoader(modF.toURI().toURL());
            Class.forName(jcl.getMainClassName()).getMethod("main", String[].class).invoke(null, null);
            ml.MODS_LOADERS.put(modF.getName(), jcl);
        } else {
            Stream.of(modF.listFiles()).forEach(f -> {
                try {
                    load(f, ml);
                } catch (MalformedURLException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                }
            });
        }
    }

    @Deprecated
    public static final void tryToUnload(String modid, ModLoader ml) {
        try {
            Class.forName(ml.MODS_LOADERS.get(modid).getMainClassName()).getMethod("unload", null).invoke(null, null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
        }
        ml.MODS_LOADERS.remove(modid);
        System.gc();
    }
}
