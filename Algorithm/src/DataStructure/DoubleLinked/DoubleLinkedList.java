package DataStructure.DoubleLinked;

import java.util.*;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jerssy
 * @version V1.0
 * @Description：双链表代码实现
 * @create 2020-11-25 16:49
 */
public class DoubleLinkedList<E extends Comparable<E>> {

    private Node<E> first;//链表头部节点
    private Node<E> last;//链表尾部节点
    private int count;//记录元素个数
    private  boolean isAngular=false;//是否为环形
    private  static  final int annularDecCount =2;//打印环形的字符(⇄)的长度
    public DoubleLinkedList() {
    }

    //内部节点类用来构建数据
     static class Node<E>{
        Node<E> prev;//前指针保存当前链表节点前一个节点数据
        Node<E> next;//后指针保存当前链表节点后一个节点数据
        E item;//当前节点数据

        Node(Node<E> prev, E item,Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }


    @Override
    public String toString() {
        Node<E> pre = first;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++) {
            sb.append(pre.item);
            pre = pre.next;
            if (i == count - 1) {
                break;
            }
            sb.append("⇄");
        }

        return sb.toString();
    }

    //添加元素，双链表默认从尾部添加
    public void addNode(E e){
        addLast(e);
    }

    //头部添加与尾部添加类似
    public void addFirst(E e){
        addOfFirst(e);
    }

    //尾部添加
    private  void addLast(E e){
        //这里可以插入null元素，双链表支持null元素的插入
        //第一步：获取尾部节点last
        Node<E> lastNode= last;
        //第二步 addData前指针指向lastNode,addData后指针置空,构建Node节点类
        Node<E> addData=new Node<>(lastNode,e,null);
        //第三步：当前addData变成last节点
         last=addData;
        if(null==lastNode){//说明链表是空的，将first元素赋予addData;
            first=addData;
        }
        else{
            lastNode.next=addData;//将lastNode的尾部指针指向addData
        }
        count++;
    }

    //头部添加
    private void addOfFirst(E e){
        //尾部一样--找头元素，构建节点Node,将插入元素变成first节点，最后将插入元素尾部指针指向原来的first节点
         Node<E> firstNode=first;
         Node<E> addData=new Node<>(null,e,firstNode);
         first=addData;
         if(null==firstNode){//说明链表是空的，将last元素赋予addData;
             last=addData;
         }
         else{
             firstNode.prev=addData;//将firstNode的头部指针指向addData
         }
        count++;
    }
    //从指定位置添加元素
    public void addInsert(E e, int index){
      //看看插入index是否合法，然后找插入的位置，断开此位置的前与后连接
        if(index<0||index>count){//不合法抛个异常
            throw new IndexOutOfBoundsException("参数不合法，请检查参数");
        }
        else if(index==0){//不就是头部插入嘛
            addFirst(e);
        }
        else if(index==count){//不就是尾部插入嘛
            addLast(e);
        }
        else{//中间插入
            //找插入位置index的元素
            Node<E> indexNode=searchNodeOfIndex(index);
            //将待插入节点的前指针指向indexNode的前一个元素，后指针指向indexNode
            Node<E> insertNode=new Node<>(indexNode.prev,e,indexNode);
            indexNode.prev.next=insertNode;//indexNode的前一个元素的后指针指向插入节点insertNode
            indexNode.prev=insertNode;//indexNode的前指针指向插入节点insertNode
        }
        count++;
    }

    //俩表尾部插入集合
    public void addAllNode(Collection<? extends E> e){
        addAll(e, count);
    }

