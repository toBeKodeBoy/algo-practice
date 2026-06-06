package algo.y2026.M06.d01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0053_MaximumSubarrayTest {

    private final LC_0053_MaximumSubarray solution = new LC_0053_MaximumSubarray();

    @Test
    void testExample() {
        assertEquals(6, solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    @Test
    void testSingleNegative() {
        assertEquals(-1, solution.maxSubArray(new int[]{-1}));
    }

    @Test
    void testAllNegative() {
        assertEquals(-1, solution.maxSubArray(new int[]{-2, -1}));
    }
}
