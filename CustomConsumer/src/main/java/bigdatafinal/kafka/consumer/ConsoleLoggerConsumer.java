package bigdatafinal.kafka.consumer;

public class ConsoleLoggerConsumer extends CustomConsumer{

	public ConsoleLoggerConsumer(String[] topics, String group) {
		super(topics, group);
	}

	@Override
	public void processMessage(String message) {			
		System.out.println(message);
	}

}
