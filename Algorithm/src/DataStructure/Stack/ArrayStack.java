package DataStructure.Stack;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 栈
 * @create 2021-02-17 17:31
 * 根据字符串计算结果
 *栈是一个先入后出(FILO-First In Last Out)的有序列表。
 *
 * 栈(stack)是限制线性表中元素的插入和删除只能在线性表的同一端进行的一种特殊线性表。允许插入和删除的一端，为变化的一端，称为栈顶(Top)，另一端为固定的一端，称为栈底(Bottom)。
 *
 * 根据栈的定义可知，最先放入栈中元素在栈底，最后放入的元素在栈顶，而删除元素刚好相反，最后放入的元素最先删除，最先放入的元素最后删除
 *
 * 栈的应用场景：
 * 1 子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，直到子程序执行完后再将地址取出，以回到原来的程序中。
 *
 * 2 处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆栈中。
 *
 * 3 表达式的转换[中缀表达式转后缀表达式]与求值(实际解决)。
 *
 * 4 二叉树的遍历。
 *
 * 5 图形的深度优先(depth一first)搜索法。
 *
 */

public class ArrayStack<T> {
       private  int head;

       private final T[] arr;

       private final int maxSize;


    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>(5);
        stack.push(1);
        stack.push(2);
        stack.push(-1);
        stack.push(0);
        stack.push(5);
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(8);
        stack.push(98);
        System.out.println(stack);
    }


    public ArrayStack(int arrMaxSize){
        if (arrMaxSize <= 0) throw new IndexOutOfBoundsException("arrMaxSize 不能为0");
        maxSize=arrMaxSize;

        arr= (T []) new Object[arrMaxSize];
        head=-1;//指向栈顶

    }

    public  void push(T t){

         if (isFull()){
             throw new IndexOutOfBoundsException("栈已满");
         }
         arr[++head]=t;

    }

    public  boolean isEmpty() {
        return  head==-1;
    }

    //判断队列满
    public  boolean isFull(){

        return  head==maxSize-1;
    }

    public T pop() {

        if (isEmpty()) {
            throw new RuntimeException("栈为空");
        }
        T t = arr[head];
        arr[head--] = null;//置空出栈的元素，防止内存泄漏
        return t;

    }

    public  String toString() {
       StringBuilder buf = new StringBuilder();
       for(int j=head;j>=0;j--){
           buf.append(arr[j]);
           if (j == 0) {
               break;
           }
           buf.append("---");

       }
       return buf.toString();
    }
}
