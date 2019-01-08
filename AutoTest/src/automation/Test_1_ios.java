package automation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.*;

public class Test_1_ios extends BaseTest {

	@Test
	public void testUntitled() {
		Boolean reboot = Threads.getReboot();
		if (reboot) {
			client.reboot(120000);
		}
//		String version = client.getDeviceProperty("os.version");
//		Double deviceversion = Double.valueOf(version);
//		if (deviceversion < 9 ) {
//			client.shake();
//		}
		specDevice = Threads.ifSpecDevice();
		if (specDevice) {
			client.setDevice("ios_app:" + myCurrThreasd);
		} else {
			client.openDevice();
		}
		client.deviceAction("unlock");
		profile = Threads.getProfile();
		client.setNetworkConditions(profile, 0);
		String apps = client.getInstalledApplications();
		if (!apps.contains("com.experitest.ExperiBank")) {
			client.install(dir + "\\app\\EriBank.ipa", true, true);
		}
		if (client.isElementFound("NATIVE", "xpath=//*[@text='loginButton']", 0)) {
			client.report("elemet found", true);
		}

		deviceName = client.getDeviceProperty("device.name");
		client.launch("com.experitest.ExperiBank", true, true);
		String csvUserName="company";
		String csvPassword="company";
//		Scanner inputStream = null;
//		try {
//			inputStream = new Scanner(new File("login.csv"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		inputStream.nextLine();// ignore the first line - Headlines
//		while (inputStream.hasNext()) {
//			String data = inputStream.nextLine(); // Read line
//			String[] values = data.split(","); // Split the line to an array
//			csvUserName = values[0];
//			csvPassword = values[1];
//			client.elementSendText("NATIVE", "xpath=//*[@placeholder='Username']", 0, csvUserName);
//			client.elementSendText("NATIVE", "xpath=//*[@placeholder='Password']", 0, csvPassword);
//			client.click("NATIVE", "xpath=//*[@text='Login']", 0, 1);
//			if (client.isElementFound("NATIVE", "xpath=//*[@text='Invalid username or password!']", 0)) // check if user
//																										// name or
//																										// password are
//																										// not correct.
//				client.click("NATIVE", "xpath=//*[@text='Dismiss']", 0, 1);
//			else
//				break;
//		}

		String oldBalance = client.getText("TEXT");
		String[] arrayList = oldBalance.split(" ");
		String oldAmount = arrayList[7];
		oldAmount = oldAmount.substring(0, oldAmount.length() - 1);

		client.isElementFound("NATIVE", "xpath=//*[@text='Make Payment']", 0); // check if the user is log in
	//	inputStream.close(); // close the connection to the csv file

		client.click("NATIVE", "xpath=//*[@text='Make Payment']", 0, 1);
		client.elementSendText("NATIVE", "xpath=//*[@placeholder='Phone']", 0, "0");
		client.elementSendText("NATIVE", "xpath=//*[@placeholder='Name']", 0, "company");
		client.elementSendText("NATIVE", "xpath=//*[@placeholder='Amount']", 0, "5");
		client.click("NATIVE", "xpath=//*[@placeholder='Country']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='Select']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='Italy']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='Send Payment']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='Yes']", 0, 1);

		String newBalance = client.getText("TEXT");
		arrayList = newBalance.split(" ");
		String newAmount = arrayList[7];
		newAmount = newAmount.substring(0, newAmount.length() - 1);
		double X1 = Double.parseDouble(oldAmount);
		double X2 = Double.valueOf(newAmount);
		double calc = X1 - 5;
		if (calc == X2) {
			System.out.println("Equal");
			client.report("The Amount is equal", true);

		} else {
			client.report("The Amount is not equal \"failed\"", false);

		}

		client.verifyIn("NATIVE", "xpath=//*[@text='Mortgage Request']", 0, "Up", "NATIVE",
				"xpath=//*[@text='Make Payment']", 0, 0);

		client.click("NATIVE", "xpath=//*[@text='Logout']", 0, 1);

//		client.swipe("Up", 26, 649);
//		client.touchDown("NATIVE", "xpath=//*[@text='Login']", 0);
//		client.touchMove("NATIVE", "xpath=//*[@text='Login']", 0);
//		client.touchUp();
//		String logs = client.getDeviceLog();
//		System.out.println(logs);
		client.launch("com.apple.mobilesafari", false, false);
		client.startMonitor("com.apple.mobilesafari:battery");
		client.launch("http://en.wikipedia.org/wiki/Electric_energy_consumption", false, false);
		String monitoringDataAsCsv = client.getMonitorsData("");
		client.setReporter("xml", path, this.getClass().getName());

	}

}
