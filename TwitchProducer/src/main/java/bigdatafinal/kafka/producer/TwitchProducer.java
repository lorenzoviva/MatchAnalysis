package bigdatafinal.kafka.producer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONObject;

import bigdatafinal.connector.TwitchConnector;

public class TwitchProducer extends CustomProducer {

	private String cursor;
	public TwitchProducer() {
		super();
	}
	
	

	public void getLeagueOfLegendsStreamList() {
		try {
			String streamsByGame = TwitchConnector.getIstance().getStreamsByGame("21779");
			cursor = getPagination(streamsByGame);
			List<String> streamList = splitData(streamsByGame, "data");
			
			for (String elem : streamList) {
				send(elem,"loltwitchstreams");
			}
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
			sendError(e);
		} catch (URISyntaxException e) {
			sendError(e);
		}
	}
	public void getNextLeagueOfLegendsStreamList() {
		getLeagueOfLegendsStreamList(cursor);
	}
	public void getLeagueOfLegendsStreamList(String pagination) {
		try {
			String streamsByGame = TwitchConnector.getIstance().getStreamsByGame("21779",pagination);
			List<String> streamList = splitData(streamsByGame, "data");

			for (String elem : streamList) {
				send(elem,"loltwitchstreams");
			}
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
			sendError(e);
		} catch (URISyntaxException e) {
			sendError(e);
		}
	}
	public void getNameFromId(String twitchId) {
		try {
			String twitchUser = TwitchConnector.getIstance().getUserFromId(twitchId);
			List<String> userList = splitData(twitchUser, "data");
			send(userList.get(0),"twitchusers");
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
			sendError(e);
		} catch (URISyntaxException e) {
			sendError(e);
		}
	}
	
	protected String getPagination(String jsonString) {
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONObject pagination = jsonObject.getJSONObject("pagination");
		String cursor = pagination.getString("cursor");
		return cursor;
	}
	//	public void getFortniteStreamList() {
	//		try {
	//			send(TwitchConnector.getInstance().getStreamsByGame("33214"));
	//		} catch (MalformedURLException e) {
	//			sendError(e);
	//		} catch (IOException e) {
	//			sendError(e);
	//		}
	//	}
}
