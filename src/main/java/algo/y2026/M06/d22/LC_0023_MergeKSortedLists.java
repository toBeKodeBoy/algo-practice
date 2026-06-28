package algo.y2026.M06.d22;

import algo.util.ListNode;

import java.util.PriorityQueue;

// 题目 合并 K 个升序链表
// 示例 lists=[[1,4,5],[1,3,4],[2,6]] => [1,1,2,3,4,4,5,6]
// leetcode https://leetcode.cn/problems/merge-k-sorted-lists/
// 算法核心 小顶堆：K 个头节点入堆，每次弹出最小节点接入结果链，并将其 next 入堆
// 时间 O(n log K) 空间 O(K)
// 难度 Hard
// 标签 链表, 分治, 堆
// 时段 晚地铁
// 类型 新题

public class LC_0023_MergeKSortedLists {

    public ListNode mergeKLists(ListNode[] lists) {
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

    public static void main(String[] args) {
        LC_0023_MergeKSortedLists s = new LC_0023_MergeKSortedLists();
        ListNode[] lists = {
                ListNode.fromArray(new int[]{1, 4, 5}),
                ListNode.fromArray(new int[]{1, 3, 4}),
                ListNode.fromArray(new int[]{2, 6})
        };
        System.out.println(java.util.Arrays.toString(ListNode.toArray(s.mergeKLists(lists))));
    }
}
