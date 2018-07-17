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

		new Thread(new RUL()).start();
		System.out.println("1.1");

		new Thread(new TIL()).start();
		System.out.println("1.2");

		new Thread(new MDB()).start();
		System.out.println("1.3");
	}
	class TIL implements Runnable{
		public void run() {
			TwitchIDListener til = new TwitchIDListener(new String[]{"loltwitchstreams"}, "1");
			til.receiveMessages();
		}
	}
	class RUL implements Runnable{
		public void run() {
			TwitchUsernameListener rul = new TwitchUsernameListener(new String[]{"twitchusers"}, "1");
			rul.receiveMessages();
		}
	}
	class MDB implements Runnable{
		public void run() {
			MongoDBConsumer mdb = new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot"}, "1");
			mdb.receiveMessages();
		}
	}
}