    //指定位置插入集合
    public void addAllNode(Collection<? extends E> e, int index){
        addAll(e, index);
    }
    private void addAll(Collection<? extends E> e, int index){
        //不合法抛异常
        if(index<0||index>count){//不合法抛个异常
            throw new IndexOutOfBoundsException("参数不合法，请检查参数");
        }
        if(null==e){
            throw new NullPointerException("集合为空");
        }

        //1 先将插入集合转成数组--为什么这么做？--1传入的集合可能是ArrayList,linkedList,Vector，栈等等，每个数据类型
        //不一样，转换成数组，便于将其转成双链表结构方便插入;2 数组查询遍历快提高性能
        Object[] objArray =e.toArray();
        if(objArray.length==0){
           return;
        }

        //方法一：先数组转成双链表
        //2先将arrays数据转成双链表，拿到此链表的first和last元素进行操作
        @SuppressWarnings("unchecked") E s = (E) objArray[0];
        Node<E> predNode=new Node<>(null,s,null);
        Node<E> firstNode=predNode;//保存第一个元素后续操作
        for (int i = 1; i < objArray.length; i++) {
            @SuppressWarnings("unchecked") E a = (E) objArray[i];
            Node<E> insertNode = new Node<>(predNode, a, null);
            predNode.next=insertNode;
            predNode=insertNode;//循环结束predNode变成此链表的最后一位
        }
         Node<E> insertFirst=firstNode.next.prev;//获取新链表的first元素
        //3 判断插入位置
        if(index==count){//就是尾部插入嘛
              last.next=insertFirst;
              insertFirst.prev=last;
              last=predNode;//更新last元素
        }
        else if(index==0){//就是头部插入嘛
            predNode.next=first;
            first.prev=predNode;
            first=firstNode;//更新first元素
        }
        else{//中间插入
            Node<E> indexNode =searchNodeOfIndex(index);
            indexNode.prev.next=insertFirst;//indexNode的前一元素后指针指向insertFirst
            insertFirst.prev=indexNode;//insertFirst的前指针指向indexNode
            predNode.next=indexNode;//新链表的后元素的后指针指向indexNode
            indexNode.prev=predNode;//indexNode的前指针指向新链表的后元素。
        }

        //方法二：JDK实现
       /* Node<E> sucNode=null;
        Node<E> predNode;
        if(index==count){//就是尾部插入嘛
           predNode=last;//获取last元素①
        }
        else{//头部或者中间插入
            sucNode =searchNodeOfIndex(index);//找index位置的元素
            predNode=sucNode.prev;//此位置的前一个元素①
        }
           for (Object o : objArray) {
               @SuppressWarnings("unchecked") E s = (E) o;
                Node<E> insertNode=new Node<>(predNode,s,null);//只能确定insertNode的前指针是predNode,后指针暂时无法确定
                if(null==predNode){//加入了插入位置是头部的情况
                    first=insertNode;
                }
                else{
                    predNode.next=insertNode;//将①位置的后指针指向当前创建的元素insertNode；
                    //下一次循环会将②位置的前一次循环的insertNode的后指针指向本次循环创建的insertNode
                }
                predNode=insertNode;//predNode赋予当前insertNode；②--将元素串成链表的关键；循环结束predNode变成此链表的最后一位③

            }
            if(null==sucNode){//说明index位置是原链表的最后一位元素
                last=predNode;//将③位置的元素作为last元素
            }
            else{//将链表最后元素与index位置的元素建立联系
                predNode.next=sucNode;
                sucNode.prev=predNode;
            }*/
        count+=objArray.length;
    }

    //根据元素值获取索引--可以有重复数据这里用数组存储索引
    public  List<Integer> getNodeOfIndex(E e){
        ArrayList<Integer> result=new ArrayList<>();
        int i = 0;
        Node<E> node=first;
        while (i<count){
            if(e==null&&node.item==null||e!=null&&e.equals(node.item)){//考虑下元素可能为null的情况
                result.add(i);
            }
            node=node.next;
            i++;
        }
        return  result;
    }

    //找指定索引index位置的元素
   public Node<E> searchNodeOfIndex(int index){

        //将数组或者链表长度折成两半，比index小的从左边查找，比index大的从右边，循环直到找到为止
        int leftIndex=count>>1;//位运算，相当于除以2;
       Node<E> node;
       int i;
       if(index<leftIndex){//比index小的从左边查找
           node = first;
           i = 0;
             while (i<index){
                 node=node.next;
                 i++;
             }
       }
        else{//比index大的从右边开始查找
           node = last;
           i = count - 1;
            while (i>index){
                node=node.prev;
                i--;
            }
       }

       return node;
    }

    //删除前元素
     public E removeFirst(){
        if(first==null){
            return null;
        }
        E oldValue=first.item;
        if(first.next==null){//表里就一个元素
            first=last=null;
        }
        else{
            Node<E> firstNode;
            firstNode=first.next;//新的first元素
            first=null;
            first=firstNode;//设置新的first元素
            first.prev=null;//将新顶上来的元素前指针置空
        }
        count--;
        return  oldValue;
     }

