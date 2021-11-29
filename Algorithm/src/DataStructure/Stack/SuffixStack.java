package DataStructure.Stack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Stack;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 后缀表达式(逆波兰表达式)
 * @create 2021-02-20 11:41
 *
 * 后缀表达式
 * 后缀表达式又称逆波兰表达式,与前缀表达式相似，只是运算符位于操作数之后
 * (6+8)*5-2 -->后缀表达式：6 8 + 5 * 2 -
 *
 * 后缀表达式的计算机求值

 * 从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（次顶元素 和 栈顶元素），并将结果入栈；重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
 * 例如: (3+4)×5-6 对应的后缀表达式就是 3 4 + 5 × 6 - , 针对后缀表达式求值步骤如下:
 *
 * 1 从左至右扫描，将3和4压入堆栈；
 * 2 +运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
 * 3 将5入栈；
 * 4 接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
 * 5 将6入栈
 * 6 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
 *
 *  小结：正向扫描表达式遇数字直接入数字栈，遇运算符，依次弹出数字栈栈顶两个数和此运算符进行计算，结果再次入数字栈，扫描结束时最后数字栈的栈顶即为表达式计算的最终结果。
 */

//逆波兰表达式计算器
public class SuffixStack {
    public static void main(String[] args) {

        String suffixString="1 2 348 + 6 4.1 * - + 5 -";

        System.out.println("后缀表达式值:"+calculate(suffixString));

        System.out.println("原值"+(1+((2+348)-6*4.1)-5));

    }

    public static String calculate(String suffixStr) {
        String newStr=suffixStr.replaceAll("\t|\r|\n*","");
        List<String> splitList = List.of(newStr.split("\\s"));

        Stack<Double> numberStack = new Stack<>();
        for (String str : splitList) {
            if (str.matches("^[+-]?\\d+(\\.\\d+)?$")) {
                numberStack.push(Double.valueOf(str));
            } else if (str.matches("^[+\\-*/%]$")) {
                calculateNumber(numberStack, str);
            }
        }
        BigDecimal bd1 = new BigDecimal(String.valueOf(numberStack.pop()));

        return bd1.stripTrailingZeros().toPlainString();
    }

    public  static void  calculateNumber(Stack<Double> numberStack ,String str){
        Double num1 = numberStack.pop();
        Double num2 = numberStack.pop();

        Double result = countMarked(str, num2, num1);
        numberStack.push(result);

    }

    public static Double countMarked(String str, Double result2, Double result1){

        Double aDouble2 = scaleResult(result2);
        Double aDouble1 = scaleResult(result1);

        return switch (str){
            case "+"-> aDouble2+aDouble1;
            case  "-"-> aDouble2-aDouble1;
            case  "*"-> aDouble2*aDouble1;
            case  "/"-> aDouble2/aDouble1;
            case  "%"-> aDouble2%aDouble1;
            case  "^"-> Math.pow(aDouble2,aDouble1);
            default -> throw new IllegalStateException("Unexpected value: " + str);
        };
    }

    private static Double scaleResult(Double result){
        BigDecimal bd1 = new BigDecimal(String.valueOf(result));
        BigDecimal bdResult= bd1.setScale(4, RoundingMode.HALF_UP);

        return bdResult.doubleValue();

    }

}
