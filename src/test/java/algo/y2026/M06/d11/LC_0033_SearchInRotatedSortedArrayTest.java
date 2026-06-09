package algo.y2026.M06.d11;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0033_SearchInRotatedSortedArrayTest {

    private final LC_0033_SearchInRotatedSortedArray solution = new LC_0033_SearchInRotatedSortedArray();

    @Test
    void testExample1() {
        assertEquals(4, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
    }

    @Test
    void testExample2() {
        assertEquals(-1, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
    }

    @Test
    void testSingleElementFound() {
        assertEquals(0, solution.search(new int[]{1}, 1));
    }

    @Test
    void testSingleElementNotFound() {
        assertEquals(-1, solution.search(new int[]{1}, 0));
    }

    @Test
    void testNotRotated() {
        assertEquals(2, solution.search(new int[]{1, 2, 3, 4, 5}, 3));
    }

    @Test
    void testTargetAtEnd() {
        assertEquals(5, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 1));
    }

    @Test
    void testTargetAtStart() {
        assertEquals(0, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 4));
    }

    @Test
    void testEmptyArray() {
        assertEquals(-1, solution.search(new int[]{}, 5));
    }

    @Test
    void testNullArray() {
        assertEquals(-1, solution.search(null, 5));
    }
}
