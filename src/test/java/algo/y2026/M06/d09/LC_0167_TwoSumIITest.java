package algo.y2026.M06.d09;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LC_0167_TwoSumIITest {

    private final LC_0167_TwoSumII solution = new LC_0167_TwoSumII();

    @Test
    void testExample1() {
        assertArrayEquals(new int[]{1, 2}, solution.twoSum(new int[]{2, 7, 11, 15}, 9));
    }

    @Test
    void testExample2() {
        assertArrayEquals(new int[]{1, 3}, solution.twoSum(new int[]{2, 3, 4}, 6));
    }

    @Test
    void testExample3() {
        assertArrayEquals(new int[]{1, 2}, solution.twoSum(new int[]{-1, 0}, -1));
    }

    @Test
    void testEmptyArray() {
        assertArrayEquals(new int[]{-1, -1}, solution.twoSum(new int[]{}, 1));
    }

    @Test
    void testNullArray() {
        assertArrayEquals(new int[]{-1, -1}, solution.twoSum(null, 1));
    }

    @Test
    void testNoSolution() {
        assertArrayEquals(new int[]{-1, -1}, solution.twoSum(new int[]{1, 2, 3}, 10));
    }
}
