package randomTests;

import java.util.HashMap;

public class HashTest {
	public static void main(String[] args) {
		HashMap<String, Object> values = new HashMap<String, Object>();

		values.put("Test", true);
		System.out.print(values.get("Test"));
	}
}
