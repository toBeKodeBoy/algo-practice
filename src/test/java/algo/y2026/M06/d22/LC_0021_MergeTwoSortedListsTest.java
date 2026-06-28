package algo.y2026.M06.d22;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0021_MergeTwoSortedListsTest {

    private final LC_0021_MergeTwoSortedLists solution = new LC_0021_MergeTwoSortedLists();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 1, 2, 3, 4, 4}),
                solution.mergeTwoLists(
                        ListNode.fromArray(new int[]{1, 2, 4}),
                        ListNode.fromArray(new int[]{1, 3, 4}))));
    }

    @Test
    void testEmpty() {
        assertTrue(ListNode.equals(null, solution.mergeTwoLists(null, null)));
    }
}
