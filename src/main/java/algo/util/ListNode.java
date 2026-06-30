package algo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 理解ListNode
 *
 * 场景1：p=p.next
 * 初始：p->[A] -> [B] -> [C]
 * 执行后:  p->[A] -> [B](p) -> [C]
 *   •把 p 指向的节点的下一个节点赋值给 p
 *   •效果：指针沿着链表往前走了一步
 *   •类比：i++，遍历链表时最常用的操作

 * 场景2：p=q.next
 *  p 跳到 q 的下一个节点；
 * 类比：把 p 移动到某个特定位置
 *
 * 场景3：p.next=q.next
 * p 的 next 跳过 q，指向 q 的下一个
 * 类比：删除节点
 *
 * 场景4：p.next=q
 * 把 q（及其后续整条链）接到 p 后面
 *
 * 场景5：p=q
 * 让 p 和 q 指向同一个节点
 * 备份指针，双指针的追该
 *
 * 场景6：遍历
 *
 * 场景7：头插法
 *
 * 场景8：尾插法
 *
 * 场景9：链表长度
 *
 * 场景10：根据数值查找
 *
 * 场景11：根据下标查找
 *
 * 场景12：根据下标删除
 *
 * 场景13：根据数值删除
 *
 * 场景14：链表翻转
 *
 * 场景15：清空链表
 *
 * 场景16：带不带头节点区分
 *
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {}

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode fromArray(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int value : values) {
            curr.next = new ListNode(value);
            curr = curr.next;
        }
        return dummy.next;
    }

    public static int[] toArray(ListNode head) {
        List<Integer> values = new ArrayList<>();
        while (head != null) {
            values.add(head.val);
            head = head.next;
        }
        return values.stream().mapToInt(Integer::intValue).toArray();
    }

    public static boolean equals(ListNode a, ListNode b) {
        while (a != null && b != null) {
            if (a.val != b.val) {
                return false;
            }
            a = a.next;
            b = b.next;
        }
        return a == null && b == null;
    }
}
