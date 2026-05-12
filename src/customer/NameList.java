package customer;

import java.util.ArrayList;
import java.util.Arrays;

import utilities.IO;

public class NameList {
	
	private static ArrayList<String> nameList = new ArrayList<String>(Arrays.asList("Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Hank"));

	public static String getRandomName() {
		int idx = IO.rand.nextInt(nameList.size());
		return nameList.get(idx);
	}

}
