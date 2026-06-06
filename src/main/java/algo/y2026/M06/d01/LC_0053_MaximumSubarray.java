package algo.y2026.M06.d01;

// 题目 找出具有最大和的连续子数组，返回最大和
// 示例 nums=[-2,1,-3,4,-1,2,1,-5,4] => 6
// leetcode https://leetcode.cn/problems/maximum-subarray/
// 算法核心 Kadane：cur 记录以当前元素结尾的最大子数组和，best 全局最大
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 动态规划
// 复盘 2026-06-01 初刷 | 易错 变量更新
// 时段 早地铁
// 类型 新题

public class LC_0053_MaximumSubarray {

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int cur = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            best = Math.max(best, cur);
        }
        return best;
    }
}
