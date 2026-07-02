package algo.util;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * TreeNode
 * 节点本质是引用传递，= 只修改指针指向，不会修改原对象。
 * 场景1： p=p.right [指针 p 移动到自己的右孩子节点，仅改变 p 自身指向，不改动树结构。]
 *     p
 *    / \
 *   A   B(p.right)
 * 适用于：遍历二叉树、循环找右子树、迭代中序 / 先序遍历。
 *
 * 场景2：p.left=p.right [把 p 的左指针，指向 p 原本的右孩子;原 p.left 子树会丢失（无引用，GC 回收），直接修改树结构。]
 * 原图
 *     p
 *    / \
 *   L   R
 * 执行后
 *     p
 *    /
 *   R
 * (L 子树被丢弃)
 *
 * 场景3：p.left=q.left [让 p 的左孩子，直接复用 q 的左子树（共享同一棵子树）,L 子树现在同时属于 p.left 和 q.left，修改一边两边都会变]
 * 原图
 *     p        q
 *    /  \      / \
 *   Lp   Rp   Lq  Rq
 * 执行后
 *     p         q
 *    /  \      / \
 *   Lq   Rp   Lq  Rq
 *
 * 场景4：p.left = q [把整个 q 节点，挂到 p 的左子节点位置； 原来的 p.left 子树直接断开丢失。]
 * 原图
 *     p        q
 *    /  \      / \
 *   Lp   Rp   Lq  Rq
 * 执行后
 *       p
 *      /  \
 *     q   Rp
 *    / \
 *   Lq  Rq
 * (Lp 子树被丢弃)
 *
 * 场景5：q.left = null[切断 q 的左子树（断开引用）]
 *
 * 场景6：切换左右子树 TreeNode tmp = p.left; p.left = q; q.left = tmp;
 *
 * 场景7：遍历缓存原节点 TreeNode tmp = p; p = p.left;
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {}

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /** Level-order array with null for missing children, e.g. [3,9,20,null,null,15,7]. */
    public static TreeNode fromArray(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(values[0]);
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.offer(node.left);
            }
            i++;
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.offer(node.right);
            }
            i++;
        }
        return root;
    }

    public static Integer[] toArray(TreeNode root) {
        if (root == null) {
            return new Integer[0];
        }
        List<Integer> values = new ArrayList<>();
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                values.add(null);
                continue;
            }
            values.add(node.val);
            queue.offer(node.left);
            queue.offer(node.right);
        }
        while (!values.isEmpty() && values.get(values.size() - 1) == null) {
            values.remove(values.size() - 1);
        }
        return values.toArray(new Integer[0]);
    }

    public static boolean equals(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.val == b.val && equals(a.left, b.left) && equals(a.right, b.right);
    }

    // 后序遍历 双栈法实现
    public static List<Integer> postOrderTraversal(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;

        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();

        stack1.push(root);
        while (!stack1.isEmpty()){
            TreeNode node = stack1.pop();
            stack2.push(node);

            // 先压左，后压右
            if(node.left != null){
                stack1.push(node.left);
            }
            if(node.right != null){
                stack1.push(node.right);
            }
        }

        // stack2 依次弹出
        while (!stack2.isEmpty()){
            result.add(stack2.poll().val);
        }

        return result;
    }

}
