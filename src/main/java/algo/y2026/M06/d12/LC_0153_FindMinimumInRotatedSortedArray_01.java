package algo.y2026.M06.d12;

// 题目 寻找旋转排序数组中的最小值（无重复）
// 示例 [3,4,5,1,2] => 1
// leetcode https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/
// 算法核心 二分，比较 mid 与 right 判断最小值在左/右半
// 时间 O(log n) 空间 O(1)
// 难度 Medium
// 标签 数组, 二分查找
// 时段 早地铁
// 类型 新题

public class LC_0153_FindMinimumInRotatedSortedArray_01 {

    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) return -1;

        int left = 0, right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
        LC_0153_FindMinimumInRotatedSortedArray_01 s = new LC_0153_FindMinimumInRotatedSortedArray_01();

        System.out.println("Test 1: " + s.findMin(new int[]{3, 4, 5, 1, 2}));
        System.out.println("Test 2: " + s.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        System.out.println("Test 3: " + s.findMin(new int[]{11, 13, 15, 17}));
        System.out.println("Test 4: " + s.findMin(new int[]{1}));
        System.out.println("Test 5: " + s.findMin(new int[]{2, 1}));
    }
}
