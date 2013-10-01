package randomTests;

public class ClassNameTest {
	public static void main(String[] args) {
		ClassNameTest test = new ClassNameTestTwo();
		System.out.print(test.getClass().getSimpleName());
	}
}

class ClassNameTestTwo extends ClassNameTest {

}