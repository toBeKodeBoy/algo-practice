package algo.y2026.M07.d02;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0236_LowestCommonAncestorOfABinaryTreeTest {

    private final LC_0236_LowestCommonAncestorOfABinaryTree solution =
            new LC_0236_LowestCommonAncestorOfABinaryTree();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p = findNode(root, 5);
        TreeNode q = findNode(root, 1);
        assertEquals(3, solution.lowestCommonAncestor(root, p, q).val);
    }

    private TreeNode findNode(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        TreeNode left = findNode(root.left, val);
        return left != null ? left : findNode(root.right, val);
    }
}
