package bigdatafinal.manager;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

import bigdatafinal.kafka.producer.TwitchProducer;

public class Scheduler {

	private static Scheduler instance = null;

	public static void main(String[] args) {
		fetchLolTwitchStreams();
	}

	public static Scheduler getInstance() {
		if (instance == null) {
			instance = new Scheduler();
		}
		return instance;
	}

	private static void fetchLolTwitchStreams() {
		TwitchProducer prod = new TwitchProducer();
		prod.getLeagueOfLegendsStreamList();
		prod.close();
	}

	private static void fetchNextLolTwitchStreams(String pagination) {
		TwitchProducer prod = new TwitchProducer();
		prod.getLeagueOfLegendsStreamList(pagination);
		prod.close();
	}
}
