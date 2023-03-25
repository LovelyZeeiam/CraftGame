package xueli;

import java.math.BigInteger;
import java.util.ArrayList;

public class PrimeTest {

	public PrimeTest() {
	}
	
	private ArrayList<BigInteger> primes = new ArrayList<>() {
		{
			this.add(BigInteger.TWO);
		}
	};
 	
	private boolean testIfPrime(BigInteger num) {
		if(!num.isProbablePrime(10)) {
			return false; 
		}
		
		BigInteger maxCompareNum = num.sqrt();
		for(BigInteger p : primes) {
			BigInteger result = num.remainder(p);
			if(result.equals(BigInteger.ZERO)) {
				return false;
			}
			if(maxCompareNum.compareTo(p) < 0) break;
		}
		
		primes.add(num);
		return true;
	}
	
	public void run() {
		BigInteger integer = new BigInteger("3");
		int findSum = 0;
		BigInteger outCmp = new BigInteger("10000");
		
		while(true) {
			if(testIfPrime(integer)) {
				findSum = 0;
//				System.out.println(integer.toString());
			} else {
				findSum++;
			}
			
			if(integer.remainder(outCmp).compareTo(BigInteger.ZERO) == 0) {
				System.out.println(String.format("Find to: %s", integer.toString()));
			}
			
			if(findSum >= 100) {
				System.out.println(String.format("Find continuous %d: %s", findSum, integer.toString()));
			}
			
			integer = integer.add(new BigInteger("1"));
			
		}
		
		
	}
	
	public static void main(String[] args) {
		new PrimeTest().run();
	}

}
