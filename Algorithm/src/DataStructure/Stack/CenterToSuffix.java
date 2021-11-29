package DataStructure.Stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 中缀表达式转后缀表达式
 * @create 2021-02-22 11:36
 *
 * 中缀表达式
 *  * 中缀表达式就是常见的运算表达式，如(3+4)×5-6
 *  * 中缀表达式的求值是我们人最熟悉的，但是对计算机来说却不好操作(前面我们讲的案例就能看的这个问题)，
 *  * 因此，在计算结果时，往往会将中缀表达式转成其它表达式来操作(一般转成后缀表达式.)
 *
 *
 * 方法：
 * 1初始化运算符栈s1和储存中间结果的List s2；
 * 2从左至右扫描中缀表达式；
 * 3 遇到操作数时，将其放入s2；
 * 4遇到运算符时，比较其与s1栈顶运算符的优先级：
 *   1 如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
 *   2 若优先级比栈顶运算符的高，也将运算符压入s1；否则，将s1栈顶的运算符弹出并放入到s2中，再次转到(4-1)与s1中新的栈顶运算符相比较；
 * 5 遇到括号时：
 * (1) 如果是左括号“(”，则直接压入s1
 * (2) 如果是右括号“)”，则依次弹出s1栈顶的运算符，并放入s2，直到遇到左括号为止，此时将这一对括号丢弃
 * 6 重复步骤2至5，直到表达式的最右边
 * 7 将s1中剩余的运算符依次弹出并放入s2
 * 8 s2中的元素并输出即为中缀表达式对应的后缀表达式
 *
 * 小结： 遇数字直接入中转s2 ,若s1为空或者栈顶为左括弧，或者当前扫描的优先级比s1栈顶优先级时高直接入s1栈，否则循环取出比栈顶优先级低的放入中转s2，
 *       遇到右括弧时，依次弹出s1栈顶元素放入s2，直到遇到左括弧为止并消除左括弧，最后若s1不为空则将s1剩余元素放入s2，输出s2即为对应的后缀表达式
 */
public class CenterToSuffix {

    public static void main(String[] args) {
        String str="((2+3*4) + 5)-5";
        String newStr=str.replaceAll("\\s*|\t|\r|\n","");
        System.out.println("后缀表达式："+transformerStr(newStr));
    }

    private static  String transformerStr(String str){

        List<String> transferList= new ArrayList<>();
        Stack<Character> operatorStack = new Stack<>();
        StringBuilder builder= new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)||c=='.'){//是数字--ASCI >=48 <=57
                builder.append(c);
                int lastLen = str.length()-1;
                char nextChar= str.charAt(Math.min(i+1,lastLen));
                if ((nextChar !='.' && !Character.isDigit(nextChar)||i==lastLen)){//下一个字符不是数字或者到末尾了
                    transferList.add(builder.toString());
                    builder.delete(0,builder.length());
                }
            }
            //是操作符
           else if (isOperator(String.valueOf(c))) {
               //若当前操作符优先级比栈顶运算符的低的放入transferList;
                while (operatorStack.size()!=0&&!isHighPriority(c,operatorStack.peek())){
                    transferList.add(String.valueOf(operatorStack.pop()));
                }
                //其他情况(operatorStack为空，当前操作符优先级比栈顶高；栈顶运算符为左括号“(”)则压入operatorStack栈
                operatorStack.push(c);
            }

           else if (c=='(') {
                operatorStack.push(c);
           }

           else if (c==')') {

                //如果是右括号“)”，则依次弹出operatorStack栈顶的运算符，并放入transferList，直到遇到左括号为止，此时将这一对括号丢弃
                while (!operatorStack.isEmpty()&&operatorStack.peek()!='(') {
                    transferList.add(String.valueOf(operatorStack.pop()));
                }
                if (!operatorStack.isEmpty()) {
                   operatorStack.pop();//消除左括弧
               }
           }
        }

        while (!operatorStack.isEmpty()) {//将operatorStack中剩余的运算符依次弹出并放入transferList
            transferList.add(String.valueOf(operatorStack.pop()));
       }

        return String.join(" ",transferList);
    }

    private static boolean isOperator(String str){

        return  "+".equals(str)||"-".equals(str)||"*".equals(str)||"/".equals(str);
    }

    private static boolean isHighPriority(char operate1, char operate2) {

        return getPriority(operate1) > getPriority(operate2);
    }

    private static int  getPriority(char option){
        return switch (option) {
            case '+', '-' -> 0;
            case '*', '/','%' -> 1;
            default -> -1;
        };
    }
}
