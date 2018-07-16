package bigdatafinal.kafka.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public abstract class CustomConsumer {
	public Consumer<String,String> consumer = null;
	public CustomConsumer(String[] topics, String group){

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", group);
		props.put("enable.auto.commit", "true");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		//kafka consumer object
		consumer = new KafkaConsumer<String, String>(props);

		//subscribe to topic
		consumer.subscribe(Arrays.asList(topics));
	}

	public abstract void processMessage(String message);

	public void receiveMessages(){
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(1000);
				for (ConsumerRecord<String, String> record : records){
					processMessage(record.value());
				}
			}
		} finally {
			consumer.close();
		}
	}
}
