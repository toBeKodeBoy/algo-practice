package algo.y2026.M06.d08;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0080_RemoveDuplicatesIITest {

    private final LC_0080_RemoveDuplicatesII solution = new LC_0080_RemoveDuplicatesII();

    @Test
    void testExample1() {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int len = solution.removeDuplicates(nums);
        assertEquals(5, len);
        assertArrayEquals(new int[]{1, 1, 2, 2, 3}, java.util.Arrays.copyOf(nums, len));
    }

    @Test
    void testExample2() {
        int[] nums = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        int len = solution.removeDuplicates(nums);
        assertEquals(7, len);
        assertArrayEquals(new int[]{0, 0, 1, 1, 2, 3, 3}, java.util.Arrays.copyOf(nums, len));
    }

    @Test
    void testAllSame() {
        int[] nums = {1, 1, 1};
        int len = solution.removeDuplicates(nums);
        assertEquals(2, len);
        assertArrayEquals(new int[]{1, 1}, java.util.Arrays.copyOf(nums, len));
    }

    @Test
    void testNoDuplicate() {
        int[] nums = {1, 2, 3};
        int len = solution.removeDuplicates(nums);
        assertEquals(3, len);
        assertArrayEquals(new int[]{1, 2, 3}, java.util.Arrays.copyOf(nums, len));
    }

    @Test
    void testEmptyArray() {
        assertEquals(0, solution.removeDuplicates(new int[]{}));
    }

    @Test
    void testNullArray() {
        assertEquals(0, solution.removeDuplicates(null));
    }

    @Test
    void testAllKeep() {
        int[] nums = {1, 1, 2, 2, 3, 3};
        int len = solution.removeDuplicates(nums);
        assertEquals(6, len);
        assertArrayEquals(new int[]{1, 1, 2, 2, 3, 3}, java.util.Arrays.copyOf(nums, len));
    }
}
