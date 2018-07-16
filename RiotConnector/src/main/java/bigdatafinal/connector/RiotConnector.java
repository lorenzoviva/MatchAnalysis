package bigdatafinal.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import bigdatafinal.connector.Connector;

public class RiotConnector extends Connector{
	public static final String EUW_SERVER = "https://euw1.api.riotgames.com";
	public static final String EUNE_SERVER = "https://eun1.api.riotgames.com";
	public static final String NA_SERVER = "https://na1.api.riotgames.com";
	public static final String KR_SERVER = "https://kr.api.riotgames.com";
	public static final String BR_SERVER = "https://br1.api.riotgames.com";
	private static final String API_KEY = "RGAPI-810e8397-0554-4fd8-b534-0c6fab638936";
	
	private static Map<String,String> properties;
	
	private static final String SUMMONERS = "/lol/summoner/v3/summoners/";
	
	public static String getSummonerByName(String summonerName, String server) throws MalformedURLException, IOException {
		String query = server + SUMMONERS + "by-name/" + summonerName + "?api_key=" + API_KEY;
		return query(query);		
	}
	
}
