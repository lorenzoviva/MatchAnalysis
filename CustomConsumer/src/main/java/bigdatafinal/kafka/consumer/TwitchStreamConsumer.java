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

public class TwitchStreamConsumer extends CustomConsumer {
	protected MongoDatabase database = null;

	public TwitchStreamConsumer(String[] topics, String group) {
		super(topics, group);
		MongoClient mongoClient = MongoClients.create();
		database = mongoClient.getDatabase("bigdatafinal");
	}

	@Override
	public void processMessage(ConsumerRecord<String, String> record) {
		MongoCollection<Document> twitchUsers = database.getCollection("twitchusers");
		JSONObject jsonObject = new JSONObject(record.value());
		final String userid = jsonObject.getString("user_id");
		twitchUsers.find(eq("user_id", userid)).first(new SingleResultCallback<Document>() {
			public void onResult(Document doc, Throwable arg1) {
				if(doc == null) {
					Scheduler.getInstance().fetchTwitchUsernameFromId(userid);
					System.out.println("Fetching userId: " + userid);
				}else {
					System.out.println("Not fetching userId: " + userid);

				}
			}
		 
		});
		
	}
	

}
