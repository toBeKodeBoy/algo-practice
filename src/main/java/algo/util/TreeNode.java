package algo.util;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
}
