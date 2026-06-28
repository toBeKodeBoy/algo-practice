package algo.y2026.M06.d25;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0226_InvertBinaryTreeTest {

    private final LC_0226_InvertBinaryTree solution = new LC_0226_InvertBinaryTree();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{4, 2, 7, 1, 3, 6, 9});
        TreeNode expected = TreeNode.fromArray(new Integer[]{4, 7, 2, 9, 6, 3, 1});
        assertTrue(TreeNode.equals(expected, solution.invertTree(root)));
    }
}
