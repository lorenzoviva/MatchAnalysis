package bigdatafinal.kafka.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class CustomConsumer {
	public Consumer<String,String> consumer = null;
	public CustomConsumer(String topic, String group){

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", group);
		props.put("enable.auto.commit", "true");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		//kafka consumer object
		consumer = new KafkaConsumer<String, String>(props);

		//subscribe to topic
		consumer.subscribe(Arrays.asList(topic));
	}



	public void receiveMessages(){
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(1000);
				for (ConsumerRecord<String, String> record : records){
					System.out.printf("topic = %s, partition = %s, offset = %d, key = %s, value = %s\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
				}
			}
		} finally {
			consumer.close();
		}
	}
}
