package DataStructure.Tree.BinarySortTree;

import DataStructure.Tree.TreeOperation;


/**
 * @author: Jerssy
 * @create: 2021-04-05 17:11
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 二叉排序树
 *
 * 二叉排序树：BST: (Binary Sort(Search) Tree), 对于二叉排序树的任何一个非叶子节点，要求左子节点的值比当前节点的值小，右子节点的值比当前节点的值大。
 *
 * 特别说明：如果有相同的值，可以将该节点放在左子节点或右子节点
 */
public class BinarySortTree<K extends Comparable<K>> {

    private  Node<K> root;

    public static void main(String[] args) {

        int[] nodeArray = new int[]{6,5,9,8,3,0,5,11,32,35,28,30};

        BinarySortTree<Integer> binarySortTree = new BinarySortTree<>();
        for (int j : nodeArray) {
            binarySortTree.addNode(j);
        }
        TreeOperation<Node<Integer>> objectTreeOperation = new TreeOperation<>();
        objectTreeOperation.show(binarySortTree.root);

        System.out.println("删除了"+binarySortTree.deleteNode(9));
        objectTreeOperation.show(binarySortTree.root);
        System.out.println("删除了"+binarySortTree.deleteNode(8));
        objectTreeOperation.show(binarySortTree.root);
        System.out.println("删除了"+binarySortTree.deleteNode(6));
        objectTreeOperation.show(binarySortTree.root);
        System.out.println("删除了"+binarySortTree.deleteNode(5));


        objectTreeOperation.show(binarySortTree.root);
    }


    /*
     * @param: [key]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/6 13:10
     * @description:添加
     */
    private  void addNode(K key){
        Node<K> currentNode = new Node<>(key);
        Node<K> parentNode = searchLeafNode(currentNode);
        currentNode.parent=parentNode;
        if (currentNode.parent!=null){

            if (parentNode.key.compareTo(currentNode.key)<0){
                parentNode.right=currentNode;
            }
            if (parentNode.key.compareTo(currentNode.key)>0){
                parentNode.left=currentNode;
            }

        }
        else  root=currentNode;


//        if(root == null) {
//            root = currentNode;//如果root为空则直接让root指向node
//        } else {
//            addNode(currentNode,root);
//        }

    }

    private void   inOrderPrint(Node<K> node){
        if (node==null) return;

        inOrderPrint(node.left);
        System.out.println(node);
        inOrderPrint(node.right);
    }

    /*
     * @param: [key]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/6 13:11
     * @description:  删除节点
     */
    private K  deleteNode(K key){
        if (key==null) return null;

        Node<K> deleteNode=searchNode(key,root);

        if (deleteNode == null) return null;

        Node<K> successorNode,replaceNode;

        if (deleteNode.left!=null&&deleteNode.right!=null){//左右孩子都不为空则找后继或者前驱节点进行替换，后继节点只会有一个字节点或者没有子节点
            successorNode = getSuccessorNode(deleteNode);
            deleteNode.key=successorNode.key;

        } else successorNode=deleteNode;

        //替换节点是后继节点的某个孩子节点，
        replaceNode=successorNode.left!=null?successorNode.left:successorNode.right;

        Node<K> rParentNode=successorNode.parent;
        if (rParentNode==null){

            root=replaceNode;

            if (replaceNode != null) replaceNode.parent=null;

        }
        else {

            if (rParentNode.left==successorNode){//删除后继节点，用替换节点连接后继节点的父节点
                rParentNode.left= replaceNode;
            }
            else  rParentNode.right=replaceNode;

            if (replaceNode!=null)  replaceNode.parent=rParentNode;

            successorNode.left=successorNode.right= successorNode.parent=null;
        }

        return key;
    }

    /*
     * @param: [searchValue, nodeP]
     * @return: DataStructure.Tree.BinarySortTree.BinarySortTree.Node<K>
     * @author: Jerssy
     * @dateTime: 2021/4/6 13:50
     * @description:查找节点
     */
    private Node<K>  searchNode(K searchValue, Node<K> nodeP){

        if (nodeP == null) {
            return null;
        }

        if (nodeP.key.compareTo(searchValue)>0){

            return  searchNode(searchValue,nodeP.left);
        }
        else if (nodeP.key.compareTo(searchValue) <0) {

            return searchNode(searchValue,nodeP.right);
        }
        else {

            return nodeP;
        }

    }

