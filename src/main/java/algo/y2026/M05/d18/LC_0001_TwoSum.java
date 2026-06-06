package algo.y2026.M05.d18;

// 题目 给定整数数组 nums 和目标值 target，找出和为 target 的两个元素下标
// 示例 nums=[2,7,11,15], target=9 => [0,1]
// leetcode https://leetcode.cn/problems/two-sum/
// 算法核心 双层 for 枚举两数之和，内层 j 从 i+1 开始避免重复
// 时间 O(n^2) 空间 O(1)
// 难度 Easy
// 标签 数组, 哈希表
// 复盘 2026-05-18 初刷 | 易错 边界错
// 时段 早地铁
// 类型 新题

public class LC_0001_TwoSum {

    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[0];
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
