package algo.y2026.M06.d11;

// 题目 在排序数组中查找目标值的第一个和最后一个位置
// 示例 [5,7,7,8,8,10], 8 => [3,4]
// leetcode https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/
// 算法核心 两次二分：找第一个 ≥target 的左边界，找第一个 >target 的右边界-1
// 时间 O(log n) 空间 O(1)
// 难度 Medium
// 标签 数组, 二分查找
// 时段 早地铁
// 类型 新题

public class LC_0034_FindFirstAndLastPositionOfElementInSortedArray_01 {

    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};

        int first = findBound(nums, target, true);
        if (first == -1) return new int[]{-1, -1};
        int last = findBound(nums, target, false);
        return new int[]{first, last};
    }

    private int findBound(int[] nums, int target, boolean isFirst) {
        int left = 0, right = nums.length - 1;
        int bound = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                bound = mid;
                if (isFirst) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return bound;
    }

    public static void main(String[] args) {
        LC_0034_FindFirstAndLastPositionOfElementInSortedArray_01 s = new LC_0034_FindFirstAndLastPositionOfElementInSortedArray_01();

        int[] r1 = s.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println("Test 1: [" + r1[0] + "," + r1[1] + "]");

        int[] r2 = s.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6);
        System.out.println("Test 2: [" + r2[0] + "," + r2[1] + "]");

        int[] r3 = s.searchRange(new int[]{}, 0);
        System.out.println("Test 3: [" + r3[0] + "," + r3[1] + "]");

        int[] r4 = s.searchRange(new int[]{1}, 1);
        System.out.println("Test 4: [" + r4[0] + "," + r4[1] + "]");

        int[] r5 = s.searchRange(null, 1);
        System.out.println("Test 5: [" + r5[0] + "," + r5[1] + "]");
    }
}
