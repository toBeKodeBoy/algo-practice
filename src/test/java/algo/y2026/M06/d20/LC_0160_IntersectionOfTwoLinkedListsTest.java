package algo.y2026.M06.d20;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LC_0160_IntersectionOfTwoLinkedListsTest {

    private final LC_0160_IntersectionOfTwoLinkedLists solution = new LC_0160_IntersectionOfTwoLinkedLists();

    @Test
    void testIntersection() {
        ListNode common = ListNode.fromArray(new int[]{8, 4, 5});
        ListNode a = new ListNode(4, new ListNode(1, common));
        ListNode b = new ListNode(5, new ListNode(6, new ListNode(1, common)));
        assertEquals(common, solution.getIntersectionNode(a, b));
    }

    @Test
    void testNoIntersection() {
        ListNode a = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode b = ListNode.fromArray(new int[]{4, 5, 6});
        assertNull(solution.getIntersectionNode(a, b));
    }

    @Test
    void testSameHeadIntersection() {
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3});
        assertEquals(head, solution.getIntersectionNode(head, head));
    }

    @Test
    void testOneNullList() {
        ListNode a = ListNode.fromArray(new int[]{1});
        assertNull(solution.getIntersectionNode(a, null));
    }
}
