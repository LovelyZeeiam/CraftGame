package net.dengzixu.java.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.InflaterInputStream;

public class ZlibUtil {
	public static byte[] inflate(byte[] compressedData) {

		if (null == compressedData) {
			return null;
		}

		ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream);
			byte[] buffer = new byte[1024];
			int n;
			while ((n = inflaterInputStream.read(buffer)) >= 0) {
				outputStream.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputStream.toByteArray();
	}

}
