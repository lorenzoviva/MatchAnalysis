package bigdatafinal.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RiotConnector extends Connector{
	private static final String SCHEME = "https";
	public static final String EUW_SERVER = "euw1.api.riotgames.com";
	public static final String EUNE_SERVER = "eun1.api.riotgames.com";
	public static final String NA_SERVER = "na1.api.riotgames.com";
	public static final String KR_SERVER = "kr.api.riotgames.com";
	public static final String BR_SERVER = "br1.api.riotgames.com";
	private static final String API_KEY = "RGAPI-810e8397-0554-4fd8-b534-0c6fab638936";
	
	private static Map<String,String> properties;
	
	private static final String SUMMONERS = "/lol/summoner/v3/summoners/";
	
	public static String getSummonerByName(String summonerName, String server) throws MalformedURLException, IOException, URISyntaxException {
//		String query = PROTOCOL + server + SUMMONERS + "by-name/" + summonerName + "?api_key=" + API_KEY;
		URI uri = new URI(SCHEME, server,SUMMONERS+"by-name/"+summonerName,"api_key="+API_KEY,"");
		return query(uri.toURL());		
	}
	
}
