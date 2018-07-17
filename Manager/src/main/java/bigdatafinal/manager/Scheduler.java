package bigdatafinal.manager;

import bigdatafinal.kafka.consumer.CustomConsumer;
import bigdatafinal.kafka.consumer.MongoDBConsumer;
import bigdatafinal.kafka.producer.TwitchProducer;

public class Scheduler {

	private static Scheduler instance = null;

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
		startConsumers();
		fetchLolTwitchStreams();
	}
	private void fetchLolTwitchStreams() {
		TwitchProducer prod = new TwitchProducer();
		prod.getLeagueOfLegendsStreamList();
		prod.close();
	}

	private void fetchNextLolTwitchStreams(String pagination) {
		TwitchProducer prod = new TwitchProducer();
		prod.getLeagueOfLegendsStreamList(pagination);
		prod.close();
	}
	private void fetchTwitchUsernameFromId(String twitchID) {
		
	}
	private void startConsumers() {
		MongoDBConsumer cc = new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot"}, "1");
		cc.receiveMessages();
	}
}
