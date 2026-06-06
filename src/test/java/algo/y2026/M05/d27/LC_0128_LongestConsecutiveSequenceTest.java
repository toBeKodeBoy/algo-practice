package algo.y2026.M05.d27;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0128_LongestConsecutiveSequenceTest {

    private final LC_0128_LongestConsecutiveSequence solution = new LC_0128_LongestConsecutiveSequence();

    @Test
    void testExample() {
        assertEquals(4, solution.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }

    @Test
    void testEmpty() {
        assertEquals(0, solution.longestConsecutive(new int[]{}));
    }

    @Test
    void testSingle() {
        assertEquals(1, solution.longestConsecutive(new int[]{1}));
    }
}
