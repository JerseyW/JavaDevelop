package DataStructure.Tree.BinaryTree;

import DataStructure.Tree.TreeOperation;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author: Jerssy
 * @create: 2021-03-27 9:47
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 树
 *
 * 为什么需要树这种数据结构
 *
 * 数组存储方式的分析
 * 优点：通过下标方式访问元素，速度快。对于有序数组，还可使用二分查找提高检索速度。
 * 缺点：如果要检索具体某个值，或者插入值(按一定顺序)会整体移动，效率较低
 *
 * 链式存储方式的分析
 * 优点：在一定程度上对数组存储方式有优化(比如：插入一个数值节点，只需要将插入节点，链接到链表中即可， 删除效率也很好)。
 * 缺点：在进行检索时，效率仍然较低，比如(检索某个值，需要从头节点开始遍历)
 *
 *树存储方式的分析
 * 能提高数据存储，读取的效率,  比如利用二叉排序树(Binary Sort Tree)，既可以保证数据的检索速度，同时也可以保证数据的插入，删除，修改的速度。
 *
 * A 二叉树的概念：
 *
 * 树有很多种，每个节点最多只能有两个子节点的一种形式称为二叉树。
 *
 * 二叉树的子节点分为左节点和右节点。
 *
 * 如果该二叉树的所有叶子节点都在最后一层，每个节点的孩子节点要么没有，要么就是两个，不允许出现单个孩子的情况，并且结点总数= 2^n -1 , n 为层数，则我们称为满二叉树。
 *
 * 如果该二叉树的所有叶子节点都在最后一层或者倒数第二层，而且最后一层的叶子节点在左边连续，倒数第二层的叶子节点在右边连续，每层节点添加，必须是从左到右，不允许跳着添加 。我们称为完全二叉树。
 *
 * 二叉树的基本属性：
 *
 * ①结点：包含一个数据元素及若干指向子树分支的信息。
 * ②结点的度：一个结点拥有子树的数目称为结点的度。
 * ③叶子结点：也称为终端结点，没有子树的结点或者度为零的结点。
 * ④分支结点：也称为非终端结点，度不为零的结点称为非终端结点。
 * ⑤树的度：树中所有结点的度的最大值。
 * ⑥结点的层次：从根结点开始，假设根结点为第1层，根结点的子节点为第2层，依此类推，如果某一个结点位于第L层，则其子节点位于第L+1层。
 * ⑦树的深度：也称为树的高度，树中所有结点的层次最大值称为树的深度。
 * ⑧有序树：如果树中各棵子树的次序是有先后次序，则称该树为有序树。
 * ⑨无序树：如果树中各棵子树的次序没有先后次序，则称该树为无序树。
 * ⑩森林：由m（m≥0）棵互不相交的树构成一片森林。如果把一棵非空的树的根结点删除，则该树就变成了一片森林，森林中的树由原来根结点的各棵子树构成
 *
 * 二叉树特点：
 *
 * (01) 每个节点有零个或多个子节点；
 * (02) 没有父节点的节点称为根节点；
 * (03) 每一个非根节点有且只有一个父节点；
 * (04) 除了根节点外，每个子节点可以分为多个不相交的子树。
 *
 * 二叉树的性质：
    性质1：二叉树第i层上的结点数目最多为 2^(i-1) (i≥1)。
    性质2：深度为k的二叉树至多有2^k-1个结点(k≥1)。
    性质3：包含n个结点的二叉树的高度至少为log2 (n+1)。
    性质4：在任意一棵二叉树中，若叶子结点的个数为n0，度为2的结点数为n2，则n0=n2+1。即度为0的节点个数总比度为2的节点个数多一个
    性质5：若对一棵有n个节点的完全二叉树进行顺序编号（1≤i≤n），那么，对于编号为i（i≥1）的节点：
          当i=1时，该节点为根，它无双亲节点。
          当i>1时，该节点的双亲节点的编号为i/2。
          若2i≤n，则有编号为2i的左节点，否则没有左节点。
          若2i+1≤n，则有编号为2i+1的右节点，否则没有右节点。

 二叉树的遍历：

 * 前序遍历：先遍历父节点，在输出左子树然后输出右子树
 * 中序遍历：先遍历左子树，然后输出父节点和右子树
 * 后续遍历：先遍历左子树和右子树，然后输出父节点
 *
 * 输出父节点的顺序即可确定是前序 中序或者后序
 *
 * B 顺序存储二叉树
 *    数组的存储方式和树的存储方式可以互相转换
 *    在遍历数组的时候，仍然可以前序遍历，中序遍历和后序遍历的方式来完成二叉树节点的遍历
 *
 *  1 顺序二叉树通常只考虑完全二叉树
 *
 *  2 第n个元素的左子节点在数组中的索引为2 * n + 1 (n>=0)
 *
 *  3 第n个元素的右子节点在数组中的索引为  2 * n + 2(n>=0)
 *
 *  4 第n个元素的父节点在数组中的索引为  (n-1) / 2(n>=1)
 *
 * C 线索化二叉树
 *
 *n个结点的二叉链表中含有n+1  【公式 2n-(n-1)=n+1】 个空指针域。利用二叉链表中的空指针域，存放指向该结点在某种遍历次序下的前驱和后继结点的指针（这种附加的指针称为"线索"）
 *
  这种加上了线索的二叉链表称为线索链表，相应的二叉树称为线索二叉树(Threaded BinaryTree)。根据线索性质的不同，线索二叉树可分为前序线索二叉树、中序线索二叉树和后序线索二叉树三种

 * 一个结点的前一个结点，称为前驱结点
 * 一个结点的后一个结点，称为后继结点
 *
 * D 二叉搜索树(二叉排序树)节点属性：
 *
 * （1）若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * （2）若右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 * （3）左、右子树也分别为二叉排序树；
 * （4）没有键值相等的结点。
 *
 */
