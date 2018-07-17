package bigdatafinal.manager;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

public class Scheduler {

	public static void main(String[] args) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("bigdatafinal");

	}
	
	private static String populateRiotFromTwitchId(String twitchId) {
		
		return null;
	}
	private static void getTwitchUsersMissingUsernames(MongoDatabase database) {
		MongoCollection<Document> streams = database.getCollection("loltwitchstreams");
		DBObject lookupOperation = (DBObject)new BasicDBObject(
			    "$lookup", new BasicDBObject("from", "twitchusers")
			        .append("localField", "address.location.place._id")
			        .append("foreignField", "_id")
			        .append("as", "address.location.place"));
	}
}
