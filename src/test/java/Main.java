import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int roadSize = cin.nextInt();
		int rangeNum = cin.nextInt();

		byte[] line = new byte[roadSize + 1];

		for (int i = 0; i < rangeNum; i++) {
			int from = cin.nextInt();
			int to = cin.nextInt();
			for(int j = from; j <= to; j++) {
				line[j] = 1;
			}
		}

		int count = 0;
		for (int i = 0; i < line.length; i++) {
			if(line[i] != 1)
				count++;
		}
		System.out.println(count);

	}

}
