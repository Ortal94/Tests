package automation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class Threads extends Thread {

	static String path;
	static String pathToDevice;
	public String deviceName;
	public static String profile;
	List<Class<?>> testsList;
	File dir, dirProfile;
	Result result;
	Class<?> testName;
	FileWriter writer = null, report = null;
	public Client client;
	static Boolean reboot, isRunning = true, repeatedly = true, specDevice;
	String direction;
	String[] NvProfile;
	int minutes = 60000, size;
	long endTime, sum, max, min, calc;


	public Threads(String path, String name, List<Class<?>> classlist, Boolean specDevice, Class<?> test,
			FileWriter fileWriter, Boolean grid, String os, GridClient gridClient) throws IOException {
		this.path = path;
		this.deviceName = name;
		this.testsList = classlist;
		this.specDevice = specDevice;
		this.testName = test;
		this.writer = fileWriter;
		this.reboot = false;
		endTime = System.currentTimeMillis() + minutes;
		direction = System.getProperty("user.dir") + "\\" + path + "\\" + profile + "\\" + deviceName;
		File fileRes = new File(path, "reports.txt");
		report = new FileWriter(fileRes);
		if(grid) {
			this.client = gridClient.lockDeviceForExecution("TestName", "@os="+os, 60, TimeUnit.MINUTES.toMillis(2));
		}
		else{
			this.client = new Client("localhost",8889);
	
		}
		
		NvProfile = client.getNVProfiles();
		size = NvProfile.length;
		
	}

	@Override
	public void run() {

		if (specDevice) {
			RunTest(testName);
			WriteResults();
		} else {
			if (repeatedly) {
				while (System.currentTimeMillis() < endTime) {

					for (Class<?> test : testsList) {
						RunTest(test);
						WriteResults();
					}
				}
			} else {
				for (Class<?> test : testsList) {
					RunTest(test);
					WriteResults();
				}
			}
		}

		try {
			report.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDeviceName() {
		return deviceName;
	}

	public static String getPathName() {
		return path;
	}

	public static Boolean ifSpecDevice() {
		return specDevice;
	}

	public static Boolean getReboot() {
		return reboot;
	}

	public static String getProfile() {
		return profile;
	}

	public void RunTest(Class<?> test) {
		try {
			report.write(test.getName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for (String prof : NvProfile) {
			profile = prof;
			dir = new File(path, profile);
			dir.mkdir();
			dirProfile = new File(dir, deviceName);
			dirProfile.mkdir();

			for (int i = 0; i < 3; i++) {
				long begin = System.currentTimeMillis();
				result = JUnitCore.runClasses(test);
				long end = System.currentTimeMillis();
				calc = end - begin;
				sum += calc;
				if (calc > max) {
					max = calc;
				}
				if (calc < min) {
					min = calc;
				}
				if (result.getFailureCount() == 0) {
					i = 3;
				} else if (i == 1) {
					// reboot
					reboot = true;
					client.collectSupportData(direction, "", deviceName, result.getFailures().toString(), "", "");

				} else {
					client.collectSupportData(direction, "", deviceName, result.getFailures().toString(), "", "");
				}
			}
			try {
				report.write(System.lineSeparator());
				report.write("time for profile " + profile + ": " + (int) calc);
				report.write(System.lineSeparator());
				report.write("MAX :" + (int) max);
				report.write(System.lineSeparator());
				report.write("MIN: " + (int) min);
				report.write(System.lineSeparator());
				report.write("AVG: " + (int) sum / size);
				report.write(System.lineSeparator());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void WriteResults() {
		
		try {		
			writer.write("Results for: " + deviceName);
			writer.write(System.lineSeparator());
			writer.write("Successful:" + (result.getRunCount() - result.getFailureCount() - result.getIgnoreCount()));
			writer.write("  Failed: " + result.getFailureCount());
			writer.write(System.lineSeparator());
			for (Failure failure : result.getFailures()) {
				writer.write("The Exception: " + failure.toString());
				writer.write(System.lineSeparator());
			}
			writer.write(System.lineSeparator());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
