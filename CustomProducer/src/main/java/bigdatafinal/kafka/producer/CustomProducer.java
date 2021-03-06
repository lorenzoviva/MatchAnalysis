package bigdatafinal.kafka.producer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomProducer {

	private Properties props;
	private Producer<String, String> producer;

	public CustomProducer() {
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
	}

	protected void send(String input,String topic) {
		try {
			JSONObject jsonObject = new JSONObject(input);
			jsonObject.put("mongo_time", Long.toString(new Date().getTime()));
			String stringToSend = jsonObject.toString();
			producer.send(new ProducerRecord<String, String>(topic, Integer.toString(stringToSend.hashCode()), stringToSend));
		}catch(JSONException e) {
			System.out.println(input);
			e.printStackTrace();
		}
	}

	protected List<String> splitData(String jsonString, String field) {
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray arrayToSplit = jsonObject.getJSONArray("data");
		List<String> returnList = new LinkedList<String>();
		for (int i=0; i<arrayToSplit.length(); i++) {
			returnList.add(arrayToSplit.getJSONObject(i).toString());
		}
		return returnList;
	}
	
	protected void sendError(Exception error) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error",  error.getMessage());
		send(jsonObject.toString(),"error");
	}

	public void close() {
		this.producer.close();
	}
	

	public void flush() {
		this.producer.flush();		
	}
}
