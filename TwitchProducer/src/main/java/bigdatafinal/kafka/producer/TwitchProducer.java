package bigdatafinal.kafka.producer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import bigdatafinal.connector.TwitchConnector;

public class TwitchProducer extends CustomProducer {
	private static final String TOPIC = "twitch";

	public TwitchProducer() {
		super(TOPIC);
	}

	public void getLeagueOfLegendsStreamList() {
		try {
			String streamsByGame = TwitchConnector.getStreamsByGame("21779");
			List<String> streamList = splitData(streamsByGame, "data");

			System.out.println("STREAMS: " + streamsByGame);
			for (String elem : streamList) {
				send(elem);
			}
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
