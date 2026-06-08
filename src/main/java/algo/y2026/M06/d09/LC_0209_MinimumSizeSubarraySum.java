package algo.y2026.M06.d09;

// 题目 给定正整数数组 nums 和正整数 target，找出满足和 ≥ target 的最短连续子数组长度
// 示例 target=7, nums=[2,3,1,2,4,3] => 2 ([4,3])
// leetcode https://leetcode.cn/problems/minimum-size-subarray-sum/
// 算法核心 滑动窗口，右指针扩展直至 sum≥target，左指针收缩找最短
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 滑动窗口
// 时段 晚地铁
// 类型 新题

public class LC_0209_MinimumSizeSubarraySum {

    public int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int left = 0, sum = 0, minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        LC_0209_MinimumSizeSubarraySum s = new LC_0209_MinimumSizeSubarraySum();

        System.out.println("Test 1: " + s.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
        System.out.println("Test 2: " + s.minSubArrayLen(4, new int[]{1, 4, 4}));
        System.out.println("Test 3: " + s.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1}));
        System.out.println("Test 4: " + s.minSubArrayLen(7, new int[]{}));
        System.out.println("Test 5: " + s.minSubArrayLen(7, null));
    }
}
