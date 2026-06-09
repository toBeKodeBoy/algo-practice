package algo.y2026.M06.d10;

// 题目 给定排序数组和目标值，查找插入位置
// 示例 [1,3,5,6], 5 => 2
// leetcode https://leetcode.cn/problems/search-insert-position/
// 算法核心 标准二分查找，返回左边界（即插入位置）
// 时间 O(log n) 空间 O(1)
// 难度 Easy
// 标签 数组, 二分查找
// 时段 早地铁
// 类型 新题

public class LC_0035_SearchInsertPosition {

    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;

        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        LC_0035_SearchInsertPosition s = new LC_0035_SearchInsertPosition();

        System.out.println("Test 1: " + s.searchInsert(new int[]{1, 3, 5, 6}, 5));
        System.out.println("Test 2: " + s.searchInsert(new int[]{1, 3, 5, 6}, 2));
        System.out.println("Test 3: " + s.searchInsert(new int[]{1, 3, 5, 6}, 7));
        System.out.println("Test 4: " + s.searchInsert(new int[]{1, 3, 5, 6}, 0));
        System.out.println("Test 5: " + s.searchInsert(new int[]{}, 5));
        System.out.println("Test 6: " + s.searchInsert(null, 5));
    }
}
