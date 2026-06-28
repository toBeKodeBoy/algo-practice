package algo.y2026.M06.d17;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0024_SwapNodesInPairsTest {

    private final LC_0024_SwapNodesInPairs solution = new LC_0024_SwapNodesInPairs();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{2, 1, 4, 3}),
                solution.swapPairs(ListNode.fromArray(new int[]{1, 2, 3, 4}))));
    }

    @Test
    void testExample2() {
        assertTrue(ListNode.equals(ListNode.fromArray(new int[]{}), solution.swapPairs(null)));
    }

    @Test
    void testSingleNode() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1}),
                solution.swapPairs(ListNode.fromArray(new int[]{1}))));
    }

    @Test
    void testThreeNodes() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{2, 1, 3}),
                solution.swapPairs(ListNode.fromArray(new int[]{1, 2, 3}))));
    }
}
