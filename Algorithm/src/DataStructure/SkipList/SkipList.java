package DataStructure.SkipList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Jerssy
 * @create: 2021-04-08 19:55
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 跳表实现
 */
public class SkipList<V extends Comparable<V>> {

    private  Node<V> head=new Node<>(null);

    public int level;

    public  static  final int MAX_LEVEL=16;

    ThreadLocalRandom random = ThreadLocalRandom.current();

   public SkipList(int level){
       this.level = level;
       initList();
   }

   public SkipList(){
       this(MAX_LEVEL);
   }

   /*
    * @param: []
    * @return: void
    * @author: Jerssy
    * @dateTime: 2021/4/9 16:56
    * @description: 初始化
    */
   private void initList() {
       int i = level;
       Node<V> temp = null;
       Node<V> prev = null;
       //从底部节点开始创建链表每个层级的头节点
       while (i-- != 0) {
           temp = new Node<>(null);
           temp.level= level-i;
           temp.down = prev;
           prev = temp;
       }
       head = temp;//最上层头节点
   }

   /*
    * @param: [value]
    * @return: DataStructure.SkipList.SkipList.Node<V>
    * @author: Jerssy
    * @dateTime: 2021/4/9 16:56
    * @description:  查找value
    */
    private Node<V> searchNode(V value){
        Node<V> headNode=head;
        while (headNode!=null){

            int isEqual = -1;

            while (headNode.next != null&&(isEqual=headNode.next.value.compareTo(value))<0)

                headNode = headNode.next;

            if(isEqual == 0){
                return  headNode.next;
            }

            else if (headNode.down==null){

                return headNode.next;
            }

            headNode=headNode.down;

        }

        return null;
    }

    // 随机函数
    private int randomLevel(){
        int k = 1;

        while(random.nextInt()%2 == 0){
                k ++;
        }

        return Math.min(k, MAX_LEVEL);

    }


    /*
     * @param: [value]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/9 16:56
     * @description:插入value
     */
    private  void  insertNode( V value){

        Node<V> tempNode=head;
        List<Node<V>> insertedLeveList=new ArrayList<>();
        while (tempNode!=null){

            //将每个层级需要插入的位置保存到集合中
            if (tempNode.next==null||tempNode.next.value.compareTo(value)>0){
                insertedLeveList.add( tempNode);
                tempNode=tempNode.down;

            }
            else  {
                tempNode=tempNode.next;

            }
        }

        int level =  randomLevel();
       // 如果随机的层级数，大于head节点的层级数
        if (level > this.level) {
            Node<V> temp = null;
            Node<V> prev = head;
            // 索引的层级数
            while (this.level++ != level) {
                temp = new Node<>(null);
                temp.level=this.level;
                insertedLeveList.add(0, temp);
                temp.down = prev;
                prev = temp;
            }
            head = temp;
            //更新层级
            this.level = level;
        }

        Node<V> downNode=null; Node<V> vNode ;Node<V> newNode;
        //这里的insertedLeveLists索引从0开始索引i=level-1开始
        for (int i = this.level-1;i>=0;i--) {//level的每一层都需要插入newNode
              newNode=new Node<>(value);
              vNode =insertedLeveList.get(i);
              newNode.next=vNode.next;
              newNode.level=vNode.level;
              vNode.next = newNode;
              newNode.down=downNode;
              downNode=newNode;
        }

    }


    /*
     * @param: [value]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/9 16:55
     * @description: 删除value
     */
    private  void  deleteNode(V value){

       if (value == null) {
           return;
       }

       Node<V> headNode=head;
       while (headNode != null) {

           //因为此跳表基于单链表，则需要找到删除的前一个进行指针的连接，如果是双链表的则直接定位到删除节点
           if (headNode.next!=null&&headNode.next.value.compareTo(value)==0){

              headNode.next=headNode.next.next;
              headNode=headNode.down;//继续向下层寻找--每个层级的都需要删除

          }
          else if (headNode.next!=null&&headNode.next.value.compareTo(value)<0) headNode=headNode.next;

          else  headNode=headNode.down;

       }

    }


    // 显示 表中的结点
    public void printNode(){
        Node<V> p = head;

        while (p.down!=null){
            p=p.down;
        }

        while(p.next!= null){
            p = p.next;
            System.out.println("打印第一层数据："+p+ " ");
        }

    }


    static class  Node<V extends Comparable<V>>{

         V value;
         Node<V> next;
         Node<V> down;
         int level;
         public  Node(V value){

             this.value = value;

         }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", level=" + level +
                    '}';
        }
    }


    public static void main(String[] args) {
        SkipList<Integer> skipList=new SkipList<>(4);
        skipList.insertNode(2);
        skipList.insertNode(4);
        skipList.insertNode(7);
        skipList.insertNode(1);
        skipList.insertNode(0);
        skipList.insertNode(12);
        skipList.insertNode(3);
        skipList.insertNode(5);
        skipList.printNode();
        System.out.println("************删除5**********");
        skipList.deleteNode(5);
        skipList.printNode();
        System.out.println("************查找 2**********");
        System.out.println(skipList.searchNode(3));
    }
}
