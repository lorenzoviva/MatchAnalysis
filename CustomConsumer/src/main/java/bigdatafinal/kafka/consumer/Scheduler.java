package bigdatafinal.kafka.consumer;

import java.io.IOException;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;

import bigdatafinal.connector.RiotConnector;
import bigdatafinal.kafka.producer.RiotProducer;
import bigdatafinal.kafka.producer.TwitchProducer;

public class Scheduler {

	private static Scheduler instance = null;
	private TwitchProducer twitchProducer;
	private RiotProducer riotProducer;
	private boolean dropDB = true;
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
		twitchProducer = new TwitchProducer();
		riotProducer = new RiotProducer();
		fetchLolTwitchStreams();
		System.out.println("1");
		startConsumers();
		System.out.println("2");

	}
	
	public void fetchLolTwitchStreams() {
		twitchProducer.getLeagueOfLegendsStreamList();
		twitchProducer.flush();
	}
	public void fetchRiotPlayerFromUsername(String username) {
		riotProducer.getSummonerByName(username, RiotConnector.BR_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.EUNE_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.EUW_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.KR_SERVER);
		riotProducer.getSummonerByName(username, RiotConnector.NA_SERVER);
		twitchProducer.flush();
	}

	public void fetchNextLolTwitchStreams() {
		twitchProducer.getNextLeagueOfLegendsStreamList();
		twitchProducer.flush();
	}
	
	public void fetchTwitchUsernameFromId(String twitchId) {
		twitchProducer.getNameFromId(twitchId);
		twitchProducer.flush();
	}
	
	private void startConsumers() {
		new Thread(new ConsumerRunnable(new ErrorConsumer(new String[]{"Error"}, "1"))).start();
		new Thread(new ConsumerRunnable(new TwitchStreamConsumer(new String[]{"loltwitchstreams"}, "1"))).start();
		new Thread(new ConsumerRunnable(new TwitchUsernameConsumer(new String[]{"twitchusers"}, "1"))).start();
		new Thread(new ConsumerRunnable(new RiotUserConsumer(new String[]{"riot"}, "1"))).start();
		new Thread(new ConsumerRunnable(new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot"}, "2"))).start();
	}
	private void executeStartupScripts() {
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("/usr/local/kafka/bin/zookeeper-server-start.sh config/zookeeper.properties");
			rt.exec("/usr/local/kafka/bin/kafka-server-start.sh config/server.properties");
			rt.exec("sudo service mongod start");
			if (dropDB) {
				MongoClient mongoClient = MongoClients.create();
				MongoDatabase database = mongoClient.getDatabase("bigdatafinal");
				database.drop(null);
			}
		} catch (IOException e) {
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
