package bigdatafinal.kafka.producer;

public class TestT {

	public static void main(String[] args) {
		TwitchProducer prod = new TwitchProducer();
		prod.getLeagueOfLegendsStreamList();
		prod.close();
	}

}
