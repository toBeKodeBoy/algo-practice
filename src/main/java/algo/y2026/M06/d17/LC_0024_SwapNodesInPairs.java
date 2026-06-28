package algo.y2026.M06.d17;

import algo.util.ListNode;

// 题目 两两交换链表中的节点，不能修改节点值
// 示例 [1,2,3,4] => [2,1,4,3]
// leetcode https://leetcode.cn/problems/swap-nodes-in-pairs/
// 算法核心 dummy 节点，每次交换 prev 后两个节点
// 时间 O(n) 空间 O(1)
// 难度 Medium
// 标签 链表, 递归
// 时段 早地铁
// 类型 新题

public class LC_0024_SwapNodesInPairs {

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;

            first.next = second.next;
            second.next = first;
            prev.next = second;
            prev = first;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        LC_0024_SwapNodesInPairs s = new LC_0024_SwapNodesInPairs();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.swapPairs(ListNode.fromArray(new int[]{1, 2, 3, 4})))));
    }
}
