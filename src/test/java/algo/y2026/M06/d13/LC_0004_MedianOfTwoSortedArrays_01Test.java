package algo.y2026.M06.d13;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0004_MedianOfTwoSortedArrays_01Test {

    private final LC_0004_MedianOfTwoSortedArrays_01 solution = new LC_0004_MedianOfTwoSortedArrays_01();

    @Test
    void testExample1() {
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}), 1e-5);
    }

    @Test
    void testExample2() {
        assertEquals(2.5, solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}), 1e-5);
    }

    @Test
    void testAllZeros() {
        assertEquals(0.0, solution.findMedianSortedArrays(new int[]{0, 0}, new int[]{0, 0}), 1e-5);
    }

    @Test
    void testOneEmpty() {
        assertEquals(1.0, solution.findMedianSortedArrays(new int[]{}, new int[]{1}), 1e-5);
    }

    @Test
    void testOtherEmpty() {
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{2}, new int[]{}), 1e-5);
    }

    @Test
    void testSingleEach() {
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{1}, new int[]{3}), 1e-5);
    }

    @Test
    void testOneSmaller() {
        assertEquals(3.0, solution.findMedianSortedArrays(new int[]{1, 2, 3}, new int[]{4, 5}), 1e-5);
    }

    @Test
    void testDuplicateValues() {
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{2, 3, 4}), 1e-5);
    }
}
