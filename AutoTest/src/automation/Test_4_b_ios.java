package automation;
import org.junit.*;

public class Test_4_b_ios extends BaseTest {

	@Test
	public void testUntitled() {
		client.deviceAction("unlock");
		profile = Threads.getProfile();
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

		client.launch("safari:http://espn.com", true, false);
		if (client.waitForElement("WEB", "text=Menu", 0, 30000)) {
			// If statement
		}
		client.click("WEB", "text=Menu", 0, 1);
		if (client.waitForElement("WEB", "text=ESPN+Espnplus", 0, 10000)) {
			// If statement
		}
		client.click("WEB", "xpath=//*[@nodeName='A' and ./*[@text='ESPN+']]", 0, 1);
		client.click("WEB", "xpath=//*[@nodeName='A' and ./*[@text='Watch']]", 0, 1);
		client.click("WEB", "xpath=//*[@nodeName='A' and ./*[@text='Listen']]", 0, 1);
		client.click("WEB", "xpath=//*[@nodeName='A' and ./*[@text='Fantasy']]", 0, 1);
		client.click("WEB", "xpath=//*[@nodeName='A' and ./*[@text='More']]", 0, 1);

		client.setReporter("xml", path, this.getClass().getName());

	}

}
