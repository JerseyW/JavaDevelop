package DataStructure.Linked;

import java.util.Objects;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 约瑟夫问题
 * @create 2021-02-16 17:10
 *
 *
 * Josephu  问题为：设编号为1，2，… n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，
 * 数到m 的那个人出列，它的下一位又从1开始报数，数到m的那个人又出列，
 * 依次类推，直到所有人出列为止，由此产生一个出队编号的序列。
 *
 *
 *
 * 思路：
 * 用一个不带头结点的循环链表来处理Josephu 问题：先构成一个有n个结点的单循环链表，
 * 然后由k结点起从1开始计数，计到m时，对应结点从链表中删除，
 * 然后再从被删除结点的下一个结点又从1开始计数，直到最后一个结点从链表中删除算法结束。
 *
 */

//环形链表解决约瑟夫问题
public class JosephuProblem {

    private  int size;
    private Node<Integer> head;
    private Node<Integer> tail;


    private  final static   int  n= 5;//创建链表个数
    private  final  static  int m=2 ;//数到m的出队列
    private  final  static  int k=1 ;//约定的编号

    public static void main(String[] args) {

        JosephuProblem josephuProblem=new JosephuProblem();
        for (int i = 1; i <=n; i++) {

            josephuProblem.addNode(i);
        }

         System.out.println(josephuProblem);

         int item = josephuProblem.play();
         System.out.println("最后的小孩："+item);
         System.out.println(josephuProblem);

    }

     public  int play1( ){

         Node<Integer> startNode = searchCurrentNode(k);//当前约定的编号节点
         System.out.println(startNode);
         int count=1;
         Node<Integer> currentNode=startNode;
         while(currentNode.next !=currentNode){

             if (count==m-1){//数数的前一个，自己位置的算一下，指针只移动了m-1次

                 System.out.println("出圈的是："+currentNode.next.item);
                 currentNode.next=currentNode.next.next;//下一个则需要出圈，删除出圈小孩
                 count=1;
                 size--;

             }else {
                 count++;
             }
             currentNode=currentNode.next;
         }

         head=tail=currentNode;
         return  currentNode.item;
     }

    public  int play(){

        Node<Integer> previousNode = searchCurrentNode(k-1);//当前约定的编号前一个节点
        System.out.println(previousNode);
        int count=1;
        Node<Integer> currentNode=previousNode.next;//当前节点

        while(currentNode.next !=currentNode){

            if (count==m){
                System.out.println("出圈的是："+currentNode.item);
                previousNode.next=currentNode.next;
                count=1;
                size--;

            }else {
                previousNode=currentNode;
                count++;
            }
            currentNode=currentNode.next;
        }

        head=tail=currentNode;
        return  currentNode.item;
    }
    //节点类;
      static class Node<Integer>{

           int item;
           Node<Integer > next;

          public Node(int item) {
               this.item = item;
           }
          @Override
          public String toString() {
              return "Node{" +
                      "item=" + item +
                      '}';
          }
      }

    //打印链表
    @Override
    public  String toString() {
        if (head==null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
         Node<Integer> node = head;
        while (true){
              sb.append(String.valueOf(node.item).strip());
              if (node.next == head ) {
                  break;
              }
              sb.append("-->");
              node=node.next;
         }

        return sb.toString();
    }


      //添加
      public  void  addNode(int item){
          Node<Integer> node= new Node<>(item);
          Node<Integer> last=tail;
          if (head==null){
              head=tail=node;
          }
          else{
              last.next=node;
              tail=node;
          }
          tail.next=head;
          size++;
      }


     //查找当前index的元素
    public  Node<Integer> searchCurrentNode(int index){
        Objects.checkIndex(index,size+1);
        Node<Integer> node=head;
        if(index==0){
            return  tail;
        }
        int i=1;
        while (index!=1&&i<index){
            node=node.next;
            i++;
        }

        return node;
    }
}
