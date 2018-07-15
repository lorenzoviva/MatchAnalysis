package TwitchConnector.TwitchConnector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import Connector.Connector.Connector;

public class TwitchConnector extends Connector{

	private static final String TWITCH_SERVER = "https://api.twitch.tv/helix/";
	private static final String CLIENT_ID = "7jau2g0pia1o2gpoaui8boopmkwb7i";
	
	private static Map<String,String> properties;
	
	private static final String STREAMS = "streams";
	
	public static String getStreamsByGame(String gameId) throws MalformedURLException, IOException {
		String query = TWITCH_SERVER + STREAMS + "?game_id=" + gameId;
		properties = getClientIDPropertiesMap();
		return query(query, properties);
		
	}

	private static Map<String,String> getClientIDPropertiesMap() {
		Map<String,String>  properties = new HashMap<String,String>();
		properties.put("Client-ID",CLIENT_ID);		
		return properties;
	}
}
