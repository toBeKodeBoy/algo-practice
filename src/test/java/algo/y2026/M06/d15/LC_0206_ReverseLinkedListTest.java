package algo.y2026.M06.d15;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0206_ReverseLinkedListTest {

    private final LC_0206_ReverseLinkedList solution = new LC_0206_ReverseLinkedList();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{5, 4, 3, 2, 1}),
                solution.reverseList(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}))));
    }

    @Test
    void testExample2() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{2, 1}),
                solution.reverseList(ListNode.fromArray(new int[]{1, 2}))));
    }

    @Test
    void testEmpty() {
        assertTrue(ListNode.equals(null, solution.reverseList(null)));
    }

    @Test
    void testSingleNode() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1}),
                solution.reverseList(ListNode.fromArray(new int[]{1}))));
    }
}
