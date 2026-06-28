package algo.y2026.M06.d30;

import algo.util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 题目 二叉树的右视图
// 示例 root=[1,2,3,null,5,null,4] => [1,3,4]
// leetcode https://leetcode.cn/problems/binary-tree-right-side-view/
// 算法核心 BFS 层序遍历，每层最后一个节点即为右视图
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, BFS, DFS
// 时段 晚地铁
// 类型 新题

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
