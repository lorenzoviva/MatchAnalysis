package bigdatafinal.kafka.consumer;

public class Test {

	public static void main(String[] args) {
		CustomConsumer cc = new CustomConsumer("twitch", "unos");
		cc.receiveMessages();
	}

}