     //删除后元素
    public E removeLast(){
        if(last==null){//表可能是空的
            return null;
        }
        E oldValue=last.item;
        if(last.prev==null){//表里就一个元素
            first=last=null;
        }
        else {
            Node<E> lastNode;
            lastNode=last.prev;
            last=null;
            last=lastNode;
            last.next=null;
        }
        count--;
        return  oldValue;
    }
 // 从指定位置删除指定个数的元素
    public List<E> removeRange( int index,int limit){//从指定位置(包含指定位置元素·)开始删除limit个元素
        ArrayList<E> result=new ArrayList<>();
        //不合法抛异常
        if(index<0||index>=count||limit<0||(index+limit)>count){//不合法抛个异常
            throw new IndexOutOfBoundsException("参数不合法，请检查参数");
        }
         if(limit>0){//确保要删除数据且limit代表删除几个
             if(first.next==null){//只有1个元素
                 result.add(first.item);
                 first=last=null;
             }
             else{
                 //找limit位置的下一个元素
                 int limitIndex;
                 if(index==0){
                     limitIndex = limit;
                 }
                 else{
                     limitIndex=(limit+index==count)?count-1:limit+index;//limit位置不能超过last位置
                 }
                 Node<E> indexNode=searchNodeOfIndex(index);//删除开始位置元素
                 Node<E> limitNode=searchNodeOfIndex(limitIndex);//删除结束位置下一个元素
                 Node<E> indexPrev=indexNode.prev;//记录删除前一位元素
                 int i = 0;
                 while (i<limit){//删除index到limit位置所有元素
                     Node<E> newNode=indexNode;
                     result.add(newNode.item);
                     indexNode.prev=null;
                     newNode=newNode.next;
                     indexNode.next=null;
                     indexNode=newNode;
                     i++;
                 }
                 if(indexPrev==null){//删除了first元素，新的first是删除结束位置的下一位置元素
                     first=limitNode;
                 }
                 else{//从中间删除元素
                     indexPrev.next=limitNode;
                     limitNode.prev=indexPrev;
                 }
                 if(limitNode.next==null){//删除元素包含last元素，新的last是删除开始位置的前一位置元素
                     last=indexPrev;
                 }
             }
             count-=limit;
         }
        return  result;
    }

    //删除指定位置元素
    public List<E> remove(int index){
        return  removeRange(index,1);
    }

    //删除某个元素值
   public boolean remove(Object obj){
        Node<E> removeNode=first;
        while (removeNode!=null){
            if(obj==null){//元素值可能为空的情况
                if(removeNode.item==null){
                    return remove(removeNode);
                }
            }
            else{
                if(obj.equals(removeNode.item)){
                    return remove(removeNode);
                }
            }
            removeNode=removeNode.next;
        }
        return false;
   }

   private  boolean remove(Node<E> node){
        //获取此位置的前一个元素和后一个元素
         Node<E> nodePrev=node.prev;
         Node<E> nodeSuc=node.next;
         node.item=null;
         node.prev=null;
         node.next=null;
         if(null==nodePrev){//删除first元素
             first=nodeSuc;
             first.prev=null;//将新顶上来的元素前指针置空
         }
         else{
             nodePrev.next=nodeSuc;
         }
         if(null==nodeSuc){//删除了last元素
             last=nodePrev;
         }
         else{
             nodeSuc.prev=nodePrev;
         }

         count--;
        return true;
   }

   //按过滤条件删除
   public  void removeIf(Predicate<? super E> filter){
       Node<E> flagNode=first;Node<E> removeNode;
       while (flagNode!=null) {
           if(filter.test((removeNode=flagNode).item)){//记录删除位置的元素
               remove(removeNode.item);
           }
           flagNode=flagNode.next;
       }
   }

   //判断某元素从fromIndex开始首次出现的位置
    public int indexOf(E e, int fromIndex){
        if(fromIndex<0){
            fromIndex=0;
        }
        if(fromIndex<count){
             Node<E> indexNode=searchNodeOfIndex(fromIndex);
            for (int i = fromIndex; i < count; i++) {
                 if(e==null&&indexNode.item==null||e!=null&&e.equals(indexNode.item)){
                     return i;
                 }
                indexNode=indexNode.next;
            }
        }
        return -1;
    }
    //判断某元素首次出现的位置
    public int indexOf(E e){
        return  indexOf(e,0);
    }
    //判断某元素从fromIndex开始反向搜索最后次出现的位置
    public int lastIndexOf(E e, int fromIndex){
        if(fromIndex<0){
            fromIndex=0;
        }
        if(fromIndex>=count){
            fromIndex=count-1;
        }
        Node<E> indexNode=searchNodeOfIndex(fromIndex);
        for (int i = fromIndex; i>=0; i--) {
            if(e==null&&indexNode.item==null||e!=null&&e.equals(indexNode.item)){
                return i;
            }
            indexNode=indexNode.prev;
        }
        return -1;
    }
    //判断某元素最后此出现的位置
    public int lastIndexOf(E e){
        return  lastIndexOf(e,count-1);
    }

