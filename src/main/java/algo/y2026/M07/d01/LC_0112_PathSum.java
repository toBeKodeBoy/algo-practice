package algo.y2026.M07.d01;

import algo.util.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

// 题目 路径总和（从根节点到叶子节点之间的路径，这些节点之和就是目标值）
// leetcode https://leetcode.cn/problems/path-sum/
// 示例 [1,2,3] 3 => true
// 算法核心 层序遍历处理，初始化两个队列，一个队列存放二叉树的每层的节点 另外一个队列存放二叉树节点累加值(从第一层到当前层)
// 难度 Easy
// 复杂度 时间n 空间n
// 标签 二叉树 BST
// 类型 新题

public class LC_0112_PathSum {
    public static boolean hasPathSum(TreeNode root , int targetSum){
        if(root == null) return false ;
        // 存放每层节点
        Queue<TreeNode> qNode = new LinkedList<>();
        // 存放当前经过的字树长度
        Queue<Integer> qVal = new LinkedList<>();
        qNode.offer(root) ;
        qVal.offer(root.val) ;

        while(!qNode.isEmpty()&&!qVal.isEmpty()){
            TreeNode node = qNode.poll();
            Integer valSum = qVal.poll();

            if(node.left == null && node.right == null){
                if(valSum == targetSum) return true;
                continue;
            }

            if(node.left != null) {
                qNode.offer(node.left) ;
                qVal.offer(valSum + node.left.val) ;
            }

            if(node.right != null) {
                qNode.offer(node.right) ;
                qVal.offer(valSum + node.right.val) ;
            }

        }
        return false;
    }

    public static void main(String[] args) {
        TreeNode treeNode = TreeNode.fromArray(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        boolean flag = LC_0112_PathSum.hasPathSum(treeNode, 9);
        System.out.println(flag );

    }
}
