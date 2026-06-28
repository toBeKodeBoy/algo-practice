package algo.y2026.M06.d18;

import algo.util.ListNode;

// 题目 删除链表的倒数第 N 个结点
// 示例 head=[1,2,3,4,5], n=2 => [1,2,3,5]
// leetcode https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
// 算法核心 快慢指针，fast 先走 n+1 步，slow 停在待删节点前驱
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 链表, 双指针
// 时段 早地铁
// 类型 新题

public class LC_0019_RemoveNthNodeFromEndOfList {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy;
        ListNode slow = dummy;

        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        LC_0019_RemoveNthNodeFromEndOfList s = new LC_0019_RemoveNthNodeFromEndOfList();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.removeNthFromEnd(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 2))));
    }
}
