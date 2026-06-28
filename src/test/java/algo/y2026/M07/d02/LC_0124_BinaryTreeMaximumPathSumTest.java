package algo.y2026.M07.d02;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0124_BinaryTreeMaximumPathSumTest {

    private final LC_0124_BinaryTreeMaximumPathSum solution = new LC_0124_BinaryTreeMaximumPathSum();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{-10, 9, 20, null, null, 15, 7});
        assertEquals(42, solution.maxPathSum(root));
    }
}
