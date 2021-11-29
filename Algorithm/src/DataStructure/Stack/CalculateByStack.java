package DataStructure.Stack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

/**
 * @author Jerssy
 * @version V1.0
 * @Description:使用栈(中缀表达式)实现综合计算器
 * @create 2021-02-18 16:36
 * 7*2*2-5+1+5+3-4=？
 * 定义两个栈：数字栈和符号栈
 * 1 通过一个index(索引)，来遍历表达式
 * 2 如果index返现式数字，入数字栈
 * 3 如果发现扫描到运算符则：
 *     1 如果当前符号栈为空则入符号栈
 *     2：如果符号栈不为空，有操作符，就进行比较运算符优先级，当前运算符优先级小于等于栈中的操作符
 *        就是从数字栈pop出两个数，符号栈pop一个符号进行运算，得到一个结果，入数字栈；然后当前运算符入符号栈
 *     3：如果符号栈不为空，有操作符。当前运算符优先级大于栈中的操作符，就直接入符号栈
 *  4 扫描结束后就顺序的从数栈和符号栈pop相应的数和符号并运算
 *  5 最后数字栈只有一个数字，即表达式的结果
 *
 *  小结：符号栈为空则直接入栈，若符号栈中栈顶优先级低，则该符号直接入栈，反之栈顶优先级高，则取出数字栈中两个元素和符号栈的一个符号进行计算结果入数字栈
 *        扫描结束从循环从数字栈依次弹出两个数字和符号栈弹出一个符号进行计算，并将结果入数字栈，最后弹出数字栈的栈顶即为表达式的最终结果。
 *
 */
public class CalculateByStack {

    public static void main(String[] args) {

         new calculateFrame();

    }

     public static Double calculate(String str){
         Stack<Double> numsStack=new Stack<>();//数字栈
         Stack<String> markStack=new Stack<>();//符号栈
         String newStr=str.replaceAll("\\s*|\t|\r|\n","");
         StringBuilder numBuilder= new StringBuilder();

         for (int i = 0; i < newStr.length();i++) {
              char calculateChar = newStr.charAt(i);
              if (Character.isDigit(calculateChar)||calculateChar=='.') {
                  numBuilder.append(calculateChar);
                   int lastLen = newStr.length()-1;
                   char nextChar= newStr.charAt(Math.min(i+1,lastLen));
                   if (nextChar!='.'&&(!Character.isDigit(nextChar)||i==lastLen)){//下一个字符不是数字或者到末尾了
                       numsStack.push(Double.parseDouble(numBuilder.toString()));
                       numBuilder.delete(0,numBuilder.length());
                   }
              }

              else if(calculateChar=='('){
                      markStack.push(String.valueOf(calculateChar));
              }
              else if(calculateChar==')'){//计算括弧里的数据
                      while (true){
                          if (markStack.peek().charAt(0)=='('){
                              markStack.pop();
                              break;
                          }
                          Double result1 = numsStack.pop();
                          Double result2 = numsStack.pop();
                          String pop = markStack.pop();
                          Double result = countMarked(pop, result2, result1);
                          numsStack.push(result);
                      }

              }else if (isOption(String.valueOf(calculateChar))){

                  if (!markStack.isEmpty()) {

                      boolean isHigh = isHighPriority(String.valueOf(newStr.charAt(i)),markStack.peek());
                      if (!isHigh) {//小于栈中的运算符优先级则计算
                          Double result1 = numsStack.pop();
                          Double result2 = numsStack.pop();
                          String pop = markStack.pop();
                          Double result = countMarked(pop, result2, result1);
                          numsStack.push(result);
                      }
                  }

                  markStack.push(String.valueOf(calculateChar));
              }

         }

         while (!numsStack.isEmpty()&&!markStack.isEmpty()) {

             Double result1 = numsStack.pop();
             Double result2 = numsStack.pop();

             String pop = markStack.pop();
             numsStack.push(countMarked(pop, result2, result1));
         }
         return numsStack.pop();
     }

     public  static int  getPriority(String option){
         return switch (option) {
             case "+", "-" -> 0;
             case "*", "/","%" -> 1;
             case "^"-> 2;
             default -> -1;
         };

     }
    private static boolean isHighPriority(String operate1, String operate2) {

        return getPriority(operate1) > getPriority(operate2);
    }

     public  static  boolean  isOption(String option){

        return option.equals("+") || option.equals("-") || option.equals("*") || option.equals("/") || option.equals("%")|| option.equals("^");
     }

