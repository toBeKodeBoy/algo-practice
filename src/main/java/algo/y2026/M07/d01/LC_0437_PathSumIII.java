package algo.y2026.M07.d01;

import algo.util.TreeNode;

import java.util.HashMap;
import java.util.Map;

// 题目 路径总和 III
// 示例 root=[10,5,-3,3,2,null,11,3,-2,null,1], targetSum=8 => 3
// leetcode https://leetcode.cn/problems/path-sum-iii/
// 算法核心 DFS + 前缀和 HashMap：统计当前路径上满足条件的子路径数
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, DFS, 前缀和, 哈希表
// 时段 晚地铁
// 类型 新题

public class LC_0437_PathSumIII {

    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefix = new HashMap<>();
        prefix.put(0L, 1);
        return dfs(root, 0L, targetSum, prefix);
    }

    private int dfs(TreeNode node, long curr, int target, Map<Long, Integer> prefix) {
        if (node == null) {
            return 0;
        }
        curr += node.val;
        int count = prefix.getOrDefault(curr - target, 0);
        prefix.merge(curr, 1, Integer::sum);
        count += dfs(node.left, curr, target, prefix);
        count += dfs(node.right, curr, target, prefix);
        prefix.merge(curr, -1, Integer::sum);
        return count;
    }

    public static void main(String[] args) {
        LC_0437_PathSumIII s = new LC_0437_PathSumIII();
        TreeNode root = TreeNode.fromArray(new Integer[]{
                10, 5, -3, 3, 2, null, 11, 3, -2, null, 1});
        System.out.println(s.pathSum(root, 8));
    }
}
