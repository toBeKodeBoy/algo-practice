package algo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListNodeTest {

    @Test
    void fromArrayAndToArrayRoundTrip() {
        int[] input = {1, 2, 3, 4, 5};
        assertArrayEquals(input, ListNode.toArray(ListNode.fromArray(input)));
    }

    @Test
    void fromEmptyArrayReturnsNull() {
        assertNull(ListNode.fromArray(new int[]{}));
        assertNull(ListNode.fromArray(null));
    }

    @Test
    void equalsDetectsSameValues() {
        ListNode a = ListNode.fromArray(new int[]{1, 2, 3});
        ListNode b = ListNode.fromArray(new int[]{1, 2, 3});
        assertTrue(ListNode.equals(a, b));
    }
}
