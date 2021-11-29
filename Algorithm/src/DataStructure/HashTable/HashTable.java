package DataStructure.HashTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jerssy
 * @create: 2021-03-25 9:42
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 散列表
 *
 * 散列表（Hash table，也叫哈希表），是根据关键码值(Key value)而直接进行访问的数据结构。
 * 也就是说，它通过把关键码值映射到表中一个位置来访问记录，以加快查找的速度。
 * 这个映射函数叫做散列函数，存放记录的数组叫做散列表
 *
 * 使用哈希表作为java程序和数据的缓存层，将经常使用无需向数据库插入的数据放入哈希表中，提高对数据的访问能力。降低数据库的负载压力
 *
 * 使用关键字key的值直接访问记录，加快访问速度
 *
 *  散列表实现方式：1 数组+链表  2 数组+二叉树
 *
 * 优点：一对一的查找效率很高；
 *
 * 缺点：一个关键字可能对应多个散列地址；需要查找一个范围时，效果不好。
 *
 * 散列冲突：不同的关键字经过散列函数的计算得到了相同的散列地址。
 *
 * 好的散列函数=计算简单+分布均匀（计算得到的散列地址分布均匀）
 *
 * 哈希表是种数据结构，它可以提供快速的插入操作和查找操作

 *
 *   几种常用散列函数的构造方法：
 *    常用的哈希函数的构造方法有 6 种：直接定址法、数字分析法、平方取中法、折叠法、除留余数法和随机数法。
 *   A 直接定制法
 *   H（key）=a*key+b
 *   假设需要统计中国人口的年龄分布，以10为最小单元。今年是2018年，那么10岁以内的分布在2008-2018，20岁以内的分布在1998-2008……假设2018代表2018-2008直接的数据，那么关键字应该是2018，2008，1998……
 * 那么可以构造哈希函数H（key）=（2018-key）/10=201-key/10
 *

 * B 数字分析法
 * 假设关键字集合中的每个关键字key都是由s位数字组成（ k 1 , k 2 , … … , k n  ）,分析key中的全体数据，并从中提取分布均匀的若干位或他们的组合构成全体

   使用举例
 * 我们知道身份证号是有规律的，现在我们要存储一个班级学生的身份证号码，假设这个班级的学生都出生在同一个地区，同一年，那么他们的身份证的前面数位都是相同的，那么我们可以截取后面不同的几位存储，假设有5位不同，那么就用这五位代表地址。
 * H（key）=key%100000
 * 此种方法通常用于数字位数较长的情况，必须数字存在一定规律，其必须知道数字的分布情况，比如上面的例子，我们事先知道这个班级的学生出生在同一年，同一个地区。

  C  平方取中法
  如果关键字的每一位都有某些数字重复出现频率很高的现象，可以先求关键字的平方值，通过平方扩大差异，而后取中间数位作为最终存储地址。
   使用举例
   比如key=1234 1234^2=1522756 取227作hash地址
   比如key=4321 4321^2=18671041 取671作hash地址
   这种方法适合事先不知道数据并且数据长度较小的情况

 *D 折叠法
 * 如果数字的位数很多，可以将数字分割为几个部分，取他们的叠加和作为hash地址
 * 使用举例
 * 比如key=123 456 789
 * 我们可以存储在61524，取末三位，存在524的位置
 * 该方法适用于数字位数较多且事先不知道数据分布的情况
 *
 *E 除留余数法用的较多
 * H（key）=key MOD p （p<=m m为表长）p一般为质数
 * 很明显，如何选取p是个关键问题。
 *
 * 使用举例
 * 比如我们存储3 6 9，那么p就不能取3
 * 因为 3 MOD 3 == 6 MOD 3 == 9 MOD 3
 * p应为不大于m的质数或是不含20以下的质因子的合数，这样可以减少地址的重复（冲突）

 F  随机数法  H（key） =Random（key） 取关键字的随机函数值为它的散列地址

 hash函数设计的考虑因素
    1.计算散列地址所需要的时间（即hash函数本身不要太复杂）
    2.关键字的长度
    3.表长
    4.关键字分布是否均匀，是否有规律可循
    5.设计的hash函数在满足以上条件的情况下尽量减少冲突

哈希冲突的解决方案
不管hash函数设计的如何巧妙，总会有特殊的key导致hash冲突，特别是对动态查找表来说。
hash函数解决冲突的方法有以下几个常用的方法
1.开放定制法
首先有一个H（key）的哈希函数
如果H（key1）=H（key）--哈希冲突了
那么key存储位置 Hi(key) = (H(key)+di)MOD m(表长) i=1,2,3,4.....k(k<=m-1)


