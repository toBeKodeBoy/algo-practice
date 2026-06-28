package algo.y2026.M06.d15;

import algo.util.ListNode;

// 题目 反转链表，将单链表原地反转
// 示例 [1,2,3,4,5] => [5,4,3,2,1]
// leetcode https://leetcode.cn/problems/reverse-linked-list/
// 算法核心 迭代三指针 prev/curr/next，逐个反转 next 指向
// 时间 O(n) 空间 O(1)
// 难度 Easy
// 标签 链表, 双指针
// 时段 早地铁
// 类型 新题

public class LC_0206_ReverseLinkedList {

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        LC_0206_ReverseLinkedList s = new LC_0206_ReverseLinkedList();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.reverseList(ListNode.fromArray(new int[]{1, 2, 3, 4, 5})))));
    }
}
