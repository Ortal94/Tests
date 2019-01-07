package automation;
import java.util.concurrent.TimeUnit;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class Grid {
	public static void main(String[] args) {

		GridClient gridClient = new GridClient("ortal", "Oy125050", "", "https://qacloud.experitest.com");
		Client client = gridClient.lockDeviceForExecution("Test_1_android", "@os='android'", 60,
				TimeUnit.MINUTES.toMillis(2));
	}
}
