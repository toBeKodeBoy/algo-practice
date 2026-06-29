package algo.y2026.M06.d29;

import algo.util.TreeNode;

// 题目 二叉树展开为链表
// 示例 root=[1,2,5,3,4,null,6] => [1,null,2,null,3,null,4,null,5,null,6]
// leetcode https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
// 算法核心 原地
//    1
//   / \
//  2   5
// / \   \
//3   4   6
//=》1 → 2 → 3 → 4 → 5 → 6
//所有节点 left = null，仅 right 串联
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 二叉树, 原地, 链表
// 时段 早地铁
// 类型 新题

public class LC_0114_FlattenBinaryTreeToLinkedList {

    public void flatten(TreeNode root) {
        TreeNode cur = root;
        // 循环沿着右指针遍历整棵树
        while (cur != null) {
            // 只有存在左子树时，才需要调整指针
            if (cur.left != null) {
                // 1. 找到左子树的最右下节点 pre
                TreeNode pre = cur.left;
                while (pre.right != null) {
                    pre = pre.right;
                }
                // 2. 把当前节点的右子树，挂到左子树最末尾
                pre.right = cur.right;
                // 3. 将左子树整体移到右边，左指针清空
                cur.right = cur.left;
                cur.left = null;
            }
            // 处理下一个节点（此时左已经为空，只能走右）
            cur = cur.right;
        }
    }

    public static void main(String[] args) {
        LC_0114_FlattenBinaryTreeToLinkedList s = new LC_0114_FlattenBinaryTreeToLinkedList();
        TreeNode root = TreeNode.fromArray(new Integer[]{1, 2, 5, 3, 4, null, 6});
        s.flatten(root);
        System.out.println(java.util.Arrays.toString(TreeNode.toArray(root)));
    }
}