    /*
     * @param: [currentNode]
     * @return: DataStructure.Tree.BinarySortTree.BinarySortTree.Node<K>
     * @author: Jerssy
     * @dateTime: 2021/4/6 10:13
     * @description: 查找叶子节点
     */
    private Node<K> searchLeafNode(Node<K> currentNode){
        Node<K> tempNode=root,LeafNode = null;
        while (tempNode!=null){
            LeafNode=tempNode;
            if (currentNode.key.compareTo(tempNode.key)<0){
                tempNode=tempNode.left;
            }
            else if (currentNode.key.compareTo(tempNode.key)>0){
                tempNode=tempNode.right;
            }
            else {

                if (tempNode.right == null) {
                    tempNode.right = currentNode;
                }

                else if (tempNode.left == null){
                    tempNode.left = currentNode;
                }

                return tempNode;
            }

        }

        return LeafNode;
    }

    /*
     * @param: [node, nodeP]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/6 15:22
     * @description:递归添加节点
     */
    public void addNode(Node<K> node,Node<K> nodeP) {

         if (nodeP.key.compareTo(node.key)>0){
             if (nodeP.left==null){
                 nodeP.left = node;
             }else  addNode(node,nodeP.left);
         }
         else {
             if (nodeP.right == null) {
                 nodeP.right = node;
             }
             else  addNode(node, nodeP.right);
         }

    }

    /*
     * @param: [currentNode]
     * @return: DataStructure.Tree.BinarySortTree.BinarySortTree.Node<K>
     * @author: Jerssy
     * @dateTime: 2021/4/6 14:07
     * @description: 找currentNode的前驱节点
     */
    private Node<K>  getPredecessorNode(Node<K> currentNode){

        if (currentNode == null) return null;

        if (currentNode.left!=null){//左子树不为空则找左子树最大的节点
            Node<K> tempNode=currentNode.left;
            while (tempNode.right != null){
                tempNode=tempNode.right;
            }
            return tempNode;
        }
        else {//从右子树找；判断当前节点与父节点关系；1 当前节点是父节点的右孩子即父节点即为前驱节点 2 不是右孩子则向上找一个节点P,其是节点Q的右子节点，则Q即为前驱节点
            Node<K> nodeP=currentNode.parent,tempNode=currentNode;
            while (nodeP!=null&&nodeP.right!=tempNode){
                nodeP=nodeP.parent;
                tempNode=nodeP;
            }
            return  nodeP;
        }

    }

   /*
     * @param: [currentNode]
     * @return: DataStructure.Tree.BinarySortTree.BinarySortTree.Node<K>
     * @author: Jerssy
     * @dateTime: 2021/4/6 14:08
     * @description:  找currentNode的后继节点
     */
    private  Node<K> getSuccessorNode(Node<K> currentNode){

        if (currentNode == null) return null;

         if (currentNode.right != null) {//右子树不为空则找右子树最小的节点
             Node<K> tempNode=currentNode.right;
             while  (tempNode.left!=null){
                 tempNode = tempNode.left;
             }
             return tempNode;
         }
         else {//从左子树找；判断当前节点与父节点关系；1 当前节点是父节点的左孩子即父节点即为前驱节点 2 不是左孩子则向上找一个节点P,其是节点Q的左子节点，则Q即为后继节点
              Node<K> nodeP=currentNode.parent, tempNode = currentNode;
              while (nodeP!=null&&nodeP.left != tempNode) {
                  nodeP = nodeP.parent;
                  tempNode = nodeP;
              }
              return nodeP;
         }
    }

    static class Node<K extends Comparable<K>> {

        private K key;
        private Node<K> left;
        private Node<K> right;
        private Node<K> parent;

        public K getKey() {
            return key;
        }

        public Node<K> getLeft() {
            return left;
        }

        public Node<K> getRight() {
            return right;
        }

        public  Node(K key){
            this.key = key;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    '}';
        }
    }
}
