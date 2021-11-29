package DataStructure.Tree.HuffManTree;

import DataStructure.Tree.TreeOperation;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author: Jerssy
 * @create: 2021-04-01 16:24
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 赫夫曼树
 *
 * 给定n个权值作为n个叶子结点，构造一棵二叉树，若该树的带权路径长度(wpl)达到最小，称这样的二叉树为最优二叉树，也称为哈夫曼树(Huffman Tree), 还有的书翻译为霍夫曼树。

 * 赫夫曼树是带权路径长度最短的树，权值较大的结点离根较近
 *
 * 路径和路径长度：在一棵树中，从一个结点往下可以达到的孩子或孙子结点之间的通路，称为路径。通路中分支的数目称为路径长度。若规定根结点的层数为1，则从根结点到第L层结点的路径长度为L-1
 *
 * 结点的权及带权路径长度：若将树中结点赋给一个有着某种含义的数值，则这个数值称为该结点的权。结点的带权路径长度为：从根结点到该结点之间的路径长度与该结点的权的乘积
 *
 * 树的带权路径长度：树的带权路径长度规定为所有叶子结点的带权路径长度之和，记为WPL(weighted path length) ,权值越大的结点离根结点越近的二叉树才是最优二叉树。
 *
 * WPL最小的就是赫夫曼树
 *
 * 创建赫夫曼树的步骤：
 *
 * 1 从小到大进行排序, 将每一个数据，每个数据都是一个节点，每个节点可以看成是一颗最简单的二叉树
 *
 * 2 取出根节点权值最小的两颗二叉树
 *
 * 3 组成一颗新的二叉树, 该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
 *
 * 4 再将这颗新的二叉树，将该根节点的权值大小与剩下的数据进行比较即再次排序，不断重复1-2-3-4的步骤，直到数列中，所有的数据都被处理，就得到一颗赫夫曼树
 *
 */
public class HuffmanTree {

    public static void main(String[] args) {

        int[] arr=new int[]{13, 7, 8, 3, 29, 6, 1};

        createHuffmanTree(arr);
    }


    /*
     * @param: [arr]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/1 19:48
     * @description:创建赫夫曼树
     */
    public  static void createHuffmanTree(int[] arr){

        //需要比较大小，且需要重复比较大小优先使用优先级队列
        var  priorityQueue=new PriorityQueue<Node>(Comparator.comparingInt(e->e.value));
        for (int i : arr) {
            priorityQueue.add(new Node(i));
        }

        while (priorityQueue.size()>1){

            Node nodeLeft = priorityQueue.poll();
            Node nodeRight=priorityQueue.poll();
            if (nodeLeft!=null&&nodeRight != null){

                Node parentNode=new Node(nodeLeft.value+nodeRight.value);
                parentNode.left=nodeLeft;
                parentNode.right=nodeRight;
                priorityQueue.remove(nodeLeft);
                priorityQueue.remove(nodeRight);
                priorityQueue.add(parentNode);
            }
        }
        Node root = priorityQueue.poll();

        TreeOperation<Node> tree = new TreeOperation<>();
        tree.show(root);
    }


    public static class  Node implements Comparable<Node> {

        int value;
        Node left;
        Node right;

        public Node(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        @Override
        public int compareTo(@NotNull HuffmanTree.Node o) {

            return Integer.compare(value,o.value);
        }
    }

}
