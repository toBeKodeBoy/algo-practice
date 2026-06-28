package algo.y2026.M06.d29;

import algo.util.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LC_0114_FlattenBinaryTreeToLinkedListTest {

    private final LC_0114_FlattenBinaryTreeToLinkedList solution = new LC_0114_FlattenBinaryTreeToLinkedList();

    @Test
    void testExample1() {
        TreeNode root = TreeNode.fromArray(new Integer[]{1, 2, 5, 3, 4, null, 6});
        solution.flatten(root);
        assertEquals(1, root.val);
        assertNull(root.left);
        assertEquals(2, root.right.val);
        assertNull(root.right.left);
        assertEquals(3, root.right.right.val);
        assertNull(root.right.right.left);
        assertEquals(4, root.right.right.right.val);
        assertNull(root.right.right.right.left);
        assertEquals(5, root.right.right.right.right.val);
        assertNull(root.right.right.right.right.left);
        assertEquals(6, root.right.right.right.right.right.val);
    }
}
