package algo.y2026.M06.d11;

// 题目 搜索旋转排序数组（无重复）
// 示例 [4,5,6,7,0,1,2], 0 => 4
// leetcode https://leetcode.cn/problems/search-in-rotated-sorted-array/
// 算法核心 二分，判断 mid 落在左/右有序区间，在有序区间内判断 target 位置
// 时间 O(log n) 空间 O(1)
// 难度 Medium
// 标签 数组, 二分查找
// 时段 晚地铁
// 类型 新题

public class LC_0033_SearchInRotatedSortedArray {

    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) return mid;

            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        LC_0033_SearchInRotatedSortedArray s = new LC_0033_SearchInRotatedSortedArray();

        System.out.println("Test 1: " + s.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        System.out.println("Test 2: " + s.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
        System.out.println("Test 3: " + s.search(new int[]{1}, 0));
        System.out.println("Test 4: " + s.search(new int[]{1}, 1));
        System.out.println("Test 5: " + s.search(new int[]{}, 5));
        System.out.println("Test 6: " + s.search(null, 5));
    }
}