    //修改index位置元素的值
    public E setValue(int index,E element){
        if(index<0||index>count-1){
            throw new IndexOutOfBoundsException("参数不合法");
        }
        Node<E> indexNode=searchNodeOfIndex(index);
        E oldValue=indexNode.item;
        indexNode.item=element;
        return  oldValue;
    }

    //指定索引置修改为位环形双链表
    public  void setAnnualLined(int index,boolean isAnnualLined){
           Objects.checkIndex(index,count);
           if (isAnnualLined) {
               Node<E> eNode = searchNodeOfIndex(index);
               last.next=eNode;
               eNode.prev=last;
               isAngular=true;
           }
           else  {
               first.prev=null;
               last.next=null;
               isAngular=false;
           }
    }


    public  Node<E> peek(){
        return  first;
    }
    public  Node<E> tail(){
        return  last;
    }

    //默认收尾设置环形双链表
    public  void setAnnualLined( boolean isAnnualLined) {
        setAnnualLined(0,isAnnualLined);
    }
    //判断是否有环及入口
    public Node<E>  annualEnter(){
        Node<E> slow= first,fast=first;
        while (fast!=null&&fast.next != null) {
             slow=slow.next;
             fast = fast.next.next;
             if (fast ==slow) {
                 Node<E> nodeP=first;
                 while (nodeP != slow) {
                     nodeP=nodeP.next;
                     slow=slow.next;
                 }
                 return  nodeP;
             }
        }
        return null;
    }

    //倒数第index个数据
    public  Node<E> getLastIndexNode(int index) {
        Objects.checkFromToIndex(1,index,count);
        if(isAngular){
            throw  new RuntimeException("不支持环形链表");
        }
        Node<E> slow=first;Node<E> fast=first;

        for(int j=index;j>0;j--){
            fast=fast.next;
        }
        while (fast != null ) {
             slow=slow.next;
             fast = fast.next;
        }
        return slow;
    }

    //获取中间节点
    public  Node<E> getCenterNode(Node<E> head){
        if(isAngular){
            throw  new RuntimeException("不支持环形链表");
        }
        Node<E> slow=head,fast=head.next;//偶数取两个中的前一个
        while (fast != null&&fast.next!=null) {
            slow = slow.next;
            fast=fast.next.next;
        }
        return  slow;
    }

    //删除链表中的重复数据
    public DoubleLinkedList<E> removeDistinct(){

        Node<E> nodePrev=first,currentNode=first.next;
        HashSet<E> nodeSet = new HashSet<>();
        nodeSet.add(nodePrev.item);
        while (currentNode!=last){
            if (!nodeSet.contains(currentNode.item)){
                nodeSet.add(currentNode.item);
                nodePrev=currentNode;
            }else {
                nodePrev.next=currentNode.next;
                currentNode.next.prev=nodePrev;
                 if (isAngular) turnUnAngularLinked(nodePrev,currentNode);
                count--;

            }
            currentNode = currentNode.next;
        }
        if(nodeSet.contains(last.item)){
            removeLast();
        }
        nodeSet.clear();
        return  this;
    }

    //退化成非环形链表
    private  void turnUnAngularLinked(Node<E> nodePrev,Node<E> currentNode){
         if (currentNode==last.next) {
             if (last.next.next==last){
                 isAngular=false;
                 last.next=null;
                 nodePrev.prev=last;

             } else {
                 currentNode.next.prev=last;
                 last.next=currentNode.next;
             }
         }
    }

    //反转
    public  DoubleLinkedList<E>  transformerLinked(){

        Node<E> node = first,currentNode=first.next;

        Node<E> tempNode;

        while (currentNode != null) {
             tempNode=currentNode.next;
             currentNode.next=node;
             node.prev=currentNode;
             node=currentNode;
             currentNode = tempNode;
        }
        last=first;
        first=node;
        last.next = null;
        first.prev = null;
        return  this;
    }

    //利用栈反转
    public  DoubleLinkedList<E>  transformerLinked1(){

        Stack<Node<E>> stack = new Stack<>();
        Node<E> node=first;
        for (int i = 0; i < count; i++) {
            stack.push(node);
            node=node.next;
        }
        Node<E> newNode=stack.pop();//作为反转后的头部，不能拿来遍历操作
        Node<E> currentNode = newNode;
        while (!stack.isEmpty()) {
              Node<E> tempNode=stack.pop();
              currentNode.next=tempNode;
              tempNode.prev=currentNode;
              currentNode=currentNode.next;
              currentNode.next=null;
        }
        last=currentNode;
        first=newNode;
        first.prev=null;

        return  this;
    }

