package algo.y2026.M06.d03;

// 题目 找出未出现的最小正整数
// 示例 nums=[1,2,0] => 3, nums=[3,4,-1,1] => 2
// leetcode https://leetcode.cn/problems/first-missing-positive/
// 算法核心 暴力：从 1 开始逐个查找是否存在
// 时间 O(n^2) 空间 O(1)
// 难度 Hard
// 标签 数组
// 复盘 2026-06-03 初刷（暴力版，待优化为 O(n) 原地哈希）
// 时段 晚地铁
// 类型 新题

public class LC_0041_FirstMissingPositive {

    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;
        int n = nums.length;
        for (int target = 1; target <= n + 1; target++) {
            boolean found = false;
            for (int j = 0; j < n; j++) {
                if (nums[j] == target) {
                    found = true;
                    break;
                }
            }
            if (!found) return target;
        }
        return 1;
    }
}
