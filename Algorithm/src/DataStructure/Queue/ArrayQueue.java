package DataStructure.Queue;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 队列
 * @create 2021-02-06 14:46
 *
 *  队列介绍
 * 队列是一个有序列表，可以用数组或是链表来实现。支持FIFO，尾部添加、头部删除（先进队列的元素先出队列）
 *
 * 遵循先入先出的原则。即：先存入队列的数据，要先取出。后存入的要后取出
 *
 * 都是从队列的尾部添加数据的;拉取数据是从队列头部拉取的，拉取完之后将该元素删除
 *
 * 队列有2中实现方式：数组和链表
 * 队列本身是有序列表，若使用数组的结构来存储队列的数据，则队列数组的声明如下图, 其中 maxSize 是该队列的最大容量。

 * 因为队列的输出、输入是分别从前后端来处理，因此需要两个变量 front及 rear分别记录队列前后端的下标，front 会随着数据输出而改变，而 rear则是随着数据输入而改变，
 *
 *  非环形队列--存在假溢出现象--队列满后，将全部元素出队却不能继续添加元素的情况
 */
public class ArrayQueue {
    public static void main(String[] args){

        Queue<String> queue = new Queue<>(3);
        Scanner scanner=new Scanner(System.in);
        label:while (true){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):获取队列数据");
            System.out.println("h(head):获取队列的头部数据");
            System.out.println("l(size):获取队列的长度");
            System.out.println("c(clear):清空队列");
            char c = scanner.next().charAt(0);
            try {
                switch (c){
                    case 's'-> queue.showQueue();
                    case 'e'-> {
                        break label;
                    }
                    case 'a'->{
                        System.out.println("请输入");
                        queue.addQueue(scanner.next());
                    }
                    case 'g'-> System.out.println("取出的数据为"+queue.popQueue());
                    case 'h'-> System.out.println("队列头部的数据为"+queue.peekQueue());
                    case 'l'->System.out.println("队列的长度为"+queue.size());
                    case 'c'->queue.clear();
                }
            }catch ( Exception e){
                e.printStackTrace();
            }
        }
    }


    static class   Queue< T>{
        private final int maxSize;//队列的最大容量，即数组长度
        private  int head;//队列的头
        private  int tail;//队列的尾部
        private final T[] arr;//存放数据，模拟队列
        private int size=0;  //队列长度

        //创建队列的构造器
        public  Queue(int arrMaxSize){
            if (arrMaxSize <= 0) throw new IndexOutOfBoundsException("arrMaxSize 不能为0");
            maxSize=arrMaxSize;

            arr= (T []) new Object[arrMaxSize];
            head=0;//指向队列的头部
            tail =0;//指向队列的尾部
        }
        //判断队列满
        public  boolean isFull(){

            return  size>=maxSize;
        }
        //判断队列为空
        public  boolean isEmpty(){
            return size==0;
        }
        //添加数据到队列
        public  void addQueue(T n){
            if (isFull()){
                throw  new RuntimeException("队列已满");
            }

            if (tail > maxSize-1){//改成循环队列
                tail = 0;
            }
            arr[tail++]=n;
            size++;
        }
        //数据出队列
        public T  popQueue() {
            if (isEmpty()){
                throw  new RuntimeException("队列为空");
            }

            if (head> maxSize-1){//改成循环队列
                head = 0;
            }
            size--;
           return   arr[head++];
        }
        //显示队列的所有数据
        public void  showQueue(){
            if (isEmpty()){
                throw  new RuntimeException("队列为空");
            }
            for (int i = head; i <head+size ; i++) {
                System.out.printf("arr[%s]=%s\n",i%maxSize, arr[i%maxSize]);
            }
        }
        //显示队列的头部

        public  T peekQueue(){
            if (isEmpty()){
                throw  new RuntimeException("队列为空");
            }
            return arr[head];
        }

        //队列的容量
        public int size(){
            return  size;
        }

        // 清空队列
        public void clear(){
            Arrays.fill(arr, 0);
            tail = head = 0;
            size = 0;
        }
    }
}
