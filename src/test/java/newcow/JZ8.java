package newcow;

import newcow.DataStructure.TreeLinkNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hhuang
 * @date: 2022/10/22
 */
public class JZ8 {

    private final List<TreeLinkNode> nodeList = new ArrayList<>();

    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        // 根据当前结点找到根结点
        TreeLinkNode root = findRoot(pNode);

        // 从根结点开始中序遍历
        InOrderTraversal(root);

        // 找到该结点在中序遍历中的下一个结点
        return findNextNode(pNode);
    }

    private TreeLinkNode findNextNode(TreeLinkNode pNode) {
        /* 正序时，若目标结点刚好为最后一个数，则会出现i + 1 > length的下标越界，
         * 故此处采用倒序且从倒数第二个数开始
         */
        for (int i = nodeList.size() - 2; i >= 0; i--) {
            if (nodeList.get(i) == pNode) {
                return nodeList.get(i + 1);
            }
        }

        return null;
    }

    private void InOrderTraversal(TreeLinkNode root) {
        if (root == null) {
            return;
        }

        InOrderTraversal(root.left);

        nodeList.add(root);

        InOrderTraversal(root.right);
    }

    public TreeLinkNode findRoot(TreeLinkNode pNode) {
        if (pNode.next == null) {
            return pNode;
        }

        return findRoot(pNode.next);
    }
}
