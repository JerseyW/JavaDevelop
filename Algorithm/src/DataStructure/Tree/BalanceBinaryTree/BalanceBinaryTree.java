package DataStructure.Tree.BalanceBinaryTree;

import DataStructure.Tree.TreeOperation;


/**
 * @author: Jerssy
 * @create: 2021-04-07 9:38
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 平衡二叉树
 *
 * 二叉排序树可能存在的问题
 *
 * 当左子树或者右子树全部为空，从形式上看，更像一个单链表.
 * 插入速度没有影响
 * 查询速度明显降低(因为需要依次比较另一个树的分支), 不能发挥BST的优势，因为每次还需要比较左子树，其查询速度比单链表还慢
 *
 *平衡二叉树也叫平衡二叉搜索树（Self-balancing binary search tree）又被称为AVL树， 可以保证查询效率较高。
 *
 * 具有以下特点：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 * 任意节点的子树的高度差都小于等于1
 * 平衡二叉树的常用实现方法有红黑树、AVL、替罪羊树、Treap、伸展树等。
 *
 * 平衡二叉树实现原理
 * 平衡二叉树构建的基本思想就是在构建二叉排序树的过程中，每当插入一个节点时，先检查是否因插入而破坏了树的平衡性，若是，找出最小不平衡树。在保持二叉排序树特性的前提下，调整最小不平衡子树中各节点之间的链接关系，进行相应的旋转，使之成为新的平衡子树。
 *
 * 旋转操作：
 * 右旋：最小不平衡子树的BF和它的子树BF符号相同且最小不平衡子树的BF大于0
 * 左旋：最小不平衡子树的BF和它的子树BF符号相同且最小不平衡子树的BF小于零
 * 左右旋：最小不平衡子树的BF与它的子树的BF符号相反时且最小不平衡子树的BF大于0时，需要对节点先进行一次向左旋使得符号相同后，在向右旋转一次完成平衡操作。
 * 右左旋：最小不平衡子树的BF与它的子树的BF符号相反时且最小不平衡子树的BF小于0时，需要对节点先进行一次向右旋转使得符号相同时，在向左旋转一次完成平衡操作。

 * 第一大类，A节点的左子树高度比右子树高度高2，最终需要经过右旋操作(可能需要先左后右)
 * 第二大类，A节点的左子树高度比右子树高度低2，最终需要经过左旋操作(可能需要先右后左)
 */
public class BalanceBinaryTree<V extends Comparable<V>> {

    private  Node<V> root;

    public static void main(String[] args) {
        int[] nodeArray = new int[]{6,5,9,8, 32,85,62,74,22,10,12};

        BalanceBinaryTree<Integer> balanceBinaryTree=new BalanceBinaryTree<>();

        for (int i : nodeArray) {
            balanceBinaryTree.addNode(i);
        }

        TreeOperation<BalanceBinaryTree.Node<Integer>> objectTreeOperation = new TreeOperation<>();

        objectTreeOperation.show(balanceBinaryTree.root);

        System.out.println("树高："+balanceBinaryTree.getTreeHeight(balanceBinaryTree.root));
        System.out.println("左子树高："+balanceBinaryTree.leftTreeHeight(balanceBinaryTree.root));
        System.out.println("右子树高："+balanceBinaryTree.rightTreeHeight(balanceBinaryTree.root));

        System.out.println("删除了"+balanceBinaryTree.deleteNode(22));
        System.out.println("删除了"+balanceBinaryTree.deleteNode(10));
        System.out.println("删除了"+balanceBinaryTree.deleteNode(6));

        objectTreeOperation.show(balanceBinaryTree.root);

    }


    /*
     * @param: [node]
     * @return: int
     * @author: Jerssy
     * @dateTime: 2021/4/7 18:49
     * @description:  树高
     */
    private int  getTreeHeight(Node<V> node){

        return  node==null?0:Math.max(getTreeHeight(node.left),getTreeHeight(node.right))+1;

    }

