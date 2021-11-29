package DataStructure.Tree.BPlusTree;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author: Jerssy
 * @create: 2021-04-18 10:04
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: B+树测试类
 *
 *  与B树类似：区别：所有关键字都出现在叶子结点的链表中即叶子结点相当于是存储（关键字）数据的数据层，为所有叶子结点增加一个链指针
 *                 节点的子树数和关键字数相同
 *   B-树中的每个结点关键字个数 n 的取值范围为⌈m/2⌉ -1≤n≤m-1，而在 B+树中每个结点中关键字个数 n 的取值范围为：⌈m/2⌉≤n≤m。
 *
 *   b+树 存在两种：第一种，结点内有n个元素就会n个子结点；每个元素是子结点元素里的最大值或最小值。
 *                第二种，结点内有n个元素就会n+1个子结点；最左边的子结点小于最小的元素，其余的子结点是>=当前元素。
 *
 *索引:节省存储空间、如何提高数据增删改查的执行效率
 * 对于系统设计需求，我们一般可以从功能性需求和非功能性需求两方面来分析
 * 1. 功能性需求
 *   1 数据是格式化数据还是非格式化数据
 *
 *   2 建索引的原始数据类型
 *     一类是结构化数据，比如，MySQL中的数据；另一类是非结构化数据
 *
 *   3 数据是静态数据还是动态数据？
 *
 *   4 索引存储在内存还是硬盘
 *
 *2. 非功能性需求
 *
 *    维护成本？
 *    占用存储空间的限制？
 *
 * 红黑树作为一种常用的平衡二叉查找树，数据插入、删除、查找的时间复杂度是O(logn)，也非常适合用来构建内存索引。Ext文件系统中，对磁盘块的索引，
 * 用的就是红黑树。
 * B+树比起红黑树来说，更加适合构建存储在磁盘中的索引。B+树是一个多叉树，所以，对相同个数的数据构建索引，B+树的高度要低于红黑树。当借助索引
 * 查询数据的时候，读取 B+ 树索引，需要的磁盘 IO 次数非常更少。所以，大部分关系型数据库的索引，比如 MySQL 、 Oracle ，都是用 B+ 树来实现的。
 * 跳表也支持快速添加、删除、查找数据。而且，我们通过灵活调整索引结点个数和数据个数之间的比例，可以很好地平衡索引对内存的消耗及其查询效
 * 率。 Redis 中的有序集合，就是用跳表来构建的。

 * 除了散列表、红黑树、 B+ 树、跳表之外，位图和布隆过滤器这两个数据结构，也可以用于索引中，辅助存储在磁盘中的索引，加速数据查找的效率。我们来
 * 看下，具体是怎么做的？
 * 我们知道，布隆过滤器有一定的判错率。但是，我们可以规避它的短处，发挥它的长处。尽管对于判定存在的数据，有可能并不存在，但是对于判定不存在
 * 的数据，那肯定就不存在。而且，布隆过滤器还有一个更大的特点，那就是内存占用非常少。我们可以针对数据，构建一个布隆过滤器，并且存储在内存
 * 中。当要查询数据的时候，我们可以先通过布隆过滤器，判定是否存在。如果通过布隆过滤器判定数据不存在，那我们就没有必要读取磁盘中的索引了。对
 * 于数据不存在的情况，数据查询就更加快速了。
 * 实际上，有序数组也可以被作为索引。如果数据是静态的，也就是不会有插入、删除、更新操作，那我们可以把数据的关键词（查询用的）抽取出来，组织
 * 成有序数组，然后利用二分查找算法来快速查找数据
 */
public class BPlusTree<K extends Comparable<K>,V> {
    public  static final int DEFAULT_DEGREE = 3;
    public BPlusNode<K,V> root;

    public BPlusTree() {
        this(DEFAULT_DEGREE);
    }

    public BPlusTree(int degree) {
        //阶数
        root = new LeafNode<>(degree);
    }

    /*
     * @param: [key]
     * @return: V
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:11
     * @description: 查找
     */
    public V  searchData(K key){
        Objects.requireNonNull(key);
        var search = (LeafNode<K, V>) root.search(key);
        if (search.insertIndex<search.entryKey.size()){

            var  searchEntry = search.entryKey.get(search.insertIndex);
            if (searchEntry.key().equals(key)) return searchEntry.value();
        }

        return null;
    }

    /*
     * @param: [key, value]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:11
     * @description: 插入
     */
    public void insert(K key, V value){
        Objects.requireNonNull(key);

        if (root.keys.isEmpty()){
            root = root.insert(key,value);
        }
        else {
            root = root.search(key).insert(key, value);
        }
    }

    /*
     * @param: [key]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:12
     * @description:删除
     */
    public  void  delete(K key){

        Objects.requireNonNull(key);

        if (root.keys.isEmpty()){
            System.out.println("BPlus tree is empty!");
            return;
        }
        var  deleteNode =(LeafNode<K,V>) root.search(key);

        if (!deleteNode.keys.get(deleteNode.insertIndex>=deleteNode.keyNum?0:deleteNode.insertIndex).equals(key)){
            throw new NoSuchElementException("The key "+key+" is not existence in the BPlus Tree or double delete it");
        }

        root= deleteNode.delete(key);
    }

    public static void main(String[] args) {
        BPlusTree<Integer,String> bPlusTree=new BPlusTree<>(4);

        bPlusTree.insert(12,"jim");
        bPlusTree.insert(15,"jack");
        bPlusTree.insert(16,"jack22");
        bPlusTree.insert(1,"tim");
        bPlusTree.insert(5,"kobe");
        bPlusTree.insert(6,"james");
        bPlusTree.insert(62,"ben");
        bPlusTree.insert(36, "leo");
        bPlusTree.insert(37, "dd");
        bPlusTree.insert(45, "hh");
        bPlusTree.insert(39, "tt");
         bPlusTree.insert(56, "gg");
         bPlusTree.insert(3, "kow");
         bPlusTree.insert(31, "hk");
         bPlusTree.insert(30, "bn");
         bPlusTree.insert(82, "er");
         bPlusTree.insert(78, "cd1");
         bPlusTree.insert(55, "cd2");
         bPlusTree.insert(43, "cd3");
        bPlusTree.insert(120, "cd4");

        bPlusTree.root.printNode();

        bPlusTree.delete(39);

         //bPlusTree.delete(45);


         bPlusTree.delete(1);

         bPlusTree.delete(37);

         bPlusTree.delete(3);

         bPlusTree.delete(6);
         bPlusTree.delete(15);
          bPlusTree.delete(16);
         bPlusTree.delete(62);
          bPlusTree.delete(36);
          bPlusTree.delete(82);

          bPlusTree.delete(12);
         bPlusTree.delete(31);

         System.out.println("**********");
         bPlusTree.root.printNode();

        System.out.println(bPlusTree.searchData(45));
    }
}



