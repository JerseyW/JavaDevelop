package DataStructure.Tree.BPlusTree;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author: Jerssy
 * @create: 2021-04-18 9:58
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: B+树节点类
 */
//B+树节点密封类--要求JDK15及以上
abstract  sealed class BPlusNode<K extends Comparable<K>,V> permits  NotLeafNode,LeafNode{

    protected  int degree;//阶数
    protected  BPlusNode<K,V> parent;//父节点
    protected  int keyNum;//关键字个数
    protected  LinkedList<K> keys;//存放数据项的list,K为key
    protected  boolean isLeaf;//是否为叶子节点

    LinkedList<BPlusNode<K,V>> childList;//存放孩子节点list

    public  BPlusNode(int degree){
        if (degree<3) throw new IllegalArgumentException("The BPlusTree degree must be at least three!");
        this.degree = degree;
        parent=null;
        keys= new LinkedList<>();
        childList= new LinkedList<>();
    }

    //查找
    abstract BPlusNode<K,V> search(K key);

    //删除
    abstract BPlusNode<K,V> delete(K key);
    //插入
    abstract BPlusNode<K,V> insert(K key,V value);

    //上溢
    protected  boolean isOverFlow(){
        return  keyNum >degree;
    }
    //下溢
    protected  boolean isUnderFlow(){

        return  keyNum<Math.ceil(degree/2.0);
    }

    //根节点
    protected  BPlusNode<K, V> getRoot(){
        var currentNode=(BPlusNode<K, V>)this;
        while (currentNode.parent != null) {
            currentNode =  currentNode.parent;
        }

        return currentNode;
    }

    /*
     * @param: [oldKey, newKey]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:14
     * @description:更新关键字
     */
    protected void upDateKey(K oldKey,K newKey){
        //向上更新父节点关键字
        var currentNode=(BPlusNode<K, V>)this;
        while (currentNode.parent!=null){
            currentNode= currentNode.parent;
            var upDateIndex=Collections.binarySearch(currentNode.keys, oldKey);
            oldKey=currentNode.keys.getLast();
            currentNode.keys.set(upDateIndex, newKey);
            if (newKey.compareTo(currentNode.keys.getLast())<0) return;
        }
    }

    /*
     * @param: []
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:15
     * @description: 递归打印
     */
    public void printNode( ) {
        var root = this;
        if (root.keys.isEmpty()){
            System.out.println("BPlus tree is empty cant not print!");
            return;
        }
        if (root.isLeaf){
            var leafNode=(LeafNode<K, V>)root;
            if (leafNode.next != null){
                System.out.println(leafNode.entryKey);
                leafNode=leafNode.next;
                leafNode.printNode();
            } else System.out.println(leafNode.entryKey);
        }
        else {

            root=root.childList.getFirst();
            root.printNode();
        }
    }

    /*
     * @param: []
     * @return: DataStructure.Tree.BPlusTree.BPlusNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:16
     * @description:分裂节点，分裂成左右两个节点，this节点点保留关键字大的序列，右节点获取小的关键字序列，并加入当前节点的父节点
     */
    protected BPlusNode<K, V> splitNode() {

        var mid = degree / 2;
        K upKey = keys.get(mid);

        var sibling = this.isLeaf ? new LeafNode<K, V>(degree) : new NotLeafNode<K, V>(degree);

        var nodeParent = this.parent == null ? new NotLeafNode<K, V>(degree) : this.parent;

        sibling.keys = new LinkedList<>(keys.subList(0, mid + 1));

        keys = new LinkedList<>(keys.subList(mid + 1, keyNum));

        if (this instanceof LeafNode<K, V> cur && sibling instanceof LeafNode<K, V> sib){

            sib.entryKey = new LinkedList<>(cur.entryKey.subList(0, mid + 1));
            cur.entryKey = new LinkedList<>(cur.entryKey.subList(mid + 1, keyNum));

        }
        else {

            sibling.childList = new LinkedList<>(childList.subList(0, mid + 1));
            childList = new LinkedList<>(childList.subList(mid + 1, keyNum));
            updateChildPoints(sibling);
        }

        sibling.parent = nodeParent;
        parent = nodeParent;
        keyNum=keys.size();
        sibling.keyNum=sibling.keys.size();
        nodeParent.childList.addFirst(sibling);

        if (nodeParent.keys.isEmpty()){
            nodeParent.keys.addFirst(upKey);
            nodeParent.keys.add(keys.getLast());
            nodeParent.childList.add(this);
            nodeParent.keyNum=nodeParent.keys.size();
            return nodeParent;
        }

        return  this.parent.insert(upKey,null);
    }

    //更新子节点的指针指向当前节点
    private  void  updateChildPoints(BPlusNode<K, V> nodeP){
        for (var  child : nodeP.childList) {
            child.parent=nodeP;
        }
    }

