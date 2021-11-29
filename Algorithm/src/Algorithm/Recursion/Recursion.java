package Algorithm.Recursion;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 递归
 * @create 2021-02-23 11:56
 *
 * 递归：递归就是方法自己调用自己,每次调用时传入不同的变量.递归有助于编程者解决复杂的问题,同时可以让代码变得简洁
 *
 * 递归调用规则：
 *  当程序执行一个次递归方法时，就会开辟一个独立的空间(栈)
 *  每个空间的数据(局部变量)是独立的
 *
 *  递归需要遵守的重要规则
 *
 *
 *递归的原则：
 *
 * 1 执行一个方法时，就创建一个新的受保护的独立空间(栈空间)
 * 2 方法的局部变量是独立的，不会相互影响, 比如n变量
 * 3 如果方法中使用的是引用类型变量(比如数组)，就会共享该引用类型的数据.
 * 4 递归必须向退出递归的条件逼近，否则就是无限递归,出现StackOverflowError，死龟了:)
 * 5 当一个方法执行完毕，或者遇到return，就会返回，遵守谁调用，就将最终的递归结果返回给递归开始的地方，并执行递归后面的语句。
 *   如果不需要返回值则直接执行递归后面的语句。同时当方法执行完毕或者返回时，该方法也就执行完毕
 */
public class Recursion {
    public static void main(String[] args) {
         print(4);
        System.out.println(factorial(5));

    }
    //打印
    public  static void   print(int n){
        if (n>2) {
            print(n-1);
        }
        //else
        System.out.println("n="+n);
    }
    //阶乘
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n; // 1 * 2 * 3
        }
    }
}
