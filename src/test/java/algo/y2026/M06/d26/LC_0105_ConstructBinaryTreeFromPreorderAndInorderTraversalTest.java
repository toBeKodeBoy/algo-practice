package algo.y2026.M06.d26;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0105_ConstructBinaryTreeFromPreorderAndInorderTraversalTest {

    private final LC_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal solution =
            new LC_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal();

    @Test
    void testExample1() {
        TreeNode root = solution.buildTree(
                new int[]{3, 9, 20, 15, 7},
                new int[]{9, 3, 15, 20, 7});
        TreeNode expected = TreeNode.fromArray(new Integer[]{3, 9, 20, null, null, 15, 7});
        assertTrue(TreeNode.equals(expected, root));
    }
}
