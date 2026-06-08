package algo.y2026.M06.d12;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0153_FindMinimumInRotatedSortedArrayTest {

    private final LC_0153_FindMinimumInRotatedSortedArray solution = new LC_0153_FindMinimumInRotatedSortedArray();

    @Test
    void testExample1() {
        assertEquals(1, solution.findMin(new int[]{3, 4, 5, 1, 2}));
    }

    @Test
    void testExample2() {
        assertEquals(0, solution.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
    }

    @Test
    void testNotRotated() {
        assertEquals(11, solution.findMin(new int[]{11, 13, 15, 17}));
    }

    @Test
    void testSingleElement() {
        assertEquals(1, solution.findMin(new int[]{1}));
    }

    @Test
    void testTwoElements() {
        assertEquals(1, solution.findMin(new int[]{2, 1}));
        assertEquals(1, solution.findMin(new int[]{1, 2}));
    }

    @Test
    void testRotatedAtMiddle() {
        assertEquals(1, solution.findMin(new int[]{2, 3, 4, 5, 1}));
    }
}
