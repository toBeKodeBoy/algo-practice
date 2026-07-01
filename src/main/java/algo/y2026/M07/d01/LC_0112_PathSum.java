package algo.y2026.M07.d01;

import algo.util.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

// 题目 路径总和（从根节点到叶子节点之间的路径，这些节点之和就是目标值）
// 示例 root=[5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum=22 => true
// leetcode https://leetcode.cn/problems/path-sum/
// 算法核心 BFS 双队列：一个队列存放节点，另一个队列存放根到该节点的路径累加和
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
        Queue<TreeNode> qNode = new ArrayDeque<>();
        Queue<Integer> qVal = new ArrayDeque<>();
        qNode.offer(root);
        qVal.offer(root.val);

        while (!qNode.isEmpty()) {
            TreeNode node = qNode.poll();
            int valSum = qVal.poll();

            if (node.left == null && node.right == null) {
                if (valSum == targetSum) {
                    return true;
                }
                continue;
            }

            if (node.left != null) {
                qNode.offer(node.left);
                qVal.offer(valSum + node.left.val);
            }

            if (node.right != null) {
                qNode.offer(node.right);
                qVal.offer(valSum + node.right.val);
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
