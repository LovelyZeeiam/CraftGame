package xueli.utils;

import org.lwjgl.util.vector.Vector3f;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import xueli.utils.io.Log;

public class WindowUtils {

	private WindowUtils() {

	}

	public static Vector3f getSystemThemeColor(Vector3f elseColor) {
		// windows 10可以获得直接有主题色作为背景
		if (System.getProperty("os.name").equals("Windows 10")) {
			long color = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER,
					"Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\History\\Colors", "ColorHistory0");
			float r = (int) ((color >> 16) & 0xFF);
			float g = (int) ((color >> 8) & 0xFF);
			float b = (int) (color & 0xFF);

			Log.logger.info("Windows 10! Set background color to theme color: " + r + ", " + g + ", " + b);
			return new Vector3f(r / 255 * 0.62f, g / 255 * 0.71f, b / 255 * 0.66f);

		}
		return elseColor;
	}

}
