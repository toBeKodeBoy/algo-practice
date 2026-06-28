package algo.y2026.M06.d12;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LC_0239_SlidingWindowMaximum_02Test {

    private final LC_0239_SlidingWindowMaximum_02 solution = new LC_0239_SlidingWindowMaximum_02();

    @Test
    void testExample1() {
        assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7},
            solution.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
    }

    @Test
    void testSingleElement() {
        assertArrayEquals(new int[]{1}, solution.maxSlidingWindow(new int[]{1}, 1));
    }

    @Test
    void testWindowSizeOne() {
        assertArrayEquals(new int[]{1, -1}, solution.maxSlidingWindow(new int[]{1, -1}, 1));
    }

    @Test
    void testWindowEqualsArray() {
        assertArrayEquals(new int[]{3}, solution.maxSlidingWindow(new int[]{1, 2, 3}, 3));
    }

    @Test
    void testDescending() {
        assertArrayEquals(new int[]{7, 6, 5},
            solution.maxSlidingWindow(new int[]{7, 6, 5, 4, 3}, 3));
    }

    @Test
    void testAscending() {
        assertArrayEquals(new int[]{3, 4, 5},
            solution.maxSlidingWindow(new int[]{1, 2, 3, 4, 5}, 3));
    }

    @Test
    void testEmptyArray() {
        assertArrayEquals(new int[]{}, solution.maxSlidingWindow(new int[]{}, 3));
    }

    @Test
    void testNullArray() {
        assertArrayEquals(new int[]{}, solution.maxSlidingWindow(null, 3));
    }
}
