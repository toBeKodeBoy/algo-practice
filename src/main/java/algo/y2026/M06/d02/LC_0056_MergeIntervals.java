package algo.y2026.M06.d02;

// 题目 合并所有重叠的区间
// 示例 intervals=[[1,3],[2,6],[8,10],[15,18]] => [[1,6],[8,10],[15,18]]
// leetcode https://leetcode.cn/problems/merge-intervals/
// 算法核心 按起点排序，与结果集最后一个区间比较，重叠则合并
// 时间 O(n log n) 空间 O(n)
// 难度 Medium
// 标签 数组, 排序
// 复盘 2026-06-02 初刷
// 时段 早地铁
// 类型 新题

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LC_0056_MergeIntervals {

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return new int[0][];
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] last = res.get(res.size() - 1);
            int[] cur = intervals[i];
            if (cur[0] <= last[1]) {
                last[1] = Math.max(last[1], cur[1]);
            } else {
                res.add(cur);
            }
        }
        return res.toArray(new int[0][]);
    }
}