    /*
     * @param: [parentTargetIndex]
     * @return: DataStructure.Tree.BPlusTree.BPlusNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:17
     * @description: 删除后下溢修复
     */
    protected BPlusNode<K, V> flxUpNode(int parentTargetIndex){

        var leftChildIndex = parentTargetIndex-1;
        var rightChildIndex = parentTargetIndex+1;
        var parentChildList = this.parent.childList;
        var isLeftBrother = leftChildIndex>=0;

        if (isLeftBrother&&parentChildList.get(leftChildIndex).keyNum>Math.ceil(degree/2.0)){
            borrowNodeFromBrother(leftChildIndex,true);
        }
        else if (rightChildIndex<parentChildList.size()&&parentChildList.get(rightChildIndex).keyNum > Math.ceil(degree / 2.0)){
            borrowNodeFromBrother(rightChildIndex,false);
        }
        else {
            var mergeIndex= isLeftBrother?leftChildIndex:rightChildIndex;
            mergeChild(mergeIndex,parentTargetIndex,isLeftBrother);
            //兄弟节点被合并到当前删除关键字的节点中如果是左兄弟节点与之合并的则删除父节点左兄弟节点位置的关键字，反之删除当前节点的位置的父节点关键字
            var deleteKey=isLeftBrother?this.parent.keys.get(leftChildIndex):this.parent.keys.get(parentTargetIndex);
            //删除合并后原来父节点合并时没有删除的关键字
            return this.parent.delete(deleteKey);
        }

        return this.getRoot();
    }

    /*
     * @param: [brotherIndex, isLeftBrother]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:17
     * @description: 向兄弟节点借节点
     */
    private  void  borrowNodeFromBrother(int brotherIndex,boolean isLeftBrother){

        var brotherNode = this.parent.childList.get(brotherIndex);
        var borrowIndex=isLeftBrother?brotherNode.keyNum-1:0;
        var insertIndex=isLeftBrother?0:this.keyNum;
        K oldKey=isLeftBrother?brotherNode.keys.getLast():this.keys.getLast();
        K newKey;
        this.keys.add(insertIndex,newKey=brotherNode.keys.remove(borrowIndex));
        brotherNode.keyNum--;
        this.keyNum++;
        if (this instanceof  LeafNode<K, V> leafNode&&brotherNode instanceof LeafNode<K, V> brother){

            leafNode.entryKey.add(insertIndex,brother.entryKey.remove(borrowIndex));
        }
        else this.childList.add(insertIndex,brotherNode.childList.remove(borrowIndex));

        K upDateKey=isLeftBrother?brotherNode.keys.getLast():newKey;

        //借完后需要更新父节点
        upDateKey(oldKey,upDateKey);
    }

    /*
     * @param: [mergeIndex, parentTargetIndex, isLeftBrother]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:17
     * @description: 兄弟节点与当前节点合并
     */
    private  void  mergeChild( int mergeIndex,int parentTargetIndex,boolean isLeftBrother){

        var mergeNode = this.parent.childList.remove(mergeIndex);//删除将要合并的兄弟节点
        var insertIndex=isLeftBrother?0:this.keyNum;
        this.keyNum=mergeNode.keyNum+this.keyNum;
        this.keys.addAll(insertIndex,mergeNode.keys);
        var preIndex=Math.max(isLeftBrother? mergeIndex-1:parentTargetIndex-1,0);
        var previousNode=this.parent.childList.get(preIndex);

        if (previousNode instanceof  LeafNode<K, V> pre&&this instanceof LeafNode<K, V> currentNode
            && mergeNode instanceof LeafNode<K, V>  merge){

            currentNode.entryKey.addAll(insertIndex,merge.entryKey);

            if (isLeftBrother) {
                if (preIndex!=0)
                    pre.next = currentNode;
                else {
                    //如果兄弟节点的前一个等于当前兄弟节点则兄弟节点的前一个则是另一个分支的后节点找到并与当前this连接指针
                    var gramP=this.parent.parent;
                    if (gramP!=null){
                        var firstLeaf=(LeafNode<K, V>)gramP.childList.getFirst().childList.getFirst();
                        while (firstLeaf.next != null){
                            if (firstLeaf.next==merge){
                                firstLeaf.next=currentNode;
                                break;
                            }
                            firstLeaf=firstLeaf.next;
                        }
                    }
                }
            }
            else currentNode.next=merge.next;

            merge.next=null;
        }

        if (!this.isLeaf) {
            this.childList.addAll(insertIndex,mergeNode.childList);
            //更新孩子的指针指向合并后的this节点
            mergeNode.childList.forEach(c -> c.parent=this);
        }
    }
}

final class  NotLeafNode<K extends Comparable<K>, V> extends BPlusNode<K,V> {

    public NotLeafNode(int degree) {
        super(degree);

        isLeaf=false;
    }

