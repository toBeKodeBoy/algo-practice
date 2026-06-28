package algo.y2026.M06.d23;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0025_ReverseNodesInKGroupTest {

    private final LC_0025_ReverseNodesInKGroup solution = new LC_0025_ReverseNodesInKGroup();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{2, 1, 4, 3, 5}),
                solution.reverseKGroup(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 2)));
    }
}
