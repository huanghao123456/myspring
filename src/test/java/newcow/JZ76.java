package newcow;

import newcow.DataStructure.ListNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: hhuang
 * @date: 2022/10/15
 */
public class JZ76 {
    public ListNode deleteDuplication(ListNode pHead) {
        int[] ints = new int[1001];

        for (int i = 0; i < ints.length; i++) {
            if (pHead == null) {
                break;
            }

            ints[pHead.val] ++;
            pHead = pHead.next;
        }

        pHead = new ListNode(-1);
        ListNode cursor = pHead;
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] == 1) {
                ListNode listNode = new ListNode(i);
                cursor.next = listNode;
                cursor = cursor.next;
            }
        }
        return pHead.next;
    }
}
