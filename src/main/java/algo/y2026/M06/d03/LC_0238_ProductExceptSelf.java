package algo.y2026.M06.d03;

// 题目 返回数组 answer，answer[i] 等于 nums 中除 nums[i] 外其余元素乘积，O(n) 且不用除法
// 示例 nums=[1,2,3,4] => [24,12,8,6]
// leetcode https://leetcode.cn/problems/product-of-array-except-self/
// 算法核心 两遍遍历：左→右累乘左前缀，右→左累乘右后缀
// 执行过程 nums=[1,2,3,4]
//   左→右: answer=[1,1,2,6] (answer[i]=左边所有数乘积)
//      i=0: answer[0]=1
//      i=1: answer[1]=1*1=1
//      i=2: answer[2]=1*2=2
//      i=3: answer[3]=2*3=6
//   右→左: r=1
//      j=3: answer[3]=6*1=6   r=1*4=4
//      j=2: answer[2]=2*4=8   r=4*3=12
//      j=1: answer[1]=1*12=12 r=12*2=24
//      j=0: answer[0]=1*24=24 r=24*1=24
//   result=[24,12,8,6] ✔
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 前缀和
// Java语法实现
//   - answer[i] 先存左乘积，复用 answer 数组省空间
//   - int r 从右向左滚动更新，每次乘完 answer[j] 后再更新 r
//   - 判空: nums==null || length==0 返回 new int[0]
// 复盘 2026-06-03 初刷，2026-06-10 R4 复习
// 时段 早地铁
// 类型 复习

public class LC_0238_ProductExceptSelf {

    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];
        int len = nums.length;
        int[] answer = new int[len];
        answer[0] = 1;
        for (int i = 1; i < len; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }
        int r = 1;
        for (int j = len - 1; j >= 0; j--) {
            answer[j] = answer[j] * r;
            r = r * nums[j];
        }
        return answer;
    }
}
