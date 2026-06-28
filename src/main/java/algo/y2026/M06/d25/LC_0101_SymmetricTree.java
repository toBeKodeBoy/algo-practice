package algo.y2026.M06.d25;

import algo.util.TreeNode;

// 题目 对称二叉树
// 示例 root=[1,2,2,3,4,4,3] => true
// leetcode https://leetcode.cn/problems/symmetric-tree/
// 算法核心 递归判断左右子树是否镜像对称
// 时间 O(n) 空间 O(n)
// 难度 Easy
// 标签 二叉树, DFS, BFS
// 时段 晚地铁
// 类型 新题

public class LC_0101_SymmetricTree {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        return t1.val == t2.val
                && isMirror(t1.left, t2.right)
                && isMirror(t1.right, t2.left);
    }

    public static void main(String[] args) {
        LC_0101_SymmetricTree s = new LC_0101_SymmetricTree();
        TreeNode root = TreeNode.fromArray(new Integer[]{1, 2, 2, 3, 4, 4, 3});
        System.out.println(s.isSymmetric(root));
    }
}
