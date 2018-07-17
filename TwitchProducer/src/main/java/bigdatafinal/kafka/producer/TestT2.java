package bigdatafinal.kafka.producer;

public class TestT2 {

	public static void main(String[] args) {
		TwitchProducer prod = new TwitchProducer();
		prod.getNameFromId("26560695");
		prod.close();
	}

}
