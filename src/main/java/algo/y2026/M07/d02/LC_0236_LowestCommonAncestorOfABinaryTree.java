package algo.y2026.M07.d02;

import algo.util.TreeNode;

// 题目 二叉树的最近公共祖先
// 示例 root=[3,5,1,6,2,0,8,null,null,7,4], p=5, q=1 => 3
// leetcode https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
// 算法核心 后序 DFS：左右子树各返回找到的节点；若左右各找到一个则当前为 LCA
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, DFS
// 时段 早地铁
// 类型 新题

public class LC_0236_LowestCommonAncestorOfABinaryTree {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        LC_0236_LowestCommonAncestorOfABinaryTree s = new LC_0236_LowestCommonAncestorOfABinaryTree();
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p = findNode(root, 5);
        TreeNode q = findNode(root, 1);
        System.out.println(s.lowestCommonAncestor(root, p, q).val);
    }

    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }
        TreeNode left = findNode(root.left, val);
        return left != null ? left : findNode(root.right, val);
    }
}
