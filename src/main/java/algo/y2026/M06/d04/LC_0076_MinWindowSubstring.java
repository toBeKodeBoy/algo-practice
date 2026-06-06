package algo.y2026.M06.d04;

// 题目  给你一个字符串 s 和一个字符串 t ，返回 s 中涵盖 t 所有字符的最小子串。
// 示例  s="ADOBECODEBANC", t="ABC" => "BANC"
// leetcode https://leetcode.cn/problems/minimum-window-substring/
// 算法核心 滑动窗口 + 频率计算 用int[128] 记录t的字符需求 need标识当前还缺少多少种字符，窗口内满足need==0收缩left找最短
// 时间 n 空间 1
// 难度 Medium
// 标签 滑动窗口
// 复盘 2026-06-04 初刷 | 易错 滑动窗口
// 时段 早地铁
// 类型 新题
public class LC_0076_MinWindowSubstring {
    public String minWindow(String s, String t) {
        // 任一为空或 s 长度不足 t，直接返回 ""
        if (s == null || t == null || s.length() < t.length()) return "";
        // freq 记录 t 中每个字符还需要的次数（负数为多余）
        int[] freq = new int[128];
        for (char c : t.toCharArray()) freq[c]++;
        // left: 窗口左边界, need: t 中还未被覆盖的字符种类数
        int left = 0, need = t.length();
        // start: 最优解起点, minLen: 最优解长度
        int start = 0, minLen = Integer.MAX_VALUE;

        // right 向右扩展窗口
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // freq[c] > 0 表示这个字符是 t 中需要的
            if (freq[c] > 0) need--;
            // 将 s[right] 纳入窗口（标记为已使用）
            freq[c]--;

            // 窗口已覆盖 t 的全部字符，尝试收缩 left 找更短解
            while (need == 0) {
                // 当前窗口比已记录的最短还短，则更新
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }
                // s[left] 移出窗口，恢复其计数
                char lc = s.charAt(left);
                freq[lc]++;
                // 恢复后 > 0 说明移出的字符是 t 所需要的
                if (freq[lc] > 0) need++;
                left++;
            }
        }
        // 未找到覆盖子串返回 ""，否则截取最短结果
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}

