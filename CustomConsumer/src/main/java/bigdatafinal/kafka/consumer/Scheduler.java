package bigdatafinal.kafka.consumer;

import java.io.IOException;

import org.bson.Document;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import bigdatafinal.connector.RiotConnector;
import bigdatafinal.kafka.producer.RiotProducer;
import bigdatafinal.kafka.producer.TwitchProducer;

public class Scheduler {

	private static Scheduler instance = null;
	private TwitchProducer twitchProducer1, twitchProducer2;
	private RiotProducer riotProducer;
	private boolean dropDB = true;
	String kafkaHome = "/home/emo/kafka_2.11-1.1.0/";

	public static void main(String[] args) {
		getInstance();
	}

	public static Scheduler getInstance() {
		if (instance == null) {
			instance = new Scheduler();
		}
		return instance;
	}

	public Scheduler() {
		executeStartupScripts();
		twitchProducer1 = new TwitchProducer();
		twitchProducer2 = new TwitchProducer();
		riotProducer = new RiotProducer();
		fetchLolTwitchStreams();
		System.out.println("1");
		startConsumers();
		System.out.println("2");

	}
	
	public void fetchLolTwitchStreams() {
		twitchProducer1.getLeagueOfLegendsStreamList();
		twitchProducer1.flush();
	}
	public void fetchRiotPlayerFromUsername(String username) {
		riotProducer.getSummonerByName(username, RiotConnector.BR_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.EUNE_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.EUW_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.KR_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.NA_SERVER);
		riotProducer.flush();
	}

	public void fetchNextLolTwitchStreams() {
		twitchProducer1.getNextLeagueOfLegendsStreamList();
		twitchProducer1.flush();
	}
	
	public void fetchTwitchUsernameFromId(String twitchId) {
		twitchProducer2.getNameFromId(twitchId);
		twitchProducer2.flush();
	}

	private void fetchRiotUserEloFromId(String userId, String username) {
		riotProducer.getEloById(userId, username, RiotConnector.EUW_SERVER);
		riotProducer.getEloById(userId, username, RiotConnector.BR_SERVER);
		riotProducer.getEloById(userId, username, RiotConnector.EUNE_SERVER);
		riotProducer.getEloById(userId, username, RiotConnector.KR_SERVER);
		riotProducer.getEloById(userId, username, RiotConnector.NA_SERVER);
		riotProducer.flush();

	}
	
	private void startConsumers() {
		new Thread(new ConsumerRunnable(new ErrorConsumer(new String[]{"error"}, "error"))).start();
		new Thread(new ConsumerRunnable(new TwitchStreamConsumer(new String[]{"loltwitchstreams"}, "streams"))).start();
		new Thread(new ConsumerRunnable(new TwitchUsernameConsumer(new String[]{"twitchusers"}, "twitchusers"))).start();
		new Thread(new ConsumerRunnable(new RiotUserConsumer(new String[]{"riot"}, "riotusers"))).start();
		new Thread(new ConsumerRunnable(new RiotUserConsumer(new String[]{"elo"}, "riotusers"))).start();
		new Thread(new ConsumerRunnable(new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot","elo"}, "mongodb"))).start();
	}
	private void executeStartupScripts() {
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(kafkaHome+"bin/zookeeper-server-start.sh "+kafkaHome+"config/zookeeper.properties");
			Thread.sleep(5000);
			System.out.println("Zookeeper started. Starting kafka...");
			rt.exec(kafkaHome+"bin/kafka-server-start.sh "+kafkaHome+"config/server.properties");
			Thread.sleep(15000);
			System.out.println("Kafka started.");


//			rt.exec("sudo service mongod start");
//			if (dropDB) {
//				MongoClient mongoClient = MongoClients.create();
//				MongoDatabase database = mongoClient.getDatabase("bigdatafinal");
//				database.drop(new SingleResultCallback<Void>() {
//					public void onResult(Void arg0, Throwable arg1) {
//						// Robe						
//					}
//				});
//			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class ConsumerRunnable implements Runnable {
		CustomConsumer customConsumer;
		
		public ConsumerRunnable(CustomConsumer cc) {
			customConsumer = cc;
		}

		public void run() {
			customConsumer.receiveMessages();
		}
	}
}
