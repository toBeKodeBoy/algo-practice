package algo.y2026.M06.d02;

// 题目 将数组向右轮转 k 个位置，原地操作
// 示例 nums=[1,2,3,4,5,6,7], k=3 => [5,6,7,1,2,3,4]
// leetcode https://leetcode.cn/problems/rotate-array/
// 算法核心 三次反转：整体反转、前 k 反转、后 n-k 反转
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组
// 复盘 2026-06-02 初刷
// 时段 晚地铁
// 类型 新题

public class LC_0189_RotateArray_02 {

    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) return;
        int n = nums.length;
        k = k % n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
