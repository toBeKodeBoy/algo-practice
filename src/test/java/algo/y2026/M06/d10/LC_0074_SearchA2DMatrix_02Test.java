package algo.y2026.M06.d10;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0074_SearchA2DMatrix_02Test {

    private final LC_0074_SearchA2DMatrix_02 solution = new LC_0074_SearchA2DMatrix_02();

    private final int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};

    @Test
    void testTargetExists() {
        assertTrue(solution.searchMatrix(matrix, 3));
    }

    @Test
    void testTargetNotExists() {
        assertFalse(solution.searchMatrix(matrix, 13));
    }

    @Test
    void testTargetFirst() {
        assertTrue(solution.searchMatrix(matrix, 1));
    }

    @Test
    void testTargetLast() {
        assertTrue(solution.searchMatrix(matrix, 60));
    }

    @Test
    void testSingleElementFound() {
        assertTrue(solution.searchMatrix(new int[][]{{1}}, 1));
    }

    @Test
    void testSingleElementNotFound() {
        assertFalse(solution.searchMatrix(new int[][]{{1}}, 2));
    }

    @Test
    void testEmptyMatrix() {
        assertFalse(solution.searchMatrix(new int[][]{}, 1));
    }

    @Test
    void testNullMatrix() {
        assertFalse(solution.searchMatrix(null, 1));
    }
}
