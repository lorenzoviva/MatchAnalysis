package bigdatafinal.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Connector {

	protected static String query(URL url) throws IOException, MalformedURLException, URISyntaxException {
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		return query(con);
	}
	protected static String query(URL url, Map<String,String> properties) throws IOException, MalformedURLException, URISyntaxException {
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		for (String property: properties.keySet()){
			con.addRequestProperty(property, properties.get(property));
		}	
		return query(con);
	}
	private static String query(HttpsURLConnection con) throws IOException, MalformedURLException {
		if (con == null) {
			return "";
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output = "";
		String line;
		while ((line = br.readLine()) != null) {
			output += line;
		}
		br.close();
		return output;
	}
}
