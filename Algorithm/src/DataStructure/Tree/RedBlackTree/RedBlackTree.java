package DataStructure.Tree.RedBlackTree;

import java.util.Objects;

/**
 * @author: Jerssy
 * @create: 2021-04-24 9:44
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 红黑树节点类
 */
public class RedBlackTree<K extends Comparable<K>,V> {

    private  static  final boolean BLACK=false;
    private  static  final boolean RED=true;
    private RBode<K,V> root;

    public RBode<K,V> getRoot() {
        return  root;
    }

    private  RBode<K,V> getGrandpaNode(RBode<K,V> node){

        if (node != null&&node.parent != null){
            return node.parent.parent;
        }

        return null;
    }

    private  boolean isRed(RBode<K,V> node){
        if (node!=null){
            return  node.color==RED;
        }
        return false;
    }
    private  boolean isBlack(RBode<K,V> node){
        if (node!=null){
            return  node.color==BLACK;
        }
        return false;
    }

    private  RBode<K,V> getBrotherNode(RBode<K,V> node){

        if (node != null&&node.parent != null){
            if (node == node.parent.left){
                return  node.parent.right;
            }
            else return node.parent.left;
        }

        return null;
    }

    private  RBode<K,V> getUncleNode(RBode<K, V> node) {

        RBode<K,V> grandpaNode=getGrandpaNode(node);
        if (grandpaNode != null){

            if (node.parent == grandpaNode.left){
                return  grandpaNode.right;
            }
            else return grandpaNode.left;

        }

        return null;
    }

    private RBode<K,V> searchLeafNode(RBode<K,V> node){

        RBode<K,V> tempNode=root,parentNode = null;
        while (tempNode != null) {
            parentNode=tempNode;
            if (tempNode.key.compareTo(node.key) < 0){
                tempNode=tempNode.right;
            }
            else  if (tempNode.key.compareTo(node.key) > 0){
                tempNode=tempNode.left;
            }
            else {
                tempNode.setValue(node.getValue());
                return null;
            }
         }

        return  parentNode;
    }

    public void insert(K key, V value){
        Objects.requireNonNull(key);
        RBode<K,V> insertNode= new RBode<>(key, value,RED);

        RBode<K,V> nodeP = searchLeafNode(insertNode);

        if (nodeP==null){
            root=insertNode;

        }
        else {
            if (insertNode.key.compareTo(nodeP.key) < 0){
                 nodeP.left=insertNode;
            }
            else {
                nodeP.right=insertNode;
            }
            insertNode.parent=nodeP;
        }
        if (nodeP!=null&&nodeP.color==RED){
            insertFlxUpNode(insertNode);
        }

    }

    private void   leftRotate(RBode<K,V> node){

        RBode<K,V> nodeR = node.right;
        if (nodeR == null) return;

        node.right = nodeR.left;
        if (nodeR.left != null){
            nodeR.left.parent=node;
        }

        if (node.parent != null){
            if (node.parent.left == node){
                node.parent.left = nodeR;
            }
            else  node.parent.right = nodeR;

            nodeR.parent=node.parent;
        }
        else {
            root=nodeR;
            root.parent=null;
        }

        nodeR.left = node;
        node.parent = nodeR;
    }

    private  void  rightRotate(RBode<K,V> node){

        RBode<K,V> nodeL=node.left;
        if (nodeL == null) return;

        node.left=nodeL.right;
        if (nodeL.right!=null){
            nodeL.right.parent=node;
        }
        if (node.parent!=null){
            if (node.parent.left == node) {
                node.parent.left=nodeL;
            }
            else  node.parent.right=nodeL;

            nodeL.parent=node.parent;
        }
        else  {
            root=nodeL;
            root.parent=null;
        }

        nodeL.right=node;
        node.parent=nodeL;

    }


    private  void insertFlxUpNode(RBode<K,V> node){

        RBode<K,V> uncleNode=getUncleNode(node);
        root.setColor(BLACK);
        if (isRed(node.parent)){
            RBode<K,V> grandpaNode=getGrandpaNode(node);
            if (grandpaNode!=null){
                if (isRed(uncleNode)) {
                    uncleNode.setColor(BLACK);
                    node.parent.setColor(BLACK);
                    grandpaNode.setColor(RED);

                    insertFlxUpNode(grandpaNode);

                }
                else {
                    if (grandpaNode.left == node.parent){
                        if (node.parent.right == node){
                            leftRotate(node.parent);
                            insertFlxUpNode(node.parent);
                            return;
                        }

                        node.parent.setColor(BLACK);
                        grandpaNode.setColor(RED);
                        rightRotate(grandpaNode);
                    }
                    else {
                        if (node.parent.left == node){
                            rightRotate(node.parent);
                            insertFlxUpNode(node.parent);
                        }

                        node.parent.setColor(BLACK);
                        grandpaNode.setColor(RED);
                        leftRotate(grandpaNode);
                    }
                }
            }

        }
    }


    public  K delete(K key){

        Objects.requireNonNull(key);

        RBode<K, V> deleteNode = searchNode(key, root);
        if (deleteNode!=null){
           return deleteNode(deleteNode);
        }
        return  null;
    }

