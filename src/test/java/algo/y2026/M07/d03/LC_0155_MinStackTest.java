package algo.y2026.M07.d03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LC_0155_MinStackTest {

    @Test
    void testOperations() {
        LC_0155_MinStack.MinStack stack = new LC_0155_MinStack.MinStack();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        assertEquals(-3, stack.getMin());
        stack.pop();
        assertEquals(0, stack.top());
        assertEquals(-2, stack.getMin());
    }
}