public class TreeTest<T> {

    private Node<T> root;
    private Node<T> preNode;   //线索化时记录前一个节点

    public  TreeTest(){

    }

    public static void main(String[] args) {

        TreeTest<Integer> treeTest=new TreeTest<>();
        Integer[] treeArray = new Integer[]{12,2,35,78,3,0,62 ,34};
        treeTest.root=treeTest.createTree(treeArray, 0);
        TreeOperation<Node<Integer>> tree = new TreeOperation<>();
        tree.show(treeTest.root);
        System.out.println(treeTest.lowestCommonAncestor(34, 35 ));

        System.out.println("二叉树的深度为："+treeTest.getTreeDepth(treeTest.root));

        treeTest.preOrderTree  (treeTest.root);
        System.out.println("");
        treeTest.preOrderTree1(treeTest.root);
        System.out.println();
        treeTest.inOrderTree(treeTest.root);
        System.out.println();
        treeTest.inOrderTree1(treeTest.root);
        System.out.println();
        treeTest.postOrderTree(treeTest.root);
        System.out.println();
        treeTest.postOrderTree1(treeTest.root);
        System.out.println();
        treeTest.levelTree(treeTest.root);
        System.out.println();
         treeTest.remove( 35 );

          tree.show( treeTest.root);
//        treeTest.preClueTree(treeTest.root);
//        treeTest.printPreClueTree();
//
//        treeTest.inClueTree(treeTest.root);
//        treeTest.printInClueTree();

        treeTest.postClueTree(treeTest.root);
        treeTest.printPostTreeNode();

    }

   private static  class  Node<T> {

        private  T value;
        private  Node<T> left;
        private  Node<T> right;
        private  Node<T> parent;
        boolean isLeftClue = false;   //左指针域类型  false：指向子节点、true：前驱或后继线索
        boolean isRightClue = false;  //右指针域类型  false：指向子节点、true：前驱或后继线索


