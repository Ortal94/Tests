package automation;

import java.util.ArrayList;
import org.junit.*;

public class Test_3_b_android extends BaseTest {

	ArrayList<String> list = new ArrayList<String>();
	int i = 0;

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

		client.launch("com.android.vending/com.android.vending.AssetBrowserActivity", false, true);
		client.click("NATIVE", "xpath=//*[@contentDescription='Home, Top Charts']", 0, 1);
		client.click("NATIVE", "xpath=//*[@id='toggle_switch_button']", 0, 1);
		String[] strArray = client.getAllValues("NATIVE", "xpath=//*[@id='inline_top_charts_content_viewpager']",
				"text");
		System.out.println(strArray.toString());
		for (int i = 0; i < strArray.length; i++) {
			list.add(strArray[i]);
			i += 4;
			if (list.size() == 10) {
				break;
			}
		}
		System.out.println(list.toString());
		client.setReporter("xml", path, this.getClass().getName());
	}

}
