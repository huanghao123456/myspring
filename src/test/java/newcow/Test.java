package newcow;

import newcow.DataStructure.ListNode;
import newcow.DataStructure.RandomListNode;
import newcow.DataStructure.TreeLinkNode;

/**
 * @author: hhuang
 * @date: 2022/10/14
 */
public class Test {
    public static void main(String[] args) {

        RandomListNode node1 = new RandomListNode(1);
        RandomListNode node2 = new RandomListNode(2);
        RandomListNode node3 = new RandomListNode(3);
        RandomListNode node4 = new RandomListNode(4);
        RandomListNode node5 = new RandomListNode(5);
//        ListNode node6 = new ListNode(6);
//        ListNode node7 = new ListNode(7);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node1.random = node3;
        node2.random = node5;
        node4.random = node2;

//        node6.next = node7;
//        node7.next = node4;


        JZ35 jz35 = new JZ35();
        RandomListNode listNode = jz35.Clone(node1);
    }


}
