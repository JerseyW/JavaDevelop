package DataStructure.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 链式队列
 * @create 2021-02-07 19:51
 */
public class LinkedQueue {

    public static void main(String[] args) {
         Queue<String> queue = new  Queue<>();
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
                    case 's'-> queue.show();
                    case 'e'-> {
                        break label;
                    }
                    case 'a'->{
                        System.out.println("请输入");
                        queue.offer(scanner.next());
                    }
                    case 'g'-> System.out.println("取出的数据为"+queue.pop());
                    case 'h'-> System.out.println("队列头部的数据为"+queue.peek());
                    case 'l'->System.out.println("队列的长度为"+queue.size());
                    case 'c'->queue.clear();
                }
            }catch ( Exception e){
                e.printStackTrace();
            }
        }
    }

    static  class  Queue<T> {
      int size=0;
      private Node<T> head;
      private Node<T> tail;

        //入队列
        public void offer(T t){
           Node<T> node= new Node<>(t);
           if(isEmpty()){
               head=node;
           }
           else tail.next=node;
           tail=node;
           size++;
        }

        //出队列
        public T pop(){
            if(isEmpty()){
                 throw new RuntimeException("队列是空的");
            }
            T oldValue=head.item;
            if (head.next==null){//就一个元素
                head=tail=null;
            }
            else {
                Node<T> newNode=head.next;
                head.next=null;
                head = null;
                head=newNode;
            }
            size--;
            return  oldValue;
        }

        public  boolean isEmpty(){
            return size == 0;
        }

        public  T peek(){
            return head.item;
        }

        public  int size(){
            return  size;
        }

        public  void show(){
            if(isEmpty()){
                throw new RuntimeException("队列是空的");
            }

            Node<T> node=head;
            List<String> list = new ArrayList<>();
            while (node!= null){
                list.add((String) node.item);
                node = node.next;
            }
            System.out.println(String.join("-->", list));
        }

        public void  clear(){
            for (Node<T> node= head; node != null; ) {
                 Node<T> next = node.next;
                 node.item = null;
                 node.next = null;
                 node = next;
            }
            head = tail = null;
            size = 0;
        }

    }
    //节点类
    static class   Node<T>{
         private T item;
         private  Node<T> next;

          public  Node(T item) {
              this.item = item;
          }
    }
}
