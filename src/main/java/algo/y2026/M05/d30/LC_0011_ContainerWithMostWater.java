package algo.y2026.M05.d30;

// 题目 给定 n 条垂线高度，找出两条线与 x 轴围成容器可容纳的最大水量
// 示例 height=[1,8,6,2,5,4,8,3,7] => 49
// leetcode https://leetcode.cn/problems/container-with-most-water/
// 算法核心 双指针左右夹逼，每次移动较矮一侧
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 数组, 双指针
// 复盘 2026-05-30 初刷
// 时段 早地铁
// 类型 新题

public class LC_0011_ContainerWithMostWater {

    public int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;
        int left = 0, right = height.length - 1, max = 0;
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            max = Math.max(max, h * (right - left));
            if (height[left] < height[right]) left++;
            else right--;
        }
        return max;
    }
}

// 复盘 2026-06-07
//称最多水的容器
//height=[1,8,6,2,5,4,8,3,7,9]
//
//初始化 left=0 right=9
//第一轮 0<9，h取值较小值，height[0]=1 height[9]=9 1<9 h=1,max=math.max(0,9)=9，left++
//第二轮 1<9,h取值较小值,height[0]=8，height[9]=9 8<9 h=8,max=math.max(9,8*8=64)=64,left++
//第三轮 2<9 h取值较小值 height[2]=6,height[9]=9,6<9 h=6,max=math.max(64,7*6=42)=64,left++
//
//问题1：为什么要用短板
//> 考虑长板，短板 不一致，水最多也就到短板的位置，超过短板的位置，水就溢出来了
//
//问题2：为什么left高度小于right高度，需要左移
//> 我们要求取的最大面积，高度已经取到最大的值，考虑宽度取最大的，右移 right 是纯浪费，不可能找到比当前更大的值，只有移动短板才有可能突破
//public int containerWithMostWater(int[] height){
//    // 鲁棒性验证
//    if(height == null || height.length < 2) return 0;
//
//    // Math.min(height[i],height[j]) * (right - left)
//    int left = 0, right=height.length-1;
//    int max = 0;
//    while(left < right){
//        int h = Math.min(height[left],height[right]);
//        max=Math.max(max,h * (right-left));
//        if(height[left] < height[right]) left ++;
//        else right -- ;
//    }
//
//    // 返回最大区域面积
//    return max;
//}