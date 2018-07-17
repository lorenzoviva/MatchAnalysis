package bigdatafinal.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsoleLoggerConsumer extends CustomConsumer{

	public ConsoleLoggerConsumer(String[] topics, String group) {
		super(topics, group);
	}

	@Override
	public void processMessage(ConsumerRecord<String,String> record) {			
		System.out.println(record.value());
	}

}
