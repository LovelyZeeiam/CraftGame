package net.dengzixu.java.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;

public class BrotliUtil {

	public static byte[] unCompress(byte[] compressedData) {

		if (null == compressedData) {
			return null;
		}

		ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			BrotliCompressorInputStream brIn = new BrotliCompressorInputStream(inputStream);
			final byte[] buffer = new byte[1024];
			int n = 0;
			while (-1 != (n = brIn.read(buffer))) {
				outputStream.write(buffer, 0, n);
			}
			outputStream.close();
			brIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}
}
