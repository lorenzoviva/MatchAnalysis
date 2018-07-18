package bigdatafinal.kafka.consumer;

import bigdatafinal.connector.RiotConnector;
import bigdatafinal.kafka.producer.RiotProducer;
import bigdatafinal.kafka.producer.TwitchProducer;

public class Scheduler {

	private static Scheduler instance = null;
	private TwitchProducer twitchProducer;
	private RiotProducer riotProducer;

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

	public void fetchNextLolTwitchStreams(String pagination) {
		twitchProducer.getLeagueOfLegendsStreamList(pagination);
		twitchProducer.flush();
	}
	public void fetchTwitchUsernameFromId(String twitchId) {
		twitchProducer.getNameFromId(twitchId);
		twitchProducer.flush();
	}
	private void startConsumers() {
		new Thread(new ConsumerRunnable(new ErrorConsumer(new String[]{"Error"}, "1"))).start();
		new Thread(new ConsumerRunnable(new MongoDBConsumer(new String[]{"loltwitchstreams","twitchusers", "riot"}, "1"))).start();
		new Thread(new ConsumerRunnable(new TwitchStreamConsumer(new String[]{"loltwitchstreams"}, "1"))).start();
		new Thread(new ConsumerRunnable( new TwitchUsernameConsumer(new String[]{"twitchusers"}, "1"))).start();
		new Thread(new ConsumerRunnable( new RiotUserConsumer(new String[]{"riot"}, "1"))).start();
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
