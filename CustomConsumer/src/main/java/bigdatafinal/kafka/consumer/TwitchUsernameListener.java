package bigdatafinal.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.json.JSONObject;

public class TwitchUsernameListener extends CustomConsumer {

	public TwitchUsernameListener(String[] topics, String group) {
		super(topics, group);
	}

	@Override
	public void processMessage(ConsumerRecord<String, String> record) {
		Scheduler scheduler = Scheduler.getInstance();
		JSONObject jsonObject = new JSONObject(record.value());
		scheduler.fetchTwitchUsernameFromId(jsonObject.getString("user_id"));
	}
	

}
