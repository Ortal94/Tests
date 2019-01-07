package automation;

import org.junit.*;

public class Test_3_b_ios extends BaseTest {

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

		client.launch("com.apple.AppStore", false, true);
		client.click("NATIVE", "xpath=//*[@text='Apps' and @class='UIAButton']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='See All' and ./parent::*[@text='Great Apps for iPhone XR']]", 0, 1);
		String[] strArray = client.getAllValues("NATIVE", "xpath=//*[@class='UIACollectionView']", "text");
		System.out.println(strArray);
		client.setReporter("xml", path, this.getClass().getName());

	}

}
