package algo.y2026.M06.d09;

// 题目 给定已升序数组 numbers，找出两个数使其和等于 target，返回下标+1
// 示例 numbers=[2,7,11,15], target=9 => [1,2]
// leetcode https://leetcode.cn/problems/two-sum-ii-input-array-is-sorted/
// 算法核心 双指针 left/right 向中间逼近，根据 sum 与 target 大小移动
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 双指针, 二分查找
// 时段 早地铁
// 类型 新题

public class LC_0167_TwoSumII {

    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length < 2) return new int[]{-1, -1};

        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        LC_0167_TwoSumII s = new LC_0167_TwoSumII();

        int[] r1 = s.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println("Test 1: [" + r1[0] + "," + r1[1] + "]");

        int[] r2 = s.twoSum(new int[]{2, 3, 4}, 6);
        System.out.println("Test 2: [" + r2[0] + "," + r2[1] + "]");

        int[] r3 = s.twoSum(new int[]{-1, 0}, -1);
        System.out.println("Test 3: [" + r3[0] + "," + r3[1] + "]");

        int[] r4 = s.twoSum(new int[]{}, 1);
        System.out.println("Test 4: [" + r4[0] + "," + r4[1] + "]");

        int[] r5 = s.twoSum(null, 1);
        System.out.println("Test 5: [" + r5[0] + "," + r5[1] + "]");
    }
}
