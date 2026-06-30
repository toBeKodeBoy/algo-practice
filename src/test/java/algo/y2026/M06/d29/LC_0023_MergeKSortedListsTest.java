package algo.y2026.M06.d29;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testEmptyArray() {
        ListNode[] lists = {};
        assertNull(solution.mergeKLists(lists));
    }

    @Test
    void testNullArray() {
        assertNull(solution.mergeKLists(null));
    }

    @Test
    void testSingleList() {
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 2, 3})
        };
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 2, 3}),
                solution.mergeKLists(lists)));
    }

    @Test
    void testAllEmptyLists() {
        ListNode[] lists = {null, null, null};
        assertNull(solution.mergeKLists(lists));
    }

    @Test
    void testSomeEmptyLists() {
        ListNode[] lists = {
                null,
                ListNode.fromArray(new int[]{1, 3}),
                null,
                ListNode.fromArray(new int[]{2, 4})
        };
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 2, 3, 4}),
                solution.mergeKLists(lists)));
    }

    @Test
    void testHeapApproach() {
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 1, 2, 3, 4, 4, 5, 6}),
                solution.mergeKListsHeap(lists)));
    }

    @Test
    void testDcApproach() {
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 1, 2, 3, 4, 4, 5, 6}),
                solution.mergeKListsDC(lists)));
    }
}
