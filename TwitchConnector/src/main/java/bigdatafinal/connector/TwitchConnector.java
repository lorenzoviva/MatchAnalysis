package bigdatafinal.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import bigdatafinal.connector.Connector;

public class TwitchConnector extends Connector{
	private static final String SCHEME = "https";
	private static final String TWITCH_SERVER = "api.twitch.tv";
	private static final String CLIENT_ID = "7jau2g0pia1o2gpoaui8boopmkwb7i";
	
	private static Map<String,String> properties;
	
	private static final String STREAMS = "/helix/streams";
	
	public static String getStreamsByGame(String gameId) throws MalformedURLException, IOException, URISyntaxException {
//		String query = TWITCH_SERVER + STREAMS + "?game_id=" + gameId;
		properties = getClientIDPropertiesMap();
		URI uri = new URI(SCHEME, TWITCH_SERVER,STREAMS,"game_id=" + gameId,null);
		return query(uri.toURL(), properties);	
		
	}

	private static Map<String,String> getClientIDPropertiesMap() {
		Map<String,String>  properties = new HashMap<String,String>();
		properties.put("Client-ID",CLIENT_ID);		
		return properties;
	}
}
