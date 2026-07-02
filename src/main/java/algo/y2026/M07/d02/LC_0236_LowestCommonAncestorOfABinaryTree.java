package algo.y2026.M07.d02;

import algo.util.TreeNode;

/**
 * LeetCode 236. 二叉树的最近公共祖先
 * 题目链接：https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
 * 难度：Medium
 * 标签：二叉树、后序深度优先搜索 DFS
 * 核心思路：后序遍历自底向上递归
 * 1. 递归终止：当前节点为空 / 等于p / 等于q，直接返回自身
 * 2. 递归遍历左、右子树
 * 3. 左右子树均找到目标节点 → 当前节点为最近公共祖先
 * 4. 仅单侧找到目标节点 → 向上传递该节点
 * 5. 两侧都无目标 → 返回 null
 * 时间复杂度：O(n) 每个节点仅访问一次
 * 空间复杂度：O(n) 最坏情况链表树，递归栈深度等于节点数
 */
public class LC_0236_LowestCommonAncestorOfABinaryTree {

    /**
     * 查找 p、q 的最近公共祖先
     * @param root 二叉树根节点
     * @param p 目标节点1
     * @param q 目标节点2
     * @return 最近公共祖先节点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 边界：空节点 / 命中任一目标节点，直接返回
        if (root == null || root == p || root == q) {
            return root;
        }

        // 后序：先搜左右子树
        TreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);

        // 左右各找到一个目标，当前为公共祖先
        if (leftRes != null && rightRes != null) {
            return root;
        }

        // 只一侧存在，向上传递找到的节点；都不存在返回 null
        return leftRes != null ? leftRes : rightRes;


    }

    // ==================== 工具辅助方法 ====================
    /**
     * 根据节点值遍历查找对应TreeNode（假设节点值唯一）
     * @param root 根节点
     * @param targetVal 目标数值
     * @return 匹配节点，无匹配返回null
     */
    private static TreeNode findTreeNodeByVal(TreeNode root, int targetVal) {
        if (root == null) {
            return null;
        }
        if (root.val == targetVal) {
            return root;
        }
        // 左树找到直接返回，否则搜右树
        TreeNode leftFind = findTreeNodeByVal(root.left, targetVal);
        return leftFind != null ? leftFind : findTreeNodeByVal(root.right, targetVal);
    }

    // ==================== 测试入口 ====================
    public static void main(String[] args) {
        LC_0236_LowestCommonAncestorOfABinaryTree solution = new LC_0236_LowestCommonAncestorOfABinaryTree();

        // 测试用例1 原题示例
        Integer[] treeArr = {3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        TreeNode root = TreeNode.fromArray(treeArr);
        TreeNode p = findTreeNodeByVal(root, 5);
        TreeNode q = findTreeNodeByVal(root, 1);

        TreeNode lca = solution.lowestCommonAncestor(root, p, q);
        System.out.printf("测试用例1 最近公共祖先节点值：%d%n", lca.val);

        // 可追加多组测试用例
        // TreeNode p2 = findTreeNodeByVal(root, 5);
        // TreeNode q2 = findTreeNodeByVal(root, 4);
        // System.out.println(solution.lowestCommonAncestor(root, p2, q2).val);
    }
}