package newcow;

import newcow.DataStructure.ListNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: hhuang
 * @date: 2022/10/14
 */
public class JZ52 {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) {
            return null;
        }

        List<ListNode> list1 = new ArrayList<>();
        List<ListNode> list2 = new ArrayList<>();

        while (pHead1 != null) {
            list1.add(pHead1);
            pHead1 = pHead1.next;
        }
        while (pHead2 != null) {
            list2.add(pHead2);
            pHead2 = pHead2.next;
        }

        int i = list1.size() - 1;
        int j = list2.size() - 1;
        while (i >= 0 && j >= 0) {
            if (!list1.get(i).equals(list2.get(j))) {
                if (i + 1 == list1.size() && j + 1 == list2.size()) {
                    return null;
                }
                return list1.get(i + 1);
            }
            i--;
            j--;
        }
        if (i == -1) {
            return list1.get(i + 1);
        }
        if (j == -1) {
            return list2.get(j + 1);
        }
        return null;
    }
}
