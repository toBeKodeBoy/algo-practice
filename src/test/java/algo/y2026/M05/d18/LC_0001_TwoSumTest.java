package algo.y2026.M05.d18;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LC_0001_TwoSumTest {

    private final LC_0001_TwoSum solution = new LC_0001_TwoSum();

    @Test
    void testExample() {
        assertArrayEquals(new int[]{0, 1}, solution.twoSum(new int[]{2, 7, 11, 15}, 9));
    }

    @Test
    void testTwoElements() {
        assertArrayEquals(new int[]{0, 1}, solution.twoSum(new int[]{3, 3}, 6));
    }
}
