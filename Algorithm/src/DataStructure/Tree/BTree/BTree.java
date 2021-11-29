package DataStructure.Tree.BTree;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author: Jerssy
 * @create: 2021-04-13 17:05
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: B 树实现
 *
 *  B-Tree是为磁盘等外存储设备设计的一种平衡查找树。因此在讲B-Tree之前先了解下磁盘的相关知识。

 *  系统从磁盘读取数据到内存时是以磁盘块（block）为基本单位的，位于同一个磁盘块中的数据会被一次性读取出来，而不是需要什么取什么。
 *
 *   B-tree中，每个结点包含：
 *
 *    1、本结点所含关键字的个数；
 *    2、指向父结点的指针；
 *    3、关键字；
 *    4、指向子结点的指针；
 *
 *   B树的说明:
 *
 *   1 B树的阶：节点的最多子节点个数。比如2-3树的阶是3，2-3-4树的阶是4
 *
 *   2 B-树的搜索，从根结点开始，对结点内的关键字（有序）序列进行二分查找，如果命中则结束，否则进入查询关键字所属范围的儿子结点；重复，直到所对应的儿子指针为空，或已经是叶子结点
 *   关键字集合分布在整颗树中, 即叶子节点和非叶子节点都存放数据.
 *
 *   3 搜索有可能在非叶子结点结束
 *
 *   4 所有叶子结点(失败节点)都出现在同一层，叶子结点不包含任何关键字信息；

 *  B树特点：
 *
 *   1 非叶子结点的关键字个数=指向儿子的指针个数-1
 *
 *   2 树中每个结点至多有m个孩子；(M为树的度，M>2)
 *
 *   3 若根结点不是叶子结点，则至少有2个子节点--[2,M]
 *
 *   4.非根节点的子节点数为[ceil(M / 2), M]；
 *
 *   5.每个非根结点存放[ceil(M / 2)-1, M-1]个关键字
 *
 *   6 根节点存放[1 M-1]个关键字
 *
 *   7 所有叶子结点位于同一层；
 *
 *   8 节点的子节点个数=该节点的关键字个数+1;
 *
 *  二叉搜索树某些父子节点合并即可变成B树
 *
 *    二代合并的节点最多拥有4个子节点（至少4阶B树）
 *    三代合并的节点最多拥有8个子节点（至少8阶B树）
 *    四代合并的节点最多拥有16个子节点（至少16阶B树）
 *    n代合并的节点最多拥有2^n个子节点（至少2^n阶B树）
 *
 *    反之 m阶B 树，最多需要log2m 代合并
 *
 *   B Tree 的搜索
 *
 *     先在节点内部从小到大开始搜索元素，如果命中直接返回搜索结束，未命中，在去对应的字节点去搜索，重复直到搜索完
 *
 *
 *   B Tree的插入：
 *
 *     新添加的元素必定是添加到叶子节点
 *     1 若该节点元素个数小于m-1，直接插入；
 *     2 若该节点元素个数等于m-1，引起节点分裂；以该节点中间元素为分界,将这个节点分为左右两部分，取中间元素（偶数个数，中间两个随机选取）插入到父节点中；
 *     3 重复上面动作，直到所有节点符合B树的规则；最坏的情况一直分裂到根节点，生成新的根节点，高度增加1；
 *
 *   B tree的删除：
 *
 *    真正被删除的节点是叶子节点
 *
 * 1.如果删除的是叶子节点上的数据，删除之后移动元素保证该叶子节点顺序不变。
 *
 *     1.1 如果此时关键字个数依然满足条件，则删除结束。
 *
 *     1.2 如果此时关键字个数小于条件个数，则需要进行移动。
 *
 *          1.2.1首先看其相邻兄弟节点是否有富余关键字[>ceil(M / 2)-1]，如果有，则将父节点的合适的关键字下移到当前要删除字节点内的最小位置，注意此处放入的时候需要保持顺序，然后将富余节点的最左（最右）关键字上移到父节点中，然后删除该关键字。
 *
 *          1.2.2如果其相邻兄弟节点没有富余关键字了，则需要将该节点和相邻节点进行合并（选择左右没关系，但是要保证顺序的一致就行），移动的规则是将父节点的中间元素（父节点必须在两个需要合并的节点之间）下移到改节点中被删除的关键字处，然后记性合并操作，操作之后可能父节点能满足数目条件，如果不满足的话，仍然再次执行1.2.1 --> 1.2.2的步骤，简要的说就是先看相邻兄弟节点是否富余，富余的话借父节点，不富余的话就合并。
 *
 * 2.如果删除的是非叶子节点上的数据，非叶子节点特殊性在于它存在孩子节点。
 *
 *     2.1 首先将该关键字的后继节点中的最左边上移到该位置（这个最左边是指中序遍历后继节点中最左边的节点），如果后继节点上移一个关键字之后满足数目条件，则结束。
 *
 *     2.2 后继节点数目不满足条件数目了，则需要观察能否进行借的操作，如果不能借，则需要合并，和叶子节点操作类似
 *
 *
