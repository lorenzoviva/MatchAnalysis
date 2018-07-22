package bigdatafinal.kafka.producer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.json.JSONObject;

import bigdatafinal.connector.RiotConnector;

public class RiotProducer extends CustomProducer {
	public RiotProducer() {
		super();
	}

	public void getSummonerByName(String summonerName, String server) {
		try {
			String summonerByName = RiotConnector.getIstance().getSummonerByName(summonerName, server);
			System.out.println("SUMMONER: " + summonerByName);
			send(summonerByName, "riot");
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
			sendError(e);
		} catch (URISyntaxException e) {
			sendError(e);
		}
	}

	public void getEloById(String id, String server) {
		try {
			String answer = RiotConnector.getIstance().getEloById(id, server);
			JSONObject answerObject = new JSONObject(answer);
			if (answerObject.has("queueType") && answerObject.get("queueType").equals("RANKED_SOLO_5x5")) {
				System.out.println("ELO: " + answer);
				send(answer, "elo");
			}
		} catch (MalformedURLException e) {
			sendError(e);
		} catch (IOException e) {
			sendError(e);
		} catch (URISyntaxException e) {
			sendError(e);
		}
	}
}