        public  Node (T value){
            this.value = value;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    /*
     * @param: [nodeArr, index]
     * @return: DataStructure.BinarySortTree.TreeTest.Node<T>
     * @author: Jerssy
     * @dateTime: 2021/3/29 13:15
     * @description: 使用顺序存储二叉树的特点创建二叉树节点
     */

    public  Node<T>  createTree(T[] nodeArr, int index) {

        if (nodeArr.length==0||index>nodeArr.length-1) {
            return null;
        }

        Node<T> node = new Node<>(nodeArr[index]);

        node.left=createTree(nodeArr,index*2+1);

        node.right=createTree(nodeArr,index*2+2);

        if(node.left != null) {
            node.left.parent = node;
        }

        if(node.right != null) {
            node.right.parent = node;
        }


        return node;
    }

    /*
     * @param: []
     * @return: int
     * @author: Jerssy
     * @dateTime: 2021/3/29 13:16
     * @description: 获取二叉树深度
     */
    public  int getTreeDepth(Node<T> node) {
        return  node==null?0:Math.max(getTreeDepth(node.left),getTreeDepth(node.right))+1;
    }

    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/29 13:27
     * @description:  递归前序遍历树
     */
    public void preOrderTree(Node<T> node){


        if(node == null) return;
        if(node==root){

            System.out.print("前序遍历：");
        }
        System.out.print(node+"-->");

        preOrderTree(node.left);
        preOrderTree(node.right);

    }


    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/29 16:31
     * @description:前序遍历非递归
     */
    public void  preOrderTree1(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();
        stack.push(node);
        System.out.print("前序遍历非递归：");
        while (!stack.isEmpty()) {
            Node<T> currentNode = stack.pop();

            System.out.print(currentNode+"-->");
            if (currentNode.right!=null) {
                stack.push(currentNode.right);
            }
            if (currentNode.left!=null) {
                stack.push(currentNode.left);
            }
        }
    }


    public void  preOrderTree2(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();

        System.out.print("前序遍历非递归：");

        while ( node!=null||!stack.isEmpty()) {

           while (node!=null){
               stack.push(node);
               System.out.print(node+"-->");
               node = node.left;
           }
            node = stack.pop();

            node = node.right;
        }
    }
    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/29 16:44
     * @description: 中序递归遍历二叉树
     */
    public void  inOrderTree(Node<T> node){
        if (node == null) return;

        if (node == root) {
            System.out.print("中序遍历递归：");
        }

        inOrderTree(node.left);
        System.out.print(node+"-->");
        inOrderTree(node.right);
    }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/29 16:49
     * @description:中序非递归遍历
     */
    public  void inOrderTree1(Node<T> node) {
        Stack<Node<T>> stack = new Stack<>();

        System.out.print("中序遍历非递归：");

        while (node != null||!stack.isEmpty()) {
             while (node!=null ){
                 stack.push(node);
                 node=node.left;
             }
            if(!stack.isEmpty()){
                node = stack.pop();
                 System.out.print(node+"-->");
                node = node.right;
            }
        }
    }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/29 17:23
     * @description: 后序递归遍历
     */
    public  void  postOrderTree(Node<T> node){
        if (node==null) return;
        if (node == root) {
            System.out.print("后序遍历递归：");
        }
        postOrderTree(node.left);
        postOrderTree(node.right);
        System.out.print(node+"-->");
    }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/29 17:24
     * @description: 后序非递归遍历
     */
    public  void  postOrderTree1(Node<T> node) {

        Stack<Node<T>> stack = new Stack<>();

        Node<T> flagNode=null;
        System.out.print( "后序遍历非递归：");
        while (node != null||!stack.isEmpty()) {
             while (node!=null ){
                 stack.push(node);
                 node = node.left;

             }
            if(!stack.isEmpty()){
                node = stack.peek();
                if (node.right==null||flagNode==node.right){
                    System.out.print(node+"-->");
                    flagNode=node;
                    node=null;
                    stack.pop();
                }
                else {

                    node = node.right;
                }
            }
        }
    }


   /*
    * @param: [node]
    * @return: void
    * @author: Jerssy
    * @dateTime: 2021/3/29 18:37
    * @description: 层次打印
    */
    public  void  levelTree(Node<T> node){

        LinkedList<Node<T>> list = new LinkedList<>();
        list.add(node);
        System.out.print("层次遍历：");
        while (!list.isEmpty()){
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Node<T> poll = list.pollFirst();
                System.out.print(poll+"-->");
                if (poll!=null) {
                    if (poll.left!=null) {
                        list.add(poll.left);
                    }
                    if (poll.right != null) {
                        list.add(poll.right);
                    }
                }
            }
        }
    }

