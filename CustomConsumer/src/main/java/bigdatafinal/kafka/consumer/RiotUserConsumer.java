package bigdatafinal.kafka.consumer;

import org.apache.hadoop.fs.FileSystem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;

public class RiotUserConsumer extends CustomConsumer {
	private FileSystem fs;
	public RiotUserConsumer(String[] topics, String group) {
		super(topics, group);
//		// ====== Init HDFS File System Object
//		Configuration conf = new Configuration();
//		// Set FileSystem URI
//		String hdfsuri = " hdfs://namenodedns:port/user/hdfs/folder/file.csv";
//		conf.set("fs.defaultFS", hdfsuri);
//		// Because of Maven
//		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
//		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
//		// Set HADOOP user
//		System.setProperty("HADOOP_USER_NAME", "hdfs");
//		System.setProperty("hadoop.home.dir", "/");
//		//Get the filesystem - HDFS
//		try {
//			fs = FileSystem.get(URI.create(hdfsuri), conf);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void processMessage(ConsumerRecord<String, String> record) {
		JSONObject jsonObject = new JSONObject(record.value());
		final String username = jsonObject.getString("name");
		System.out.println("Finally fetched (riot) user: "+ username + " : \t"+ record.value());
		
	}
	

}
