package bigdatafinal.kafka.consumer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class RiotEloFetcher {

	public static void main(String[] args) throws MalformedURLException, URISyntaxException, IOException {
		// TODO Auto-generated method stub

		RiotEloFetcher rf = new RiotEloFetcher();
	}

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collectionToWrite;

	public RiotEloFetcher() throws MalformedURLException, URISyntaxException, IOException {
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("bigdatafinal");
		MongoCollection<Document> collectionToIter = database.getCollection("riot");
		this.collectionToWrite = database.getCollection("elo");
		BasicDBObject query = new BasicDBObject();
		query.put("id", 1);
		query.put("name", 1);
		MongoCursor<Document> cursor = collectionToIter.find(query).iterator();
		try {
			while (cursor.hasNext()) {
				iterInServers(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	private void iterInServers(Document document) {
		fetchRiotUserEloFromId(document.get("id").toString(), document.get("name").toString());
	}
	public static void fetchRiotUserEloFromId(String userId, String username) {
		Scheduler.getInstance().fetchRiotUserEloFromId(userId,username);
	}
	
}
