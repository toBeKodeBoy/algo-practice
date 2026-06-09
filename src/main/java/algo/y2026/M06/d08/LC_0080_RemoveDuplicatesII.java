package algo.y2026.M06.d08;

// 题目 删除有序数组中的重复项 II，每个元素最多出现两次
// 示例 [1,1,1,2,2,3] => 5, [1,1,2,2,3]
// leetcode https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/description/
// 算法核心 快慢指针 + 计数器，允许重复最多 2 次
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 快慢指针
// 时段 早地铁
// 类型 新题

public class LC_0080_RemoveDuplicatesII {

    /**
     * 删除有序数组中的重复项，使每个元素最多出现两次。
     * <p>
     * 使用快慢指针 + 计数器策略：慢指针 {@code i} 指向已处理区域的最后一个有效位置，
     * 快指针 {@code j} 遍历整个数组；计数器 {@code count} 记录当前元素连续出现的次数，
     * 仅当重复次数不超过2时保留该元素。
     *
     * @param nums 已按非递减排序的整数数组，原地修改
     * @return 去重后数组的有效长度（前 {@code 返回值} 个元素为有效结果）
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int i = 0;
        int count = 1;

        for (int j = 1; j < nums.length; j++) {
            if (nums[j] == nums[i]) {
                count++;
                /* 重复元素不超过2次时保留 */
                if (count <= 2) {
                    i++;
                    nums[i] = nums[j];
                }
            } else {
                /* 遇到新元素，重置计数器并保留 */
                i++;
                nums[i] = nums[j];
                count = 1;
            }
        }
        return i + 1;
    }

    public static void main(String[] args) {
        LC_0080_RemoveDuplicatesII s = new LC_0080_RemoveDuplicatesII();

        int[] nums1 = {1, 1, 1, 2, 2, 3};
        int len1 = s.removeDuplicates(nums1);
        System.out.print("Test 1: len=" + len1 + " [");
        for (int i = 0; i < len1; i++) System.out.print(nums1[i] + (i < len1 - 1 ? "," : ""));
        System.out.println("]");

        int[] nums2 = {0, 0, 1, 1, 1, 1, 2, 3, 3};
        int len2 = s.removeDuplicates(nums2);
        System.out.print("Test 2: len=" + len2 + " [");
        for (int i = 0; i < len2; i++) System.out.print(nums2[i] + (i < len2 - 1 ? "," : ""));
        System.out.println("]");

        int[] nums3 = {1, 1, 1};
        int len3 = s.removeDuplicates(nums3);
        System.out.print("Test 3: len=" + len3 + " [");
        for (int i = 0; i < len3; i++) System.out.print(nums3[i] + (i < len3 - 1 ? "," : ""));
        System.out.println("]");

        System.out.println("Test 4 (null): len=" + s.removeDuplicates(null));
        System.out.println("Test 5 (empty): len=" + s.removeDuplicates(new int[]{}));
    }
}

// 复盘1 2026-06-08 13:30
