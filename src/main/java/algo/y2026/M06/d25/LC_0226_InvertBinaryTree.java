package algo.y2026.M06.d25;

import algo.util.TreeNode;

// 题目 翻转二叉树
// 示例 root=[4,2,7,1,3,6,9] => [4,7,2,9,6,3,1]
// leetcode https://leetcode.cn/problems/invert-binary-tree/
// 算法核心 递归交换左右子树
// 时间 O(n) 空间 O(n)
// 难度 Easy
// 标签 二叉树, DFS
// 时段 早地铁
// 类型 新题

public class LC_0226_InvertBinaryTree {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.right);
        TreeNode right = invertTree(root.left);
        root.left = left;
        root.right = right;
        return root;
    }

    public static void main(String[] args) {
        LC_0226_InvertBinaryTree s = new LC_0226_InvertBinaryTree();
        TreeNode root = TreeNode.fromArray(new Integer[]{4, 2, 7, 1, 3, 6, 9});
        System.out.println(java.util.Arrays.toString(TreeNode.toArray(s.invertTree(root))));
    }
}
