package algo.y2026.M06.d30;

import algo.util.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

// 题目 二叉搜索树中第 K 小的元素
// 示例 root=[3,1,4,null,2], k=1 => 1
// leetcode https://leetcode.cn/problems/kth-smallest-element-in-a-bst/
// 算法核心 中序遍历 BST 得到升序序列，数到第 k 个即答案
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, BST, 中序遍历
// 时段 早地铁
// 类型 新题

public class LC_0230_KthSmallestElementInABst {

    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (--k == 0) {
                return curr.val;
            }
            curr = curr.right;
        }
        return -1;
    }

    public static void main(String[] args) {
        LC_0230_KthSmallestElementInABst s = new LC_0230_KthSmallestElementInABst();
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 1, 4, null, 2});
        System.out.println(s.kthSmallest(root, 1));
    }
}