    /*
     * @param: [node]
     * @return: int
     * @author: Jerssy
     * @dateTime: 2021/4/7 20:17
     * @description:左子树高
     */
    private  int leftTreeHeight(Node<V>node) {

        return node == null ? 0 : getTreeHeight(node.left)+1;
    }

    /*
     * @param: [node]
     * @return: int
     * @author: Jerssy
     * @dateTime: 2021/4/7 20:17
     * @description: 右子树高
     */
    private  int rightTreeHeight(Node<V>node) {

        return node == null ? 0 : getTreeHeight(node.right)+1;
    }


    /*
     * @param: [node]
     * @return: int
     * @author: Jerssy
     * @dateTime: 2021/4/7 20:16
     * @description:计算平衡因子
     */
    public int calcNodeBalanceFactor(Node<V> node) {

        return node == null?0:getTreeHeight(node.left)-getTreeHeight(node.right) ;
    }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/7 11:38
     * @description:对节点node左旋
     */
    private  void leftRotate(Node<V> node){
        Node<V> nodeRight=node.right;

        if (nodeRight==null) return;

         node.right= nodeRight.left;
         if (nodeRight.left != null){
             nodeRight.left.parent=node;
         }

         if (node.parent != null){
             if (node.parent.left==node){
                 node.parent.left= nodeRight;
             }else  node.parent.right = nodeRight;
             nodeRight.parent=node.parent;
         }
         else {
             root=nodeRight;
             root.parent = null;
         }

         nodeRight.left=node;
         node.parent=nodeRight;

    }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/7 11:40
     * @description:对node节点右旋
     */
    private void rightRotated(Node<V> node) {
        Node<V> nodeLeft=node.left;

        if (nodeLeft==null) return;

        node.left=nodeLeft.right;

        if (nodeLeft.right!=null){
            nodeLeft.right.parent=node;
        }

        if (node.parent != null){
             if (node.parent.left==node){

                 node.parent.left = nodeLeft;
             }
             else  node.parent.right=nodeLeft;

             nodeLeft.parent=node.parent;
        }
        else {
            root=nodeLeft;
            root.parent = null;
        }

        nodeLeft.right=node;
        node.parent=nodeLeft;

    }


    /*
     * @param: [value]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/7 18:45
     * @description: 添加节点
     */
    private  void  addNode(V value){
        if (value == null) {
            return;
        }

        Node<V> nodeV = new Node<>(value);
        Node<V> leafNode = searchLeafNode(nodeV);
        nodeV.parent=leafNode;
        if (nodeV.parent!=null){

             if (nodeV.value.compareTo(leafNode.value)>0){
                 nodeV.parent.right=nodeV;
             }

             if (nodeV.value.compareTo(leafNode.value)<0){
                 nodeV.parent.left = nodeV;
             }

            setTreeBalanced(nodeV.parent);

        }
        else  root=nodeV;

    }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/7 19:42
     * @description:设置二叉树平衡
     */
    private  void setTreeBalanced(Node<V> node) {

        if (node==null) return;

        if (calcNodeBalanceFactor(node)>1){
            //当前node节点的左子树的右子树的高度大于左子树的左子树的高度--如下图先左旋左节点然后对父节点右旋
            /*
                        a                      a                         e
            *         /   \                 /    \                    /     \
            *        b     c   左旋b        e      c    右旋a         b       a
                   /  \        --->       /  \         ---->        /      /   \
                  d    e                 b    f                    d      f     c
                        \               /
                        f              d
            * */
            if (node.left!=null&&calcNodeBalanceFactor(node.left)>0){
                //先对左子树即当前节点的左子节点左旋
                leftRotate(node.left);
            }

            //对当前节点右旋
            rightRotated(node);

         }

              /*       a                           a                               d
                    /     \       右旋c         /     \         左旋a           /      \
                   b      c       ------>     b       d        ------>        a        c
                        /  \                        /   \                   /   \       \
                       d    f                      e     c                 b    e        f
                      /                                   \
                     e                                     f
            * */
         if ( calcNodeBalanceFactor(node)<-1){
            //当当前节点的右子树的右子树的左子树节点高度大于右子树高度先对其右子树即右子节点右旋--如上图先右旋右节点然后对父节点左旋
             if (node.right!=null&&calcNodeBalanceFactor(node.right)>0){
                rightRotated(node.right);
             }

            //对当前节点左旋
            leftRotate(node);

        }

        setTreeBalanced(node.parent);//继续向上检索是否平衡
    }