    //非叶子节点查找,定位到需要插入的叶子节点
    @Override
    BPlusNode<K,V> search(K key) {
        var insertIndex= Collections.binarySearch(keys, key);
        if (this.isLeaf){
            return  this;
        }

        insertIndex= insertIndex>=0?insertIndex:-insertIndex-1;
        if (childList.size() > 0&&childList.getFirst().keys.size() > 0){
            return childList.get(Math.min(insertIndex, keys.size()-1)).search(key);
        }

        return  this;

    }
    //非叶子节点插入
    @Override
    BPlusNode<K, V> insert(K key, V value) {

         if (this.keys.contains(key)) throw  new IllegalArgumentException(key+" to key not unique");

         var insertIndex= Collections.binarySearch(keys, key);

         insertIndex= -insertIndex - 1;

         if (insertIndex>0){//分裂的时候右节点由于是较小的所以默认加入第一位，如果关键字插入位置不是首位则更新孩子节点插入位置
             this.childList.add(insertIndex,this.childList.removeFirst());
         }

         if (keyNum>0&&keys.getLast().compareTo(key)<0){
            upDateKey(keys.getLast(),key);
         }

        this.keys.add(insertIndex,key);
        this.keyNum=this.keys.size();

        concatLeaf();

        if (this.isOverFlow()) splitNode();

        return this.getRoot();
   }

   /*
    * @param: []
    * @return: void
    * @author: Jerssy
    * @dateTime: 2021/4/20 18:18
    * @description:连接叶子节点为单链表
    */
    private void concatLeaf(){

        if (childList.getFirst().isLeaf){
            var previous=(LeafNode<K, V>)childList.getFirst();

            if (previous.next!=null) previous.next=null;
            for (var  i = 1; i < childList.size(); i++) {
                var nextNode = (LeafNode<K, V>)childList.get(i);
                previous.next =nextNode;
                previous=nextNode;
            }
        }
    }

    /*
     * @param: [key]
     * @return: DataStructure.Tree.BPlusTree.BPlusNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:19
     * @description:非叶子节点删除
     */
    @Override
    BPlusNode<K,V> delete(K key) {

        var targetParentIndex=this.parent!=null?Collections.binarySearch(this.parent.keys,this.keys.getLast()):0;

        this.keys.remove(key);
        this.keyNum--;

        if (this.parent==null){
            //如果父节点关键字不够合并及借，降低树的高度根节点[2,M]
            if (this.childList.size() > 0&&this.keyNum<2){
                var newRoot=this.childList.getFirst();
                newRoot.parent = null;
                return newRoot;
            }
        }
        else {
            K updateKey;
            if ((updateKey=this.keys.getLast()).compareTo(key)<0){
                upDateKey(key,updateKey);
            }

            if (isUnderFlow()){
                return  flxUpNode(targetParentIndex);
            }

        }

        return this.getRoot();
    }
}

final class  LeafNode<K extends Comparable<K>,V> extends BPlusNode<K,V>  {

    LeafNode<K,V>  next;//连接叶子节点的指针
    LinkedList<LeafNode.Entry<K,V>> entryKey;//存放数据项的list,K 为key,V为data
    int insertIndex;//插入的位置索引

    public  LeafNode(int degree){
       super(degree);
       entryKey= new LinkedList<>();
       isLeaf=true;
       next=null;
    }

    //对叶子节点的key使用二分查找
    @Override
    LeafNode<K, V> search(K key)   {

        var searchIndex = Collections.binarySearch(keys, key);
        var node=this;
        if (searchIndex>=0){
            this.insertIndex=searchIndex;//找到保留检索位置为后序删除做铺垫
            return this;
        }
        else{

            this.insertIndex=-searchIndex-1;
        }

        return node;
    }

    /*
     * @param: [key, value]
     * @return: DataStructure.Tree.BPlusTree.BPlusNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:19
     * @description:叶子节点插入
     */
    BPlusNode<K,V> insert(K key,V value) {

        var  kvEntry =  new Entry<>(key, value);

        if (this.keys.contains(key)) throw  new IllegalArgumentException(key+" to key not unique");

        entryKey.add(insertIndex,kvEntry);

        if (keyNum>0&&keys.getLast().compareTo(key)<0){
                upDateKey(keys.getLast(),key);
        }

        keys.add(insertIndex,key);
        keyNum=entryKey.size();

        if (isOverFlow()) return  splitNode();

        return this.getRoot();
    }

    /*
     * @param: [key]
     * @return: DataStructure.Tree.BPlusTree.BPlusNode<K,V>
     * @author: Jerssy
     * @dateTime: 2021/4/20 18:20
     * @description: 叶子节点删除
     */
    @Override
    BPlusNode<K, V> delete(K key) {

        var deleteIndex=this.insertIndex;

        var removeKey = this.keys.remove(deleteIndex);
        this.entryKey.remove(deleteIndex);
        if (deleteIndex==--keyNum&&keyNum>0){

            upDateKey(removeKey,this.keys.getLast());
        }

        if (this.parent!=null){
            var parentTargetIndex= Collections.binarySearch(this.parent.keys,this.keys.getLast());

            if (this.isUnderFlow()){
                return flxUpNode(parentTargetIndex);
            }
        }

        return this.getRoot();
    }

    //记录类--要求JDK14及以上
   static record  Entry<K extends Comparable<K>, V>(K key ,V value) {
        @Override
       public String toString() {
           return "Entry{" + "key=" + key + ", value=" + value + '}';
       }
    }
}