关键要领，元素个数小于  （m/2 -1）就合并，大于 （m-1）就分裂

删除小结：
 *      先找到根节点，从根节点往下找要删除的关键字，如果关键字不存在，抛出删除异常；
 *      如果存在，若关键字不是最下层非终端节点（叶子节点的上一层），此时只需要关键字和关键字节点紧邻的右子树中的最小值N互换，然后删除N，
至此已转化为待删除关键字在最下层非终端节点的情况，所以只需讨论关键字在最下层
 *       非终端节点的情况。此时分为三种情况：
 *        1. 被删除关键字所在的节点关键字数大于等于 ceil(M/2)
 *        2. 被删除关键字所在的节点关键字数等于 ceil(M/2)-1，且该节点相邻右兄弟（或左兄弟）中的关键字数大于 ceil(M/2)-1，只需将该兄弟节点中的最小（或最大）关键字上移到 双亲节点中，而将双亲节点中小于（或大于）该上移动关键字的紧邻关键字下移到被删关键字所在的节点中。
 *        3. 被删除关键字所在的节点关键字数等于 ceil(M/2)-1，且左右兄弟节点的关键字数都等于 ceil(M/2)-1，假设该节点有右兄弟A，则在删除关键字之后，它所在的节点中剩余的关键字和 孩子引用，加上双亲节点中的指向A的左侧关键字一起，合并到A中去（若没有右兄弟则合并到左兄弟中）。此时双亲节点的关键字数减少了一个，若因此导致其关键字数小于ceil(M/2)-1，则对双亲节点做递归处理。
 *
 *      删除可能会产生新的根节点，会导致当前节点不再是根节点
 *
 *
 * B+树是B树的变体，也是一种多路搜索树。
 *
 *
 * B+树的说明:
 *
 * 1 B+树的搜索与B树也基本相同，区别是B+树只有达到叶子结点才命中（B树可以在非叶子结点命中），其性能也等价于在关键字全集做一次二分查找
 *
 * 2 所有关键字都出现在叶子结点的链表中（即数据只能在叶子节点【也叫稠密索引】），且链表中的关键字(数据)恰好是有序的。
 *
 * 3 不可能在非叶子结点命中
 *
 * 4 非叶子结点相当于是叶子结点的索引（稀疏索引），叶子结点相当于是存储（关键字）数据的数据层
 *
 * 5 更适合文件索引系统
 *
 * 6 非叶子结点的  子树指针=关键字数相同；
 *
 * 7 为所有叶子结点增加一个链指针
 * 8 根结点只有1个，分支数量范围[2,m]
 * 9 根节点外其他节点关键字[Ceil(M/2),M]
 * B树和B+树各有自己的应用场景，不能说B+树完全比B树好，反之亦然.
 *
 * B* tree
 *
 *
 * B*树是B+树的变体，在B+树的非根和非叶子结点再增加指向兄弟的指针。
 *
 * B*树的说明:
 *
 * B*树定义了非叶子结点关键字个数至少为(2/3)*M，即块的最低使用率为2/3，而B+树的块的最低使用率为B+树的1/2。

 * 从第1个特点我们可以看出，B*树分配新结点的概率比B+树要低，空间使用率更高
 *
 *  为什么说B+树比B树更适合数据库索引？
 *
 * 1、 B+树的磁盘读写代价更低：B+树的内部节点并没有指向关键字具体信息的指针，因此其内部节点相对B树更小，如果把所有同一内部节点的关键字存放在同一盘块中，那么盘块所能容纳的关键字数量也越多，一次性读入内存的需要查找的关键字也就越多，相对IO读写次数就降低了。
 *
 * 2、B+树的查询效率更加稳定：由于非终结点并不是最终指向文件内容的结点，而只是叶子结点中关键字的索引。所以任何关键字的查找必须走一条从根结点到叶子结点的路。所有关键字查询的路径长度相同，导致每一个数据的查询效率相当。
 *
 * 3、由于B+树的数据都存储在叶子结点中，分支结点均为索引，方便扫库，只需要扫一遍叶子结点即可，但是B树因为其分支结点同样存储着数据，我们要找到具体的数据，需要进行一次中序遍历按序来扫，所以B+树更加适合在区间查询的情况，所以通常B+树用于数据库索引。
 */
