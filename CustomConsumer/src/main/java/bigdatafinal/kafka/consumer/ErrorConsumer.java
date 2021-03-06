package bigdatafinal.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ErrorConsumer extends CustomConsumer{

	public ErrorConsumer(String[] topics, String group) {
		super(topics, group);
	}

	@Override
	public void processMessage(ConsumerRecord<String,String> record) {			
		System.err.println(record.value());
	}

}
