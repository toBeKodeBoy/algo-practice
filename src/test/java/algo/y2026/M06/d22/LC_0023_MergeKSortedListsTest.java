package algo.y2026.M06.d22;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0023_MergeKSortedListsTest {

    private final LC_0023_MergeKSortedLists solution = new LC_0023_MergeKSortedLists();

    @Test
    void testExample1() {
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 1, 2, 3, 4, 4, 5, 6}),
                solution.mergeKLists(lists)));
    }
}
