package DataStructure.Linked;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 单链表的实现
 * @create 2021-02-09 11:36
 */
 public class  SingleLinkedList<T extends Comparable<T>> {
    private  int size;
    private  Node<T> head;
    private  Node<T> tail;
    private  boolean isAnnular = false;//是否为环形
    private  static  final int annularDecCount =3;//打印环形的字符(-->)的长度

    public SingleLinkedList() { }

    static  class  Node<T extends Comparable<T>> {
        private T item;

        private  Node<T> next;

        public  Node(T item){
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }

    //链表长度
    public int size() {
        return size;
    }

    //为空判断
    public  boolean isEmpty() {
        return size == 0;
    }

    //打印链表
    @Override
    public  String toString() {

        if (isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Node<?> node = head;
        for (int i = 0; i <size; i++) {
            sb.append(String.valueOf(node.item).strip());
            node=node.next;
            if (i== size-1) {
                break;
            }
            sb.append("-->");
        }
        //是环形链表则获取环中字符长度以打印出环
        if (isAnnular){
            Node<T> tailNode=tail;
            int len=0,count = 0;
            while (tailNode.next != null){
                len+=String.valueOf(tailNode.item).strip().length();
                tailNode=tailNode.next;
                if (tailNode==tail){
                    break;
                }
                count++;
            }
            int tailLen=(count)*annularDecCount+len;
            String str=(("<--".repeat(tailLen/annularDecCount)).concat("-".repeat(tailLen%annularDecCount)));
            System.out.print(str.indent(sb.length()-tailLen));
            System.out.print(("↓"+"↑".indent(count*annularDecCount+len-2)).indent(sb.length()-tailLen));
        }

        return sb.toString();
    }


    //************************************插入*****************************
    //尾部添加
    public void  addLast( T item){

        Objects.requireNonNull(item,"不能插入null型");

        Node<T> node = new Node<>(item);
        Node<T> last=tail;
        Node<T> annualEntrance = null;

        if (isEmpty()){
            head=node;
        }
        else {
            if (tail.next!=null) annualEntrance =tail.next;

            last.next=node;
        }

        tail=node;
        if (isAnnular) tail.next=annualEntrance;

        size++;

    }

    //头部添加
    public  void addFirst( T item){

        Objects.requireNonNull(item,"不能插入null型");
        Node<T> node = new Node<>(item);

        if (isEmpty()){
            tail=node;
        }
        else node.next=head;
        head=node;
        size++;
    }

    //指定索引位置插入
    public  void insert( T item,int index){
        Objects.checkIndex(index,size+1);

        Objects.requireNonNull(item,"不能插入null型");

        if (index==0){
            addFirst(item);
        }
        else if (index == size) {
            addLast(item);
        }
        else {
            //因为头节点不能动，所有需要个辅助节点indexPreNode来找到添加的位置
            Node<T> indexPreNode = searchNode(index);//因为是单链表所以需要找到待添加节点的前一个位置
            Node<T> insertNode= new Node<>(item);
            insertNode.next=indexPreNode.next;
            indexPreNode.next=insertNode;
            size++;
        }
    }

    //指定位置添加集合
    public void addAll(Collection<? extends T> t, int index){
        Objects.checkIndex(index,size+1);
        Object[] array=t.toArray();
        if(array.length == 0){
            throw  new RuntimeException("集合为空");
        }

        Node<T> indexNode = searchNode(index);//代表index位置的前一个元素
        Node<T> preNode = head,scuNode=indexNode.next;
        Node<T> enterNode=tail.next;

        if (index==0){
            indexNode= new Node<>(null);
        }
        List<Object> collect = Arrays.stream(array).filter(Objects::nonNull).collect(Collectors.toList());
        for (Object o : collect) {
            @SuppressWarnings("unchecked") T s = (T) o;
            Node<T> insertNode = new Node<>(s);
            if (indexNode.item == null) {
                indexNode = head = insertNode;
            }
            indexNode.next = insertNode;
            indexNode = insertNode;
        }
        if (index == 0){
            indexNode.next = preNode;
        }
        else if (index==size){
            tail=indexNode;
            if (isAnnular) tail.next=enterNode;
        }
        else {
             indexNode.next=scuNode;
        }
        size+=collect.size();
    }

    //指定位置插入多个数据
    @SuppressWarnings({"unchecked", "varargs"})
    public  void addAll(int index, T... args){

        addAll(Arrays.asList(args),index);
    }

    //************************************查找*****************************

    //查找节点--返回index位置的前一个元素
    private  Node<T> searchNode(int index){
        Node<T> newNode=head;
        int i = 0;
        while (index!=0&&i<index-1) {
            newNode=newNode.next;
            i++;
        }

        return  newNode;
    }
    //查找节点--返回index位置的元素
    public  Node<T> searchCurrentNode(int index){

        return searchNode(index+1);
    }

    //查找某个元素的前一个元素
    public  Node<T> searchPreNode(T t){
        Node<T> slowNode=head,fastNode=head.next;

        if (head.item.equals(t)){
            return new Node<>(null);
        }

        while (fastNode != tail.next||fastNode!=tail){//循环链表需要加入其它判断条件
            if (fastNode.item.equals(t)) {
                return slowNode;
            }
            if (fastNode.next==null||fastNode==tail) {
                break;
            }
            slowNode=fastNode.next;

            if (slowNode.item.equals(t)) {
                return  fastNode;
            }
            fastNode = slowNode.next;

        }
        return new Node<>(null);
    }

    //判断某元素从fromIndex开始首次在链表中出现的位置
    public int indexOf(T e, int fromIndex){
        if (fromIndex<0){
            fromIndex=0;
        }
        if (fromIndex<size){
            Node<T> indexNode=searchCurrentNode(fromIndex);
            for (int i = fromIndex; i < size; i++) {
                if(e.equals(indexNode.item)){
                    return i;
                }
                indexNode=indexNode.next;
            }
        }
        return -1;
    }

    //判断某元素在链表中首次出现的位置
    public int indexOf(T e){
        return  indexOf(e,0);
    }

    //判断某元素从fromIndex开始反向搜索最后次出现的位置
    public int lastIndexOf(T e, int fromIndex){
        if (fromIndex<0){
            fromIndex=0;
        }
        if (fromIndex>=size){
            fromIndex=size-1;
        }
        Node<T> indexNode;
        List<Integer> list = new ArrayList<>();
        for (int i = fromIndex; i>=0; i--) {
            indexNode=searchCurrentNode(i);
            if(e.equals(indexNode.item)){
                list.add(i);
            }

        }
        return list.size()==0?-1:list.get(list.size() - 1);
    }
    //判断某元素最后此出现的位置
    public int lastIndexOf(T e){
        return  lastIndexOf(e,size-1);
    }

    //判断单链表是否有环
    public boolean isHasAnnular(){
        Node<T> fastNode =head,slowNode= head;int len=0;
        while (fastNode != null&&fastNode.next != null) {
            fastNode = fastNode.next.next;
            slowNode=slowNode.next;
            len++;
            if (fastNode == slowNode) {//相遇
//                Node<T> p=head;
//                while (p != slowNode) {
//                    p=p.next;
//                    slowNode=slowNode.next;
//                }
                //return p;//环入口

                System.out.println("链表环长度："+len);//慢指针走到相遇点就是环的长度

                return true;
            }
        }
        return false;
    }

    //获取环形单链表的入口点
    public  Node<T> getAnnualEntrance(){
        if (isAnnular) {
            HashSet<Node<T>> set = new HashSet<>();
            Node<T> node=head;
            while (node != null){
                if (set.contains(node)){
                    return node;
                }
                set.add(node);
                node = node.next;
            }
        }
        return new Node<>(null);
    }

    //获得头节点
    public  Node<T> peek() {
        return head;
    }
    //获得尾部节点
    public  Node<T> tail() {
        return tail;
    }

    //获取单链表的倒数第index节点
    public Node<T>  getLastIndexNode(int index){
        Objects.checkFromToIndex(1,index,size);

        if (isAnnular){
            throw  new RuntimeException("不支持环形链表！");
        }
        Node<T> slowNode=head,fastNode = head;
        for (int i = 0; i <index ; i++) {
            fastNode=fastNode.next;
        }
        while (fastNode != null) {
            slowNode=slowNode.next;
            fastNode = fastNode.next;
        }
        return slowNode;
    }

    //获取单链表中间节点
    public Node<T> getCenterNode(Node<T> head){
        if (isAnnular){
            throw  new RuntimeException("循环链表没有中间节点");
        }

        if (head == null || head.next == null) {
            return head;
        }
        //Node<T> fastNode=head//长度为偶数时获取两个中的后一个
        Node<T> fastNode=head.next,slowNode=head;
        while (fastNode!= null&&fastNode.next!=null) {
            slowNode=slowNode.next;
            fastNode = fastNode.next.next;
        }
        return slowNode;
    }

    //复制单链表
    public  SingleLinkedList<T>  copyLinked( ){

        if (isEmpty()){
            return new SingleLinkedList<>();
        }
        SingleLinkedList<T> objectSingleLinkedList = new SingleLinkedList<>();
        Node<T> first =head;
        Node<T> copyHeadNode = new Node<>(first.item);
        Node<T> newNode = copyHeadNode;
        Node<T> enterNode=tail.next!=null?tail.next : null;
        for (int i = 1; i <size; i++) {
            first=first.next;
            newNode.next= new Node<>(first.item);
            newNode=newNode.next;
        }

        objectSingleLinkedList.isAnnular =false;
        objectSingleLinkedList.head= copyHeadNode;
        objectSingleLinkedList.size= size;
        objectSingleLinkedList.tail=newNode;

        //原链表是环形链表，获取新链表的入口
        if (enterNode!=null ){

            if (enterNode.item == objectSingleLinkedList.head.item){
                objectSingleLinkedList.tail.next=objectSingleLinkedList.head;
            }
            else {
                Node<T> currentNode = objectSingleLinkedList.searchPreNode(enterNode.item);

                if (currentNode != null) objectSingleLinkedList.tail.next = currentNode.next;

            }
            objectSingleLinkedList.isAnnular=true;
        }

        return objectSingleLinkedList;
    }

    //************************************删除*****************************
    //删除尾部元素
    public  T removeLast() {

        return removeNode(size-1);
    }

    //删除指定索引位置的元素
    public  T removeNode(int index){
        Node<T>  deletePreNode,deleteNode; T oldValue;
        if (isEmpty()){
            throw new RuntimeException("空链表");
        }

        Objects.checkIndex(index,size+1);
        deletePreNode=searchNode(index);

        if (index == 0){
            deleteNode=head;
            oldValue=head.item;
            head.item=null;
            head=head.next;

        }
        else  if ((deleteNode=deletePreNode.next)== tail) {

            oldValue= tail.item;
            tail.item = null;
            tail=deletePreNode;
        }
        else {
            oldValue=deleteNode.item;
            deletePreNode.next=deleteNode.next;

        }
        //如果删除环形链表入口点，则删除元素下一个元素为入口，当尾部等于入口时，退化成非环形链表
        if (isAnnular) {
            turnCommonLinked(deleteNode);
        }

        deleteNode.next=null;
        deleteNode.item = null;
        size--;

        return  oldValue;
    }

    //删除某个元素
    public T removeNode(T t){

        if (isEmpty()){
            throw new RuntimeException("空链表");
        }

        Node<T> deleteNode;T oldNodeValue;
        Node<T> deletePreNode=searchPreNode(t);

        if (t!=head.item&&deletePreNode.next==null) {
            throw new RuntimeException("节点不存在");
        }

        if (t==head.item){
            oldNodeValue= head.item;
            deleteNode=head;
            head=head.next;
        }
        else  if ((deleteNode=deletePreNode.next)==tail){
            oldNodeValue= tail.item;
            tail=deletePreNode;
        }
        else {
            oldNodeValue= deleteNode.item;
            deletePreNode.next=deleteNode.next;
        }

        if (isAnnular) turnCommonLinked(deleteNode);

        deleteNode.next=null;
        deleteNode.item=null;
        size--;

        return  oldNodeValue;
    }

    //删除首元素
    public  T removeFirst(){
        return removeNode(0);
    }

    //从index位置开始删除limit个元素,返回删除元素的集合，如果limit=0则返回空集合
    public  List<T> removeRange(int index,int limit){

        Objects.checkIndex(limit,size+1);
        Objects.checkFromToIndex(index,index+limit,size);
        List<T> list = new ArrayList<>();
        if (limit > 0){

            if (head==tail){//只有1个元素
                list.add(head.item);
                head=tail=null;
            }
            else {
                //找删除终止索引
                int limitIndex=Math.min(limit+index,size);
                Node<T> indexNode=searchCurrentNode(index);

                for (int i = index; i <limitIndex; i++) {
                    list.add(indexNode.item);
                    indexNode=indexNode.next;
                    removeNode(index);
                }

//                Node<T> limitNode=searchCurrentNode(limitIndex);//结束位置的节点
//                Node<T> indexPreNode=searchNode(index);//开始的位置前一个节点
//                Node<T> nextNode = indexPreNode.next;
//
//                int i = 0;
//                if(limitNode==tail){//删除了尾部节点
//                    tail= indexPreNode;
//                }
//
//                if (index==0){//删除了头部节点
//                    nextNode=head;
//                    head=limitNode;
//                }
//                else {//中间删除
//                    indexPreNode.next=limitNode;
//                }
//
//                if (isAnnular) tail.next=oldTail.next;
//
//                while (i<limit&&nextNode!=null) {//删除index到limit位置所有元素
//                    Node<T> deleteNode=nextNode;//当前删除节点
//                    list.add(deleteNode.item);
//                    nextNode=nextNode.next;
//                    deleteNode.next=null;
//                    deleteNode.item = null;
//                    i++;
//                }

            }
           // size-=limit;
        }

        return list;
    }

    //按过滤条件删除
    public  void removeIf(Predicate<? super T> filter){
        Node<T> flagNode=head;Node<T> removeNode;

        while (flagNode!=tail) {//考虑循环链表，所以不能以null作为终止

            if (filter.test((removeNode=flagNode).item)){//记录删除位置的元素
                flagNode=removeNode.next;//删除后导致指针回收，需要重新赋值
                removeNode(removeNode.item);
            }
            else  {
                flagNode=flagNode.next;
            }
        }
        if (filter.test(tail.item)) {
            removeLast();
        }
    }

     //删除链表中的重复元素
     public SingleLinkedList<T>  removeDistinct(){
         Node<T> preNode=head,currentNode=head.next;
         HashSet<T> nodeSet=new HashSet<>();

         nodeSet.add(head.item);
         while (currentNode!=tail) {//考虑循环链表，所以不能以null作为终止
             if (!nodeSet.contains(currentNode.item)) {
                 nodeSet.add(currentNode.item);
                 preNode=currentNode;
             }
             else {
                    preNode.next=currentNode.next;

                    if (isAnnular) turnCommonLinked(currentNode);

                    size--;
             }
             currentNode=currentNode.next;
         }

         if (nodeSet.contains(tail.item)){
             removeLast();
         }
         nodeSet.clear();

         return this;
     }


    //************************************改*****************************
    //修改index位置元素的值
    public T setItem(int index,T element){
        Objects.checkIndex(index,size);

        Node<T> indexNode=searchCurrentNode(index);
        T oldValue=indexNode.item;
        indexNode.item=element;
        return  oldValue;
    }

    //指定位置设置为环形链表
    public void setAnnularLinked (int index, boolean isAnnular){

        Objects.checkIndex(index,size);

        if (index==size-1){
            throw new IndexOutOfBoundsException("已经是链表尾部无法设置为环形");
        }

        if (isAnnular) {
            this.isAnnular=true;
            tail.next=searchCurrentNode(index);
        }
        else {
            this.isAnnular=false;
            tail.next=null;
        }
    }

    //设置为环形链表，默认是头尾相连接
    public void setAnnularLinked(boolean isAnnular){
        setAnnularLinked(0,isAnnular);
    }

    //单链表的反转
    public  SingleLinkedList<T> transformedLinked(){

        Node<T>  nodeHead =head;
        Node<T>  currentNode = nodeHead.next;
        Node<T> oldTail=tail.next!=null?tail.next : null;
        head.next=null;
        tail.next = null;
        Node<T> tempNode;
        while (currentNode != null){
            tempNode=currentNode.next;//保存当前节点的下一个指针,否则链表断裂后无法继续遍历
            currentNode.next=nodeHead;//当前元素的指针之前一个元素
            nodeHead=currentNode;//前一个元素替换当前元素，实现反转
            currentNode=tempNode;//继续遍历
        }
        tail=head;
        if (isAnnular) tail.next=oldTail;
        head=nodeHead;

        return this;
    }
    //单链表的反转
    public  SingleLinkedList<T>  transformedLinked1(){

        Stack<Node<T> > stack = new Stack<>();
        Node<T> oldTailNext=tail.next;
        tail.next=null;
        for (Node<T>  node = head; null != node; node = node.next) {
            stack.push(node);
        }

        Node<T>  nodeHead = stack.pop();
        Node<T>  currentNode = nodeHead;
        while(!stack.isEmpty()){
            currentNode.next=stack.pop();
            currentNode = currentNode.next;
            currentNode.next=null;
        }
        head=nodeHead;
        tail=currentNode;

        if (isAnnular) tail.next=oldTailNext;

        return this;
    }


    //退化成普通链表
    private void turnCommonLinked(Node<T> selectedNode){
        //如果删除环形链表入口点，则删除元素下一个元素为入口，当尾部等于入口时，退化成非环形链表
        if (tail.next==selectedNode) {
            if (tail.next.next==tail){//环的入口等于尾部，退化成非环形链表
                isAnnular=false;
                tail.next=null;
            }else tail.next=tail.next.next;
        }
    }

    //反向打印单链表
    public  void printRevertLinked(Node<T> head) {

        if (head == null) {
            return;
        }

        if (head.next == null) {
             System.out.println(head.item);
             return;
        }

        printRevertLinked(head.next);
        System.out.println(head.item);
    }


    /*
    * 借助优先队列（小根堆），时间复杂度为 O(n\log(k))O(nlog(k))，k 为链表个数，n 为总的结点数，空间复杂度为 O(k)O(k)，小根堆需要维护一个长度为 k 的数组。
    时间复杂度分析：有 k 个结点的完全二叉树，高度为 \lfloor\log(k)\rfloor+1⌊log(k)⌋+1，每次弹出堆顶元素和插入元素重新调整堆的时间复杂度都为 O(\log(k))O(log(k))，所以总的时间复杂度为 O(n\log(k))O(nlog(k))。分析的比较粗略，不是精确的时间复杂度，不过大 O 表示法本身就是一个粗略的量级的时间复杂度表示，这样就足够了。
    *
    * */
    //单链表的合并
    @SafeVarargs
    public final SingleLinkedList<T>  mergeLinked(SingleLinkedList<T>... args){

        PriorityQueue<Node<T>> pq = new PriorityQueue<>(Comparator.comparing(o -> o.item));

        Node<T> entryNode = null;
        //如果原队列(调用者)为循环队列,则保存原来的入口
        if (isAnnular){
            entryNode=tail.next;
            tail.next=null;

        }
        pq.add(head);

        for(SingleLinkedList<T> e : args){
            if(e != null){
                e.tail.next=null;
                pq.add(e.head);
            }
        }
        //定义个虚拟节点作为合并后的头，虚拟节点的next即为最后合并的链表头节点
        Node<T> head = new Node<>(null);

        head.next = null;
        Node<T> last = head;
        int size=0;
        while (!pq.isEmpty()){//借助优先级队列，取一次放一次，如果是有序，因为是队列则保证了顺序，当队列为空则合并完成
            last.next = pq.poll();
            last = last.next;
            if(last.next != null){
                pq.add(last.next);
            }
            last.next = null;
            size++;
        }

        SingleLinkedList<T> tSingleLinkedList = new SingleLinkedList<>();
        tSingleLinkedList.size= size;
        tSingleLinkedList.head=head.next;
        tSingleLinkedList.tail=last;
        tSingleLinkedList.isAnnular=isAnnular;

        if (isAnnular) tSingleLinkedList.tail.next=entryNode;

        return tSingleLinkedList;

    }

    //单链表的合并
    private  Node<T> mergeLinked(Node<T> head1, Node<T> head2){

        Node<T> left = head1, right = head2;
        Node<T> tempNode=new Node<>(null);

        Node<T> mergeHead=tempNode;

        while ((left!=null&&right!=null)) {
            if( left.item.compareTo(right.item)<=0){
                tempNode.next=left;
                left=left.next;
            }
            else {
                tempNode.next=right;
                right=right.next;

            }
            tempNode=tempNode.next;
        }

         tempNode.next = left != null ? left : right;

          if ( tail.item.compareTo(tempNode.next.item)<0) {
              tail=tempNode.next;
          }
          else tail.next=null;

         return  mergeHead.next;
    }

    //************************************排序*****************************
    //对单链表进行冒泡排序
    public  SingleLinkedList<T> sortLinked(){

        Node<T> curredNode=head,temNode = null,annularNode = null;

            if (tail.next!=null){
                annularNode=tail.next;
                tail.next=null;
            }
            while (curredNode!=temNode ) {
                while (curredNode.next!=temNode){
                     if (curredNode.item.compareTo(curredNode.next.item)>0){
                         T temp=curredNode.item;
                         curredNode.item=curredNode.next.item;
                         curredNode.next.item=temp;
                     }
                         curredNode=curredNode.next;
                 }
                 temNode=curredNode;//保存一轮遍历后的最大值
                 curredNode=head;//继续遍历剩下元素
            }
            if (isAnnular) tail.next=annularNode;


        return  this;
    }

   //对单链表进行快速排序
    public void quickSorted(Node<T> head, Node<T> tail){

        Node<T> annularNode = null;
       //选取head的值作为key当做基准值,定义指针prev,next,确保prev，及prev之前的元素都比key小。prev和next之间的值都比key大
       //next指针到达尾部节点交换prev与key完成一次切分。
            if (isAnnular){
                annularNode=tail.next;
                tail.next=null;
            }

            if (head.next==tail) {//间隔为1，说明中间没有元素完全没必要递归
                return;
            }
            if (head != tail) {
                Node<T> prev = head, next = prev.next;T key=head.item;
                while (next != null) {
                    if (next.item.compareTo(key)<0){//prev 与 next之间存在小于key的值则交换
                        prev = prev.next;//不是交换prev和next,而是prev与next之间的即prev的下一个元素 --prev的下一个元素与next元素交换，
                        T temp = prev.item;
                        prev.item = next.item;
                        next.item = temp;
                    }
                    next = next.next;
                }
                //交换prev与head值完成一次切分
                if (head.item.compareTo(prev.item)<0){

                    T temp = head.item;
                    head.item= prev.item;
                    prev.item = temp;
                }


                quickSorted(head, prev);//对左边分区递归
                quickSorted(prev.next, tail);//对右边分区递归


            }
          if(isAnnular) tail.next=annularNode;//如果是环形链表则连接之前记录的节点
    }

    //对单链表归并排序
    /*
    * 1将待排序数组（链表）取中点并一分为二；
      2递归地对左半部分进行归并排序；
      3递归地对右半部分进行归并排序；
      4 将两个半部分进行合并（merge）,得到结果。
    *
    * */
    public Node<T> mergeSortedLinked(Node<T> head){

        if (head == null || head.next == null) {

            return head;
        }

        if (isAnnular){
           throw new RuntimeException("不支持环形链表排序");
        }

       //获取中间节点
       Node<T> middleNode=getCenterNode(head);
       Node<T> rightNode=middleNode.next;
       middleNode.next =null;//断开链表

       Node<T> left= mergeSortedLinked(head);
       Node<T> right= mergeSortedLinked(rightNode);

        this.head=mergeLinked(left, right);//归并

        //合并
        return this.head;
    }
}
