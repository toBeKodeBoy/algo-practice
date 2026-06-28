package algo.y2026.M06.d22;

import algo.util.ListNode;

// 题目 合并两个有序链表
// 示例 list1=[1,2,4], list2=[1,3,4] => [1,1,2,3,4,4]
// leetcode https://leetcode.cn/problems/merge-two-sorted-lists/
// 算法核心 dummy 头节点 + 双指针遍历两链表，取较小值接入结果链
// 时间 O(n+m) 空间 O(1)
// 难度 Easy
// 标签 链表, 递归
// 时段 早地铁
// 类型 新题

public class LC_0021_MergeTwoSortedLists {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }
        curr.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    public static void main(String[] args) {
        LC_0021_MergeTwoSortedLists s = new LC_0021_MergeTwoSortedLists();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.mergeTwoLists(
                        ListNode.fromArray(new int[]{1, 2, 4}),
                        ListNode.fromArray(new int[]{1, 3, 4})))));
    }
}
