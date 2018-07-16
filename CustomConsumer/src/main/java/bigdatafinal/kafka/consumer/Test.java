package bigdatafinal.kafka.consumer;

public class Test {

	public static void main(String[] args) {
		CustomConsumer cc = new ConsoleLoggerConsumer(new String[]{"twitch", "riot"}, "unos");
		cc.receiveMessages();
	}

}
