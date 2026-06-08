package algo.y2026.M06.d13;

// 题目 寻找两个正序数组的中位数，要求 O(log(m+n))
// 示例 nums1=[1,3], nums2=[2] => 2.0
// leetcode https://leetcode.cn/problems/median-of-two-sorted-arrays/
// 算法核心 二分法划分短数组，使左右两部分元素数相等且满足交叉 ≤ 条件
// 时间 O(log(min(m,n))) 空间 O(1)
// 难度 Hard
// 标签 数组, 二分查找, 分治
// 时段 早地铁
// 类型 新题

public class LC_0004_MedianOfTwoSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) return 0.0;

        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;
        int totalLeft = (m + n + 1) / 2;

        while (left <= right) {
            int i = left + (right - left) / 2;
            int j = totalLeft - i;

            int nums1LeftMax = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                if ((m + n) % 2 == 1) {
                    return Math.max(nums1LeftMax, nums2LeftMax);
                } else {
                    return (Math.max(nums1LeftMax, nums2LeftMax)
                        + Math.min(nums1RightMin, nums2RightMin)) / 2.0;
                }
            } else if (nums1LeftMax > nums2RightMin) {
                right = i - 1;
            } else {
                left = i + 1;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        LC_0004_MedianOfTwoSortedArrays s = new LC_0004_MedianOfTwoSortedArrays();

        System.out.println("Test 1: " + s.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println("Test 2: " + s.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
        System.out.println("Test 3: " + s.findMedianSortedArrays(new int[]{0, 0}, new int[]{0, 0}));
        System.out.println("Test 4: " + s.findMedianSortedArrays(new int[]{}, new int[]{1}));
        System.out.println("Test 5: " + s.findMedianSortedArrays(new int[]{2}, new int[]{}));
    }
}
