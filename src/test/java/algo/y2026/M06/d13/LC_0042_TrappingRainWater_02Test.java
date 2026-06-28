package algo.y2026.M06.d13;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0042_TrappingRainWater_02Test {

    private final LC_0042_TrappingRainWater_02 solution = new LC_0042_TrappingRainWater_02();

    @Test
    void testExample1() {
        assertEquals(6, solution.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }

    @Test
    void testExample2() {
        assertEquals(9, solution.trap(new int[]{4, 2, 0, 3, 2, 5}));
    }

    @Test
    void testAscending() {
        assertEquals(0, solution.trap(new int[]{1, 2, 3}));
    }

    @Test
    void testDescending() {
        assertEquals(0, solution.trap(new int[]{3, 2, 1}));
    }

    @Test
    void testFlat() {
        assertEquals(0, solution.trap(new int[]{1, 1, 1}));
    }

    @Test
    void testTwoElements() {
        assertEquals(0, solution.trap(new int[]{1, 2}));
    }

    @Test
    void testValley() {
        assertEquals(1, solution.trap(new int[]{2, 1, 2}));
    }

    @Test
    void testEmptyArray() {
        assertEquals(0, solution.trap(new int[]{}));
    }

    @Test
    void testNullArray() {
        assertEquals(0, solution.trap(null));
    }
}
