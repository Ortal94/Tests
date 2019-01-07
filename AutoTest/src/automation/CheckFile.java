package automation;
import java.io.File;

public class CheckFile {

	private static boolean foundFolder = false;

	static String fileName;

	public CheckFile(String name) {
		this.fileName = name;
	}

	public static void main(String[] args) {
		File dir = new File("C:\\Users\\ortal.yona\\eclipse-workspace\\Test");
		findDirectory(dir, fileName);
	}

	static void findDirectory(File parentDirectory, String name) {
		if (foundFolder) {

			// TODO return the path
			return;
		}
		File[] files = parentDirectory.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				continue;
			}
			if (file.getName().equals(name)) {
				foundFolder = true;
				break;
			}
			if (file.isDirectory()) {
				findDirectory(file, name);
			}
		}
	}

}