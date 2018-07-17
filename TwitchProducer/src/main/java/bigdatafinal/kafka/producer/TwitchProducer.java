package bigdatafinal.kafka.producer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import bigdatafinal.connector.TwitchConnector;

public class TwitchProducer extends CustomProducer {

	public TwitchProducer() {
		super();
	}

	public void getLeagueOfLegendsStreamList() {
		try {
			String streamsByGame = TwitchConnector.getStreamsByGame("21779");
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
	
	public void getLeagueOfLegendsStreamList(String pagination) {
		try {
			String streamsByGame = TwitchConnector.getStreamsByGame("21779",pagination);
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
			String twitchUser = TwitchConnector.getUserFromId(twitchId);
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
	//	public void getFortniteStreamList() {
	//		try {
	//			send(TwitchConnector.getStreamsByGame("33214"));
	//		} catch (MalformedURLException e) {
	//			sendError(e);
	//		} catch (IOException e) {
	//			sendError(e);
	//		}
	//	}
}