    /*
     * @param: [currentNode]
     * @return: DataStructure.Tree.BalanceBinaryTree.BalanceBinaryTree.Node<V>
     * @author: Jerssy
     * @dateTime: 2021/4/7 18:46
     * @description: 查找叶子节点
     */
    private  Node<V> searchLeafNode(Node<V> currentNode){

        Node<V> tempNode=root,parentNode = null;
        while (tempNode!=null){
             parentNode=tempNode;
            if (tempNode.value.compareTo(currentNode.value) > 0) {
                tempNode = tempNode.left;
            }
            else if (tempNode.value.compareTo(currentNode.value) < 0) {
                 tempNode = tempNode.right;
            }else {

                  if (tempNode.left==null) tempNode.left=currentNode;

                  else tempNode.right=currentNode;

                  return tempNode;
            }

        }

        return  parentNode;

    }

    /*
     * @param: [value]
     * @return: V
     * @author: Jerssy
     * @dateTime: 2021/4/7 18:46
     * @description: 删除节点
     */
    private V  deleteNode(V value){

        if (value==null) return null;

        Node<V> deleteNode=searchNode(value, root);
        if (deleteNode != null){
            Node<V> successorNode;
            if (deleteNode.left!=null&&deleteNode.right != null){
                 successorNode = getSuccessorNode(deleteNode);
                 deleteNode.value=successorNode.value;

            }
            else  successorNode=deleteNode;

            Node<V> replaceNode=successorNode.left!=null?successorNode.left:successorNode.right;

            Node<V> parentNode = successorNode.parent;


            if (parentNode==null) {
                root=replaceNode;
                if (replaceNode!=null) replaceNode.parent=null;
            }
            else {
                 if (parentNode.left==successorNode){
                     parentNode.left=replaceNode;
                 }
                 else  parentNode.right = replaceNode;

                 if(replaceNode != null)  replaceNode.parent=parentNode;

                 successorNode.left=successorNode.right=successorNode.parent=null;

                 setTreeBalanced(parentNode);
            }


            return value;
        }

        return null;
    }

  /*
   * @param: [node]
   * @return: DataStructure.Tree.BalanceBinaryTree.BalanceBinaryTree.Node<V>
   * @author: Jerssy
   * @dateTime: 2021/4/7 18:46
   * @description:  查找后继节点
   */
    private  Node<V> getSuccessorNode(Node<V> node){
        if (node == null) {
            return null;
        }
        Node<V> tempNode;
        if (node.right!=null){
            tempNode=node.right;
            while (tempNode.left != null){
                tempNode=tempNode.left;
            }

            return tempNode;

        }
        else {
              tempNode=node.parent;

              while (tempNode!=null&&tempNode.left!=node){
                   tempNode=tempNode.parent;
                   node=tempNode;
              }
              return tempNode;
        }
    }

    /*
     * @param: [value, nodeP]
     * @return: DataStructure.Tree.BalanceBinaryTree.BalanceBinaryTree.Node<V>
     * @author: Jerssy
     * @dateTime: 2021/4/7 18:46
     * @description:  查找值为value的节点
     */
    private  Node<V> searchNode(V value, Node<V> nodeP){
         if (nodeP == null) {
             return null;
         }
         if (value.compareTo(nodeP.value)>0){
             return  searchNode(value, nodeP.right);
         }
         else if (value.compareTo(nodeP.value) <0) {
             return  searchNode(value, nodeP.left);
         }
         else return nodeP;
    }

    static class Node<V extends Comparable<V>> {

        private V value;
        private Node<V> left;
        private Node<V> right;
        private Node<V> parent;

        public V getValue() {
            return value;
        }

        public Node<V> getLeft() {
            return left;
        }

        public Node<V> getRight() {
            return right;
        }

        public  Node(V value){
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}
