package bigdatafinal.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.Document;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

public class MongoDBConsumer extends CustomConsumer{

	protected MongoDatabase database = null;
	public MongoDBConsumer(String[] topics, String group) {
		super(topics, group);
		MongoClient mongoClient = MongoClients.create();
		database = mongoClient.getDatabase("bigdatafinal");
	}
	
	@Override
	public void processMessage(ConsumerRecord<String,String> record) {
		System.out.println("Currently processing: " + record.topic() + ": " + record.value());
		Document document = Document.parse(record.value());
		MongoCollection<Document> collection = database.getCollection(record.topic());
		collection.insertOne(document, new SingleResultCallback<Void>() {
		    public void onResult(final Void result, final Throwable t) {
		        System.out.println("Inserted!");
		    }
		});
	}


}