    /*
     * @param: [value]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/30 15:20
     * @description: 删除节点
     */
    public void remove(T value){

        Node<T> preSuccessorNode,deleteNode;
        Node<T> preSuccessorNodeParent;
        if(value==null) return;

         deleteNode= searchTreeNode(root, value);
          if (deleteNode!=null){
                if (deleteNode.left==null&&deleteNode.right == null) {
                   preSuccessorNodeParent=deleteNode.parent;
                   preSuccessorNode=deleteNode;
                }

                else {
                   preSuccessorNode = searchTreeNode(deleteNode, null);
                    if (preSuccessorNode == null) {
                        return;
                    }
                    preSuccessorNodeParent=preSuccessorNode.parent;

                }

              deleteNode.value = preSuccessorNode.value;

              if (preSuccessorNodeParent.left == preSuccessorNode) {
                  preSuccessorNodeParent.left = null;
              }
              else preSuccessorNodeParent.right = null;

         }

    }

    /*
     * @param: [node, value]
     * @return: DataStructure.BinarySortTree.TreeTest.Node<T>
     * @author: Jerssy
     * @dateTime: 2021/3/31 9:12
     * @description: 递归查找value节点
     */
   private   Node<T> searchTreeNode(Node<T> node, T value){


       if ( node.value==value) return node;


       if (node.left !=null){
           Node<T> lNode = searchTreeNode(node.left, value);

           if ((lNode!=null && lNode.value == value) || value == null) {

               return  lNode;
           }

       }

        if (node.right !=null){

          Node<T> rNode = searchTreeNode(node.right, value);


          if ((rNode!=null && rNode.value == value) || value == null) {

             return  rNode;
          }
          //右分支搜索完则整个树搜索完回溯到根节点说明此搜索节点不存在
          if (node == root) return null;
       }

        return  node;

  }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/30 17:52
     * @description: 前序线索化二叉树
     */
    public void   preClueTree(Node<T> node){

        if (node==null){
            return;
        }


        if (node.left == null){//当前节点的左子节点为空则需要线索化
            node.left=preNode;//记录前节点
            node.isLeftClue=true;
        }

        if (preNode!=null&&preNode.right==null){
            preNode.right=node;
            preNode.isRightClue=true;
        }

        preNode=node;//当前节点是下一个节点的前驱节点

        if(!node.isLeftClue) preClueTree(node.left);
        if(!node.isRightClue) preClueTree(node.right);

    }
    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/30 16:16
     * @description: 中序线索化二叉树
     */
    public void   inClueTree(Node<T> node){

        if (node==null){
            return;
        }
        inClueTree(node.left);

        if (node.left == null){
            node.left=preNode;//记录前节点
            node.isLeftClue=true;
        }

        if (preNode!=null&&preNode.right==null){
            preNode.right=node;
            preNode.isRightClue=true;
        }

        preNode=node;//当前节点是下一个节点的前驱节点

        inClueTree(node.right);

    }