     public static Double countMarked(String pop, Double result2, Double result1){
         Double aDouble2 = scaleResult(result2);
         Double aDouble1 = scaleResult(result1);

         return switch (pop){
             case "+"-> aDouble2+aDouble1;
             case  "-"-> aDouble2-aDouble1;
             case  "*"-> aDouble2*aDouble1;
             case  "/"-> aDouble2/aDouble1;
             case  "%"-> aDouble2%aDouble1;
             case  "^"-> Math.pow(aDouble2,aDouble1);
             default -> throw new IllegalStateException("Unexpected value: " + pop);
         };
     }

    private static Double scaleResult(Double result){
        BigDecimal bd1 = new BigDecimal(String.valueOf(result));
        BigDecimal bdResult= bd1.setScale(4, RoundingMode.HALF_UP);

        return bdResult.doubleValue();

    }

    static class calculateFrame extends Frame {
        Button  btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,add,
                min, mul, div,div1,div2,div3,div4, equal, clear, delete;
        TextField tx;

        public calculateFrame() {

            //添加组件

            btn0 = new Button("0");
            btn1 = new Button("1");
            btn2 = new Button("2");
            btn3 = new Button("3");
            btn4 = new Button("4");
            btn5 = new Button("5");
            btn6 = new Button("6");
            btn7 = new Button("7");
            btn8 = new Button("8");
            btn9 = new Button("9");
            btn10 = new Button(".");
            add = new Button("+");
            min = new Button("-");
            mul = new Button("*");
            div = new Button("/");
            div1 = new Button("%");
            div2 = new Button("(");
            div3 = new Button(")");
            div4 = new Button("^");
            equal = new Button("=");
            clear = new Button("clear");
            delete = new Button("<-");
            tx = new TextField("");

            //添加一个面板
            Panel panel = new Panel();
            //设置面板布局
            panel.setLayout(new GridLayout(4, 3));
            addBtn(panel, btn0,btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8,btn9,btn10);
            addBtn(panel, add, min, mul, div, div1,div2,div3,div4, delete);
            //将面板添加到Frame中心
            add(panel, BorderLayout.CENTER);
            //将文本框添加到Frame
            add(tx, BorderLayout.NORTH);
            add(panel);
            add(clear, BorderLayout.WEST);
            add(equal, BorderLayout.EAST);


            //添加事件监听
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            ActionListen  actionListen = new ActionListen();
            addListens(actionListen, add, min, mul, div, div1,div2,div3, clear, delete, equal,div4);
            addListens(actionListen, btn0,btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,btn10);


            setVisible(true);
            setBounds(100, 100, 350, 300);
            setResizable(false);
        }


        private void addListens(ActionListen actionListen, Button ...arButtons) {
            for (Button arButton : arButtons) {
                arButton.addActionListener(actionListen);
            }
        }

        private void addBtn(Panel panel, Button ...buttons) {

            for (Button button : buttons) {
                panel.add(button);
            }
        }


        //事件监听
        private class ActionListen implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tx.getText();


                text = setText(e, text, btn0, btn1, btn2, btn3, btn4, btn5, btn6,btn7,div4);
                text = setText(e, text, btn8, btn9,btn10, add, min, mul, div,div1,div2,div3);

                //计算表达式的结果
                if (e.getSource() == equal) {
                    if (tx.getText() != null) {
                        String newString;
                        int i = text.lastIndexOf("=");
                        if (i > 0) {
                            newString=text.substring(text.lastIndexOf("=")+1);
                        }
                        else {
                            newString=text;
                        }
                        text += equal.getLabel();
                        tx.setText(text);

                        Double calculate = calculate(newString);
                        BigDecimal bdCalculate = new BigDecimal(String.valueOf(calculate));
                        BigDecimal bdResult= bdCalculate.setScale(4, RoundingMode.HALF_UP);
                        String sResult = bdResult.stripTrailingZeros().toPlainString();

                        text += sResult;
                        tx.setText(text);
                    }

                }
                //清零按钮
                if (e.getSource() == clear) {
                    tx.setText("");
                }
                //删除按钮
                if (e.getSource() == delete) {
                    String s = tx.getText();
                    if (s.length() > 0) {
                        tx.setText(s.substring(0, s.length() - 1));
                    }
                }
            }

            private String setText(ActionEvent e, String text, Button ...buttons) {
                for (Button button : buttons) {
                    if (e.getSource() == button) {
                        text += button.getLabel();
                        tx.setText(text);
                    }
                }

                return text;
            }

        }
    }
}
