package primerange;

import static org.junit.Assert.*;

/**
 Class with tests for the PrimeRange class
 */
public class PrimeRangeTest {
    /**
     Equivalence partitioning tests
     @throws Exception
     */
    @org.junit.Test
    public void equivalencePartitioning() throws Exception {

        /*
        Boundary parameters equivalence classes -> order 3 tuple (0, a, b) -> 3! = 6 classes
        C_1 = { (a, b) in ZxZ / 0 < a < b }
        C_2 = { (a, b) in ZxZ / a < 0 < b }
        C_3 = { (a, b) in ZxZ / 0 < b < a }
        C_4 = { (a, b) in ZxZ / b < a < 0 }
        C_5 = { (a, b) in ZxZ / a < b < 0 }
        C_6 = { (a, b) in ZxZ / b < 0 < a }

        Strategy parameter equivalence classes -> 2 classes
        M_1 = { m / m = DETERMINISTIC }
        M_2 = { m / m = NON_DETERMINISTIC }

        Return value equivalence classes -> 2 classes
        R_1 = { r / r != INVALID_PRIME }
        R_2 = { r / r  = INVALID_PRIME }

        In total -> |T| = 6 * 2 * 2 = 24 independent tests
        */

        // 1-1-1                                    a   b
        assertEquals(PrimeRange.extractLargestPrime(2, 11, PrimeRange.Strategy.DETERMINISTIC), 11);
        // 1-1-2
        assertEquals(PrimeRange.extractLargestPrime(14, 16, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 1-2-1
        assertEquals(PrimeRange.extractLargestPrime(2, 11, PrimeRange.Strategy.NON_DETERMINISTIC), 11);
        // 1-2-2
        assertEquals(PrimeRange.extractLargestPrime(14, 16, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 2-1-1
        assertEquals(PrimeRange.extractLargestPrime(-2, 11, PrimeRange.Strategy.DETERMINISTIC), 11);
        // 2-1-2
        assertEquals(PrimeRange.extractLargestPrime(-1, 1, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 2-2-1
        assertEquals(PrimeRange.extractLargestPrime(-1, 2, PrimeRange.Strategy.NON_DETERMINISTIC), 2);
        // 2-2-2
        assertEquals(PrimeRange.extractLargestPrime(-1, 1, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 3-1-1
        assertEquals(PrimeRange.extractLargestPrime(11, 2, PrimeRange.Strategy.DETERMINISTIC), 11);
        // 3-1-2
        assertEquals(PrimeRange.extractLargestPrime(16, 14, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 3-2-1
        assertEquals(PrimeRange.extractLargestPrime(11, 2, PrimeRange.Strategy.NON_DETERMINISTIC), 11);
        // 3-2-2
        assertEquals(PrimeRange.extractLargestPrime(16, 14, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 4-1-1
        assertEquals(PrimeRange.extractLargestPrime(-1, -2, PrimeRange.Strategy.DETERMINISTIC), -2);
        // 4-1-2
        assertEquals(PrimeRange.extractLargestPrime(-15, -16, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 4-2-1
        assertEquals(PrimeRange.extractLargestPrime(-1, -2, PrimeRange.Strategy.NON_DETERMINISTIC), -2);
        // 4-2-2
        assertEquals(PrimeRange.extractLargestPrime(-15, -16, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 5-1-1
        assertEquals(PrimeRange.extractLargestPrime(-2, -1, PrimeRange.Strategy.DETERMINISTIC), -2);
        // 5-1-2
        assertEquals(PrimeRange.extractLargestPrime(-16, -15, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 5-2-1
        assertEquals(PrimeRange.extractLargestPrime(-2, -1, PrimeRange.Strategy.NON_DETERMINISTIC), -2);
        // 5-2-2
        assertEquals(PrimeRange.extractLargestPrime(-16, -15, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 6-1-1
        assertEquals(PrimeRange.extractLargestPrime(11, -2, PrimeRange.Strategy.DETERMINISTIC), 11);
        // 6-1-2
        assertEquals(PrimeRange.extractLargestPrime(1, -1, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 6-2-1
        assertEquals(PrimeRange.extractLargestPrime(2, -1, PrimeRange.Strategy.NON_DETERMINISTIC), 2);
        // 6-2-2
        assertEquals(PrimeRange.extractLargestPrime(1, -1, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
    }

    /**
     Boundary analysis tests
     @throws Exception
     */
    @org.junit.Test
    public void boundaryAnalysis() throws Exception {

        /*
        Equivalence classes for the boundaries - The boundary is actually when l = u
        B_1 -> { (a, b) / a = b , a < 0}
        B_2 -> { (a, b) / a = b , a = 0}
        B_3 -> { (a, b) / a = b , a > 0}

        In total, by combining with M_1, M_2, R_1, R_2 -> |T| = 10 tests
        */

        // 1-1-1
        assertEquals(PrimeRange.extractLargestPrime(-17, -17, PrimeRange.Strategy.DETERMINISTIC), -17);
        // 1-1-2
        assertEquals(PrimeRange.extractLargestPrime(-1, -1,   PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 1-2-1
        assertEquals(PrimeRange.extractLargestPrime(-17, -17, PrimeRange.Strategy.NON_DETERMINISTIC), -17);
        // 1-2-2
        assertEquals(PrimeRange.extractLargestPrime(-1, -1,   PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 2-1-2
        assertEquals(PrimeRange.extractLargestPrime(0, 0, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 2-2-2
        assertEquals(PrimeRange.extractLargestPrime(0, 0, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 3-1-1
        assertEquals(PrimeRange.extractLargestPrime(23, 23, PrimeRange.Strategy.DETERMINISTIC), 23);
        // 3-1-2
        assertEquals(PrimeRange.extractLargestPrime(200, 200, PrimeRange.Strategy.DETERMINISTIC), PrimeRange.INVALID_PRIME);
        // 3-2-1
        assertEquals(PrimeRange.extractLargestPrime(23, 23, PrimeRange.Strategy.NON_DETERMINISTIC), 23);
        // 3-2-2
        assertEquals(PrimeRange.extractLargestPrime(200, 200, PrimeRange.Strategy.NON_DETERMINISTIC), PrimeRange.INVALID_PRIME);
    }

    /**
     Category partitioning tests
     @throws Exception
     */
    @org.junit.Test
    public void categoryPartitioning() throws Exception {

        // Test isPrimeV2(x)
        // x = 0 (fundamental special case)
        assertFalse(PrimeRange.isPrimeV2(0));
        // x = +1 (fundamental special case)
        assertFalse(PrimeRange.isPrimeV2(1));
        // x = -1 (fundamental special case)
        assertFalse(PrimeRange.isPrimeV2(-1));
        // x > 0 (x small 0 < x <= 2^16-1)
        assertFalse(PrimeRange.isPrimeV2(65535));
        // x < 0 (x small -2^16 < x < 0)
        assertFalse(PrimeRange.isPrimeV2(65536));
        // x > 0 (x big 0 < x <= 2^31-1)
        assertTrue(PrimeRange.isPrimeV2(2147483647));
        // x < 0 (x big -2^31 < x < 0)
        assertFalse(PrimeRange.isPrimeV2(-2147483648));

        // Test isPrimeV3(x)
        // x = 0 (fundamental special case)
        assertFalse(PrimeRange.isPrimeV3(0));
        // x = +1 (fundamental special case)
        assertFalse(PrimeRange.isPrimeV3(1));
        // x = -1 (fundamental special case)
        assertFalse(PrimeRange.isPrimeV3(-1));
        // x > 0 (x small 0 < x <= 2^16-1)
        assertFalse(PrimeRange.isPrimeV3(65535));
        // x < 0 (x small -2^16 < x < 0)
        assertFalse(PrimeRange.isPrimeV3(65536));
        // x > 0 (x big 0 < x <= 2^31-1)
        assertTrue(PrimeRange.isPrimeV3(2147483647));
        // x < 0 (x big -2^31 < x < 0)
        assertFalse(PrimeRange.isPrimeV3(-2147483648));
    }
}