public class BTree {

    public static void main(String[] args) {
        BTreeNode<Integer,String> node = new BTreeNode<>(3);
        node= node.insert(35,"jim");
        node= node.insert(13,"jack");
        node=  node.insert(14,"leo");
        node=  node.insert(22,"JDK");
        node=  node.insert(25,"JDK8");
        node=  node.insert(20,"JDK9");
        node=  node.insert(40,"JDK11");
        node=  node.insert(45,"JDK12");
        node=  node.insert(89,"JDK13");
        node.print();
        node=node.delete(22);
        node=node.delete(25);
        node= node.delete(89);
        node.print();

    }

}

class BTreeNode <K extends Comparable<K>, V> {

    int degree;//阶数
    BTreeNode<K,V> parent;//父节点
    LinkedList<BTreeNode<K,V>> childList;//存放子节点的list

    LinkedList<Entry<K,V>> entryKey;//存放关键字的list,K 为key,V 为data

    public  BTreeNode(int degree){
        if (degree<3) throw new IllegalArgumentException("The BTree degree must be at least three!");

        this.degree = degree;
        parent=null;
        entryKey=new LinkedList<>();
        childList=new LinkedList<>();
    }
    public  BTreeNode(BTreeNode<K,V> parent){
        this(parent.degree);
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "BTreeNode{" +
                "degree=" + degree +
                ", parent=" + parent +
                ", entryKey=" + entryKey +
                '}';
    }


