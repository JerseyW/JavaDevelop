package DataStructure.Stack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Stack;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 前缀表达式(波兰表达式)
 * @create 2021-02-20 11:29
 *
 * 前缀表达式的运算符都位于操作数之前
 *  (6+8)*5-2 -->前缀表达式：- * +6 8 5 2
 *
 * 前缀表达式的计算机求值：
 *
 * 从右至左扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（栈顶元素 和 次顶元素），并将结果入栈；重复上述过程直到表达式最左端，最后运算得出的值即为表达式的结果
 * 例如: (3+4)×5-6 对应的前缀表达式就是 - × + 3 4 5 6 , 针对前缀表达式求值步骤如下:
 * 1 从右至左扫描，将6、5、4、3压入堆栈
 * 2 遇到+运算符，因此弹出3和4（3为栈顶元素，4为次顶元素），计算出3+4的值，得7，再将7入栈
 * 3 接下来是×运算符，因此弹出7和5，计算出7×5=35，将35入栈
 * 4 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
 *
 *
 * 小结：反向扫描表达式，遇数字压人数字栈，遇运算符弹出栈顶和次栈顶两个数和此运算符进行计算结果入数字栈，扫描结束时，数字栈栈顶即为表达式的最终计算的结果
 *
 */
public class PrefixStack {

    public static void main(String[] args) {
        String PrefixString="- * + 3 4 5 6.5";

        System.out.println("前缀表达式的值："+preFixCalculate(PrefixString));
        System.out.println("原值"+((3+4)*5-6.5));
    }

    public  static  String  preFixCalculate(String string){
        String newStr=string.replaceAll("\t|\r|\n*","");
        Stack<Double> numberStack = new Stack<>();
        List<String> splitList = List.of(newStr.split("\\s"));

        for(int j=splitList.size()-1;j>=0;j--){
            if (splitList.get(j).matches("^[+-]?\\d+(\\.\\d+)?$")) {
                numberStack.push(Double.valueOf(splitList.get(j)));
            } else if (splitList.get(j).matches("^[+\\-*/%]$")) {
                calculateNumber(numberStack, splitList.get(j));
            }
        }

        BigDecimal bd1 = new BigDecimal(String.valueOf(numberStack.pop()));

        return bd1.stripTrailingZeros().toPlainString();
    }
    public  static void  calculateNumber(Stack<Double> numberStack ,String str){
        Double num1 = numberStack.pop();
        Double num2 = numberStack.pop();

        Double result = countMarked(str, num1, num2);
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
