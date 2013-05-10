package randomTests;

public class ForEachTest {
	public static void main(String[] args) {
		Test[] t = new Test[5];
		for(int i = 0; i < t.length; i++) {
			t[i] = new Test("" + i);
		}
		for(Test k : t) {
			k.test = "s";
		}
		for(Test k : t) {
			System.out.println(k.test);
		}
	}
}

class Test {
	public String test;

	Test(String t) {
		test = t;
	}
}
