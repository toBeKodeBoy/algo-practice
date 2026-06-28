package algo.y2026.M06.d24;

import algo.util.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// 题目 二叉树的中序遍历
// 示例 root=[1,null,2,3] => [1,3,2]
// leetcode https://leetcode.cn/problems/binary-tree-inorder-traversal/
// 算法核心 迭代栈模拟：先深入左子树，出栈访问，再进入右子树
// 时间 O(n) 空间 O(n)
// 难度 Easy
// 标签 二叉树, 遍历, 栈
// 时段 早地铁
// 类型 新题

public class LC_0094_BinaryTreeInorderTraversal {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }

    public static void main(String[] args) {
        LC_0094_BinaryTreeInorderTraversal s = new LC_0094_BinaryTreeInorderTraversal();
        TreeNode root = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        System.out.println(s.inorderTraversal(root));
    }
}
