package bigdatafinal.hadoop;

import java.io.IOException;

public class HadoopBatch {
	public static void main(String[] args) {
		HDFSWriter writer = null;
		try {
			writer = new HDFSWriter();
			writer.iterateCollection("twitchusers");
			writer.iterateCollection("riot");
			writer.iterateCollection("elo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}

	}
}
