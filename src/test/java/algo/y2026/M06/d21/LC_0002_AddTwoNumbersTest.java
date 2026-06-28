package algo.y2026.M06.d21;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0002_AddTwoNumbersTest {

    private final LC_0002_AddTwoNumbers solution = new LC_0002_AddTwoNumbers();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{7, 0, 8}),
                solution.addTwoNumbers(
                        ListNode.fromArray(new int[]{2, 4, 3}),
                        ListNode.fromArray(new int[]{5, 6, 4}))));
    }

    @Test
    void testExample2() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{0}),
                solution.addTwoNumbers(
                        ListNode.fromArray(new int[]{0}),
                        ListNode.fromArray(new int[]{0}))));
    }

    @Test
    void testDifferentLengths() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{8, 0, 1}),
                solution.addTwoNumbers(
                        ListNode.fromArray(new int[]{9}),
                        ListNode.fromArray(new int[]{9, 9}))));
    }

    @Test
    void testCarryAtEnd() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{0, 1, 1, 1}),
                solution.addTwoNumbers(
                        ListNode.fromArray(new int[]{5, 5, 5}),
                        ListNode.fromArray(new int[]{5, 5, 5}))));
    }
}
