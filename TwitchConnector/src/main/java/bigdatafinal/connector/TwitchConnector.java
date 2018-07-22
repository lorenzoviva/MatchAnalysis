package bigdatafinal.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TwitchConnector extends Connector{
	

	private static final String SCHEME = "https";
	private static final String TWITCH_SERVER = "api.twitch.tv";
	private static final String CLIENT_ID = "7jau2g0pia1o2gpoaui8boopmkwb7i";
	
	private static Map<String,String> properties;
	
	private static final String STREAMS = "/helix/streams";
	private static final String USERS = "/helix/users";
	
	private static TwitchConnector instance = null;

	public TwitchConnector(int requestsLimit, int timeLimit) {
		super(requestsLimit, timeLimit);
	}
	
	public static TwitchConnector getIstance() {
		if (instance == null) {
			instance = new TwitchConnector(40,60000);
		}
		return instance;
	}
	
	public String getStreamsByGame(String gameId) throws MalformedURLException, IOException, URISyntaxException {
//		String query = TWITCH_SERVER + STREAMS + "?game_id=" + gameId;
		properties = getClientIDPropertiesMap();
		URI uri = new URI(SCHEME, TWITCH_SERVER,STREAMS,"game_id=" + gameId,null);
		
		return query(uri.toURL(), properties);	
	}
	
	public String getStreamsByGame(String gameId, String pagination) throws URISyntaxException, MalformedURLException, IOException {
		properties = getClientIDPropertiesMap();
		URI uri = new URI(SCHEME, TWITCH_SERVER,STREAMS,"game_id=" + gameId + "&after=" + pagination,null);
		return query(uri.toURL(), properties);	
	}
	
	public String getUserFromId(String userId) throws MalformedURLException, IOException, URISyntaxException {
		properties = getClientIDPropertiesMap();
		URI uri = new URI(SCHEME, TWITCH_SERVER,USERS,"id=" + userId,null);
		return query(uri.toURL(), properties);
	}

	private Map<String,String> getClientIDPropertiesMap() {
		Map<String,String>  properties = new HashMap<String,String>();
		properties.put("Client-ID",CLIENT_ID);		
		return properties;
	}
	

}
