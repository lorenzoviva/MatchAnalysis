import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsClient {
	private static final String TWITCH_SERVER = "https://api.twitch.tv/helix/streams?";
	private static final String CLIENT_ID = "7jau2g0pia1o2gpoaui8boopmkwb7i";
	private static final String request = "game_id=33214";


	public static void main(String[] args) {
		new HttpsClient().connect(request);
	}
	
	private void connect(String request) {
		URL url;
		String queryString = TWITCH_SERVER + request;

		try {
			url = new URL(queryString);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			con.addRequestProperty("Client-ID", CLIENT_ID);
			print_content(con);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void print_content(HttpsURLConnection con) {
		if(con!=null){

			try {

				System.out.println("****** Content of the URL ********");			
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String input;

				while ((input = br.readLine()) != null){
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
