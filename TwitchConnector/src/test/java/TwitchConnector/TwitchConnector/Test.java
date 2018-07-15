package TwitchConnector.TwitchConnector;

import java.io.IOException;
import java.net.MalformedURLException;

public class Test {

	public static void main(String[] args) {
		try {
			System.out.println(TwitchConnector.getStreamsForGame("33214"));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
