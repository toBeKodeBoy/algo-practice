package algo.y2026.M06.d11;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LC_0034_FindFirstAndLastPositionOfElementInSortedArrayTest {

    private final LC_0034_FindFirstAndLastPositionOfElementInSortedArray solution = new LC_0034_FindFirstAndLastPositionOfElementInSortedArray();

    @Test
    void testExample1() {
        assertArrayEquals(new int[]{3, 4}, solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8));
    }

    @Test
    void testExample2() {
        assertArrayEquals(new int[]{-1, -1}, solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6));
    }

    @Test
    void testEmptyArray() {
        assertArrayEquals(new int[]{-1, -1}, solution.searchRange(new int[]{}, 0));
    }

    @Test
    void testSingleElementFound() {
        assertArrayEquals(new int[]{0, 0}, solution.searchRange(new int[]{1}, 1));
    }

    @Test
    void testSingleElementNotFound() {
        assertArrayEquals(new int[]{-1, -1}, solution.searchRange(new int[]{1}, 2));
    }

    @Test
    void testAllSame() {
        assertArrayEquals(new int[]{0, 4}, solution.searchRange(new int[]{2, 2, 2, 2, 2}, 2));
    }

    @Test
    void testNullArray() {
        assertArrayEquals(new int[]{-1, -1}, solution.searchRange(null, 1));
    }
}
