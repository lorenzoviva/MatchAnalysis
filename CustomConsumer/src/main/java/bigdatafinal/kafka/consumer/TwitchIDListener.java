package bigdatafinal.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.json.JSONObject;

public class TwitchIDListener extends CustomConsumer {

	public TwitchIDListener(String[] topics, String group) {
		super(topics, group);
	}

	@Override
	public void processMessage(ConsumerRecord<String, String> record) {
		Scheduler scheduler = Scheduler.getInstance();
		JSONObject jsonObject = new JSONObject(record.value());
		jsonObject.getString("user_id");
		
	}
	

}
