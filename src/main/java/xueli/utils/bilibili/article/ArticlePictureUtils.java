package xueli.utils.bilibili.article;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import xueli.utils.io.Files;

public class ArticlePictureUtils {

	private ArticlePictureUtils() {
	}

	public static void saveAllPicture(int cvId, String saveFolder) {
		Document doc;
		try {
			doc = Jsoup.connect("https://www.bilibili.com/read/cv" + cvId).get();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Logger.getLogger(ArticlePictureUtils.class.getName()).info("Get Bilibili CV page: " + cvId);

		Elements picElements = doc.select("img[data-src]");

		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(20, 25, 5L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
		ArrayList<Future<?>> futures = new ArrayList<>();

		picElements.forEach(e -> {
			Future<?> future = poolExecutor.submit(() -> {
				String dataSrc = e.attr("data-src");
				String imgAddr = "https:" + dataSrc;

				try {
					URL url = new URL(imgAddr);
					URLConnection conn = url.openConnection();
					conn.setConnectTimeout(3 * 1000);
					conn.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:86.0) Gecko/20100101 Firefox/86.0");

					InputStream urlIn = conn.getInputStream();
					int cache = 0;
					ByteArrayOutputStream cacheByte = new ByteArrayOutputStream();

					while ((cache = urlIn.read()) != -1) {
						cacheByte.write(cache);
					}

					urlIn.close();

					Files.fileOutput("output/" + System.nanoTime() + ".jpg", cacheByte.toByteArray());

				} catch (Exception exception) {
					exception.printStackTrace();
				}
			});

			futures.add(future);

		});

		for (Future<?> f : futures) {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
		}

		poolExecutor.shutdownNow();

	}

	public static ArrayList<Integer> searchArticle(String key) {
		try {
			ArrayList<Integer> cvs = new ArrayList<>();

			// 获取页数
			int pageCount = 0;

			{
				URL url = new URL(
						"https://api.bilibili.com/x/web-interface/search/type?search_type=article&keyword=" + key);

				InputStream urlInputStream = url.openStream();
				JsonObject pageUrlJsonObject = new Gson().fromJson(new InputStreamReader(urlInputStream),
						JsonObject.class);
				urlInputStream.close();

				int code = pageUrlJsonObject.get("code").getAsInt();
				if (code != 0) {
					Logger.getLogger(ArticlePictureUtils.class.getName()).warning(
							"Article Search Failed: " + pageUrlJsonObject.get("message").getAsString() + ", " + key);
					return null;
				}

				pageCount = pageUrlJsonObject.getAsJsonObject("data").get("numPages").getAsInt();

			}

			Logger.getLogger(ArticlePictureUtils.class.getName()).fine("Bilibili Article Search: " + key + ", " + pageCount);

			// 获取专栏
			for (int i = 1; i <= pageCount; i++) {
				URL url = new URL("https://api.bilibili.com/x/web-interface/search/type?search_type=article&page=" + i
						+ "&keyword=" + key);

				InputStream urlInputStream = url.openStream();
				JsonObject pageJsonObject = new Gson().fromJson(new InputStreamReader(urlInputStream),
						JsonObject.class);
				urlInputStream.close();

				int code = pageJsonObject.get("code").getAsInt();
				if (code != 0) {
					Logger.getLogger(ArticlePictureUtils.class.getName()).warning(
							"Article Search Failed: " + pageJsonObject.get("message").getAsString() + ", " + key);
					continue;
				}

				JsonArray resultJsonObject = pageJsonObject.getAsJsonObject("data").getAsJsonArray("result");
				resultJsonObject.forEach(r -> {
					int id = r.getAsJsonObject().get("id").getAsInt();
					cvs.add(id);

				});

			}

			return cvs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> searchResult = searchArticle("二次元电脑壁纸");
		searchResult.forEach(i -> saveAllPicture(i, "output/"));

	}

}
