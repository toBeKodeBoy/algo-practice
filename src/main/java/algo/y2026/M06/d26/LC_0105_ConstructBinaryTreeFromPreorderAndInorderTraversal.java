package algo.y2026.M06.d26;

import algo.util.TreeNode;

import java.util.HashMap;
import java.util.Map;

// 题目 从前序与中序遍历序列构造二叉树
// 示例 preorder=[3,9,20,15,7], inorder=[9,3,15,20,7]
// leetcode https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
// 算法核心 前序首元素为根，在中序中定位根划分左右子树，递归构建
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, 分治, 哈希表
// 时段 晚地铁
// 类型 新题

public class LC_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> idxMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            idxMap.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1, idxMap);
    }

    private TreeNode build(int[] pre, int pL, int pR,
                           int[] in, int iL, int iR,
                           Map<Integer, Integer> map) {
        if (pL > pR || iL > iR) {
            return null;
        }

        int rootVal = pre[pL];
        TreeNode root = new TreeNode(rootVal);
        int rootIdx = map.get(rootVal);
        int leftSize = rootIdx - iL;

        root.left = build(pre, pL + 1, pL + leftSize,
                in, iL, rootIdx - 1, map);
        root.right = build(pre, pL + leftSize + 1, pR,
                in, rootIdx + 1, iR, map);
        return root;
    }

    public static void main(String[] args) {
        LC_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal s =
                new LC_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal();
        TreeNode root = s.buildTree(
                new int[]{3, 9, 20, 15, 7},
                new int[]{9, 3, 15, 20, 7});
        System.out.println(java.util.Arrays.toString(TreeNode.toArray(root)));
    }
}
