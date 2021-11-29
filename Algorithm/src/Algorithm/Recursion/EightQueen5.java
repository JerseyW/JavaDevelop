package Algorithm.Recursion;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 八皇后方法五
 * @create 2021-02-28 10:38
 *
 * 本质是递归回溯
 * 只是判断条件不同
 *
 *  每放置一个皇后需要移动到下一行，不需要对该行判断，只需要对列主副对角线进行判断即可
 *
 *  那么问题来了如何用一个值代表一条线呢
 *
 * 列:  column[y]
 *
 * 平面坐标系中的副对角线实际上也就是二维数组中的主对角线
 * 所以得出:
 * 任意一条副对角线公式为
 * y = x + b => b = y - x + n(数组中没有负数所以平移n位)
 * 任意一条主对角线公式为
 * y = - x + b => b = x + y
 *
 * 主对角线: rup[x+y]
 * 副对角线: lup[y-x+n]
 *
 * b 是任意一条主副对角线到（0，0）的截距
 *
 * 那我们就可以通过一个一维数组来表示二维数组中的每一条主副对角线
 *
 * 至多会有2*n条主或副对角线，所以只需要开2n的空间就可以了
 *
 */
public class EightQueen5 {
    private final int[] column; //同栏是否有皇后，1表示有
    private final int[] rup; //主对角线
    private final int[] lup; //副对角线
    private final int[] queen; //皇后位置
    private int num; //解法
    private  static   final  int maxCount=8;

    public EightQueen5() {
        column = new int[maxCount+1];
        rup = new int[2*maxCount+1];
        lup = new int[2*maxCount+1];
        for (int i = 1; i <=maxCount; i++)
            column[i] = 0;
        for (int i = 1; i <=2*maxCount; i++)
            rup[i] = lup[i] = 0;  //初始定义全部无皇后

        queen = new int[maxCount+1];
    }

    public void putQueens(int i) {
        if (i >maxCount) {
            printQueen();
        } else {
            for (int j = 1; j <=maxCount; j++) {//遍历当前行的所有列
                if ((column[j] == 0) && (rup[i+j] == 0) && (lup[i-j+maxCount] == 0)) {
                    //若无皇后
                    queen[i] = j; //设定为占用
                    column[j] = rup[i+j] = lup[i-j+maxCount] = 1;
                    putQueens(i+1);  //递归调用
                    column[j] = rup[i+j] = lup[i-j+maxCount] = 0;
                }
            }
        }
    }

    private void printQueen() {
        num++;
        System.out.println("第"+num+"种解-----------------------------");
        for (int y = 1; y <= maxCount; y++) {
            for (int x = 1; x <= maxCount; x++) {
                if(queen[y]==x) {
                    System.out.print(" * ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        long start= System.currentTimeMillis();
        EightQueen5 queen = new EightQueen5();

        queen.putQueens(1);  //循环调用
        long end= System.currentTimeMillis();
        System.out.println("一共耗时"+(end-start));
    }
}
