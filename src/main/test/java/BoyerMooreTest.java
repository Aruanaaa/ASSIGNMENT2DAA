package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreTest {

    @Test
    public void testEmptyArray() {
        Integer[] arr = new Integer[0];
        Integer res = BoyerMoore.findMajority(arr, new PerformanceTracker());
        assertNull(res, "Empty array should return null (no majority)");
    }

    @Test
    public void testSingleElement() {
        Integer[] arr = new Integer[] { 42 };
        Integer res = BoyerMoore.findMajority(arr, new PerformanceTracker());
        assertEquals(42, res);
    }

    @Test
    public void testAllSame() {
        Integer[] arr = new Integer[] {1,1,1,1,1};
        Integer res = BoyerMoore.findMajority(arr, new PerformanceTracker());
        assertEquals(1, res);
    }

    @Test
    public void testMajorityExists() {
        Integer[] arr = new Integer[] {2,3,2,2,5,2,2};
        Integer res = BoyerMoore.findMajority(arr, new PerformanceTracker());
        assertEquals(2, res);
    }

    @Test
    public void testNoMajority() {
        Integer[] arr = new Integer[] {1,2,3,1,2,3,4};
        Integer res = BoyerMoore.findMajority(arr, new PerformanceTracker());
        assertNull(res);
    }

    @Test
    public void testLargeMajority() {
        int n = 1001;
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) arr[i] = (i % 2 == 0) ? 7 : 8;
        // insert more 7's to make majority
        for (int i = 0; i < 501; i++) arr[i] = 7;
        Integer res = BoyerMoore.findMajority(arr, new PerformanceTracker());
        assertEquals(7, res);
    }

    @Test
    public void testNullInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> BoyerMoore.findMajority(null, new PerformanceTracker()));
    }
}
