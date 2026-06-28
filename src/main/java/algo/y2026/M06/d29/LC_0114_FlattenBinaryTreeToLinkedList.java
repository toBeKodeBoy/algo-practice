package algo.y2026.M06.d29;

import algo.util.TreeNode;

// 题目 二叉树展开为链表
// 示例 root=[1,2,5,3,4,null,6] => [1,null,2,null,3,null,4,null,5,null,6]
// leetcode https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
// 算法核心 前序 DFS：先递归展开左右子树，再将左子树最右节点接到右子树
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, DFS, 链表
// 时段 早地铁
// 类型 新题

public class LC_0114_FlattenBinaryTreeToLinkedList {

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.left);
        flatten(root.right);

        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = null;
        root.right = left;

        TreeNode curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        curr.right = right;
    }

    public static void main(String[] args) {
        LC_0114_FlattenBinaryTreeToLinkedList s = new LC_0114_FlattenBinaryTreeToLinkedList();
        TreeNode root = TreeNode.fromArray(new Integer[]{1, 2, 5, 3, 4, null, 6});
        s.flatten(root);
        System.out.println(java.util.Arrays.toString(TreeNode.toArray(root)));
    }
}
