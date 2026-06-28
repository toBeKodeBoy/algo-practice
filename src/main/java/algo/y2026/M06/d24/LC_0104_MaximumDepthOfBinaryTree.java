package algo.y2026.M06.d24;

import algo.util.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

// 题目 二叉树的最大深度
// 示例 root=[3,9,20,null,null,15,7] => 3
// leetcode https://leetcode.cn/problems/maximum-depth-of-binary-tree/
// 算法核心 BFS 层序遍历统计层数
// 时间 O(n) 空间 O(n)
// 难度 Easy
// 标签 二叉树, DFS, BFS
// 时段 晚地铁
// 类型 新题

public class LC_0104_MaximumDepthOfBinaryTree {

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
        }
        return depth;
    }

    public static void main(String[] args) {
        LC_0104_MaximumDepthOfBinaryTree s = new LC_0104_MaximumDepthOfBinaryTree();
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 9, 20, null, null, 15, 7});
        System.out.println(s.maxDepth(root));
    }
}
