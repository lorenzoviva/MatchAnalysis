package bigdatafinal.kafka.consumer;

import static com.mongodb.client.model.Filters.eq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

public class TwitchUsernameConsumer extends CustomConsumer {
	protected MongoDatabase database = null;

	public TwitchUsernameConsumer(String[] topics, String group) {
		super(topics, group);
		MongoClient mongoClient = MongoClients.create();
		database = mongoClient.getDatabase("bigdatafinal");
	}

	@Override
	public void processMessage(ConsumerRecord<String, String> record) {
		MongoCollection<Document> riotUsers = database.getCollection("riot");
		JSONObject jsonObject = new JSONObject(record.value());
		final String username = jsonObject.getString("display_name");
		riotUsers.find(eq("name", username)).first(new SingleResultCallback<Document>() {
			public void onResult(Document doc, Throwable arg1) {
				if(doc == null) {
					Scheduler.getInstance().fetchRiotPlayerFromUsername(username);
					System.out.println("Fetching username (riot): " + username);
				}else {
					System.out.println("Not fetching username (riot): " + username);

				}
			}
		 
		});
		
	}

}
