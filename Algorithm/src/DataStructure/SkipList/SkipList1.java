package DataStructure.SkipList;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-04-08 10:16
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 跳表 有序的数据结构
 *
 *
 * 跳跃表（SkipList）是一种可以替代平衡树的数据结构。跳跃表让已排序的数据分布在多层次的链表结构中，默认是将 Key 值升序排列的，以 0-1 的随机值决定一个数据是否能够攀升到高层次的链表中。它通过容许一定的数据冗余，达到 “以空间换时间” 的目的。
 *
 * 跳跃表的效率和 AVL 相媲美，查找、添加、插入、删除操作都能够在 O(LogN) 的复杂度内完

跳表具有如下性质：

(1) 由很多层结构组成

(2) 每一层都是一个有序的链表

(3) 最底层(Level 1)的链表包含所有元素

(4) 如果一个元素出现在 Level i 的链表中，则它在 Level i 之下的链表也都会出现。

(5) 每个节点包含两个指针，一个指向同一链表中的下一个元素，一个指向下面一层的元素

每2个节点抽1个，每层索引节点 n/2,n/4n/8…8,4,2 空间复杂度是O(n)

 *
 */

// 跳表中存储的是正整数，并且存储的数据是不重复的
public class SkipList1 {

    private static final int MAX_LEVEL = 16;    //

    private int levelCount = 1;   // 索引的层级数

    private final Node head = new Node( -1);    // 头结点

    private final Random random = new Random();


    public static void main(String[] args) {
        SkipList1 list = new SkipList1();
        list.insert(3);
        list.insert(2);
        list.insert(55);
        list.insert(4);
        list.insert(8);
        list.insert(0);
        list.insert(12);
        list.insert(5);

        list.display();
        System.out.println(list.find(0));
        list.delete(55);
        System.out.println("**********");
        list.display();
    }

    // 查找操作
    public Node find(int value){
        Node p = head;
        for(int i = levelCount - 1; i >= 0; --i){
            while(p.next[i] != null && p.next[i].data < value){
                p = p.next[i];
            }
        }


        if(p.next[0] != null && p.next[0].data == value){
            return p.next[0];    // 找到，则返回原始链表中的结点
        }else{
            return null;
        }
    }

    // 插入操作
    public void insert(int value){

        int level =head.next[0]==null?1: randomLevel();
        Node newNode = new Node(value);

        newNode.level = level; // 通过随机函数改变索引层的结点布置
        // 记录要更新的层数，表示新节点要更新到哪几层，即找出每层中待插入节点的前继节点

        Node[] predecessors  = new Node[level];

        Node p = head;

        for(int i = level - 1; i >= 0; i--){// 层是从下到上的,从最顶层开始查找每层的前驱节点
            while(p.next[i] != null && p.next[i].data < value){
                p = p.next[i];
            }
            predecessors [i] = p;//找到该层level的前驱节点
        }

        for(int i = 0; i < level; ++i){//为每层都插入newNode节点
            newNode.next[i] = predecessors [i].next[i];
            predecessors [i].next[i] = newNode;
        }


        levelCount=Math.max(levelCount, level);
    }

    // 删除操作
    public void delete(int value){
        Node[] predecessors = new Node[levelCount];
        Node p = head;
        System.out.println(head);
        for(int i = levelCount - 1; i >= 0;  i--){//还是找每层的前驱，如果这里是双链表形式的就不要要找前驱了，可以直接定位到删除节点的前一个及后一个节点
            while(p.next[i] != null && p.next[i].data < value){
                p = p.next[i];
            }
            predecessors[i] = p;
        }

        if(p.next[0] != null && p.next[0].data == value){
            for(int i = levelCount - 1; i >= 0; --i){
                if(predecessors[i].next[i] != null && predecessors[i].next[i].data == value){
                    predecessors[i].next[i] = predecessors[i].next[i].next[i];
                }
            }
        }
        //如果删除元素value后level层元素数目为0，层数减少一层
        while (head.next[levelCount-1] == null) {
            levelCount--;
        }
    }

    // 随机函数
    private int randomLevel(){
        int level = 1;
        for(int i = 1; i < MAX_LEVEL; ++i){
            if(random.nextInt() % 2 == 1){
                level++;
            }
        }

        return level;
    }

    // Node内部类
    public static class Node{
        private final int data;
        //表示当前节点位置的下一个节点所有层的数据，从上层切换到下层，就是数组下标-1，
       // next[2]表示当前节点在第二层的下一个节点指针。
        private final Node[] next = new Node[MAX_LEVEL];

        private int level = 0;

        public  Node(int data){
            this.data = data;
        }

        @Override
        public String toString(){
            return "{data:" +
                    data +
                    "; leves: " +
                    level +
                    " }";
        }
    }

    // 显示跳表中的结点
    public void display(){
        Node p = head;
        while(p.next[0] != null){
            System.out.println(p.next[0] + " ");
            p = p.next[0];
        }
        System.out.println();
    }

}



