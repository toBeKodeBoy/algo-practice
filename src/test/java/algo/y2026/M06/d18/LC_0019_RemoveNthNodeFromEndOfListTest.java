package algo.y2026.M06.d18;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0019_RemoveNthNodeFromEndOfListTest {

    private final LC_0019_RemoveNthNodeFromEndOfList solution = new LC_0019_RemoveNthNodeFromEndOfList();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 2, 3, 5}),
                solution.removeNthFromEnd(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 2)));
    }

    @Test
    void testExample2() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{}),
                solution.removeNthFromEnd(ListNode.fromArray(new int[]{1}), 1)));
    }

    @Test
    void testRemoveHead() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{2, 3, 4, 5}),
                solution.removeNthFromEnd(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 5)));
    }

    @Test
    void testRemoveTail() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 2, 3, 4}),
                solution.removeNthFromEnd(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 1)));
    }
}