    /*
     * @param: [node]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/30 20:04
     * @description: 后序线索化二叉树
     */
    public void   postClueTree(Node<T> node){
        if (node == null) {
            return;
        }
        if (!node.isLeftClue) postClueTree(node.left);


        if (!node.isRightClue) postClueTree(node.right);

        if (node.left == null){
            node.left=preNode;//记录前节点
            node.isLeftClue=true;
        }
        if (preNode!=null&&preNode.right == null){
            preNode.right = node;
            preNode.isRightClue=true;
        }
        preNode=node;
    }

    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/30 20:00
     * @description: 前序线索化遍历二叉树
     */
    public void printPreClueTree(){
        Node<T>  clueNode=root;
        preNode=null;
        System.out.print("前序线索化遍历二叉树:");


        while (clueNode!=null){

            while (!clueNode.isLeftClue){
                System.out.print(clueNode+"-->");
                clueNode=clueNode.left;
            }

            System.out.print(clueNode+"-->");
            clueNode=clueNode.right;
        }
//        System.out.println(clueNode);
//        if (!clueNode.isLeftClue)  printPreClueTree(clueNode.left);
//        if (!clueNode.isRightClue)  printPreClueTree(clueNode.right);

    }

    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/30 17:54
     * @description:中序遍历线索化二叉树
     */
    public void printInClueTree(){
        Node<T>  clueNode=root;
        preNode=null;
        System.out.print("中序遍历线索化二叉树:");

        while (clueNode!=null){

            while (!clueNode.isLeftClue){

                clueNode=clueNode.left;
            }

            System.out.print(clueNode+"-->");
            while (clueNode.isRightClue){
                clueNode = clueNode.right;
                System.out.print(clueNode+"-->");
            }
            clueNode=clueNode.right;
        }

    }

    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/30 20:11
     * @description: 后序遍历线索化二叉树
     */
    public  void  printPostTreeNode(){
        Node<T>  clueNode=root;

        System.out.print("后序遍历线索化二叉树:");
        while (clueNode!=null&&!clueNode.isLeftClue) {
            clueNode=clueNode.left;
        }
        System.out.print(clueNode+"-->");
        preNode=null;
        while (clueNode!=null){
            if (clueNode.isRightClue) {
                preNode=clueNode;
                clueNode=clueNode.right;
                System.out.print(clueNode+"-->");
            }
            else {
                if (clueNode.right == preNode){

                    if (clueNode==root){
                        System.out.print(clueNode+"-->");
                        return;
                    }
                    preNode=clueNode;
                    clueNode=clueNode.parent;
                }
                else {
                    clueNode = clueNode.right;
                    while (clueNode!=null&&!clueNode.isLeftClue) {
                        clueNode = clueNode.left;
                        System.out.print(clueNode+"-->");
                    }
                }
            }
        }
        preNode=null;
    }


   /*
    * @param: [root, p, q]
    * @return: DataStructure.Tree.BinaryTree.TreeTest.Node<T>
    * @author: Jerssy
    * @dateTime: 2021/3/30 22:33
    * @description: //找到二叉树中两个指定节点的最近公共祖先
    */
   private Node<T> lowestCommonAncestor(Node<T> node, Node<T> p, Node<T> q) {


        if (node == p || node == q) {//1.如果node==p，q其中一个那么公共节点是先遇到的节点
            return node;
        }
        Node<T> left = null; Node<T> right = null;
        if (node.left !=null) {
            left = lowestCommonAncestor(node.left, p, q);
        }
        if (node.right != null) {
           right= lowestCommonAncestor(node.right, p, q);
        }

        if (left != null && right != null) {//左右都找到了，说明最近公共祖先就是node
            return node;
        } else if (left == null) {//右边找到

            return right;
        } else { //左边找到

            return left;
        }

   }

   public  Node<T> lowestCommonAncestor(T p,T q){

       Node<T> pNode = searchTreeNode(root, p);
       Node<T> qNode = searchTreeNode(root, q);

       if (pNode == null || qNode == null) return null;

       return  lowestCommonAncestor(root,pNode,qNode);

   }

}
