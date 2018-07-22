package bigdatafinal.kafka.consumer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import bigdatafinal.connector.RiotConnector;

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

		MongoCursor<Document> cursor = collectionToIter.find().iterator();
		try {
			while (cursor.hasNext()) {
				iterInServers(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	private void iterInServers(Document document) {
		String tempString;
		try {
			tempString = RiotConnector.getIstance().getEloById(document.get("id").toString(), RiotConnector.EUW_SERVER)
					.replaceAll("(\\[|\\])", "");
			tempString = tempString.replaceAll("},", "}#sep#");
			for (String elem : tempString.split("#sep#")) {
				if (elem.contains("\"queueType\":\"RANKED_SOLO_5x5\"")) {
					this.collectionToWrite.insertOne(Document.parse(elem));
					System.out.println("Inserito " + elem);
				}
			}

			tempString = RiotConnector.getIstance().getEloById(document.get("id").toString(), RiotConnector.EUNE_SERVER)
					.replaceAll("(\\[|\\])", "");
			tempString = tempString.replaceAll("},", "}#sep#");
			for (String elem : tempString.split("#sep#")) {
				if (elem.contains("\"queueType\":\"RANKED_SOLO_5x5\"")) {
					this.collectionToWrite.insertOne(Document.parse(elem));
					System.out.println("Inserito " + elem);
				}
			}

			tempString = RiotConnector.getIstance().getEloById(document.get("id").toString(), RiotConnector.NA_SERVER)
					.replaceAll("(\\[|\\])", "");
			tempString = tempString.replaceAll("},", "}#sep#");
			for (String elem : tempString.split("#sep#")) {
				if (elem.contains("\"queueType\":\"RANKED_SOLO_5x5\"")) {
					this.collectionToWrite.insertOne(Document.parse(elem));
					System.out.println("Inserito " + elem);
				}
			}

			tempString = RiotConnector.getIstance().getEloById(document.get("id").toString(), RiotConnector.KR_SERVER)
					.replaceAll("(\\[|\\])", "");
			tempString = tempString.replaceAll("},", "}#sep#");
			for (String elem : tempString.split("#sep#")) {
				if (elem.contains("\"queueType\":\"RANKED_SOLO_5x5\"")) {
					this.collectionToWrite.insertOne(Document.parse(elem));
					System.out.println("Inserito " + elem);
				}
			}

			tempString = RiotConnector.getIstance().getEloById(document.get("id").toString(), RiotConnector.BR_SERVER)
					.replaceAll("(\\[|\\])", "");
			tempString = tempString.replaceAll("},", "}#sep#");
			for (String elem : tempString.split("#sep#")) {
				if (elem.contains("\"queueType\":\"RANKED_SOLO_5x5\"")) {
					this.collectionToWrite.insertOne(Document.parse(elem));
					System.out.println("Inserito " + elem);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
