package bigdatafinal.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class Test {

	public static void main(String[] args) {
		String streamsByGame = "";
		try {
			streamsByGame = TwitchConnector.getIstance().getStreamsByGame("33214");
			System.out.println(streamsByGame);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(streamsByGame);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
