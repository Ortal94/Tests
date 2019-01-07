package automation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class TestRunner {

	public static void main(String[] args) throws IOException {

		String connectedDevice, conf, testName, deviceName = null, host = "localhost", path, deviceOS;
		String[] listOfConnectedDevice;
		int port = 8889;
		File file, fileRes;
		Date date = new Date();
		Client client;
		Boolean grid = true;
		Scanner sc = null;
		List<Class<?>> classlistAndroid = new ArrayList<>();
		List<Class<?>> classlistIos = new ArrayList<>();
		Threads myThread = null;
		HashMap<String, Class<?>> hashMap = new HashMap<String, Class<?>>();
		hashMap.put("Test_1_android", Test_1_android.class);
		hashMap.put("Test_1_ios", Test_1_ios.class);
		hashMap.put("Test_2_android", Test_2_android.class);
		hashMap.put("Test_3_a_android", Test_3_a_android.class);
		hashMap.put("Test_3_a_ios", Test_3_a_ios.class);
		hashMap.put("Test_3_b_android", Test_3_b_android.class);
		hashMap.put("Test_3_b_ios", Test_3_b_ios.class);
		hashMap.put("Test_4_a_android", Test_4_a_android.class);
		hashMap.put("Test_4_a_ios", Test_4_a_ios.class);
		hashMap.put("Test_4_b_android", Test_4_b_android.class);
		hashMap.put("Test_4_b_ios", Test_4_b_ios.class);
		FileWriter writer = null;
		GridClient gridClient = new GridClient("admin","Aa123456","","http://oyona-pc:9192");

		Class<?> test;
		long time = date.getTime();
		file = new File("Run_" + time);
		file.mkdirs();
		path = file.getName();
		fileRes = new File(path, "results.txt");
		writer = new FileWriter(fileRes);

		classlistAndroid.add(Test_1_android.class);
		classlistAndroid.add(Test_2_android.class);
		classlistAndroid.add(Test_3_a_android.class);
		classlistAndroid.add(Test_3_b_android.class);
		classlistAndroid.add(Test_4_a_android.class);
		classlistAndroid.add(Test_4_b_android.class);
		classlistIos.add(Test_1_ios.class);
		classlistIos.add(Test_3_a_ios.class);
		classlistIos.add(Test_3_b_ios.class);
		classlistIos.add(Test_4_a_ios.class);
		classlistIos.add(Test_4_b_ios.class);
		
		if(grid) {
			client = gridClient.lockDeviceForExecution("TestName", "", 60, TimeUnit.MINUTES.toMillis(2));
		}
		else {
			client = new Client(host,port);
		}
		
		try {
			// read from the configuration file
			sc = new Scanner(new File("Conf.txt"));
			conf = sc.nextLine();
			connectedDevice = client.getConnectedDevices();
			client.releaseClient();
			listOfConnectedDevice = connectedDevice.split("\n");
			if (conf.equals("all")) {
				for (int i = 0; i < listOfConnectedDevice.length; i++) {
					String[] connectedDeviceNames = listOfConnectedDevice[i].split(":");
					if (connectedDeviceNames[0].equals("ios_app")) {
						myThread = new Threads(path, connectedDeviceNames[1], classlistIos, false, null, writer, grid,"'ios'", gridClient);
						myThread.start();
					} else if (connectedDeviceNames[0].equals("adb")) {
						myThread = new Threads(path, connectedDeviceNames[1], classlistAndroid, false, null, writer, grid,"'android'", gridClient);
						myThread.start();
					}

				}

			}

			else {
				String[] listconf = conf.split(",");
				testName = listconf[0];
				deviceName = listconf[1];
				deviceOS = listconf[2];

				if (hashMap.containsKey(testName)) {
					test = hashMap.get(testName);
				} else {
					System.out.println("please choose correct test");
					return;
				}

				if (deviceOS.equals("ios")) {
					myThread = new Threads(path, deviceName, classlistIos, true, test, writer, grid,"'ios'",gridClient);
					myThread.start();
				} else if (deviceOS.equals("adb")) {
					myThread = new Threads(path, deviceName, classlistAndroid, true, test, writer,grid,"'android'",gridClient);
					myThread.start();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
//		try {
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

}