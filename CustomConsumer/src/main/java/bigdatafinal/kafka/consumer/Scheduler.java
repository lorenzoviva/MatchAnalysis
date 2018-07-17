package bigdatafinal.kafka.consumer;

import bigdatafinal.kafka.producer.TwitchProducer;

public class Scheduler {

	private static Scheduler instance = null;
	private TwitchProducer twitchProducer;

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
		twitchProducer = new TwitchProducer();
		startConsumers();
		fetchLolTwitchStreams();
	}
	
	public void fetchLolTwitchStreams() {
		twitchProducer.getLeagueOfLegendsStreamList();
		twitchProducer.flush();
	}

	public void fetchNextLolTwitchStreams(String pagination) {
		twitchProducer.getLeagueOfLegendsStreamList(pagination);
		twitchProducer.flush();
	}
	public void fetchTwitchUsernameFromId(String twitchId) {
		twitchProducer.getNameFromId(twitchId);
		twitchProducer.flush();

	}
	private void startConsumers() {
		MongoDBConsumer cc = new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot"}, "1");
		cc.receiveMessages();
	}
}
