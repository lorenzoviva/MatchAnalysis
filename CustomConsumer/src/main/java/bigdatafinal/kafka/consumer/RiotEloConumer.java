package bigdatafinal.kafka.consumer;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bigdatafinal.hadoop.HDFSWriter;

public class RiotEloConumer extends ConsoleLoggerConsumer{
	
	HDFSWriter writer;
	protected MongoDatabase database = null;

	public RiotEloConumer(String[] topics, String group) {
		super(topics, group);
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("bigdatafinal");		
	}
	public void processMessage(ConsumerRecord<String,String> record) {			
		try {
			writer = new HDFSWriter();
			JSONObject value = new JSONObject(record.value());
			String username = value.getString("username");
			Document document = database.getCollection("twitch").find(eq("display_name", username)).first();
			writer.appendToHDFS("/user/emo/input/tables/", "usernameelo", value.getString("elo") + "," + document.getString("view_count"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}

}