    //双链表的复制
    public  DoubleLinkedList<E> copyDoubleLinked(){

        Node<E> newHead= new Node<>(null, first.item, null);
        Node<E> linkedFirst=first.next;
        Node<E> tempNode=newHead;
        int count=1;
        while (linkedFirst!= null){

            Node<E> nextNode= new Node<>(newHead, linkedFirst.item, null);
            tempNode.next=nextNode;
            tempNode=nextNode;
            count++;
            linkedFirst=linkedFirst.next;

        }
        DoubleLinkedList<E> newLinked=new DoubleLinkedList<>();
        newLinked.count=count;
        newLinked.first=newHead;
        newLinked.last=tempNode;
        newLinked.isAngular=false;
        return  newLinked;
    }

    //反向打印双链表
    public void turnBackPrinter(Node<E> head){
          if (head == null) {
              return;
          }
          if (head.next == null) {
              System.out.println(head);
              return;
          }

          turnBackPrinter(head.next);
          System.out.println(head);

    }

    //冒泡排序
    public  DoubleLinkedList<E>  sort(){
        Node<E> currentNode=first;
        Node<E> maxNode = null;

        while (currentNode != maxNode) {
            while (currentNode.next!= maxNode) {
                if (currentNode.item.compareTo(currentNode.next.item)>=0){
                    E  temp=currentNode.item;
                    currentNode.item = currentNode.next.item;
                    currentNode.next.item=temp;
                }
                currentNode=currentNode.next;
            }
            maxNode=currentNode;
            currentNode=first;
        }
        return  this;
    }

    //快速排序
    public void quickSort(Node<E> head1, Node<E> head2) {

        if (head1!=head2) {
            Node<E> prev = head1;
            Node<E> next = prev.next;
            E key=head1.item;
            while (next!=last.next) {
                if (next.item.compareTo(key) < 0) {
                    prev = prev.next;
                    E temp = prev.item;
                    prev.item = next.item;
                    next.item = temp;
                }
                next=next.next;
            }
            E temp = head1.item;
            head1.item = prev.item;
            prev.item = temp;

            quickSort(head1,prev);
            quickSort(head1.next, head2);
        }

    }

    //双链表的合并
    @SafeVarargs
    public final DoubleLinkedList<E> mergeDoubleLinked(DoubleLinkedList<E>... args){
         if (args.length==0) {
             return  this;
         }

         PriorityQueue<Node<E>> queue = new PriorityQueue<>((Comparator.comparing(o -> o.item)));

         queue.add(first);
        for (DoubleLinkedList<E> node : args) {
            queue.add(node.first);
        }
        Node<E> newHead = new Node<>(null,null,null);
        int count = 0;
        Node<E> last = newHead;
        while (!queue.isEmpty()){
            Node<E> nextNode=queue.poll();
            last.next= nextNode;
            nextNode.prev= last;
            last=last.next;

            if (last.next != null) {
                queue.add(last.next);
            }
            last.next=null;
            count++;
        }

        DoubleLinkedList<E> newList =new DoubleLinkedList<>();
        newList.count = count;
        newList.first= newHead.next;
        newList.last=last;

        return newList;
    }

    //归并排序
    public  Node<E> mergeSortedLinked(Node<E> head){
        if (head == null||head.next == null) {
            return head;
        }
        Node<E> centerNode = getCenterNode(head);
        Node<E> nextNode=centerNode.next;
        centerNode.next=null;
        Node<E> leftNode= mergeSortedLinked(head);
        Node<E> rightNode= mergeSortedLinked(nextNode);
        first=linkedSortedNode(leftNode,rightNode);
        first.prev=null;
        return first;
    }

    private  Node<E> linkedSortedNode(Node<E> head ,Node<E> next){

        Node<E> previousNode = head;
        Node<E> sucNode=next;
        Node<E> node= new Node<>(null, null, null);
        Node<E> tempNode=node;
         while (previousNode!=null&&sucNode!=null) {
               if (previousNode.item.compareTo(sucNode.item)<=0) {
                   tempNode.next=previousNode;
                   previousNode.prev=tempNode;
                   previousNode=previousNode.next;
               }
               else {
                   tempNode.next=sucNode;
                   sucNode.prev=tempNode;
                   sucNode=sucNode.next;
               }
               tempNode=tempNode.next;
         }
         if(previousNode==null){
             tempNode.next=sucNode;
         }
         else {
             tempNode.next=previousNode;
         }

         return  node.next;
    }
}