    private static record  Entry<K extends Comparable<K>, V>(K key ,V data) {

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", data=" + data +
                    '}';
        }
    }

    /*
     * @param: [key, data]
     * @return: DataStructure.Tree.BTree.BTreeNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/14 11:53
     * @description:插入
     */
    public BTreeNode<K,V> insert(K key, V data){
          Objects.requireNonNull(key);
          var  kvEntry = new Entry<>(key, data);
          if ( Objects.isNull(entryKey)||entryKey.size()==0){
               //创建一个节点作为根节点，存在两个孩子

              entryKey.add(kvEntry);
              childList.add(new BTreeNode<>(this));
              childList.add(new BTreeNode<>(this));
              return this;
          }

         var insertNode=this.getRoot().search(key);
         var isPresent=insertNode.entryKey.stream().filter(e->e.key.compareTo(key)==0).findFirst();

         if (isPresent.isPresent()) throw  new IllegalArgumentException(key+" to key not unique");

         insertNode(insertNode, kvEntry, new BTreeNode<>(degree));

        return  getRoot();

    }

    /*
     * @param: [insertNode, entry, childNode]
     * @return: DataStructure.Tree.BTree.BTreeNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/14 13:59
     * @description: 插入实现
     */
    private BTreeNode<K,V> insertNode(BTreeNode<K,V> insertNode, Entry<K,V> entry, BTreeNode<K,V> childNode){

         var insertIndex=0;
         var entryKey=insertNode.entryKey;

         while (insertIndex<entryKey.size()&&entryKey.get(insertIndex).key.compareTo(entry.key)<0){

             insertIndex++;
         }

         entryKey.add(insertIndex,entry);
         childNode.parent= insertNode;

         insertNode.childList.add(insertIndex+1,childNode);

         if (insertNode.entryKey.size()>degree-1){
             return splitNode(insertNode);
         }

         return this;
    }

    /*
     * @param: [insertNode]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/14 13:15
     * @description: 分裂节点
     */
    private BTreeNode<K,V> splitNode(BTreeNode<K,V> insertNode){

        var middleIndex=degree/2;
        var middleEntry=insertNode.entryKey.get(middleIndex);


        var  rNode = new BTreeNode<K,V>(degree);
        rNode.entryKey= new LinkedList<>(insertNode.entryKey.subList(middleIndex+1, insertNode.entryKey.size()));
        rNode.childList= new LinkedList<>(insertNode.childList.subList(middleIndex+1, insertNode.childList.size()));

        for(var rChild : rNode.childList) {
            rChild.parent = rNode;
        }
        insertNode.entryKey= new LinkedList<>(insertNode.entryKey.subList(0, middleIndex));
        insertNode.childList= new LinkedList<>(insertNode.childList.subList(0, middleIndex+1));

        if (insertNode.parent==null){
            insertNode.parent= new BTreeNode<>(degree);
            insertNode.parent.entryKey.add(middleEntry);
            insertNode.parent.childList.add(insertNode);
            insertNode.parent.childList.add(rNode);
            rNode.parent = insertNode.parent;
            return insertNode;
        }

        return insertNode(insertNode.parent, middleEntry, rNode);

    }

    /*
     * @param: []
     * @return: DataStructure.Tree.BTree.BTreeNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/14 11:53
     * @description:根节点
     */
    public BTreeNode<K,V> getRoot() {
        var p = this;
        while(p.parent!=null) {
            p = p.parent;
        }
        return p;
    }

    public void print() {
        printNode(this);
    }

    private void printNode(BTreeNode<K,V> node) {
        if (node.entryKey.size()>0)
        System.out.println(node.entryKey);
        for(var  child : node.childList) {
            printNode(child);
        }
    }

    /*
     * @param: [key]
     * @return: DataStructure.Tree.BTree.BTreeNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/14 11:53
     * @description:查找
     */
    public BTreeNode<K,V> search(K key){

        var searchIndex=0;
        var keyNum=0;
        while (searchIndex<entryKey.size()&&(keyNum=entryKey.get(searchIndex).key.compareTo(key))<=0){
            if (keyNum==0){
               return this;
            }
            searchIndex++;
        }

        if (childList.size() > 0&&childList.get(0).entryKey.size() > 0){
            return childList.get(searchIndex).search(key);
        }

        return  this;
    }

    /*
     * @param: [key]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/14 15:30
     * @description: 删除
     */
    public BTreeNode<K,V>  delete(K key){
        Objects.requireNonNull(key);
        var root = this.getRoot();
        if (root==null) return this;
        var keyNode = root.search(key);

        var deletedIndex = keyNode.entryKey.stream().filter(e -> e.key.compareTo(key) == 0).mapToInt(keyNode.entryKey::indexOf).findFirst().orElseThrow(()->new NoSuchElementException("key is not present to delete"));

        //找后继
        BTreeNode<K,V> successorNode,targetNode=keyNode;

        if ((successorNode=keyNode.childList.get(deletedIndex+1)).entryKey.size()>0){

            while (successorNode.childList.get(0).entryKey.size()>0){
                successorNode=successorNode.childList.get(0);
            }

            deletedIndex=0;
            targetNode.entryKey.remove(deletedIndex);
            targetNode.entryKey.add(deletedIndex,successorNode.entryKey.get(0));
            targetNode=successorNode;

        }

        return delete(targetNode, deletedIndex, 0);
    }

    /*
     * @param: [targetNode, deletedIndex, childIndex]
     * @return: DataStructure.Tree.BTree.BTreeNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/14 18:19
     * @description: 删除对应节点targetNode；deletedIndex:删除的entry在entryKey中的索引；childIndex:targetNode的对应孩子节点
     */
    private  BTreeNode<K,V>  delete(BTreeNode<K,V> targetNode,int deletedIndex,int childIndex){

         var parentTargetIndex=targetNode.parent!=null?targetNode.parent.childList.indexOf(targetNode):0;

          targetNode.entryKey.remove(deletedIndex);
          targetNode.childList.remove(childIndex);

        if (targetNode.parent==null){
             //如果父节点关键为空了，降低树的高度根节点[1,M-1]
             if (targetNode.entryKey.size()==0&&targetNode.childList.size() > 0){
                 targetNode = targetNode.childList.get(0);
                 targetNode.parent = null;
             }
         }
         else {

             if (targetNode.entryKey.size()<Math.ceil(degree/2.0)-1)
                 return flxUpNode(targetNode,parentTargetIndex);
         }

        return targetNode.getRoot() ;
    }

    /*
     * @param: [targetNode, parentTargetIndex]
     * @return: DataStructure.Tree.BTree.BTreeNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/14 18:18
     * @description: B 树修复 targetNode：当前节点； parentTargetIndex：当前节点在父节点childList的索引位置
     */
    private   BTreeNode<K,V> flxUpNode(BTreeNode<K,V> targetNode,int parentTargetIndex){
          //targetNode的左右孩子索引
          var leftChildIndex=parentTargetIndex-1;var rightChildIndex=parentTargetIndex+1;
          var parentIndex=Math.max(0,leftChildIndex);
          var parentChildList=targetNode.parent.childList;
          var  hasLeftChild=leftChildIndex>=0;
          //左边兄弟节点关键字富余
          if (hasLeftChild&&parentChildList.get(leftChildIndex).entryKey.size()>Math.ceil(degree/2.0)-1){

              downParent(targetNode,parentIndex,leftChildIndex, true);
          }
          //右边兄弟节点关键字富余
          else if (rightChildIndex<parentChildList.size()&&parentChildList.get(rightChildIndex).entryKey.size()>Math.ceil(degree/2.0)-1){
              downParent(targetNode,parentIndex,rightChildIndex,false);
          }
          else {
              //都不富余与对应兄弟节点合并
              mergeChild(targetNode,parentTargetIndex,parentIndex,hasLeftChild);

              //删除下移动后原来父节点合并时没有删除的关键字和及其孩子
              return delete(targetNode.parent, parentIndex, parentTargetIndex);
          }

          return  this.getRoot();
    }

    /*
     * @param: [targetNode, childIndex, parentIndex, hasLeftChild]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/14 18:17
     * @description: 下移父节点，上移动兄弟节点entry；childIndex：targetNode的孩子对应索引； parentIndex父节点对应entry索引
     */
    private  void  downParent(BTreeNode<K,V> targetNode,int parentIndex,int childIndex, boolean hasLeftChild){
          //下移父节点关键字
          var downEntry=targetNode.parent.entryKey.remove(parentIndex);

          var subChild= targetNode.parent.childList.get(childIndex);

          var removeKeyIndex=hasLeftChild?subChild.entryKey.size()-1:0;
          //上移动兄弟节点对应的关键字
          var  upEntry = subChild.entryKey.remove(removeKeyIndex++);
          var  insetIndex=hasLeftChild?0:targetNode.entryKey.size();

          targetNode.entryKey.add(insetIndex ,downEntry);

          var removeChildIndex=hasLeftChild?removeKeyIndex:0;
         //目标节点加入兄弟节点的删除的关键字对应的孩子节点
          var  removeChild = subChild.childList.remove(removeChildIndex);
          targetNode.childList.add(hasLeftChild?insetIndex:0,removeChild);
          removeChild.parent=targetNode;

          //父节点加入兄弟节点上移动的关键字
          targetNode.parent.entryKey.add(parentIndex,upEntry);

    }

    /*
     * @param: [targetNode, parentTargetIndex, parentIndex, hasLeftChild]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/14 18:19
     * @description: 与兄弟节点合并 childIndex：parentTargetIndex当前节点在父节点childList的索引位置； parentIndex父节点对应entry索引
     */
    private  void  mergeChild(BTreeNode<K,V> targetNode,int parentTargetIndex,int parentIndex, boolean hasLeftChild){
        // 如果有左兄弟，和左兄弟合并,否则和右兄弟合并
        var mergeIndex=hasLeftChild?parentTargetIndex-1:parentTargetIndex+1;
        //不能直接删除父节点关键字，等合并后递归删除多余未删除的父节点，这样是合理且安全的
        var downEntry=targetNode.parent.entryKey.get(parentIndex);
        var mergeSub =targetNode.parent.childList.get(mergeIndex);

        var insertIndex=hasLeftChild?mergeSub.entryKey.size():0;

        mergeSub.entryKey.add(insertIndex,downEntry);

        //加上targetNode剩余的关键字注意上面entryKey已经加过一次，在加剩下的要加一，也为孩子节点添加做铺垫--这个位置坑爹啊，找了很久，孩子总比关键字多一，插入指针也要多加一
        mergeSub.entryKey.addAll(hasLeftChild?++insertIndex:0,targetNode.entryKey);

        //加上targetNode剩余的孩子节点
        mergeSub.childList.addAll(insertIndex,targetNode.childList);

        //更改孩子的指针指向合并节点
        targetNode.childList.forEach(c -> c.parent=mergeSub);

    }
}
