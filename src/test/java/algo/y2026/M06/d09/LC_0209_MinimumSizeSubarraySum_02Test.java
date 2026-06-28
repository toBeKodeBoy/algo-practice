package algo.y2026.M06.d09;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0209_MinimumSizeSubarraySum_02Test {

    private final LC_0209_MinimumSizeSubarraySum_02 solution = new LC_0209_MinimumSizeSubarraySum_02();

    @Test
    void testExample1() {
        assertEquals(2, solution.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
    }

    @Test
    void testExample2() {
        assertEquals(1, solution.minSubArrayLen(4, new int[]{1, 4, 4}));
    }

    @Test
    void testExample3() {
        assertEquals(0, solution.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1}));
    }

    @Test
    void testSingleElement() {
        assertEquals(1, solution.minSubArrayLen(3, new int[]{3}));
    }

    @Test
    void testEmptyArray() {
        assertEquals(0, solution.minSubArrayLen(7, new int[]{}));
    }

    @Test
    void testNullArray() {
        assertEquals(0, solution.minSubArrayLen(7, null));
    }
}
