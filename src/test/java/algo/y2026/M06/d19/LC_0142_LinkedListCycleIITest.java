package algo.y2026.M06.d19;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LC_0142_LinkedListCycleIITest {

    private final LC_0142_LinkedListCycleII solution = new LC_0142_LinkedListCycleII();

    @Test
    void testCycleAtMiddle() {
        ListNode node4 = new ListNode(-4);
        ListNode node3 = new ListNode(0, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(3, node2);
        node4.next = node2;
        assertEquals(node2, solution.detectCycle(node1));
    }

    @Test
    void testCycleAtHead() {
        ListNode node2 = new ListNode(2);
        ListNode node1 = new ListNode(1, node2);
        node2.next = node1;
        assertEquals(node1, solution.detectCycle(node1));
    }

    @Test
    void testNoCycle() {
        assertNull(solution.detectCycle(ListNode.fromArray(new int[]{1, 2, 3})));
    }

    @Test
    void testSingleNodeNoCycle() {
        assertNull(solution.detectCycle(ListNode.fromArray(new int[]{1})));
    }
}
