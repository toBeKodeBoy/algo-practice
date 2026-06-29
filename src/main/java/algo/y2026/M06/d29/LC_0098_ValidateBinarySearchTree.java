package algo.y2026.M06.d29;

import algo.util.TreeNode;

import java.util.Stack;

// 题目 验证二叉搜索树
// 示例 root=[2,1,3] => true；root=[5,1,4,null,null,3,6] => false
// leetcode https://leetcode.cn/problems/validate-binary-search-tree/
// 算法核心 中序遍历（左→根→右）严格单调递增，遍历过程记录前一个节点值，每次对比当前值是否大于前驱，一旦不满足直接返回 false。
//  1.一路向左走到最底层的左孩子，每经过一个节点就压入栈
//  2.当走到 null 时，从栈里弹出一个节点（这是当前最小节点）
//  3.访问该节点（比较值是否严格递增）
//  4.转向该节点的右子树，重复以上过程
// 时间 O(n) 空间 O(h)
// 难度 Medium
// 标签 二叉树 BST
// 时段 晚地铁
// 类型 新题
// 思考
// 问题1：为什么用stack 既避免了递归可能导致的 StackOverflow，又比 Morris 遍历更直观、不易出错。
// 问题2：判断条件为什么这样用，只用其中一个怎么样
//解答
// `curr != null` → 负责"还有节点要深入"（起始和转向右子树时）
// `!stack.isEmpty()` → 负责"栈里还有祖先待回溯"（左子树走到底时）

public class LC_0098_ValidateBinarySearchTree {

    public boolean isValidBST(TreeNode root) {

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        // 使用long最小值覆盖Integer边界用例
        long preVal = Long.MIN_VALUE;

        while (curr != null || !stack.isEmpty()) {
            // 一路向左压入栈
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            // 弹出最小节点
            curr = stack.pop();
            // 不满足严格递增
            if (curr.val <= preVal) {
                return false;
            }

            // 更新前驱值
            preVal = curr.val;
            // 处理右子树
            curr = curr.right;
        }
        return true;
    }

    public static void main(String[] args) {
        LC_0098_ValidateBinarySearchTree s = new LC_0098_ValidateBinarySearchTree();
        TreeNode root = TreeNode.fromArray(new Integer[]{2, 1, 3});
        System.out.println(s.isValidBST(root));
    }
}
