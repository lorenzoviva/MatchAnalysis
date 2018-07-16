package bigdatafinal.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class CustomProducer {
	
	private Properties props;
	private Producer<String, String> producer;
	private String topic;
	
	public CustomProducer(String topic) {
		this.props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		this.producer = new KafkaProducer<String, String>(props);
		this.topic = topic;
	}

	protected void send(String input) {
		try {
			producer.send(new ProducerRecord<String, String>(this.topic, Integer.toString(input.hashCode()), input));
		} finally {
			close();
		}
	}
	
	protected void sendError(Exception error) {
		send("{'error':" + error.getMessage() + "}");
	}
	
	protected void close() {
		this.producer.close();
	}
}
