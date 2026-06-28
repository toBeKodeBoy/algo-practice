package algo.y2026.M06.d26;

import algo.util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 题目 二叉树的层序遍历
// 示例 root=[3,9,20,null,null,15,7] => [[3],[9,20],[15,7]]
// leetcode https://leetcode.cn/problems/binary-tree-level-order-traversal/
// 算法核心 BFS 队列：每轮处理当前层所有节点
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, BFS, 队列
// 时段 早地铁
// 类型 新题

public class LC_0102_BinaryTreeLevelOrderTraversal {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            res.add(level);
        }
        return res;
    }

    public static void main(String[] args) {
        LC_0102_BinaryTreeLevelOrderTraversal s = new LC_0102_BinaryTreeLevelOrderTraversal();
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 9, 20, null, null, 15, 7});
        System.out.println(s.levelOrder(root));
    }
}
