package xueli.craftgame;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.JarFile;

public class Constants {

	public static final String GAME_NAME = "CraftGame";
	public static final String GAME_NAME_LOWER_CASE = GAME_NAME.toLowerCase();

	public static final String VERSION = "alpha";

	public static final String GAME_NAME_FULL = String.format("%s - %s", GAME_NAME, VERSION);

	public static URL currentBinPath = Constants.class.getProtectionDomain().getCodeSource().getLocation();
	public static JarFile currentBinJarFile = null;

	static {
		if (currentBinPath.getProtocol().equalsIgnoreCase("jar")) {
			JarURLConnection connection;
			try {
				connection = (JarURLConnection) currentBinPath.openConnection();
				currentBinJarFile = connection.getJarFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
