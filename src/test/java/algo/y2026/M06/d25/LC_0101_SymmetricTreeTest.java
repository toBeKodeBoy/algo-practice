package algo.y2026.M06.d25;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0101_SymmetricTreeTest {

    private final LC_0101_SymmetricTree solution = new LC_0101_SymmetricTree();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{1, 2, 2, 3, 4, 4, 3});
        assertTrue(solution.isSymmetric(root));
    }
}
