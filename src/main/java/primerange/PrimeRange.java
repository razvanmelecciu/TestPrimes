package primerange;

import java.math.BigInteger;
import java.util.Random;

public class PrimeRange
{

    public static final long INVALID_PRIME = -1;

    public enum Strategy
    {
        CLASSIC_DETERMINISTIC, MILLER_RABIN
    }

    /**
     * Extract a positive prime from the given range
     * @param lowerLimit the lower limit for searching for the prime > 0
     * @param upperLimit the upper limit for searching for the prime > 0
     * @param currentStrategy the strategy employed (A/B/C/D)
     * @return
     */
    public static long extractLargestPrime(long lowerLimit, long upperLimit, Strategy currentStrategy)
    {
        int nbIterations = 5;
        for (long i = upperLimit; i >= lowerLimit; --i)
        {
            switch (currentStrategy)
            {
                case CLASSIC_DETERMINISTIC:
                default:
                {
                    if (isPrimeV2(i))
                        return i;
                }
                case MILLER_RABIN:
                {
                    if (isPrimeV3(i, nbIterations))
                        return i;
                }
            }
        }

        return INVALID_PRIME;
    }

    /**
     * Extract a positive prime from the given range
     * @param lowerLimit the lower limit for searching for the prime > 0
     * @param upperLimit the upper limit for searching for the prime > 0
     * @param currentStrategy the strategy employed (A/B/C/D)
     * @return
     */
    public static long extractSmallestPrime(long lowerLimit, long upperLimit, Strategy currentStrategy)
    {
        int nbIterations = 5;

        for (long i = lowerLimit; i <= upperLimit; ++i)
        {
            switch (currentStrategy)
            {
                case CLASSIC_DETERMINISTIC:
                default:
                {
                    if (isPrimeV2(i))
                        return i;
                }
                case MILLER_RABIN:
                {
                    if (isPrimeV3(i, nbIterations))
                        return i;
                }
            }
        }

        return INVALID_PRIME;
    }


    /**
     * Classic primality test
     *
     * @param numberTested
     * @return
     */
    private static boolean isPrimeV1(long numberTested)
    {
        if (numberTested <= 1)
            return false;

        long limit = (long) Math.sqrt(numberTested);
        for (long j = 2; j <= limit; ++j)
        {
            if (numberTested % j == 0)
                return false;
        }

        return true;
    }

    /**
     * Classic primality test optimized
     *
     * @param numberTested
     * @return
     */
    private static boolean isPrimeV2(long numberTested)
    {
        if (numberTested <= 1)
            return false;
        else if (numberTested <= 3)
            return true;
        else if (numberTested % 2 == 0 || numberTested % 3 == 0)
            return false;

        long j = 5;
        long limit = (long) Math.sqrt(numberTested);
        while (j <= limit)
        {
            if (numberTested % j == 0 || numberTested % (j + 2) == 0)
                return false;
            j += 6;
        }

        return true;
    }

    /**
     * Miller-Rabine primality test (non-deterministic)
     * @param numberTested
     * @param noIterations
     * @return
     */
    private static boolean isPrimeV3(long numberTested, int noIterations)
    {
        /** base case **/
        if (numberTested == 0 || numberTested == 1)
            return false;
        /** base case - 2 is prime **/
        if (numberTested == 2)
            return true;
        /** an even number other than 2 is composite **/
        if (numberTested % 2 == 0)
            return false;

        long s = numberTested - 1;
        while (s % 2 == 0)
            s /= 2;

        Random randGen = new Random();
        for (int i = 0; i < noIterations; i++)
        {
            long randomVal = Math.abs(randGen.nextLong());
            long a = randomVal % (numberTested - 1) + 1, temp = s;
            long mod = modularExponentiation(a, temp, numberTested);
            while (temp != numberTested - 1 && mod != 1 && mod != numberTested - 1)
            {
                mod = modularMultiplication(mod, mod, numberTested);
                temp *= 2;
            }
            if (mod != numberTested - 1 && temp % 2 == 0)
                return false;
        }
        return true;
    }

    /**
     * Fermat primality test (non-deterministic)
     * @param numberTested
     * @param noIterations
     * @return
     */
    public static boolean isPrimeV4(long numberTested, int noIterations)
    {
        /** base case **/
        if (numberTested == 0 || numberTested == 1)
            return false;
        /** base case - 2 is prime **/
        if (numberTested == 2)
            return true;
        /** an even number other than 2 is composite **/
        if (numberTested % 2 == 0)
            return false;

        Random rand = new Random();
        for (int i = 0; i < noIterations; i++)
        {
            long r = Math.abs(rand.nextLong());
            long a = r % (numberTested - 1) + 1;
            if (modularExponentiation(a, numberTested - 1, numberTested) != 1)
                return false;
        }
        return true;
    }

    /**
     * Helper method that computes (a ^ b) % c
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static long modularExponentiation(long a, long b, long c)
    {
        long result = 1;
        for (int i = 0; i < b; i++)
        {
            result *= a;
            result %= c;            // prevents overflowing long
        }
        return result % c;
    }

    /**
     * Helper method that computes (a * b) % c
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static long modularMultiplication(long a, long b, long c)
    {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(c)).longValue();
    }

    public static void main(String[] argc)
    {
        System.out.println("Hello world");
        System.out.println(extractPrime(2, 11, Strategy.APPROACH_A, Criteria.SMALLEST));
        System.out.println(extractPrime(2, 11, Strategy.APPROACH_B, Criteria.SMALLEST));
        System.out.println(extractPrime(2, 11, Strategy.APPROACH_C, Criteria.SMALLEST));
        System.out.println(extractPrime(2, 11, Strategy.APPROACH_D, Criteria.SMALLEST));
        System.out.println(extractPrime(150, 300, Strategy.APPROACH_A, Criteria.GREATEST));
        System.out.println(extractPrime(150, 300, Strategy.APPROACH_B, Criteria.GREATEST));
        System.out.println(extractPrime(150, 300, Strategy.APPROACH_C, Criteria.GREATEST));
        System.out.println(extractPrime(150, 300, Strategy.APPROACH_D, Criteria.GREATEST));
    }


    private long lowerLimitRange;
    private long upperLimitRange;
    private Strategy strategyUsed;
    private Criteria primeCriteria;
}

