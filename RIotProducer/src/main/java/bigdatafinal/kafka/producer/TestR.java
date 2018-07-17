package bigdatafinal.kafka.producer;

import bigdatafinal.connector.RiotConnector;

public class TestR {

	public static void main(String[] args) {
		RiotProducer prod = new RiotProducer();
		prod.getSummonerByName("Shiphtur", RiotConnector.NA_SERVER);
		prod.close();
	}
	
}
