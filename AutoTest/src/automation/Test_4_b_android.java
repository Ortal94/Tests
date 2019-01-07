package automation;

import org.junit.*;

public class Test_4_b_android extends BaseTest {

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
			client.setDevice("adb:" + myCurrThreasd);
		} else {
			client.openDevice();
		}
		client.deviceAction("unlock");

		client.launch("chrome:http://espn.com", true, false);
		client.hybridWaitForPageLoad(60000);
		if (client.waitForElement("WEB", "xpath=//*[@text='Menu']", 0, 60000)) {
			if (client.isElementFound("WEB", "xpath=//*[@text='Menu']")) {
				client.click("WEB", "xpath=//*[@text='Menu' and @nodeName='SPAN']", 0, 1);
				client.click("WEB", "xpath=//*[@nodeName='A' and @width>0 and ./*[@text='ESPN+']]", 0, 1);
				client.click("WEB", "xpath=//*[@nodeName='A' and @width>0 and ./*[@text='Watch']]", 0, 1);
				client.click("WEB", "xpath=//*[@nodeName='A' and @width>0 and ./*[@text='Listen']]", 0, 1);
				client.click("WEB", "xpath=//*[@nodeName='A' and @width>0 and ./*[@text='Fantasy']]", 0, 1);
				client.click("WEB", "xpath=//*[@nodeName='A' and @width>0 and ./*[@text='More']]", 0, 1);
			}

			client.setReporter("xml", path, this.getClass().getName());

		}
	}

}
