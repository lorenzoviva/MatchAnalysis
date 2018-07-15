package Connector.Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Connector {

	protected static String query(String queryString) throws IOException, MalformedURLException {
		HttpsURLConnection con = (HttpsURLConnection) (new URL(queryString)).openConnection();
		return query(con);
	}
	protected static String query(String queryString, Map<String,String> properties) throws IOException, MalformedURLException {
		HttpsURLConnection con = (HttpsURLConnection) (new URL(queryString)).openConnection();
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
