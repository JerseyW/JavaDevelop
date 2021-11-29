package DataStructure.DoubleLinked;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 双向链表
 * @create 2021-02-15 11:11
 *
 * prev指针和next指针，分别指向当前节点的前一个数据和后一个数据
 * item:data域保存当前节点的数据
 *
 * 遍历：可以向前，也可以向后查找；
 * 添加最后：找到尾部节点last；连接当前节点temp.next=last;last.prev=temp
 * 修改：单链表一样；
 * 删除:temp.prev.next=temp.next; temp.next.prev=temp.prev;
 */
public class DoubleLinked {
}
