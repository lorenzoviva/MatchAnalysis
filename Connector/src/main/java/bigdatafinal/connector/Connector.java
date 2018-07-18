package bigdatafinal.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Connector {
	private int requests = 0;
	private long begin = -1;
	private int requestsLimit = -1;
	private int timeLimit = -1;
	
	public Connector(int requestsLimit, int timeLimit) {
		this.requestsLimit = requestsLimit;
		this.timeLimit = timeLimit;
	}
	protected String query(URL url) throws IOException, MalformedURLException, URISyntaxException {
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		return query(con);
	}
	protected String query(URL url, Map<String,String> properties) throws IOException, MalformedURLException, URISyntaxException {
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		for (String property: properties.keySet()){
			con.addRequestProperty(property, properties.get(property));
		}	
		return query(con);
	}
	private String query(HttpsURLConnection con) throws IOException, MalformedURLException {
		if (con == null) {
			return "";
		}
		updateRates();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output = "";
		String line;
		while ((line = br.readLine()) != null) {
			output += line;
		}
		br.close();
		return output;
	}
	protected void updateRates() {
		if(timeLimit > 0 && requestsLimit > 0) {
			long now = new Date().getTime();
			System.out.println("-------------QUERY: " + (now - begin) + "/" + timeLimit+  "   " + requests + "/" + requestsLimit );
			if(begin == -1) {
				begin = now;
			}else if (now - begin < timeLimit ) {
				requests++;
			}else {
				begin = now;
				requests = 0;
			}
			if(requests >= requestsLimit) {
				try {
					Thread.sleep(timeLimit - (now -  begin));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
