package algo.y2026.M06.d03;

// 题目 找出未出现的最小正整数
// 示例 nums=[1,2,0] => 3, nums=[3,4,-1,1] => 2
// leetcode https://leetcode.cn/problems/first-missing-positive/
// 算法核心 原地哈希，将数放到正确位置 nums[i]-1，再扫描找缺失
// 执行过程 nums=[3,4,-1,1]
//   i=0: nums[0]=3 → swap 到 idx=2 → [-1,4,3,1]
//   i=0: nums[0]=-1<1 → 跳过
//   i=1: nums[1]=4 → swap 到 idx=3 → [-1,1,3,4]
//   i=1: nums[1]=1 → swap 到 idx=0 → [1,-1,3,4]
//   i=1: nums[1]=-1<1 → 跳过
//   i=2: nums[2]=3 → 已在 idx=2 → 跳过
//   i=3: nums[3]=4 → 已在 idx=3 → 跳过
//   二次扫描: nums[0]=1✔ nums[1]=-1≠2 → return 2 ✔
// 时间 O(n) 空间 O(1)
// 难度 Hard
// 标签 数组, 原地哈希
// Java语法实现
//   - while 内用 swap，当前位换完后继续检查（不 i++）直到值不符条件
//   - swap 条件: 值在 [1, n] 且 目标位 nums[val-1] != val
//   - 二次扫描找第一个 nums[i] != i+1
// 复盘 2026-06-03 初刷（暴力版），2026-06-10 复习（原地哈希）
// 时段 晚地铁
// 类型 复习

public class LC_0041_FirstMissingPositive {

    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] >= 1 && nums[i] <= n
                    && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return n + 1;
    }

    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    // 暴力版（初刷）：O(n^2) 逐个查找
    public int firstMissingPositiveBrute(int[] nums) {
        if (nums == null || nums.length == 0) return 1;
        int n = nums.length;
        for (int target = 1; target <= n + 1; target++) {
            boolean found = false;
            for (int j = 0; j < n; j++) {
                if (nums[j] == target) {
                    found = true;
                    break;
                }
            }
            if (!found) return target;
        }
        return 1;
    }

    public static void main(String[] args) {
        LC_0041_FirstMissingPositive s = new LC_0041_FirstMissingPositive();

        System.out.println("Test 1: " + s.firstMissingPositive(new int[]{1, 2, 0}));
        System.out.println("Test 2: " + s.firstMissingPositive(new int[]{3, 4, -1, 1}));
        System.out.println("Test 3: " + s.firstMissingPositive(new int[]{7, 8, 9, 11, 12}));
        System.out.println("Test 4: " + s.firstMissingPositive(new int[]{}));
        System.out.println("Test 5: " + s.firstMissingPositive(null));

        System.out.println("---Brute---");
        System.out.println("Test 1: " + s.firstMissingPositiveBrute(new int[]{1, 2, 0}));
        System.out.println("Test 2: " + s.firstMissingPositiveBrute(new int[]{3, 4, -1, 1}));
    }
}
