package algo.y2026.M06.d29;

// 合并有序链表
// 示例 l1=[1,2,4] l2=[1,3,4] 合并之后 [1,1,2,3,4,4]
// leetcode https://leetcode.cn/problems/merge-two-sorted-lists
// 算法核心 双指针各自遍历一条链表，每次选较小值的节点接到尾部
// 时间O(n+m) 空间(1)
// 难度 Easy
// 标签 链表，双指针

import algo.util.ListNode;

public class LC_0021_MergeTwoSortedLists {
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 定义虚拟头结点，方便返回链表
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

        // 如果一条为空，一条非空
        curr.next = l1 == null ? l2 : l1;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode l1 = ListNode.fromArray(new int[]{1, 2, 4});
        ListNode l2 = ListNode.fromArray(new int[]{1, 3, 4});

        ListNode cur = LC_0021_MergeTwoSortedLists.mergeTwoLists(l1, l2);
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
    }

}
