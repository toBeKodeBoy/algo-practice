package algo.y2026.M06.d30;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0199_BinaryTreeRightSideViewTest {

    private final LC_0199_BinaryTreeRightSideView solution = new LC_0199_BinaryTreeRightSideView();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{1, 2, 3, null, 5, null, 4});
        List<Integer> expected = Arrays.asList(1, 3, 4);
        assertEquals(expected, solution.rightSideView(root));
    }
}
