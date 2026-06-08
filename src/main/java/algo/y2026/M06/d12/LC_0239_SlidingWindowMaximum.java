package algo.y2026.M06.d12;

import java.util.Deque;
import java.util.ArrayDeque;

// 题目 滑动窗口最大值，返回每个窗口内最大值
// 示例 [1,3,-1,-3,5,3,6,7], k=3 => [3,3,5,5,6,7]
// leetcode https://leetcode.cn/problems/sliding-window-maximum/
// 算法核心 单调递减双端队列，存下标，维持窗口大小
// 时间 O(n) 空间 O(k)
// 难度 Hard
// 标签 队列, 滑动窗口, 单调队列
// 时段 晚地铁
// 类型 新题

public class LC_0239_SlidingWindowMaximum {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        LC_0239_SlidingWindowMaximum s = new LC_0239_SlidingWindowMaximum();

        int[] r1 = s.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        System.out.print("Test 1: ");
        for (int v : r1) System.out.print(v + " ");
        System.out.println();

        int[] r2 = s.maxSlidingWindow(new int[]{1}, 1);
        System.out.println("Test 2: " + r2[0]);

        int[] r3 = s.maxSlidingWindow(new int[]{1, -1}, 1);
        System.out.print("Test 3: ");
        for (int v : r3) System.out.print(v + " ");
        System.out.println();

        int[] r4 = s.maxSlidingWindow(new int[]{}, 3);
        System.out.println("Test 4: " + r4.length);

        int[] r5 = s.maxSlidingWindow(null, 3);
        System.out.println("Test 5: " + r5.length);
    }
}
