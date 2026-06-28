package algo.y2026.M06.d24;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0104_MaximumDepthOfBinaryTreeTest {

    private final LC_0104_MaximumDepthOfBinaryTree solution = new LC_0104_MaximumDepthOfBinaryTree();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 9, 20, null, null, 15, 7});
        assertEquals(3, solution.maxDepth(root));
    }
}
