package algo.y2026.M06.d30;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0230_KthSmallestElementInABstTest {

    private final LC_0230_KthSmallestElementInABst solution = new LC_0230_KthSmallestElementInABst();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 1, 4, null, 2});
        assertEquals(1, solution.kthSmallest(root, 1));
    }
}
