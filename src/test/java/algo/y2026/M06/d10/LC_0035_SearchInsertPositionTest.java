package algo.y2026.M06.d10;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0035_SearchInsertPositionTest {

    private final LC_0035_SearchInsertPosition solution = new LC_0035_SearchInsertPosition();

    @Test
    void testExample1() {
        assertEquals(2, solution.searchInsert(new int[]{1, 3, 5, 6}, 5));
    }

    @Test
    void testExample2() {
        assertEquals(1, solution.searchInsert(new int[]{1, 3, 5, 6}, 2));
    }

    @Test
    void testExample3() {
        assertEquals(4, solution.searchInsert(new int[]{1, 3, 5, 6}, 7));
    }

    @Test
    void testExample4() {
        assertEquals(0, solution.searchInsert(new int[]{1, 3, 5, 6}, 0));
    }

    @Test
    void testSingleElement() {
        assertEquals(0, solution.searchInsert(new int[]{1}, 0));
        assertEquals(0, solution.searchInsert(new int[]{1}, 1));
        assertEquals(1, solution.searchInsert(new int[]{1}, 2));
    }

    @Test
    void testEmptyArray() {
        assertEquals(0, solution.searchInsert(new int[]{}, 5));
    }

    @Test
    void testNullArray() {
        assertEquals(0, solution.searchInsert(null, 5));
    }
}
