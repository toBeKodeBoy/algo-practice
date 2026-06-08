package algo.y2026.M06.d13;

// 题目 接雨水，给定柱子高度数组，计算能接多少雨水
// 示例 [0,1,0,2,1,0,1,3,2,1,2,1] => 6
// leetcode https://leetcode.cn/problems/trapping-rain-water/
// 算法核心 双指针，维护左右最大高度，矮的一侧决定能接水量
// 时间 O(n) 空间 O(1)
// 难度 Hard
// 标签 数组, 双指针, 单调栈
// 时段 晚地铁
// 类型 新题

public class LC_0042_TrappingRainWater {

    public int trap(int[] height) {
        if (height == null || height.length < 3) return 0;

        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int total = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    total += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    total += rightMax - height[right];
                }
                right--;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        LC_0042_TrappingRainWater s = new LC_0042_TrappingRainWater();

        System.out.println("Test 1: " + s.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println("Test 2: " + s.trap(new int[]{4, 2, 0, 3, 2, 5}));
        System.out.println("Test 3: " + s.trap(new int[]{1, 2, 3}));
        System.out.println("Test 4: " + s.trap(new int[]{3, 2, 1}));
        System.out.println("Test 5: " + s.trap(new int[]{}));
        System.out.println("Test 6: " + s.trap(null));
    }
}
