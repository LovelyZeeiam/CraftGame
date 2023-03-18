package xueli.genshincharacterbrowser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BrowserService {
	
	@SuppressWarnings("unused")
	private final HttpClient client = HttpClient.newHttpClient(); 
	
	public BrowserService() {
		
	}
	
	public static void loadCity(String cityId, HttpClient client) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create("https://content-static.mihoyo.com/content/ysCn/getContentList?pageSize=100&pageNum=1&order=asc&channelId=324"))
			    .header("Accept", "*/*")
			    .header("User-Agent", "Thunder Client (https://www.thunderclient.com)")
			    .method("GET", HttpRequest.BodyPublishers.noBody())
			    .build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		
	}

}
