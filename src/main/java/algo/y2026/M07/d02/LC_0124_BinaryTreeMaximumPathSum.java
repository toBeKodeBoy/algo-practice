package algo.y2026.M07.d02;

import algo.util.TreeNode;

// 题目 二叉树中的最大路径和
// 示例 root=[-10,9,20,null,null,15,7] => 42
// leetcode https://leetcode.cn/problems/binary-tree-maximum-path-sum/
// 算法核心 后序 DFS：返回以当前节点为端点的最大单边贡献，用 left+val+right 更新全局最大值
// 时间 O(n) 空间 O(n)
// 难度 Hard
// 标签 二叉树, DFS, 分治
// 时段 晚地铁
// 类型 新题

public class LC_0124_BinaryTreeMaximumPathSum {

    public int maxPathSum(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxGain(root, max);
        return max[0];
    }

    private int maxGain(TreeNode node, int[] max) {
        if (node == null) {
            return 0;
        }
        int left = Math.max(maxGain(node.left, max), 0);
        int right = Math.max(maxGain(node.right, max), 0);
        max[0] = Math.max(max[0], node.val + left + right);
        return node.val + Math.max(left, right);
    }

    public static void main(String[] args) {
        LC_0124_BinaryTreeMaximumPathSum s = new LC_0124_BinaryTreeMaximumPathSum();
        TreeNode root = TreeNode.fromArray(new Integer[]{-10, 9, 20, null, null, 15, 7});
        System.out.println(s.maxPathSum(root));
    }
}
