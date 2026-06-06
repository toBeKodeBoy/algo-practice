package algo.y2026.M06.d04;

// 题目 删除有序数组中的重复项，返回去重后长度

// 示例 [1,1,2] => 2

// leetcode https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/

// 算法核心 慢指针新数组最后一位，快指针指向新数字，找到和慢指针不一致数字，慢指针往前走，把新数字存入

// 时间 n 空间 1

// 难度 Easy

// 标签 数组, 快慢指针
// 复盘 2026-06-04 初刷
// 时段 晚地铁
// 类型 新题

public class LC_0026_RemoveDuplicates {
    public int removeDuplicates(int[] nums){
        // 边界判断
        if(nums == null || nums.length == 0) return 0 ;

        // 慢指针 从0开始
        int i = 0;
        // 快指针从j遍历数组
        for(int j = 1; j<nums.length ; j++){
            // 找到不一样的数字
            if(nums[j] != nums[i]){
                i++;
                nums[i]=nums[j];
            }
        }
        return i+1;
    }

    public static void main(String[] args) {
        LC_0026_RemoveDuplicates solution = new LC_0026_RemoveDuplicates();

        int[] nums1 = {1, 1, 2};
        int len1 = solution.removeDuplicates(nums1);
        System.out.print("Test 1: len=" + len1 + " [");
        for (int i = 0; i < len1; i++) System.out.print(nums1[i] + (i < len1 - 1 ? "," : ""));
        System.out.println("]");

        int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int len2 = solution.removeDuplicates(nums2);
        System.out.print("Test 2: len=" + len2 + " [");
        for (int i = 0; i < len2; i++) System.out.print(nums2[i] + (i < len2 - 1 ? "," : ""));
        System.out.println("]");

        System.out.println("Test 3 (empty): len=" + solution.removeDuplicates(new int[]{}));
        System.out.println("Test 4 (null): len=" + solution.removeDuplicates(null));
    }
}
