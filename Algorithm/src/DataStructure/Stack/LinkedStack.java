package DataStructure.Stack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 栈的单链表展现
 * @create 2021-02-18 10:24
 */
public class LinkedStack<T> {

    private  Node<T> top;


    static  class  Node<T>{

        T item;
        Node<T> next;

        public Node(T item) {
            this.item = item;
        }
        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }


    public  void push(T item) {

        Node<T> node= new Node<>(item);
        if (top != null) {
            node.next=top;
        }
          top=node;
    }

    public T  pop() {

        if (top == null){
            throw  new RuntimeException("栈为空");
        }

        Node<T>  newNode = top.next;
        T oldValue= top.item;
        top.next = null;
        top=newNode;

        return oldValue;
    }

    public  String toString() {

        if(top == null){
            throw new RuntimeException("队列是空的");
        }

        List<String> list = new ArrayList<>();

        Node<T> node=top;
        while (node != null){
            list.add((String) node.item);
            node=node.next;
        }

        return  String.join("-->", list);
    }

    public static void main(String[] args) {

        LinkedStack<String> linkedStack=new LinkedStack<>();
        linkedStack.push("kobe");
        linkedStack.push("james");
        linkedStack.push("leo");
        linkedStack.push("cop");
        linkedStack.push("io");

        System.out.println(linkedStack);
        linkedStack.pop();
        linkedStack.pop();
        linkedStack.pop();
        System.out.println(linkedStack);
    }
}
