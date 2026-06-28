package algo.y2026.M06.d04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0076_MinWindowSubstring_02Test {

    private final LC_0076_MinWindowSubstring_02 solution = new LC_0076_MinWindowSubstring_02();

    @Test
    void testExample() {
        assertEquals("BANC", solution.minWindow("ADOBECODEBANC", "ABC"));
    }

    @Test
    void testSingleCharMatch() {
        assertEquals("a", solution.minWindow("a", "a"));
    }

    @Test
    void testNoMatch() {
        assertEquals("", solution.minWindow("a", "aa"));
    }

    @Test
    void testEmptyS() {
        assertEquals("", solution.minWindow("", "a"));
    }

    @Test
    void testNullS() {
        assertEquals("", solution.minWindow(null, "a"));
    }

    @Test
    void testNullT() {
        assertEquals("", solution.minWindow("a", null));
    }

    @Test
    void testSingleCharInLong() {
        assertEquals("a", solution.minWindow("ab", "a"));
    }

    @Test
    void testSingleCharInEnd() {
        assertEquals("b", solution.minWindow("ab", "b"));
    }

    @Test
    void testTwoCharsNonAdjacent() {
        assertEquals("abc", solution.minWindow("abc", "ac"));
    }

    @Test
    void testDuplicateInT() {
        assertEquals("baa", solution.minWindow("bbaa", "aba"));
    }
}
