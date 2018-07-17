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

		fetchLolTwitchStreams();
		System.out.println("1");
		startConsumers();
		System.out.println("2");

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
		MongoDBConsumer mdb = new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot"}, "1");
		mdb.receiveMessages();
		System.out.println("1.1");

		TwitchIDListener til = new TwitchIDListener(new String[]{"loltwitchstreams"}, "1");
		til.receiveMessages();
		System.out.println("1.2");

		TwitchUsernameListener rul = new TwitchUsernameListener(new String[]{"twitchusers"}, "1");
		rul.receiveMessages();
		System.out.println("1.3");



	}
}
