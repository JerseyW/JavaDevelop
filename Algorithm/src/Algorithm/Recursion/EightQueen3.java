package Algorithm.Recursion;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 八皇后解法3：回溯法
 * @create 2021-02-27 18:03
 *
 * 回溯法从问题本身出发，寻找可能出现的所有情况。和穷举法类似，但回溯法如果发现情况不存在就返回上一步继续尝试其他
 * 分支，直到找到所有解或者搜索完所有分支为止
 *
 * 递归是从问题结果出发，不断的自己调用自己
 *
 * 能进则进，不能进则换，不能换则退
 *
 *
 * 回溯法框架：
 *
 *    int a[n];int i=1
 *    while(i>0){//有路可走,但没有回溯到第一行
 *        a[i]//设置第一个皇后的占位
 *        while(i<=n&& a[i]位置不合法){
 *            寻找a[i]下一个列的占位
 *        }
 *        if(在搜索范围内){
 *            if(i==n)//找到一组可行解
 *              打印皇后
 *            else{
 *                n++//继续寻找完整的解
 *            }
 *        }
 *        else{
 *            //回溯到上一行继续寻找占位，i--;并清空当前 a[i]的所占空间
 *        }
 *    }
 *
 */
public class EightQueen3 {

    private  static  final  int MAX_QUEEN_LENGTH=8;

    private  int tailNumber=0;

    private final int[] queenArray=new int[MAX_QUEEN_LENGTH+1];//记录每列皇后的位置，从1的位置开始记录


    private  void putQueens(){

        int n=1;//代表行

        while (n>0){//找到一组可行解后此时n为8，则从当前行开始寻找此行其他的可行解，然后回溯到下一行继续寻找，直到第一行回溯完为止退出循环
            queenArray[n]++;//设置皇后的占位
           while (queenArray[n]<=MAX_QUEEN_LENGTH&&!isLegal(n)){//遍历一行的所有列，如果不合法则继续找当前行的下一列位置，如果此行所有列均不合法则queenArray[n]代表的列数会自增到9
               queenArray[n]++;
           }
           //在搜索空间内
            if (queenArray[n]<=MAX_QUEEN_LENGTH) {//当前行第queenArray[n]列的皇后的位置符合条件，否则当前行所有列位置均不合法则需要回溯到上一行
                if(n==MAX_QUEEN_LENGTH){//找到一组解
                    printQueens();
                }
                else n++;//没找全继续找
            }
            else {//不在则回溯到上一行，并清空占位
                queenArray[n]=0;
                n--;
            }
        }
    }

    //校验皇后放的位置合法性
    private  boolean isLegal(int n){
        for (int i = 1; i <n; i++) {
            //当前行的元素与前一行(n-1)的元素位于相同的列或者位于斜线上
            //行号-列号之差绝对值相等说明在同一条对角线上
            if (queenArray[n]==queenArray[i]||Math.abs(queenArray[n] - queenArray[i]) ==Math.abs(n-i)) {
                return  false;
            }
        }
        return true;
    }

    private  void  printQueens(){
        tailNumber++;
        System.out.println("第"+tailNumber+"种解-----------------------------");
        for (int i = 1; i <=MAX_QUEEN_LENGTH; i++) {
            for (int j = 1; j <= MAX_QUEEN_LENGTH; j++) {
                if (j == queenArray[i]) {
                    System.out.print(" * ");
                } else System.out.print(" 0 ");
            }
            System.out.println("");
        }



    }
    public static void main(String[] args) {
        long start= System.currentTimeMillis();
        new  EightQueen3().putQueens();
        long end= System.currentTimeMillis();
        System.out.println("一共耗时"+(end-start));
    }
}
