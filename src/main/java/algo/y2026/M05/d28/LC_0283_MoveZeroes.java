package algo.y2026.M05.d28;

// 题目 将数组中的 0 移动到末尾，保持非零元素相对顺序，原地操作
// 示例 nums=[0,1,0,3,12] => [1,3,12,0,0]
// leetcode https://leetcode.cn/problems/move-zeroes/
// 算法核心 双指针交换，slow 指向待写入位置，fast 遍历非零元素
// 时间 O(n) 空间 O(1)
// 难度 Easy
// 标签 数组, 双指针
// 复盘 2026-05-28 初刷
// 时段 早地铁
// 类型 新题

public class LC_0283_MoveZeroes {

    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;
                slow++;
            }
        }
    }
}
