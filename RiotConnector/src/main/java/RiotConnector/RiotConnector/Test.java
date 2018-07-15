package RiotConnector.RiotConnector;

import java.io.IOException;
import java.net.MalformedURLException;

public class Test {

	public static void main(String[] args) {
		try {
			System.out.println(RiotConnector.getSummonerByName("lollouno", RiotConnector.EUW_SERVER));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
