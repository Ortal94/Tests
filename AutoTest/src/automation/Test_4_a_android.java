package automation;

import org.junit.*;

public class Test_4_a_android extends BaseTest {

	String[] menuList = { "NFL", "NBA", "MLB", "NCAAF", "Soccer", "NHL", "NCAAM", "MMA", "Boxing", "WWE", "Golf",
			"Tennis", "NBA G League", "esports", "Chalk", "Analytics", "WNBA", "NCAAW", "NCAA Softball", "NASCAR",
			"Jayski", "F1", "Racing", "Olympic Sports", "Horse Racing", "Recruiting FB", "Recruiting BB",
			"College Sports", "Little League World Series", "Special Olympics", "X Games", "Cricket", "Rugby", "CFL" };

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

		client.launch("chrome:http://espn.com", true, false);
		client.hybridWaitForPageLoad(60000);
		if (client.waitForElement("WEB", "xpath=//*[@text='Menu']", 0, 60000)) {
			if (client.isElementFound("WEB", "xpath=//*[@text='Menu']")) {
				client.click("WEB", "xpath=//*[@text='Menu']", 0, 1);
				String[] strArray = client.getAllValues("WEB", "xpath=//*[@nodeName='UL' and ./*[./*[@nodeName='H1']]]",
						"text");
				System.out.println(strArray);
				System.out.println(client.hybridRunJavascript("", 0, "result = document.URL")); // will print out all
																								// open tabs
				client.hybridGetHtml("id=balanceWebView", 0);
				for (int i = 1; i < strArray.length; i++) {
					if (strArray[i + 1] != menuList[i]) {
						client.report("The list is not equal \"failed\"", false);
						break;

					} else {
						client.report("The list is equal", true);
					}
				}

			}
		}
		client.setReporter("xml", path, this.getClass().getName());

	}

}
