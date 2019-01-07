package automation;

//package <set your test package>;
import org.junit.*;

public class Test_3_a_android extends BaseTest {

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

		client.launch("com.android.vending/com.android.vending.AssetBrowserActivity", false, true);
		client.click("NATIVE", "xpath=//*[@id='search_box_idle_text']", 0, 1);
		client.elementSendText("NATIVE", "xpath=//*[@id='search_box_text_input']", 0, "instagram");
		client.deviceAction("Enter");
		String[] strArray = client.getAllValues("NATIVE", "xpath=//*[@id='mdp_content']", "text");
		System.out.println(strArray[0].toString());
		if (strArray[0].toString().compareTo("Instagram") == 0) {
			System.out.println("equal");
			client.report("equal", true);
		} else {
			client.report("not equal \"failed\"", false);

		}
		client.click("NATIVE", "xpath=//*[@text='INSTALL']", 0, 1);
		// client.closeAllApplications();

		client.setReporter("xml", path, this.getClass().getName());

	}

}
