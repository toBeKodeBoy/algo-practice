package algo.y2026.M06.d23;

import algo.util.ListNode;

// 题目 K 个一组翻转链表
// 示例 head=[1,2,3,4,5], k=2 => [2,1,4,3,5]
// leetcode https://leetcode.cn/problems/reverse-nodes-in-k-group/
// 算法核心 dummy 头节点，每次定位 K 个节点区间并翻转，不足 K 不翻转
// 时间 O(n) 空间 O(1)
// 难度 Hard
// 标签 链表, 翻转
// 时段 晚地铁
// 类型 新题

public class LC_0025_ReverseNodesInKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;

        while (true) {
            ListNode end = prev;
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) {
                break;
            }

            ListNode start = prev.next;
            ListNode next = end.next;

            ListNode curr = start;
            ListNode prevNode = next;
            while (curr != next) {
                ListNode tmp = curr.next;
                curr.next = prevNode;
                prevNode = curr;
                curr = tmp;
            }

            prev.next = end;
            prev = start;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        LC_0025_ReverseNodesInKGroup s = new LC_0025_ReverseNodesInKGroup();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.reverseKGroup(ListNode.fromArray(new int[]{1, 2, 3, 4, 5}), 2))));
    }
}
