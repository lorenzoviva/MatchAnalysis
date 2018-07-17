package bigdatafinal.kafka.consumer;

public class Test {

	public static void main(String[] args) {
		CustomConsumer cc = new MongoDBConsumer(new String[]{"twitch", "riot"}, "unos");
		cc.receiveMessages();
	}

}
