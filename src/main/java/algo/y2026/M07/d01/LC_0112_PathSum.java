package algo.y2026.M07.d01;

import algo.util.TreeNode;

// 题目 路径总和
// 示例 root=[5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum=22 => true
// leetcode https://leetcode.cn/problems/path-sum/
// 算法核心 DFS 递归，targetSum -= node.val，到叶子且剩余为 0 则命中
// 时间 O(n) 空间 O(n)
// 难度 Easy
// 标签 二叉树, DFS
// 时段 早地铁
// 类型 新题

public class LC_0112_PathSum {

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        int remain = targetSum - root.val;
        return hasPathSum(root.left, remain) || hasPathSum(root.right, remain);
    }

    public static void main(String[] args) {
        LC_0112_PathSum s = new LC_0112_PathSum();
        TreeNode root = TreeNode.fromArray(new Integer[]{
                5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1});
        System.out.println(s.hasPathSum(root, 22));
    }
}
