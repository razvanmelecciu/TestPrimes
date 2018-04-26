package primerange;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrimeRangeTest
{

    @Test
    public void equivalencePartitioning()
    {
        assertEquals(2, PrimeRange.extractPrime(2, 11, PrimeRange.Strategy.APPROACH_A, PrimeRange.Criteria.SMALLEST));
        assertEquals(2, PrimeRange.extractPrime(2, 11, PrimeRange.Strategy.APPROACH_B, PrimeRange.Criteria.SMALLEST));

        assertEquals(11, PrimeRange.extractPrime(2, 11, PrimeRange.Strategy.APPROACH_A, PrimeRange.Criteria.GREATEST));
        assertEquals(11, PrimeRange.extractPrime(2, 11, PrimeRange.Strategy.APPROACH_B, PrimeRange.Criteria.GREATEST));
    }
}