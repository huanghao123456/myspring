package newcow;

import newcow.DataStructure.ListNode;
import newcow.DataStructure.RandomListNode;

import java.util.HashMap;

public class JZ35 {

    public RandomListNode Clone(RandomListNode pHead) {

        if (pHead == null) {
            return null;
        }

        HashMap<RandomListNode, Integer> nodeOffsetMap = new HashMap<>();

        // 前链表部分
        RandomListNode p = pHead;
        RandomListNode pNext = p.next;
        RandomListNode copyNode = new RandomListNode(p.label);
        RandomListNode copyHead = copyNode;
        while (pNext != null) {
            RandomListNode copyNodeNext = new RandomListNode(pNext.label);
            copyNode.next = copyNodeNext;
            copyNode = copyNode.next;

            pNext = pNext.next;
        }

        int offset = 0;
        RandomListNode q = pHead;
        while (p != null) {
            while (q != null) {
                if (q == p.random) {
                    nodeOffsetMap.put(p, offset);
                    break;
                }
                offset ++;
                q = q.next;
            }

            // 计算下一个node绝对偏移量，offset回零
            offset = 0;

            // 计算下一个node绝对偏移量，q回七点
            q = pHead;

            p = p.next;
        }

        // 游标p回原链表头结点位
        p = pHead;

        // 后随机链表部分
        copyNode = copyHead;
        while (p != null) {
            Integer integer = nodeOffsetMap.get(p);
            if (integer == null) {
                p = p.next;
                copyNode = copyNode.next;
                continue;
            }

            // 游标q回副本链表头结点位
            q = copyHead;

            for (int i = 0; i < integer; i++) {
                q = q.next;
            }
            copyNode.random = q;

            copyNode = copyNode.next;
            p = p.next;
        }

        return copyHead;
    }

}
