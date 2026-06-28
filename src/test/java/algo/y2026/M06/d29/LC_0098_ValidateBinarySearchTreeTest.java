package algo.y2026.M06.d29;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0098_ValidateBinarySearchTreeTest {

    private final LC_0098_ValidateBinarySearchTree solution = new LC_0098_ValidateBinarySearchTree();

    @Test
    void testValidTree() {
        TreeNode root = TreeNode.fromArray(new Integer[]{2, 1, 3});
        assertTrue(solution.isValidBST(root));
    }

    @Test
    void testInvalidTree() {
        TreeNode root = TreeNode.fromArray(new Integer[]{5, 1, 4, null, null, 3, 6});
        assertFalse(solution.isValidBST(root));
    }
}
