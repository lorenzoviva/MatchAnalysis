package bigdatafinal.kafka.producer;

import java.io.IOException;
import java.net.MalformedURLException;

import bigdatafinal.connector.TwitchConnector;

public class TwitchProducer extends CustomProducer {
	private static final String TOPIC = "twitch-stream-unsupervised";
	
	public TwitchProducer() {
		super(TOPIC);
	}
	public void getLeagueOfLegendsStreamList() {
		try {
			send(TwitchConnector.getStreamsByGame("21779"));
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
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
