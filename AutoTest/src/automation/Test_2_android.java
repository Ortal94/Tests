package automation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.*;

public class Test_2_android extends BaseTest {

	String csvUserName;
	String csvPassword;
	Scanner inputStream = null;

	@Test
	public void testUntitled() {
		client.deviceAction("unlock");
		profile = Threads.getProfile();
		client.setNetworkConditions(profile, 3000);
		Boolean reboot = Threads.getReboot();
		if (reboot) {
			client.reboot(120000);
		}
//		try {
		specDevice = Threads.ifSpecDevice();
		if (specDevice) {
			client.setDevice("adb:" + myCurrThreasd);
		} else {
			client.openDevice();
		}
		client.deviceAction("unlock");

		String apps = client.getInstalledApplications();
		if (!apps.contains("com.example.ortalyona.game")) {
			//client.install(dir + "\\apps\\myApp.apk", true, true);
			client.install("cloud:uniqueName=DotGame", true, true);

		}
		client.launch("com.example.ortalyona.game/.Login", true, true);
		try {
			inputStream = new Scanner(new File("login.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		inputStream.nextLine();// ignore the first line - Headlines
		while (inputStream.hasNext()) {
			String data = inputStream.nextLine(); // Read line
			String[] values = data.split(","); // Split the line to an array
			csvUserName = values[0];
			csvPassword = values[1];
			if (csvUserName.isEmpty()) {
				client.elementSendText("NATIVE", "xpath=//*[@id='username']", 0, " "); // send the text from the csv
																						// file to "password"
			} else {
				client.elementSendText("NATIVE", "xpath=//*[@id='username']", 0, csvUserName); // send the text from the

			}
			if (csvPassword.isEmpty()) {
				client.elementSendText("NATIVE", "id=password", 0, " "); // send the text from the csv file to
																			// "password"

			} else {
				client.elementSendText("NATIVE", "id=password", 0, csvPassword); // send the text from the csv file to
																					// "password"
			}

			if (csvUserName.equals(csvPassword)) {
				client.click("NATIVE", "text=LOGIN", 0, 1);
				break;
			}

		}
		inputStream.close(); // close the connection to the csv file

		for (int i = 0; i < 3; i++) {
			client.click("NATIVE", "text=START GAME", 0, 1);
			client.click("NATIVE", "xpath=//*[@id='floatingActionButton']", 0, 1);
			client.click("NATIVE", "xpath=//*[@id='floatingActionButton']", 0, 1);
			client.click("NATIVE", "xpath=//*[@id='floatingActionButton']", 0, 1);
			client.click("NATIVE", "xpath=//*[@id='floatingActionButton']", 0, 1);
			client.click("NATIVE", "xpath=//*[@id='floatingActionButton']", 0, 1);
			client.click("NATIVE", "text=Back To Menu", 0, 1);

		}

		client.applicationClose("com.example.ortalyona.game/.Login");
		client.setMonitorPollingInterval(30000);
		client.setReporter("xml", path, this.getClass().getName());

	}

}
