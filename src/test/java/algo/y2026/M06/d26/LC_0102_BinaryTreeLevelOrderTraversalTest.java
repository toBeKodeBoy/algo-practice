package algo.y2026.M06.d26;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0102_BinaryTreeLevelOrderTraversalTest {

    private final LC_0102_BinaryTreeLevelOrderTraversal solution = new LC_0102_BinaryTreeLevelOrderTraversal();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 9, 20, null, null, 15, 7});
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(3),
                Arrays.asList(9, 20),
                Arrays.asList(15, 7));
        assertEquals(expected, solution.levelOrder(root));
    }
}
