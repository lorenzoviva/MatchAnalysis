package bigdatafinal.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class Test {

	public static void main(String[] args) {
		try {
			System.out.println(RiotConnector.getSummonerByName("Vadim Black", RiotConnector.EUW_SERVER));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
