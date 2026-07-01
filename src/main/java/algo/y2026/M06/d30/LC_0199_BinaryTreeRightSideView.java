package algo.y2026.M06.d30;

import algo.util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 题目 二叉树的右视图 给定一棵二叉树，按照从顶部到底部，返回每一层最右侧节点的值，这就是二叉树右视图。
// 示例 root=[1,2,3,null,5,null,4] => [1,3,4]
// leetcode https://leetcode.cn/problems/binary-tree-right-side-view/
// 算法核心 BFS 层序遍历，每层最后一个节点即为右视图
    //逐层遍历，每层只取最后一个节点。
    //队列存当前层所有节点
    //记录当前层节点数量 size
    //循环 size 次遍历本层，每次弹出队首
    //遍历到本层最后一个节点时，加入结果
    //左、右子节点依次入队
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, BFS, DFS
// 时段 晚地铁
// 类型 新题
// 思考
// 1、为什么使用queue
// 2、Queue<TreeNode> queue = new LinkedList<>()
// 3、时间复杂度逻辑，空间复杂度逻辑
// 4、是最优方案吗，其他方案呢
// 5、判断条件的逻辑
public class LC_0199_BinaryTreeRightSideView {

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
                if (i == size - 1) {
                    res.add(node.val);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LC_0199_BinaryTreeRightSideView s = new LC_0199_BinaryTreeRightSideView();
        TreeNode root = TreeNode.fromArray(new Integer[]{1, 2, 3, null, 5, null, 4});
        System.out.println(s.rightSideView(root));
    }
}
