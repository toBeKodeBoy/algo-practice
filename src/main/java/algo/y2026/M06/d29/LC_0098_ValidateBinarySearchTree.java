package algo.y2026.M06.d29;

import algo.util.TreeNode;

// 题目 验证二叉搜索树
// 示例 root=[2,1,3] => true；root=[5,1,4,null,null,3,6] => false
// leetcode https://leetcode.cn/problems/validate-binary-search-tree/
// 算法核心 递归传上下界 (min, max)，节点值必须在开区间内
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, DFS, BST
// 时段 晚地铁
// 类型 新题

public class LC_0098_ValidateBinarySearchTree {

    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    private boolean validate(TreeNode node, Integer min, Integer max) {
        if (node == null) {
            return true;
        }
        if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
            return false;
        }
        return validate(node.left, min, node.val)
                && validate(node.right, node.val, max);
    }

    public static void main(String[] args) {
        LC_0098_ValidateBinarySearchTree s = new LC_0098_ValidateBinarySearchTree();
        TreeNode root = TreeNode.fromArray(new Integer[]{2, 1, 3});
        System.out.println(s.isValidBST(root));
    }
}
