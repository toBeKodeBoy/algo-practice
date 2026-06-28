package algo.y2026.M06.d21;

import algo.util.ListNode;

// 题目 两数相加，两链表表示非负整数（逆序），返回相同形式的结果链表
// 示例 l1=[2,4,3], l2=[5,6,4] => [7,0,8] 即 342+465=807
// leetcode https://leetcode.cn/problems/add-two-numbers/
// 算法核心 逐位相加维护 carry，dummy 头节点串结果
// 时间 O(max(m,n)) 空间 O(max(m,n))
// 难度 Medium
// 标签 链表, 数学
// 时段 早地铁
// 类型 新题

public class LC_0002_AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        LC_0002_AddTwoNumbers s = new LC_0002_AddTwoNumbers();
        System.out.println(java.util.Arrays.toString(ListNode.toArray(
                s.addTwoNumbers(
                        ListNode.fromArray(new int[]{2, 4, 3}),
                        ListNode.fromArray(new int[]{5, 6, 4})))));
    }
}
