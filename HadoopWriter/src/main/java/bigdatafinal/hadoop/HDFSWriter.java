package bigdatafinal.hadoop;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class HDFSWriter {
	private MongoDatabase database = null;
	private static final String HDFS_URI = "hdfs://localhost:9000";
	private String path;
	private FileSystem fs;
	private MongoClient mongoClient;

	public void close() {
		this.mongoClient.close();
	}

	public HDFSWriter() throws IOException {
		// MongoDB setup
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("bigdatafinal");
		// HDFS setup
		this.path = "/user/emo/input/";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", HDFS_URI);
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		System.setProperty("HADOOP_USER_NAME", "emo");
		System.setProperty("hadoop.home.dir", "/");
		// Get the filesystem HDFS
		this.fs = FileSystem.get(URI.create(HDFS_URI), conf);
	}

	public void iterateCollection(String collection) throws IOException {
		MongoCollection<Document> collectionToIter = database.getCollection(collection);
		if (collectionToIter.count() == 0) {
			System.out.println("Collezione " + collection + " vuota.");
			return;
		}
		MongoCursor<Document> cursor = collectionToIter.find().iterator();
		try {
			while (cursor.hasNext()) {
				String json = cursor.next().toJson();
				writeToHDFS(this.path + collection + "/", Integer.toString(json.hashCode()) + ".json", json);
			}
		} finally {
			cursor.close();
		}
	}

	public void writeToHDFS(String pathToWrite, String name, String content) throws IOException {
		// Crea la directory se non esiste già
		Path newFolderPath = new Path(pathToWrite);
		if (!fs.exists(newFolderPath)) {
			fs.mkdirs(newFolderPath);
			System.out.println("Path " + path + " created.");
		}
		Path hdfswritepath = new Path(newFolderPath + "/" + name);
		FSDataOutputStream outputStream = fs.create(hdfswritepath);
		outputStream.writeBytes(content);
		System.out.println("Inserito " + name);
		outputStream.close();
	}

}
