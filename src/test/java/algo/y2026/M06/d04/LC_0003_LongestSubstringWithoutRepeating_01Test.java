package algo.y2026.M06.d04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0003_LongestSubstringWithoutRepeating_01Test {

    private final LC_0003_LongestSubstringWithoutRepeating_01 solution = new LC_0003_LongestSubstringWithoutRepeating_01();

    @Test
    void testExample1() {
        assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    void testExample2() {
        assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    void testExample3() {
        assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    void testEmptyString() {
        assertEquals(0, solution.lengthOfLongestSubstring(""));
    }

    @Test
    void testNullString() {
        assertEquals(0, solution.lengthOfLongestSubstring(null));
    }

    @Test
    void testDvdf() {
        assertEquals(3, solution.lengthOfLongestSubstring("dvdf"));
    }
}
