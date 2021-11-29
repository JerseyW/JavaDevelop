package DataStructure.DoubleLinked;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author Jerssy
 * @version V1.0
 * @Description
 * @create 2020-11-26 21:31
 */
public class DoubleLinkedTest {
    public static void main(String[] args) {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("tom");
        arrayList.add("tom");
        arrayList.add("tom");
        arrayList.add("leo");
        arrayList.add("jack");
        arrayList.add("lo");
        arrayList.add("hu");
        arrayList.add("kobe");
        arrayList.add("cu");

        arrayList.add("2");
        System.out.println("**********添加元素********");
        list.addNode("2");
        list.addNode("3");
        System.out.println(list);
        System.out.println("**********头部添加元素********");
        list.addFirst("ben");
        System.out.println(list);
        System.out.println("**********指定1位置添加collection集合********");
        list.addAllNode(arrayList,1);
        System.out.println(list);
        System.out.println("**********遍历集合********");
        System.out.println(list);
        System.out.println("**********3位置插入kobe元素********");
        list.addInsert("kjf",3);

        System.out.println(list);
        System.out.println("**********获取环形链表的入口********");
        System.out.println(list.annualEnter());
        System.out.println("**********获取倒数第3节点********");
        System.out.println(list.getLastIndexNode(3));
        System.out.println("**********获取中间节点********");
        System.out.println(list.getCenterNode(list.peek()));

        System.out.println("**********反转********");
        System.out.println(list.transformerLinked1());

        System.out.println("**********复制********");
        DoubleLinkedList<String> stringDoubleLinkedList = list.copyDoubleLinked();
        System.out.println(stringDoubleLinkedList);
        stringDoubleLinkedList.addFirst("ko");
        System.out.println(stringDoubleLinkedList);

        System.out.println("**********修改2位置元素的值为jrSmith,并返回原来的值********");
        System.out.println(list.setValue(2,"jrSmith"));
        System.out.println(list);
        System.out.println("**********删除第一个，并返回原来的值********");
        System.out.println(list.removeFirst());
        System.out.println(list);
        System.out.println("**********删除最后一个，并返回原来的值********");
        System.out.println(list.removeLast());
        System.out.println(list);
        System.out.println("**********删除重复元素********");
        System.out.println(list.removeDistinct());
        System.out.println("**********判断tom元素从2开始在链表首次出现的位置********");
        System.out.println(list.indexOf("tom",2));
        System.out.println("**********判断tom元素在链表中首次出现的位置********");
        System.out.println(list.indexOf("tom"));
        System.out.println("**********反向搜索tom元素从7开始在链表中最后次出现的位置********");
        System.out.println(list.lastIndexOf("tom",7));
        System.out.println("**********判断tom元素在链表最后出现的位置********");
        System.out.println(list.lastIndexOf("tom"));
        System.out.println("**********根据元素tom值获取索引********");
        System.out.println(list.getNodeOfIndex("tom"));
        System.out.println("**********从2的位置删除3个元素********");
        System.out.println(list.removeRange(2 ,3));
        System.out.println(list);
        System.out.println("**********删除3位置的元素********");
        System.out.println(list.remove(3));
        System.out.println(list);
        System.out.println("**********删除2元素,要用包装类修饰否则会按索引删除********");
        System.out.println(list.remove(Integer.valueOf(2)));
        System.out.println(list);
        System.out.println("**********删除满足条件的所有元素,需要指定泛型********");
        DoubleLinkedList<String> list2 = new DoubleLinkedList<>();
        list2.addNode("12");
        list2.addNode("13");
        list2.addNode("14");
        list2.addNode("17");
        list2.removeIf(e-> e.equals("12"));//删除满足条件的所有元素
        System.out.println(list2);
        System.out.println("**********反向打印********");
        list2.turnBackPrinter(list2.peek());
//        System.out.println("**********冒泡排序********");
//        System.out.println(list2.sort());
        System.out.println("**********归并排序********");
        list2.mergeSortedLinked(list2.peek());
        System.out.println(list2);

        System.out.println("**********快速排序********");
        list2.quickSort(list2.peek(),list2.tail());
        System.out.println(list2);
//        System.out.println("**********双链表的合并********");
        System.out.println(list2);
        System.out.println(list);
        System.out.println(list.mergeDoubleLinked(list2));
      }
}
