package algo.y2026.M06.d29;

// 题目 合并 K 个升序链表
// 示例 lists=[[1,4,5],[1,3,4],[2,6]] => [1,1,2,3,4,4,5,6]
// leetcode https://leetcode.cn/problems/merge-k-sorted-lists/
// 算法核心 解法1：小顶堆；解法2：分治（两两合并）
// 时间 O(n log K) / O(n log K)  空间 O(K) / O(log K)
// 难度 Hard
// 标签 链表, 堆, 分治
// 时段 晚地铁
// 类型 复习

import algo.util.ListNode;

import java.util.PriorityQueue;

public class LC_0023_MergeKSortedLists {

    /**
     * 解法1：小顶堆
     * 将所有链表头节点入堆，每次弹出最小值节点，将其 next 入堆
     */
    public ListNode mergeKListsHeap(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode node : lists) {
            if (node != null) {
                pq.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            curr.next = node;
            curr = curr.next;
            if (node.next != null) {
                pq.offer(node.next);
            }
        }
        return dummy.next;
    }

    /**
     * 解法2：分治（两两合并）
     * 不断将 lists 中的链表两两合并，直到只剩一条
     */
    public ListNode mergeKListsDC(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        // 复用已有 mergeTwoLists
        return merge(lists, 0, lists.length - 1);
    }

    private ListNode merge(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
        }
        int mid = l + (r - l) / 2;
        ListNode left = merge(lists, l, mid);
        ListNode right = merge(lists, mid + 1, r);
        return LC_0021_MergeTwoSortedLists.mergeTwoLists(left, right);
    }

    /**
     * 解法3（主入口）：分治，原地归并，不递归
     * 每次将相邻两组合并，步长翻倍
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        int n = lists.length;
        while (n > 1) {
            int idx = 0;
            for (int i = 0; i < n; i += 2) {
                if (i + 1 < n) {
                    lists[idx++] = LC_0021_MergeTwoSortedLists.mergeTwoLists(lists[i], lists[i + 1]);
                } else {
                    lists[idx++] = lists[i];
                }
            }
            n = idx;
        }
        return lists[0];
    }

    public static void main(String[] args) {
        LC_0023_MergeKSortedLists s = new LC_0023_MergeKSortedLists();
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };
        System.out.println(java.util.Arrays.toString(ListNode.toArray(s.mergeKLists(lists))));

        // 空数组测试
        ListNode[] empty = {};
        System.out.println(s.mergeKLists(empty));
    }
}
