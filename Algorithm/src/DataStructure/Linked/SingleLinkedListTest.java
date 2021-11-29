package DataStructure.Linked;

import java.util.List;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 单链表测试类
 * @create 2021-02-10 11:49
 */
public class SingleLinkedListTest {

    public static void main(String[] args) {
        SingleLinkedList<Integer> linkedList=new SingleLinkedList<>();
        System.out.println("*******************尾部添加与头部添加*****************");
        linkedList.addLast(67);
        linkedList.addFirst(0);
        System.out.println("链表尾部"+linkedList.tail());
        System.out.println("链表头部"+linkedList.peek());
        System.out.println(linkedList.size());

        System.out.println(linkedList);
        System.out.println("*******************指定1位置添加集合*****************");
        linkedList.addAll(List.of(2,5,9,1) ,2);
        System.out.println(linkedList);

        System.out.println("*******************指定2位置添加多个元素*****************");
         linkedList.addAll(2,11,55,66,null);
        System.out.println(linkedList);
        System.out.println("*******************查找index=3位置的元素*****************");

        System.out.println("index=3位置的元素"+linkedList.searchCurrentNode(3));
        System.out.println("*******************查找给定值11的元素的前一个元素*****************");
        System.out.println("11的前一个元素："+linkedList.searchPreNode(11));
        System.out.println("*******************判断1元素首次链表中出现的位置*****************");
        System.out.println("1元素首次出现在链表中的位置:"+linkedList.indexOf(1));
        System.out.println("*******************判断1元素从7开始首次链表中出现的位置*****************");
        System.out.println("1元素从7开始首次出现在链表中的位置:"+linkedList.indexOf(1,7));

        System.out.println("*******************判断1元素反向搜索最后次出现在链表的位置*****************");
        System.out.println("1元素最后次出现在链表的位置:"+linkedList.lastIndexOf(1));
        System.out.println("*******************判断1元素从5开始反向搜索在链表中最后次出现的位置*****************");
        System.out.println("1元素从5开始反向搜索在链表中最后次出现的位置:"+linkedList.lastIndexOf(1,5));
        System.out.println("*******************从索引1开始删除3个元素,并返回删除元素集合*****************");

        System.out.println(linkedList.removeRange(1, 3));
        System.out.println(linkedList);
        System.out.println("*******************获取倒数第4节点*****************");
        System.out.println("倒数第4个节点："+linkedList.getLastIndexNode(4));
        System.out.println("*******************链表中间节点,长度为偶数时返回两个中的第一个节点*****************");
        System.out.println("链表中间节点:"+linkedList.getCenterNode(linkedList.peek()));
        System.out.println(linkedList);
        System.out.println("*******************设置为环形链表，默认是头尾相连接*****************");
        linkedList.setAnnularLinked(true);
        System.out.println("*******************是否有环*****************");

        System.out.println(linkedList.isHasAnnular());
        System.out.println("*******************返回环形链表入口节点*****************");
        System.out.println("入口节点："+linkedList.getAnnualEntrance());
        System.out.println(linkedList);
        System.out.println("*******************深度拷贝此单链表*****************");
        SingleLinkedList<Integer> copyLinked = linkedList.copyLinked();
        System.out.println(copyLinked);
        System.out.println("*******************拷贝链表加入数据*****************");
        copyLinked.insert(22,3);
        copyLinked.insert(22,4);
        copyLinked.addLast(4);
        copyLinked.addLast(8);
        System.out.println( copyLinked);
        System.out.println("*******************原链表*****************");
        System.out.println(linkedList);
        System.out.println("*******************删除拷链表的尾节点和头节点,并在第3个索引位置设置为环形链表*****************");
        System.out.println(copyLinked.removeLast());
        System.out.println(copyLinked.removeFirst());
        copyLinked.setAnnularLinked(3,true);//从第3个索引位置设置为环形链表

        System.out.println(copyLinked);

        System.out.println("*******************删除6位置的元素,若删除了入口，则删除元素下一个元素为入口当尾部等于入口时，退化成非环形链表*****************");

        System.out.println(copyLinked.removeNode(6));
        ;
        System.out.println(copyLinked);
        System.out.println("*******************删除重复元素*****************");

        System.out.println(copyLinked.removeDistinct());
        System.out.println("*******************根据节点22删除元素*****************");
        System.out.println(copyLinked.removeNode((Integer) 22));
        System.out.println(copyLinked);
         System.out.println("*******************按过滤条件删除==5的节点*****************");
        copyLinked.removeIf(e-> e==5);
        System.out.println(copyLinked);
        System.out.println("*******************修改1位置的值为88,返回旧值*****************");

        System.out.println(copyLinked.setItem(1, 88));
        System.out.println(copyLinked);
        System.out.println("*******************反转单链表*****************");

         System.out.println(copyLinked.transformedLinked());

         System.out.println("*******************原单链表冒泡排序*****************");
         System.out.println(linkedList.sortLinked());
         //System.out.println("*******************单链表快速排序*****************");
          copyLinked.setAnnularLinked(false);
           copyLinked.quickSorted(copyLinked.peek(),copyLinked.tail());
           System.out.println(copyLinked);
          System.out.println("*******************单链表归并排序*****************");
          //copyLinked.setAnnularLinked(false);

           copyLinked.mergeSortedLinked(copyLinked.peek()) ;

           System.out.println(copyLinked);
         System.out.println("*******************单链表倒叙打印*****************");
         copyLinked.printRevertLinked(copyLinked.peek()) ;
          System.out.println(copyLinked);
          System.out.println("*******************单链表的合并,原来有序则合并后有序,原来为环形则合并后为环形*****************");

         System.out.println(linkedList);
         System.out.println(linkedList.mergeLinked(copyLinked));
    }
}
