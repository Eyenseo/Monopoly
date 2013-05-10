import java.util.Random;

public class RandomTest {
	public static void main(String[] args) {
		Random randomGenerator = new Random();
		int dice;
		int[] result = new int[6];
		for(int i = 0; i < 1000; i++) {
			dice = randomGenerator.nextInt(6) + 1;
			switch(dice) {
				case 1:
					result[dice - 1]++;
					break;
				case 2:
					result[dice - 1]++;
					break;
				case 3:
					result[dice - 1]++;
					break;
				case 4:
					result[dice - 1]++;
					break;
				case 5:
					result[dice - 1]++;
					break;
				case 6:
					result[dice - 1]++;
					break;
			}
		}
		System.out.println(
				"Eins:\t" + result[0] + "\nZwei:\t" + result[1] + "\nDrei:\t" + result[2] + "\nVier:\t" + result[3] +
				"\nFuenf:\t" + result[4] + "\nSechs:\t" + result[5]);
	}
}
