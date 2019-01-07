package automation;
import com.experitest.client.Client;

public class MyClient extends Client {

	public MyClient(String host, int port, boolean useSessionID) {
		super(host, port, true);
	}

	public void myGenerateReport(Boolean bool) {
		super.generateReport(bool);
	}

	public void MyCollectSupportData(String zipDestination, String applicationPath, String device, String scenario,
			String expectedResult, String actualResult) {
		super.collectSupportData(zipDestination, applicationPath, device, scenario, expectedResult, actualResult);
	}

//	public void myClick(String zone, String element, int index, int clickCount, String device, String app) {
//		try {
//			super.click(zone, element, index, clickCount);
//		} catch (Exception e) {
//			super.collectSupportData("", "C:\\projects\\app\\bin\\ipas\\eribank.apk", device, "click error",
//					"expected click to work", "click has failed");
//		}
//	}
//
//	public void MySetNetworkConnection(String connection, Boolean enable, String device, String app) {
//		try {
//			super.setNetworkConnection(connection, enable);
//		} catch (Exception e) {
//			super.collectSupportData("C:\\projects\\supportData.zip", "C:\\projects\\app\\bin\\ipas\\eribank.apk",
//					device, "click error", "expected click to work", "click has failed");
//		}
//	}

}
