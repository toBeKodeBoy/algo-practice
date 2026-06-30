package algo.y2026.M06.d30;

import algo.util.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

// 题目 二叉搜索树中第 K 小的元素
// 示例 root=[3,1,4,null,2], k=1 => 1
// leetcode https://leetcode.cn/problems/kth-smallest-element-in-a-bst/
// 算法核心 中序遍历 BST 得到升序序列，数到第 k 个即答案
// 时间 O(n) 空间 O(n)
// 难度 Medium
// 标签 二叉树, BST, 中序遍历
// 时段 早地铁
// 类型 新题
// 思考
// 1、为什么使用stack存储，能否使用队列
// 2、判断条件为什么这样编写
// 3、是否是最优方案
// 4、是否有其他方案


public class LC_0230_KthSmallestElementInABst {

    public int kthSmallest(TreeNode root, int k) {
        // 记录 k=cnt 时候的节点
        int cnt = 0;
        // 定义stack 处理 先入栈的数值是大的，后入栈的小的，所以使用这种结构 需要按照序列从小到大
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        // 开始处理
        while (curr != null || !stack.isEmpty()) {
            // 一直往左走，BST最小节点在最左侧
            // 左子树遍历
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            cnt++;
            // 找到k对应的元素
            if (cnt == k) return curr.val;
            // 遍历右子树
            curr = curr.right;
        }

        // 没有
        return -1;
    }

    public static void main(String[] args) {
        LC_0230_KthSmallestElementInABst s = new LC_0230_KthSmallestElementInABst();
        TreeNode root = TreeNode.fromArray(new Integer[]{3, 1, 4, null, 2});
        System.out.println(s.kthSmallest(root, 1));
    }
}
