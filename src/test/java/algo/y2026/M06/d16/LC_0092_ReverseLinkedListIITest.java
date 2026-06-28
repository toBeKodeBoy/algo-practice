package algo.y2026.M06.d16;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0092_ReverseLinkedListIITest {

    private final LC_0092_ReverseLinkedListII solution = new LC_0092_ReverseLinkedListII();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 4, 3, 2, 5}),
                solution.reverseBetween(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 2, 4)));
    }

    @Test
    void testExample2() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{5}),
                solution.reverseBetween(ListNode.fromArray(new int[]{5}), 1, 1)));
    }

    @Test
    void testReverseFromHead() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{3, 2, 1, 4, 5}),
                solution.reverseBetween(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 1, 3)));
    }

    @Test
    void testReverseToTail() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 4, 3, 2}),
                solution.reverseBetween(ListNode.fromArray(new int[]{1, 2, 3, 4}), 2, 4)));
    }
}
