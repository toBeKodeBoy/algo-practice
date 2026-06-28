package algo.y2026.M07.d03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0020_ValidParenthesesTest {

    private final LC_0020_ValidParentheses solution = new LC_0020_ValidParentheses();

    @Test
    void testValid() {
        assertTrue(solution.isValid("()[]{}"));
    }

    @Test
    void testInvalid() {
        assertFalse(solution.isValid("(]"));
    }
}
