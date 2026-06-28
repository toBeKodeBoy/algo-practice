package algo.y2026.M06.d23;

import algo.util.ListNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LC_0148_SortListTest {

    private final LC_0148_SortList solution = new LC_0148_SortList();

    @Test
    void testExample1() {
        assertTrue(ListNode.equals(
                ListNode.fromArray(new int[]{1, 2, 3, 4}),
                solution.sortList(ListNode.fromArray(new int[]{4, 2, 1, 3}))));
    }
}
