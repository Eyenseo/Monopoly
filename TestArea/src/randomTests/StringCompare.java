package randomTests;

public class StringCompare {
	public static void main(String[] args) {
		// Values from somewhere else
		String one = "Aaz";
		String two = "AA";
		boolean bigger = stringCompare(one, two);
		System.out.println(bigger ? "String one was smaller" : "String two was smaller");
		//		seeCharValues();
	}

	private static void seeCharValues() {
		for(int i = 0; i < 200; i++) {
			System.out.println(i + ":\t\t" + (char) i);
		}
	}

	public static boolean stringCompare(String first, String second) {
		char[] one = first.toCharArray();
		char[] two = second.toCharArray();
		int length = one.length < two.length ? one.length : two.length;
		for(int i = 0; i < length; i++) {
			int o = (one[i] > 96) ? (one[i] - 32) : one[i];
			int t = (two[i] > 96) ? (two[i] - 32) : two[i];
			if(o > t) {
				return false;
			} else if(o < t) {
				return true;
			}
		}
		return true;
	}
}
