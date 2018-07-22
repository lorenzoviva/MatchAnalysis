package bigdatafinal.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConsumer extends CustomConsumer{

	protected MongoDatabase database = null;
	public MongoDBConsumer(String[] topics, String group) {
		super(topics, group);
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("bigdatafinal");
	}
	
	@Override
	public void processMessage(ConsumerRecord<String,String> record) {
		System.out.println("Adding to database: " + record.topic() + "\t the value: " + record.value());
		Document document = Document.parse(record.value());
		MongoCollection<Document> collection = database.getCollection(record.topic());
		collection.insertOne(document);
	}


}