di有三种取法
1）线性探测再散列
    在找到查找位置的index的index-1，index+1位置查找，index-2，index+2查找，依次类推。这种方法称为线性再探测。
    只要哈希表没有被填充满，保证能找到一个空的地址存放冲突的元素
2）平方探测再散列
    di=1^2 -1^2 2^2 -2^2......
3）随机探测在散列（双探测再散列）
在查找位置index周围随机的查找。称为随机在探测。

2.链地址法
产生hash冲突后在存储数据后面加一个指针，指向后面冲突的数据
 哈希表上的节点动态申请，适合表长不确定的情况


3.公共溢出区法
建立一个特殊存储空间，专门存放冲突的数据。此种方法适用于数据和冲突较少的情况。
4.再散列法
准备若干个hash函数，如果使用第一个hash函数发生了冲突，就使用第二个hash函数，第二个也冲突，使用第三个……
重点了解一下开放定制法和链地址法


 哈希表查找
   线性探测冲突的方法
   1 根据哈希函数计算哈希值，如果此位置为空，则查找失败
   2 如果地址不为空，比较查找值与地址值上的关键字，如果相同则查找成功，不相同则继续根据线性探测冲突的方法继续比较下一个
     直到找到或者扫描表结束为止

 */
public class HashTable<K,V>  {

    private  int defaultSize=4;//默认桶位大小

    private  int count;//桶位中所有元素个数
    private Node<K, V>[] hashTable;

    public static void main(String[] args) {

        HashTable<Integer,String> hashTable = new HashTable<>();
        hashTable.putValue(10 , "jack");
        hashTable.putValue(11 , "leo");
        hashTable.putValue(22, "ko");
        hashTable.putValue(66, "jrSmith");
        hashTable.putValue(36,"james");
        hashTable.putValue(100,"tom");
        hashTable.putValue(1,"jerry");
        hashTable.putValue(2,"jerry1");

        hashTable.putValue(30, "kop");
        hashTable.putValue(11, "cop");


        hashTable.printTable();

        System.out.println(hashTable.setKey(66, "james"));
        System.out.println(hashTable.setKey(11, "bens"));
        System.out.println(hashTable.deleteKey(22));
        System.out.println("-------------------");
        System.out.println(hashTable.getValue(11));
        hashTable.printTable();
    }

    public  HashTable( ){
        hashTable=  new Node[defaultSize];
    }

    public  HashTable(int size){
        this.defaultSize = size<=0 ?this.defaultSize : size;
        hashTable=  new Node[this.defaultSize];
    }

    private  int hash(K key){


        return  key.hashCode()%(defaultSize=hashTable.length);
    }

    private static class  Node<K, V>{
        final K key;
        V value;
        Node<K,V> next;
        final int hash;
         Node(K key, V value, int hash, Node<K,V> next) {
             this.key = key;
             this.value = value;
             this.hash = hash;
             this.next=next;
         }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }


    /*
     * @param: [key, value]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/26 11:26
     * @description:增
     */
    public void putValue(K key, V value){
          int tableHash;
          Node<K, V> currentNode;Node<K, V> replaceNode = null;
          if (key==null){
              return;
          }

          if ((currentNode=hashTable[tableHash=hash(key)]) == null) {//如果当前桶位null
             hashTable[tableHash]=new Node<>(key, value,key.hashCode(),null);

         }
         else if (currentNode.hash==tableHash||key.equals(currentNode.key)){//当前桶hash与key hash相同或者不同但equal相等
              replaceNode=currentNode;
         }
         else {
                if (currentNode.next==null){//当前桶位只有一个将此节点放入链表尾部
                    currentNode.next=new Node<>(key, value, key.hashCode(), null);

                }
                else {
                    Node<K, V>  node=currentNode;
                    boolean isReplace=false;
                    while (node.next != null) {
                         if (node.hash==tableHash||key.equals(node.key)) {//链表中存在相同值的节点需要替换
                             isReplace=true;
                             replaceNode=node;
                             break;
                         }
                         node=node.next;
                    }
                   if (!isReplace) node.next = new Node<>(key, value, key.hashCode(), null);//只有value值不相同的时候才放入链表尾部
                }

         }

         if (replaceNode!=null&&value != null){
              replaceNode.value = value;
         }

        float loadFactor = 0.75f;

        if (++count>= loadFactor *defaultSize){
           hashTable= resize();
        }
    }

