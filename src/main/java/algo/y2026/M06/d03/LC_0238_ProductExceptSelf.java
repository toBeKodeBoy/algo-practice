package algo.y2026.M06.d03;

// 题目 返回数组 answer，answer[i] 等于 nums 中除 nums[i] 外其余元素乘积，O(n) 且不用除法
// 示例 nums=[1,2,3,4] => [24,12,8,6]
// leetcode https://leetcode.cn/problems/product-of-array-except-self/
// 算法核心 先存左乘积，再从右向左滚动乘右乘积
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 前缀和
// 复盘 2026-06-03 初刷
// 时段 早地铁
// 类型 新题

public class LC_0238_ProductExceptSelf {

    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];
        int len = nums.length;
        int[] answer = new int[len];
        answer[0] = 1;
        for (int i = 1; i < len; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }
        int r = 1;
        for (int j = len - 1; j >= 0; j--) {
            answer[j] = answer[j] * r;
            r = r * nums[j];
        }
        return answer;
    }
}
