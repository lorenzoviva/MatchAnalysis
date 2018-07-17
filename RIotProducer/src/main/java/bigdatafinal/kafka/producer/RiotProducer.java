package bigdatafinal.kafka.producer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import bigdatafinal.connector.RiotConnector;

public class RiotProducer extends CustomProducer {
	public RiotProducer() {
		super();
	}
	public void getSummonerByName() {
		try {
			String summonerByName = RiotConnector.getSummonerByName("Seggio", RiotConnector.EUW_SERVER);
			System.out.println("SUMMONER: " + summonerByName);
			send(summonerByName,"riot");
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
			sendError(e);
		} catch (URISyntaxException e) {
			sendError(e);
		}
	}
}