    private K  deleteNode(RBode<K,V> deleteNode){

        RBode<K, V> successorsNode;
        K oldKey=deleteNode.key;
        if (deleteNode.left!=null&&deleteNode.right != null){
            successorsNode = getSuccessorsNode(deleteNode);
            deleteNode.key=successorsNode.key;
            deleteNode.value=successorsNode.value;
        }
        else successorsNode=deleteNode;

        RBode<K, V> replaceNode=successorsNode.left!=null?successorsNode.left:successorsNode.right;
        RBode<K, V>  parentNode = successorsNode.parent;
        if (parentNode==null){
            root=replaceNode;
            if (replaceNode!=null) {
                replaceNode.parent=null;
                root.setColor(BLACK);
            }
        }
        else {

            if (replaceNode!=null){ //后继有替换节点则删除后继将替换节点连接后继的父节点，并染黑
                replaceNode.parent = parentNode;
                replaceNode.setColor(BLACK);
            }
            else {//没有替换节点直接删除后继节点，如果是黑色则先修复，然后删除
                if (isBlack(successorsNode)){
                    deleteFlxUp(successorsNode);
                }
            }

             if (parentNode.left==successorsNode){
                 parentNode.left=replaceNode;
             }
             else parentNode.right = replaceNode;

             successorsNode.left=successorsNode.right=successorsNode.parent=null;

        }

        return oldKey;
    }

    private  void  deleteFlxUp(RBode<K,V> flxNode){

        if (isBlack(flxNode)){
            if(flxNode.parent!=null){
                if (flxNode.parent.left==flxNode){
                    flxUpLeft(flxNode);
                }else {
                    flxUpRight(flxNode);
                }
            }
        }

    }

    private  void  flxUpLeft(RBode<K, V> flxNode){

        RBode<K,V> brotherNode=getBrotherNode(flxNode);
        if (isRed(brotherNode)){
            swapNodeColor(brotherNode, flxNode.parent);
            leftRotate(flxNode.parent);
            brotherNode=getBrotherNode(flxNode);
        }
        if (!isRed(brotherNode.left)&&!isRed(brotherNode.right)){
             if (isRed(flxNode.parent)){
                 swapNodeColor(brotherNode,flxNode.parent);
                 return;
             }
             brotherNode.setColor(RED);
             deleteFlxUp(flxNode.parent);
        }
        else {
              if (isRed(brotherNode.left)){
                  swapNodeColor(brotherNode, brotherNode.left);
                  rightRotate(brotherNode);
                  brotherNode=getBrotherNode(flxNode);
              }

              swapNodeColor(brotherNode, flxNode.parent);
              brotherNode.right.setColor(BLACK);
              leftRotate(flxNode.parent);

        }

    }

    private  void  flxUpRight(RBode<K,V> flxNode){
        RBode<K,V> brotherNode=getBrotherNode(flxNode);
        if (isRed(brotherNode)){
            swapNodeColor(brotherNode, flxNode.parent);
            rightRotate(flxNode.parent);
            brotherNode=getBrotherNode(flxNode);
        }
        if (!isRed(brotherNode.left)&&!isRed(brotherNode.right)){
            if (isRed(flxNode.parent)){
                swapNodeColor(brotherNode,flxNode.parent);
                return;
            }
            brotherNode.setColor(RED);
            deleteFlxUp(flxNode.parent);
        }
        else {
            if (isRed(brotherNode.right)){
                swapNodeColor(brotherNode, brotherNode.right);
                leftRotate(brotherNode);
                brotherNode=getBrotherNode(flxNode);
            }

            swapNodeColor(brotherNode, flxNode.parent);
            brotherNode.left.setColor(BLACK);
            rightRotate(flxNode.parent);

        }
    }

    private void swapNodeColor(RBode<K,V> node1,RBode<K,V> node2){

        if (node1!=null&&node2!=null) {
            boolean temp;
            temp=node1.color;
            node1.color=node2.color;
            node2.color=temp;
        }

    }

    private  RBode<K,V> getSuccessorsNode(RBode<K,V> currentNode){

        if (currentNode == null) {
            return null;
        }
        RBode<K,V> tempNode;
        if (currentNode.right!=null){
            tempNode=currentNode.right;
            while (tempNode.left != null){
                tempNode=tempNode.left;
            }

            return tempNode;

        }
        else {

            tempNode=currentNode.parent;
            while (tempNode!=null&&tempNode.left!=currentNode){
                tempNode=tempNode.parent;
                currentNode=tempNode;
            }
            return  tempNode;

        }
    }


    public  RBode<K,V> searchNode(K key,RBode<K,V> node){

        if (node==null){
            return null;
        }

        if (key.compareTo(node.key)<0){
            return  searchNode(key,node.left);
        }
        else if (key.compareTo(node.key) >0) {
            return  searchNode(key,node.right);
        }
        else {
            return node;
        }
    }

    public void inOrderPrint(){
        inOrderPrint(root);
    }

    private void inOrderPrint(RBode<K,V> node){
        if(node!=null){
            inOrderPrint(node.left);
            String str="(key:"+node.key+","+"value"+node.value+",color:"+node.color+")"+",";
            System.out.print(str);
            inOrderPrint(node.right);
        }
    }

    static class RBode<K extends Comparable<K>, V>   {

        K key;
        V value;
        private RBode<K,V> left;
        private RBode<K,V> right;
        private RBode<K,V> parent;
        private  boolean color;

        public RBode( K key, V value ,boolean color ) {
            this.key = key;
            this.value = value;
            this.color=color;

        }

        public RBode<K, V> getLeft() {
            return left;
        }

        public RBode<K, V> getRight() {
            return right;
        }

        public K getKey() {
            return key;
        }


        public V getValue() {
            return value;
        }


        public void setValue(V value) {
            this.value = value;
        }
        public void setColor(boolean color){
            this.color = color;
        }

        public boolean getColor() {
            return color;
        }

        @Override
        public String toString() {
            return "RBode{" +
                    "key=" + key +
                    ", value=" + value +
                    ", color=" + color +
                    '}';
        }
    }
}