    /*
     * @param: [key]
     * @return: V
     * @author: Jerssy
     * @dateTime: 2021/3/26 11:26
     * @description: 获取key,对应的value--查
     */
    public V getValue(K key){

        int keyHash=hash(key);
        Node<K, V> tableBucket;
        if (hashTable[keyHash]!=null){
            if ((tableBucket=hashTable[keyHash]).hash==keyHash){
                return  tableBucket.value;
            }
            else {
                Node<K, V> node=tableBucket;
                while (node != null){
                    if (node.hash==keyHash||key.equals(node.key)){
                        return node.value;
                    }
                    node=node.next;
                }
            }
        }
        return null;
    }

    /*
     * @param: [key, value]
     * @return: boolean
     * @author: Jerssy
     * @dateTime: 2021/3/26 11:26
     * @description: 改
     */
    public boolean setKey(K key,V value){

        int keyHash=hash(key);
        Node<K, V> tableBucket;
        if (hashTable[keyHash]!=null){
            if ((tableBucket=hashTable[keyHash]).hash==keyHash){
                tableBucket.value = value;
                return true;
            }
            else {
                Node<K, V> node=tableBucket;
                while (node != null){
                    if (node.hash==keyHash||key.equals(node.key)){
                        node.value = value;
                        return true;
                    }
                    node=node.next;
                }
            }
        }
        return false;
    }

    /*
     * @param: [key]
     * @return: V
     * @author: Jerssy
     * @dateTime: 2021/3/26 11:27
     * @description:删
     */
    public  V deleteKey(K key){

        int keyHash=hash(key);V oldValue;
        Node<K, V> tableBucket,deleteKeyNode = null;  Node<K, V> node = null;

        if (hashTable!=null&&defaultSize>0&&(tableBucket=hashTable[keyHash])!=null){
            if (tableBucket.hash == keyHash||tableBucket.key.equals(key)){
                deleteKeyNode=tableBucket;
            }
            else {
                if (tableBucket.next!=null){

                    node=tableBucket;
                    while (node!= null){
                        if (node.hash==keyHash||key.equals(node.key)){
                            deleteKeyNode = node;
                            break;
                        }
                        node=node.next;
                    }
                }
            }

            if (deleteKeyNode != null){
                oldValue=deleteKeyNode.value;
                if ((tableBucket==deleteKeyNode)){
                    hashTable[keyHash]=tableBucket.next;
                }

                else tableBucket.next=node.next;

                return oldValue;
            }

        }

        return null;
    }

    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/26 12:14
     * @description: 遍历
     */
    public  void  printTable(){

        for (Node<K, V> kvNode : hashTable) {
            if (kvNode != null) {
                if (kvNode.next == null) System.out.println(kvNode);

                else {
                    Node<K, V> tempNode = kvNode;
                    List<String> list = new ArrayList<>();
                    while (tempNode != null) {
                        list.add(tempNode.toString());
                        tempNode = tempNode.next;
                    }
                    System.out.println(String.join("-->", list));
                }
            }
        }
    }

    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/3/26 14:29
     * @description: 简单扩容,正好再熟悉HashMap的底层扩容机制
     */
    private  Node<K, V>[]  resize(){
        int  newSize=defaultSize<<1;
        Node<K, V>[] newTable=new Node[newSize];
        Node<K, V> currentNode;int newHash = 0;
        Node<K, V>[]oldTable=hashTable;
        hashTable=null;
        for (Node<K, V> kvNode : oldTable) {
            if ((currentNode = kvNode) != null) {
                if (currentNode.next == null) {
                    newTable[newHash=currentNode.hash % newSize] = currentNode;
                } else {

                    Node<K, V> tempNode = currentNode;
                    Node<K, V> hNode = null, lNode = null;
                    while (tempNode!=null){
                        if ((currentNode.hash & defaultSize) == 0) {//低位链表
                            if (lNode == null) {
                                lNode = tempNode;
                            } else {
                                lNode.next = tempNode;
                            }
                        } else {//高位链表
                            if (hNode == null) {
                                hNode = tempNode;
                            } else {
                                hNode.next = tempNode;
                            }
                        }
                        tempNode = tempNode.next;
                    }
                    if (lNode!=null){
                        newTable[newHash]= lNode;
                    }
                    if (hNode!=null){
                        newTable[newHash+defaultSize]= hNode;
                    }
                }
            }
        }

        return newTable;

    }
}
