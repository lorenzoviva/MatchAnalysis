package bigdatafinal.kafka.producer;

import java.io.IOException;
import java.net.MalformedURLException;

import bigdatafinal.connector.RiotConnector;

public class RiotProducer extends CustomProducer {
	private static final String TOPIC = "riot";
	
	public RiotProducer(String topic) {
		super(TOPIC);
	}
	
	public void getSummonerByName() {
		try {
			String summonerByName = RiotConnector.getSummonerByName("Vadim Black", RiotConnector.EUW_SERVER);
			System.out.println("SUMMONER: " + summonerByName);
			send(summonerByName);
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
			sendError(e);
		}
	}
}
