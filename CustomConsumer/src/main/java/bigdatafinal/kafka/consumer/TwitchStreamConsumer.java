package bigdatafinal.kafka.consumer;

import static com.mongodb.client.model.Filters.eq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TwitchStreamConsumer extends CustomConsumer {
	protected MongoDatabase database = null;

	public TwitchStreamConsumer(String[] topics, String group) {
		super(topics, group);
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("bigdatafinal");
	}

	@Override
	public void processMessage(ConsumerRecord<String, String> record) {
		System.out.println("Twitch stream processing: " + record.value());
		MongoCollection<Document> twitchUsers = database.getCollection("twitchusers");
		JSONObject jsonObject = new JSONObject(record.value());
		final String userid = jsonObject.getString("user_id");
		Scheduler.getInstance().fetchNextLolTwitchStreams();
		if (twitchUsers.find(eq("id", userid)).first() == null)
			Scheduler.getInstance().fetchTwitchUsernameFromId(userid);
//		twitchUsers.find(eq("id", userid)).first(new SingleResultCallback<Document>() {
//			public void onResult(Document doc, Throwable arg1) {
//				if(doc == null) {
//					Scheduler.getInstance().fetchTwitchUsernameFromId(userid);
////					System.out.println("Fetching userId: " + userid);
//				}else {
////					System.out.println("Not fetching userId: " + userid);
//
//				}
//			}
//		 
//		});
		
	}
	

}
