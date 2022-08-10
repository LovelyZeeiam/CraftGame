import java.math.BigInteger;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		int numVertInt = cin.nextInt();
		BigInteger numVertex = BigInteger.valueOf(numVertInt);
		BigInteger count = BigInteger.ONE;
		if(numVertInt != 3) {
			count = count
					.multiply(numVertex)
					.multiply(numVertex = numVertex.subtract(BigInteger.ONE))
					.multiply(numVertex = numVertex.subtract(BigInteger.ONE))
					.multiply(numVertex.subtract(BigInteger.ONE))
					.divide(BigInteger.valueOf(24));
			System.out.println(count);
		} else {
			System.out.println(0);
		}

	}

}