package algo.y2026.M06.d03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LC_0238_ProductExceptSelfTest {

    private final LC_0238_ProductExceptSelf solution = new LC_0238_ProductExceptSelf();

    @Test
    void testExample() {
        assertArrayEquals(new int[]{24, 12, 8, 6}, solution.productExceptSelf(new int[]{1, 2, 3, 4}));
    }

    @Test
    void testWithZero() {
        assertArrayEquals(new int[]{0, 0, 0, 0}, solution.productExceptSelf(new int[]{0, 0, 0, 0}));
    }
}
