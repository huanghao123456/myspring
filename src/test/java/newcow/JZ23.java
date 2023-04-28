package newcow;

import newcow.DataStructure.ListNode;

/**
 * @author: hhuang
 * @date: 2022/10/22
 */
public class JZ23 {

    private boolean isRinged = false;

    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead.next == null) {
            return null;
        }

        if (pHead.next == pHead) {
            return pHead;
        }

        ListNode slow = pHead;
        ListNode fast = pHead;

        // 用快慢指针验证是否有环
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow) {
                isRinged = true;
                break;
            }
        }

        if (!isRinged) {
            return null;
        }

        // 假如此刻相遇点距离入口的距离恰好等于头结点距离入口的距离
        fast = pHead;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }
}
