package algo.y2026.M06.d04;

// 题目 给定字符串 s，找出不含重复字符的最长子串长度
// 示例 s="abcabcbb" => 3 ("abc")
// leetcode https://leetcode.cn/problems/longest-substring-without-repeating-characters/
// 算法核心 滑动窗口 [left,right)，哈希集合记录窗口内字符
// 时间 O(n) 空间 O(min(n,|Σ|))
// 难度 Medium
// 标签 哈希表, 滑动窗口
// 复盘 2026-06-04 初刷 | 易错 String遍历
// 时段 早地铁
// 类型 新题

import java.util.HashSet;
import java.util.Set;

public class LC_0003_LongestSubstringWithoutRepeating_01 {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;
        Set<Character> set = new HashSet<>();
        int left = 0, max = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(c);
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}
