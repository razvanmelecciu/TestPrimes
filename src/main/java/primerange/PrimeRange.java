package primerange;

import java.math.BigInteger;
import java.util.Random;

/**
 A class that wraps up a few methods for determining
 the smallest, largest prime number from an interval. The noIterations
 static member is used by nondeterministic algorithms like Miller-Rabin and Fermat.
 */
public class PrimeRange {

    public static final long INVALID_PRIME = -1;

    public static int noIterations = 5;

    public enum Strategy {
        DETERMINISTIC, NON_DETERMINISTIC
    }

    /**
     Extract the largest prime from the given range (from A->B V B->A depending on the largest value)
     @param limitA          the first limit for searching for the prime
     @param limitB          the second limit for searching for the prime
     @param currentStrategy the strategy employed (DETERMINISTIC/NON_DETERMINISTIC)
     @return the biggest prime number or INVALID_PRIME if no prime has been found
     */
    public static long extractLargestPrime(long limitA, long limitB, Strategy currentStrategy) {

        if (limitA > limitB) {
            limitA = limitA ^ limitB;
            limitB = limitA ^ limitB;
            limitA = limitA ^ limitB;
        }

        int nbIterations = 5;
        for (long i = limitB; i >= limitA; --i) {
            switch (currentStrategy) {
                case DETERMINISTIC:
                default: {             /* Classic optimized */
                    if (isPrimeV2(i)) {
                        return i;
                    }
                }
                case NON_DETERMINISTIC: { /* Miller Rabin */
                    if (isPrimeV3(i)) {
                        return i;
                    }
                }
            }
        }

        return INVALID_PRIME;
    }

    /**
     Extract the smallest prime from the given range (from A->B V B->A depending on the largest value)
     @param limitA          the first limit for searching for the prime
     @param limitB          the second limit for searching for the prime
     @param currentStrategy the strategy employed (DETERMINISTIC/NON_DETERMINISTIC)
     @return the smallest prime number or INVALID_PRIME if no prime has been found
     */
    public static long extractSmallestPrime(long limitA, long limitB, Strategy currentStrategy) {

        if (limitA > limitB) {
            limitA = limitA ^ limitB;
            limitB = limitA ^ limitB;
            limitA = limitA ^ limitB;
        }

        int nbIterations = 5;

        for (long i = limitA; i <= limitB; ++i) {
            switch (currentStrategy) {
                case DETERMINISTIC:
                default: {             /* Classic optimized */
                    if (isPrimeV2(i)) {
                        return i;
                    }
                }
                case NON_DETERMINISTIC: { /* Miller Rabin */
                    if (isPrimeV3(i)) {
                        return i;
                    }
                }
            }
        }

        return INVALID_PRIME;
    }

    /**
     Classic primality test
     @param numberTested
     @return true or false
     */
    protected static boolean isPrimeV1(long numberTested) {

        numberTested = Math.abs(numberTested);

        if (numberTested <= 1) {
            return false;
        }

        long limit = (long) Math.sqrt(numberTested);
        for (long j = 2; j <= limit; ++j) {
            if (numberTested % j == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     Classic primality test optimized
     @param numberTested
     @return true or false
     */
    protected static boolean isPrimeV2(long numberTested) {

        numberTested = Math.abs(numberTested);

        if (numberTested <= 1) {
            return false;
        }
        else if (numberTested <= 3) {
            return true;
        }
        else if (numberTested % 2 == 0 || numberTested % 3 == 0) {
            return false;
        }

        long j = 5;
        long limit = (long) Math.sqrt(numberTested);
        while (j <= limit) {
            if (numberTested % j == 0 || numberTested % (j + 2) == 0) {
                return false;
            }
            j += 6;
        }

        return true;
    }

    /**
     Miller-Rabine primality test (non-deterministic)
     @param numberTested
     @return true or false
     */
    protected static boolean isPrimeV3(long numberTested) {

        if (noIterations <= 0)
            return false;

        numberTested = Math.abs(numberTested);

        /** base case **/
        if (numberTested == 0 || numberTested == 1) {
            return false;
        }
        /** base case - 2 is prime **/
        if (numberTested == 2) {
            return true;
        }
        /** an even number other than 2 is composite **/
        if (numberTested % 2 == 0) {
            return false;
        }

        long s = numberTested - 1;
        while (s % 2 == 0) {
            s /= 2;
        }

        Random randGen = new Random();
        for (int i = 0; i < noIterations; i++) {
            long randomVal = Math.abs(randGen.nextLong());
            long a = randomVal % (numberTested - 1) + 1, temp = s;
            long mod = modularExponentiation(a, temp, numberTested);
            while (temp != numberTested - 1 && mod != 1 && mod != numberTested - 1) {
                mod = modularMultiplication(mod, mod, numberTested);
                temp *= 2;
            }
            if (mod != numberTested - 1 && temp % 2 == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     Fermat primality test (non-deterministic)
     @param numberTested
     @return true or false
     */
    protected static boolean isPrimeV4(long numberTested) {

        if (noIterations <= 0)
            return false;

        numberTested = Math.abs(numberTested);

        /** base case **/
        if (numberTested == 0 || numberTested == 1) {
            return false;
        }
        /** base case - 2 is prime **/
        if (numberTested == 2) {
            return true;
        }
        /** an even number other than 2 is composite **/
        if (numberTested % 2 == 0) {
            return false;
        }

        Random rand = new Random();
        for (int i = 0; i < noIterations; i++) {
            long r = Math.abs(rand.nextLong());
            long a = r % (numberTested - 1) + 1;
            if (modularExponentiation(a, numberTested - 1, numberTested) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     Helper method that computes (a ^ b) % c
     @param a
     @param b
     @param c
     @return (a ^ b) % c
     */
    protected static long modularExponentiation(long a, long b, long c) {
        long result = 1;
        for (long i = 0; i < b; i++) {
            result *= a;
            result %= c;            // prevents overflowing long
        }
        return result % c;
    }

    /**
     Helper method that computes (a * b) % c
     @param a
     @param b
     @param c
     @return (a * b) % c
     */
    protected static long modularMultiplication(long a, long b, long c) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(c)).longValue();
    }

    public static void main(String[] argc) {
        System.out.println("Largest prime -> deterministic");
        System.out.println(extractLargestPrime(2, 11, Strategy.DETERMINISTIC));
        System.out.println(extractLargestPrime(150, 300, Strategy.DETERMINISTIC));
        System.out.println("Largest prime -> nondeterministic");
        System.out.println(extractLargestPrime(2, 11, Strategy.NON_DETERMINISTIC));
        System.out.println(extractLargestPrime(150, 300, Strategy.NON_DETERMINISTIC));

        System.out.println("Smallest prime -> deterministic");
        System.out.println(extractSmallestPrime(2, 11, Strategy.DETERMINISTIC));
        System.out.println(extractSmallestPrime(150, 300, Strategy.DETERMINISTIC));
        System.out.println("Smallest prime -> nondeterministic");
        System.out.println(extractSmallestPrime(2, 11, Strategy.NON_DETERMINISTIC));
        System.out.println(extractSmallestPrime(150, 300, Strategy.NON_DETERMINISTIC));

        System.out.println("Smallest prime -> deterministic");
        System.out.println(extractSmallestPrime(-5, 11, Strategy.DETERMINISTIC));
    }
}

