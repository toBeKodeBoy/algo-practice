package algo.y2026.M06.d24;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0094_BinaryTreeInorderTraversalTest {

    private final LC_0094_BinaryTreeInorderTraversal solution = new LC_0094_BinaryTreeInorderTraversal();

    @Test
    void testExample1() {
        TreeNode root = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        assertEquals(Arrays.asList(1, 3, 2), solution.inorderTraversal(root));
    }
}
