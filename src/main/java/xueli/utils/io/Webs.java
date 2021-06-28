package xueli.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import xueli.utils.console.ConwidDownloadProgressbar;

public class Webs {
	
	static {
		System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11");
	}

	public static byte[] readAllFromWeb(String url) throws IOException {
		URL u = new URL(url);
		InputStream in = u.openStream();
		byte[] all = in.readAllBytes();
		in.close();
		return all;
	}
	
	public static byte[] readAllFromWeb(String url, ConnectionCreatedCallback callback) throws IOException {
		URL u = new URL(url);
		HttpsURLConnection c = (HttpsURLConnection) u.openConnection();
		callback.connectionCallback(c);
		
		c.connect();
		InputStream in = c.getInputStream();
		ConwidDownloadProgressbar progressbar = new ConwidDownloadProgressbar("Download");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		final int cacheSize = 1024;
		byte[] cache = new byte[cacheSize];
		while(true) {
			int readSize = in.read(cache);
			if(readSize == -1) break;
			
			out.write(cache, 0, readSize);
			progressbar.call(out.size(), out.size() + in.available());
			
		}
		
		System.out.println("[Download] Done!");
		
		in.close();
		c.disconnect();
		
		return out.toByteArray();
	}

	public static interface ConnectionCreatedCallback {
		public void connectionCallback(HttpsURLConnection c);
	}

	public static String readAllFromWebString(String url) throws IOException {
		URL u = new URL(url);
		InputStream in = u.openStream();
		String all = Files.readAllStringFromIn(in);
		in.close();
		return all;
	}
	
}
