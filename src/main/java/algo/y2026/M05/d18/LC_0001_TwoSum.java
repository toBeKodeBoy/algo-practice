package algo.y2026.M05.d18;

// 题目 给定整数数组 nums 和目标值 target，找出和为 target 的两个元素下标
// 示例 nums=[2,7,11,15], target=9 => [0,1]
// leetcode https://leetcode.cn/problems/two-sum/
// 算法核心 双层 for 枚举两数之和，内层 j 从 i+1 开始避免重复
// 时间 O(n^2) 空间 O(1)
// 难度 Easy
// 标签 数组, 哈希表
// 复盘 2026-05-18 初刷 | 易错 边界错
// 时段 早地铁
// 类型 新题

public class LC_0001_TwoSum {

    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[0];
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}

//复习1:2026-06-06 22:10-22:30
//算法核心：遍历数组，如果target-current 再hash结构有没有，如果没有添加元素，如果有直接返回
//public int[] twoSum(int[] nums,int target){
//    // 数组鲁棒性验证
//    if(nums == null || nums.length == 0) return new int[0] ;
//
//    // 两数之和为target，第一个数为遍历的每个数字，第二个数字是hash结构
//    // 如果遍历的时候存在target-current 说明找到结果了
//    // 如果不存在，current添加到hash结构
//    Map<Integer,Integer> map = new HashMap<>();
//    for(int i=0;i<nums.length;i++){
//        if(map.containsKey(target-nums[i])){
//            return new int[]{i,map.get(target-nums[i])};
//        }
//        map.put(nums[i],i);
//    }
//
//    // 返回最终结果
//    return new int[0];
//}