package algo.y2026.M06.d20;

import algo.util.ListNode;

// 题目 相交链表，找出两链表相交的起始节点
// 示例 listA=[4,1,8,4,5], listB=[5,6,1,8,4,5], 相交于值为 8 的节点
// leetcode https://leetcode.cn/problems/intersection-of-two-linked-lists/
// 算法核心 双指针交替遍历两链，走完后切换头，相遇处为交点或 null
// 时间 O(m+n) 空间 O(1)
// 难度 Easy
// 标签 链表, 双指针
// 时段 早地铁
// 类型 新题

public class LC_0160_IntersectionOfTwoLinkedLists {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }

    public static void main(String[] args) {
        LC_0160_IntersectionOfTwoLinkedLists s = new LC_0160_IntersectionOfTwoLinkedLists();
        ListNode common = ListNode.fromArray(new int[]{8, 4, 5});
        ListNode a = new ListNode(4, new ListNode(1, common));
        ListNode b = new ListNode(5, new ListNode(6, new ListNode(1, common)));
        ListNode node = s.getIntersectionNode(a, b);
        System.out.println("Intersection val: " + (node == null ? "null" : node.val));
    }
}
