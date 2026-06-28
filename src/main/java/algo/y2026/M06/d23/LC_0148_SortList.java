package algo.y2026.M06.d23;

import algo.util.ListNode;

// 题目 排序链表
// 示例 head=[4,2,1,3] => [1,2,3,4]
// leetcode https://leetcode.cn/problems/sort-list/
// 算法核心 归并排序：快慢指针找中点切分，递归排序后合并
// 时间 O(n log n) 空间 O(log n)
// 难度 Medium
// 标签 链表, 排序, 归并
// 时段 早地铁
// 类型 新题

public class LC_0148_SortList {

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode mid = slow.next;
        slow.next = null;

        ListNode left = sortList(head);
        ListNode right = sortList(mid);
        return merge(left, right);
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    public static void main(String[] args) {
        LC_0148_SortList s = new LC_0148_SortList();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.sortList(ListNode.fromArray(new int[]{4, 2, 1, 3})))));
    }
}
