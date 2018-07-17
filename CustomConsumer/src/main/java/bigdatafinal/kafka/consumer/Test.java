package bigdatafinal.kafka.consumer;

public class Test {

	public static void main(String[] args) {
		CustomConsumer cc = new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot"}, "unos");
		cc.receiveMessages();
	}

}
