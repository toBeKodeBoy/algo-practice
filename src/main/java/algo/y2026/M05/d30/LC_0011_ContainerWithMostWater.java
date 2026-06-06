package algo.y2026.M05.d30;

// 题目 给定 n 条垂线高度，找出两条线与 x 轴围成容器可容纳的最大水量
// 示例 height=[1,8,6,2,5,4,8,3,7] => 49
// leetcode https://leetcode.cn/problems/container-with-most-water/
// 算法核心 双指针左右夹逼，每次移动较矮一侧
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 双指针
// 复盘 2026-05-30 初刷
// 时段 早地铁
// 类型 新题

public class LC_0011_ContainerWithMostWater {

    public int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;
        int left = 0, right = height.length - 1, max = 0;
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            max = Math.max(max, h * (right - left));
            if (height[left] < height[right]) left++;
            else right--;
        }
        return max;
    }
}
