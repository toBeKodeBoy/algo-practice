package algo.y2026.M06.d16;

import algo.util.ListNode;

// 题目 反转链表 II，反转位置 left 到 right 之间的节点
// 示例 head=[1,2,3,4,5], left=2, right=4 => [1,4,3,2,5]
// leetcode https://leetcode.cn/problems/reverse-linked-list-ii/
// 算法核心 找到 left 前驱，区间内头插法逐节点移到区间头部
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 链表
// 时段 早地铁
// 类型 新题

public class LC_0092_ReverseLinkedListII {

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) {
            return head;
        }

        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }

        ListNode curr = prev.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        LC_0092_ReverseLinkedListII s = new LC_0092_ReverseLinkedListII();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.reverseBetween(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 2, 4))));
    }
}
