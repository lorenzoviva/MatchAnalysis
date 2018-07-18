package bigdatafinal.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.json.JSONObject;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

public class RiotUserConsumer extends CustomConsumer {
	public RiotUserConsumer(String[] topics, String group) {
		super(topics, group);
	}

	@Override
	public void processMessage(ConsumerRecord<String, String> record) {
		JSONObject jsonObject = new JSONObject(record.value());
		final String username = jsonObject.getString("name");
		System.out.println("Finally fetched (riot) user: "+ username + " : \t"+ record.value());
		
	}
	

}
