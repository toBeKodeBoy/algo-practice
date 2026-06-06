package algo.y2026.M05.d27;

// 题目 未排序整数数组，找出数字连续的最长序列长度，要求 O(n)
// 示例 nums=[100,4,200,1,3,2] => 4 (序列 1,2,3,4)
// leetcode https://leetcode.cn/problems/longest-consecutive-sequence/
// 算法核心 HashSet 去重，只从序列起点（num-1 不存在）向后扩展
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 数组, 哈希表
// 复盘 2026-05-27 初刷 | 易错 变量更新
// 时段 早地铁
// 类型 新题

import java.util.HashSet;
import java.util.Set;

public class LC_0128_LongestConsecutiveSequence {

    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int maxLen = 0;
        for (int item : set) {
            if (!set.contains(item - 1)) {
                int curLen = 1;
                int curNum = item;
                while (set.contains(curNum + 1)) {
                    curNum++;
                    curLen++;
                }
                maxLen = Math.max(maxLen, curLen);
            }
        }
        return maxLen;
    }
}
