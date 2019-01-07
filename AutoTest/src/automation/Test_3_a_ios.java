package automation;

import org.junit.*;

public class Test_3_a_ios extends BaseTest {

	@Test
	public void testUntitled() {
		client.deviceAction("unlock");
		String profile = Threads.getProfile();
		client.setNetworkConditions(profile, 3000);

		Boolean reboot = Threads.getReboot();
		if (reboot) {
			client.reboot(120000);
		}
		specDevice = Threads.ifSpecDevice();
		if (specDevice) {
			client.setDevice("ios_app:" + myCurrThreasd);
		} else {
			client.openDevice();
		}
		client.deviceAction("unlock");

		client.launch("com.apple.AppStore", false, true);
		client.click("NATIVE", "xpath=//*[@text='Search']", 0, 1);
		client.elementSendText("NATIVE", "xpath=//*[@placeholder='App Store']", 0, "instagram");
		client.click("NATIVE", "xpath=//*[@accessibilityLabel='Search' and @class='UIAButton']", 0, 1);

		client.setReporter("xml", path, this.getClass().getName());

	}

}
