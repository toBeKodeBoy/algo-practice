package algo.y2026.M06.d19;

import algo.util.ListNode;

// 题目 环形链表 II，返回环入口节点，无环返回 null
// 示例 head=[3,2,0,-4], pos=1 => 返回值为 2 的节点
// leetcode https://leetcode.cn/problems/linked-list-cycle-ii/
// 算法核心 Floyd 快慢指针判环，相遇后一指针从头走，再相遇处为入口
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 链表, 双指针
// 时段 早地铁
// 类型 新题

public class LC_0142_LinkedListCycleII {

    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode ptr = head;
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LC_0142_LinkedListCycleII s = new LC_0142_LinkedListCycleII();
        ListNode node4 = new ListNode(-4);
        ListNode node3 = new ListNode(0, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(3, node2);
        node4.next = node2;
        ListNode entry = s.detectCycle(node1);
        System.out.println("Cycle entry val: " + (entry == null ? "null" : entry.val));
    }
}
