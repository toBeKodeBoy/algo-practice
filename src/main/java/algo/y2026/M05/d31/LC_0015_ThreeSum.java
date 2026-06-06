package algo.y2026.M05.d31;

// 题目 找出数组中所有和为 0 且不重复的三元组
// 示例 nums=[-1,0,1,2,-1,-4] => [[-1,-1,2],[-1,0,1]]
// leetcode https://leetcode.cn/problems/3sum/
// 算法核心 排序后固定 i，双指针 l/r 找两数之和为 -nums[i]，跳过重复
// 时间 O(n^2) 空间 O(1)
// 难度 Medium
// 标签 数组, 双指针, 排序
// 复盘 2026-05-31 初刷
// 时段 早地铁
// 类型 新题

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC_0015_ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) return res;
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int l = i + 1, r = n - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    while (l < r && nums[l] == nums[l + 1]) l++;
                    while (l < r && nums[r] == nums[r - 1]) r--;
                    l++;
                    r--;
                } else if (sum < 0) l++;
                else r--;
            }
        }
        return res;
    }
}
