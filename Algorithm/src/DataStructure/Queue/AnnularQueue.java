package DataStructure.Queue;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 数组模拟环形队列
 * @create 2021-02-07 11:00
 *
 * 环形队列，顾名思义即让普通队列首尾相连，形成一个环形。当rear指向尾元素后，当队列有元素出队时，可以继续向队列中添加元素。
 *
 * 初始化队列时: front = rear = 0
 * 尾索引的下一个为头索引时表示队列满，即将队列容量空出一个作为约定,这个在做判断队列满的
 * 时候需要注意 (tail + 1) % maxSize == head [满]
 * tail == head [空]
 * 其中：head--->指向队列的第一个元素；tail--->指向队列的最后一个元素的后一个位置
 *
 * 使用环形实现后，会出现tail<head的情况，所以这里使用(tail-head+maxSize)%maxSize的方式计算有效元素个数。（或者在内部定义一个size属性，当元素入队时size++，当出队时size--）
 */
public class AnnularQueue {

    public static void main(String[] args) {
         Queue queue = new  Queue(3);//队列最大数据为3
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
                        System.out.println("输入一个数字");
                        queue.add(scanner.nextInt());
                    }
                    case 'g'-> System.out.printf("取出的数据为%d\n",queue.pop());
                    case 'h'-> System.out.printf("队列头部的数据为%d\n",queue.peek());
                    case 'l'->System.out.printf("队列的长度为%d\n",queue.size());
                    case 'c'->queue.clear();
                }
            }catch ( Exception e){
                e.printStackTrace();
            }
        }
    }

    //此队列为环形队列--预留空间法
    static class   Queue{
        private final int maxSize;//队列的最大容量，即数组长度
        private  int head;//队列的头
        private  int tail;//队列的尾部
        private final int[] arr;//存放数据，模拟队列
        private  int flag;

        //创建队列的构造器
        public  Queue(int arrMaxSize){
            if (arrMaxSize <= 0) throw new IndexOutOfBoundsException("arrMaxSize must be more than zero");
            maxSize=arrMaxSize;
            arr = new int[arrMaxSize];
            head=0;//指向队列的头部
            tail =0;//指向队列的尾部
            flag=1;
        }
        //判断队列满
        public  boolean  isFull(){
             return  (head == tail)&&flag == 0;
            //return  (tail+1)%maxSize==head;
        }
        //判断队列为空a
        public  boolean isEmpty(){
            return   (head == tail)&&flag == 1;
             //return head==tail;
        }
        //添加数据到队列
        public  void add(int n){
            if (isFull()){
                throw  new RuntimeException("队列已满");
            }

            arr[tail]=n;
            tail=(tail+1)%maxSize;
            System.out.println(tail);
            if (head == tail) flag = 0;
        }
        //数据出队列
        public int  pop() {
            if (isEmpty()){
                throw  new RuntimeException("队列为空");
            }
            int value = arr[head];
            System.out.println(tail);
            head=(head+1)%maxSize;
            if (head == tail) flag = 1;
            return value;
        }
        //显示队列的所有数据
        public void  showQueue(){
            if (isEmpty()){
                throw  new RuntimeException("队列为空");
            }
            for (int i = head; i <head+size(); i++) {
                System.out.printf("arr[%d]=%d\n",i%maxSize, arr[i%maxSize]);
            }
        }
        //显示队列的头部

        public  int peek(){
            if (isEmpty()){
                throw  new RuntimeException("队列为空");
            }
            return arr[head];
        }

        //队列的容量
        public int size(){
              if (tail > head) return tail - head;
              //return  (tail+maxSize-head)%maxSize;
              return   tail+maxSize-head ;
        }

        // 清空队列
        public void clear(){
            Arrays.fill(arr, 0);
            tail = head = 0;
        }
    }
}
