package xueli.utils.io;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryStack;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Files {

	public static void fileOutput(String name, String content) throws IOException {
		fileOutput(name, content.getBytes(StandardCharsets.UTF_8));

	}

	public static void fileOutput(String name, byte[] bytes) throws IOException {
		File file = new File(name);
		if (!file.exists())
			file.createNewFile();

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(bytes);
		out.flush();
		out.close();
	}

	public static void fileOutput(String name, int[] bytes) throws IOException {
		File file = new File(name);
		if (!file.exists())
			file.createNewFile();

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		DataOutputStream stream = new DataOutputStream(out);
		for (int i = 0; i < bytes.length; i++) {
			stream.writeInt(bytes[i]);
		}
		out.flush();
		out.close();
	}

	public static void mkDir(String path) {
		new File(path).mkdirs();

	}

	public static String readAllString(File file) throws IOException {
		return new String(readAllByte(file));
	}

	public static String readAllStringFromIn(InputStream in) throws IOException {
		return new String(in.readAllBytes(), StandardCharsets.UTF_8);
	}

	public static byte[] readAllByte(File file) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		byte[] data = new byte[in.available()];
		in.read(data);
		in.close();
		return data;
	}

	public static ArrayList<File> getAllFiles(File file) {
		ArrayList<File> files = new ArrayList<>();

		if (file.isDirectory()) {
			File[] allFiles = file.listFiles();
			for (int i = 0; i < allFiles.length; i++) {
				File f = allFiles[i];
				files.addAll(getAllFiles(f));

			}

		} else if (file.isFile()) {
			files.add(file);

		}

		return files;
	}

	public static ArrayList<File> getAllFiles(String path) {
		return getAllFiles(new File(path));
	}

	public static int[] readImageAndReturnRawData(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[] data = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth());
		return data;
	}

	public static File getResourcePackedInJar(String path) {
		return new File(URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource(path).getPath(),
				StandardCharsets.UTF_8));
	}
	
	public static byte[] readResourcePackedInJar(String path) throws IOException {
		InputStream in = Files.class.getResourceAsStream(path);
		if(in == null)
			throw new IOException("Stream is null! Maybe the file doesn't exist?");
		byte[] all = in.readAllBytes();
		in.close();
		return all;
	}
	
	public static ByteBuffer readResourcePackedInJarAndPackedToBuffer(String path) throws IOException {
		byte[] all = readResourcePackedInJar(path);
		return MemoryStack.stackBytes(all);
	}

	public static void writeObject(Object obj, File file) throws Exception {
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream oo = new ObjectOutputStream(out);
		oo.writeObject(obj);
		oo.flush();
		oo.close();
	}

	public static Object readObject(File file) throws Exception {
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream oi = new ObjectInputStream(in);
		Object o = oi.readObject();
		oi.close();
		return o;
	}

	public static String getNameExcludeSuffix(String fileName) {
		int lastIndex = fileName.lastIndexOf('.');
		if (lastIndex == -1)
			return fileName;
		return fileName.substring(0, lastIndex);
	}

	public static String getFileExtension(String path) {
		String fileName = new File(path).getName();
		int index = fileName.lastIndexOf('.');
		return (index == -1) ? "" : fileName.substring(index + 1);
	}

}
