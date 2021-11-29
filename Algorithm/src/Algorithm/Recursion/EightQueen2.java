package Algorithm.Recursion;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 八皇后解法2:生成遍历法：先产生所有的可能，然后根据限制条件过滤结果
 * @create 2021-02-27 17:26
 *
 * 此方法相比递归和回溯效率要差，因为回溯是只要当前分支无法继续搜素下去时候就会停止搜索，返回上一行寻找其他可行分支。
 */

public class EightQueen2 {


    int[] queueArray=new int[8]; int tailNumber = 0, maxCount = 8,executeNum=0;

    private void search(int n) {

       executeNum++;//执行次数
       //检查每个可能，看是否会冲突
       if(n == maxCount) {
           for(int i = 0; i < maxCount; i++) {//当前行位置的皇后
               for (int j = i + 1; j < maxCount; j++) {//下一行位置的皇后
                   //当前行的元素与下一行的元素位于相同的列或者位于斜线上则冲突
                   if (queueArray[i] == queueArray[j] || Math.abs(queueArray[i] - queueArray[j]) == Math.abs(i - j)) {
                       return;
                   }
               }
           }
           //找到一个没冲突的可能
           tailNumber++;
       }
       //遍历生成所有的可能
       else for(int i = 0; i < maxCount; i++) {
           queueArray[n] = i;
           search(n+1);
       }
   }



    public static void main(String[] args) {
        long start= System.currentTimeMillis();

        EightQueen2 eightQueue2 = new EightQueen2();
        eightQueue2.search(0);
        System.out.println("可行解:"+eightQueue2.tailNumber);
        System.out.println("一共执行次数："+eightQueue2.executeNum);
        long end= System.currentTimeMillis();
        System.out.println("一共耗时"+(end-start));
    }
}
