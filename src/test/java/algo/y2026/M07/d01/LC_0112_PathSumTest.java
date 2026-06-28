package algo.y2026.M07.d01;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0112_PathSumTest {

    private final LC_0112_PathSum solution = new LC_0112_PathSum();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{
                5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1});
        assertTrue(solution.hasPathSum(root, 22));
    }
}
