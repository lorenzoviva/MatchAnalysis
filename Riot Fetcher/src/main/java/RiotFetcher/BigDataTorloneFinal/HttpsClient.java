package RiotFetcher.BigDataTorloneFinal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsClient {
	private static final String EUW_SERVER = "https://euw1.api.riotgames.com";
	private static final String EUNE_SERVER = "https://eun1.api.riotgames.com";
	private static final String NA_SERVER = "https://na1.api.riotgames.com";
	private static final String KR_SERVER = "https://kr.api.riotgames.com";
	private static final String BR_SERVER = "https://br1.api.riotgames.com";
	private static final String API_KEY = "RGAPI-6c6d4f9b-64fc-4f69-94fa-d973a8fd2a3e";

	public static void main(String[] args) {
		new HttpsClient().connect();
	}
	
	private void connect() {
		URL url;
		String queryString = EUW_SERVER + "/lol/summoner/v3/summoners/by-name/VadimBlack?api_key=" + API_KEY;

		try {
			url = new URL(queryString);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

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
