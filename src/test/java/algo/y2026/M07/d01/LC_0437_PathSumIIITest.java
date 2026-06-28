package algo.y2026.M07.d01;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0437_PathSumIIITest {

    private final LC_0437_PathSumIII solution = new LC_0437_PathSumIII();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{
                10, 5, -3, 3, 2, null, 11, 3, -2, null, 1});
        assertEquals(3, solution.pathSum(root, 8));
    }
}
