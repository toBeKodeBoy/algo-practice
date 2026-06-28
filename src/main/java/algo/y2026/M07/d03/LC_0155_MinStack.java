package algo.y2026.M07.d03;

import java.util.ArrayDeque;
import java.util.Deque;

// 题目 最小栈
// 示例 push(-2), push(0), push(-3), getMin()=>-3, pop(), top()=>0, getMin()=>-2
// leetcode https://leetcode.cn/problems/min-stack/
// 算法核心 辅助栈同步记录当前最小值；push 时若 val <= min 则同步入 minStack
// 时间 O(1) 空间 O(n)
// 难度 Medium
// 标签 栈, 设计
// 时段 晚地铁
// 类型 新题

public class LC_0155_MinStack {

    static class MinStack {
        private final Deque<Integer> stack = new ArrayDeque<>();
        private final Deque<Integer> minStack = new ArrayDeque<>();

        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            if (stack.pop().equals(minStack.peek())) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        System.out.println(stack.getMin());
        stack.pop();
        System.out.println(stack.top());
        System.out.println(stack.getMin());
    }
}
