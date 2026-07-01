package algo.y2026.M07.d01;

import algo.util.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

// 题目 路径总和
// 示例 root=[5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum=22 => true
// leetcode https://leetcode.cn/problems/path-sum/
// 算法核心 BFS 层序遍历，双队列同步存放节点与剩余目标和，到叶子且剩余等于节点值则命中
// 时间 O(n) 空间 O(n)
// 难度 Easy
// 标签 二叉树, BFS
// 时段 早地铁
// 类型 新题

public class LC_0112_PathSum {

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        Deque<TreeNode> nodes = new ArrayDeque<>();
        Deque<Integer> remains = new ArrayDeque<>();
        nodes.offer(root);
        remains.offer(targetSum);

        while (!nodes.isEmpty()) {
            TreeNode node = nodes.poll();
            int remain = remains.poll();
            if (node.left == null && node.right == null) {
                if (remain == node.val) {
                    return true;
                }
                continue;
            }
            // 每经过一个节点，就从剩余目标里减去这个节点的值，继续往下找。
            int next = remain - node.val;
            if (node.left != null) {
                nodes.offer(node.left);
                remains.offer(next);
            }
            if (node.right != null) {
                nodes.offer(node.right);
                remains.offer(next);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LC_0112_PathSum s = new LC_0112_PathSum();
        TreeNode root = TreeNode.fromArray(new Integer[]{
                5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1});
        System.out.println(s.hasPathSum(root, 22));
    }
}
