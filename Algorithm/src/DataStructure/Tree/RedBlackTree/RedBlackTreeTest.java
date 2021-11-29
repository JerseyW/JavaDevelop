package DataStructure.Tree.RedBlackTree;

import DataStructure.Tree.TreeOperation;

/**
 * @author: Jerssy
 * @create: 2021-04-24 17:18
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description:
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {
        RedBlackTree<Integer,Integer> myRBTree=new RedBlackTree<>();
        int[] treeNodes=new int[]{1,1,23,0,56,98,12,45,25,66,128,68,15,100};
        for (int treeNode : treeNodes) {
            myRBTree.insert(treeNode, treeNode);
        }
        myRBTree.inOrderPrint();

        TreeOperation<RedBlackTree.RBode<Integer,Integer>> treeOperation = new TreeOperation<>();
        treeOperation.show(myRBTree.getRoot());
        System.out.println();
        System.out.println(myRBTree.delete(1));
        treeOperation.show(myRBTree.getRoot());
        System.out.println(myRBTree.delete(100));
        treeOperation.show(myRBTree.getRoot());
        System.out.println(myRBTree.delete(128));
        treeOperation.show(myRBTree.getRoot());

        System.out.println("删除了"+myRBTree.delete(23)+"节点");
        System.out.println("删除了"+myRBTree.delete(68)+"节点");
         System.out.println(myRBTree.delete(98));
          System.out.println(myRBTree.delete(15));
          System.out.println(myRBTree.delete(66));
          System.out.println(myRBTree.delete(12));
          System.out.println(myRBTree.delete(0));
          System.out.println(myRBTree.delete(25));
         treeOperation.show(myRBTree.getRoot());
    }
}
