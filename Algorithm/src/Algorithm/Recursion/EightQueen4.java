package Algorithm.Recursion;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 递归回溯二维数组
 * @create 2021-02-27 19:55
 */
public class EightQueen4 {

    private  static  final  int MAX_QUEEN_LENGTH=8;

    private  int totalCount=0;

    private final int [][] queenArray=new int[MAX_QUEEN_LENGTH][MAX_QUEEN_LENGTH];


    private  void putQueens(int i){
        if (i==MAX_QUEEN_LENGTH) {//递归边界. 只要走到这里,所有皇后必然不冲突,找到一次可行解
            printQueens();
            return;
        }

        //遍历每行的每一列
        for (int j = 0; j < MAX_QUEEN_LENGTH; j++) {
              if(checkLegal(i,j)){
                  queenArray[i][j]=1;//将此皇后放在第i行j列设置占位
                  putQueens(i+1);//探测下一行所有的列
                  //queenArray[i][j]=0;//每次递归完成即下一行皇后摆放出现不合法的则清空当前位置，并回溯到第i行j+1的位置即下一列①
              }
             queenArray[i][j]=0;//②此位置与①效果相同，因为校验位置失败则退出i+1的递归，继续执行下面代码

        }
    }

    private  boolean checkLegal(int n, int j){

        for (int i = 0; i < MAX_QUEEN_LENGTH; i++) {//检查行列
             if(queenArray[i][j]==1){
                 return  false;
             }
        }
        //因为这里是在放好了一行的皇后后，接着在下一行的某列寻找合适位置，所以只需要检查前一行某列位置的皇后与当前位置的皇后是否存在冲突
        for (int k = n-1,m=j-1; k>=0&&m>=0; k--,m--) {//检查当前皇后的位置与当前位置的前一行所有列的左对角线
            if (queenArray[k][m]==1 ) {
                return false;
            }
        }
        for (int k = n-1,m=j+1; k>=0&&m<MAX_QUEEN_LENGTH; k--,m++) {//检查当前皇后的位置与当前位置的前一行所有列的右对角线
            if ( queenArray[k][m]==1 ) {
                return false;
            }
        }

        return true;
    }

    private void  printQueens(){
        totalCount++;
        System.out.println("第"+totalCount+"种解法---------------------------");
        for (int i = 0; i <MAX_QUEEN_LENGTH ; i++) {
            for (int j = 0; j <MAX_QUEEN_LENGTH; j++) {
                 if (queenArray[i][j]==1){
                     System.out.print(" * ");
                 }else System.out.print(" 0 ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        long start= System.currentTimeMillis();
        EightQueen4 eightQueen4 = new EightQueen4();
        eightQueen4.putQueens(0);
        long end= System.currentTimeMillis();
        System.out.println("一共耗时"+(end-start));
    }
}
