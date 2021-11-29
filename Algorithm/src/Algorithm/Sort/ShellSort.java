package Algorithm.Sort;

import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-03-13 17:52
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 希尔排序
 *
 *
 * 希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序。
 * 希尔排序法基本思想

 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
 *
 *优点：
 * 空间复杂度较好，O(1)；作为改进版的插入排序，是一种相对高效的基于交换元素的排序方法。
 * 缺点：
 * (i) 不稳定，在交换的过程中，会改变元素的相对次序。
 * (ii) 希尔排序的时间复杂度依赖于增量序列函数，所以分析起来比较困难，当n在某个特定范围的时候，希尔排序的时间复杂都约为O(n1.3)
 *
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }

         shellSwapSort(arr.clone());

        shellInsertSort(new int[]{1,2,90,3,87,3,1});


    }

    //希尔交换排序
    public static void shellSwapSort(int[] arr){
        long start=System.currentTimeMillis();
        int len= arr.length;

        for (int grep = len/2; grep>0; grep/=2) {//分组次数
            for (int i = grep;i<len;i++) {//分组位置的每一个元素都需要与间隔k位置进行比较，比如10个数若分成5组则每组为2个元素则arr[5]比较arr[0],arr[6]比较arr[1]

                if ( arr[i]<arr[i-grep]){//如果当前的元素 小于分组里已经排好的序的元素(分组的最后一个)，例如：arr[5]<arr[0];arr[6]<arr[1]
                    int temp=arr[i];//当前需要处理的元素
                    //参考插入排序，当前元素与分组内所有元素进行比较
                    for (int j = i - grep; j >= 0&&temp<arr[j]; j -= grep) {//对每组内元素进行判断与交换，在分组内每遇到比当前元素大的都进行交换

                        arr[j] = arr[j] ^ arr[j + grep];
                        arr[j + grep] = arr[j] ^ arr[j + grep];
                        arr[j] = arr[j] ^ arr[j + grep];
                    }
                }
            }
        }
        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("希尔交换排序消耗时间："+(end-start));

    }

    //希尔插入排序
    public  static  void  shellInsertSort(int[] arr){
        int len = arr.length;
        long start=System.currentTimeMillis();
        for (int grep= len/2; grep >0 ; grep/=2) {
            for (int j = grep; j <len; j++) {
               if (arr[j]< arr[j-grep]) {//当前元素比分组的最后一个元素小
                   int temp = arr[j];//当前需要处理(插入)的元素
                   int k;
                   for (k=j-grep; k>=0&&temp<arr[k]; k-=grep) {//在分组中找插入位置
                         arr[k+grep] = arr[k];
                   }
                    arr[k+grep] = temp;//插入元素
               }
            }
        }
        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("希尔插入排序消耗时间："+(end-start));


    }

    public static void shellSort5(int[] arr){
        long start=System.currentTimeMillis();
        // 增量gap 并逐步的缩小增量
        for (int gap = arr.length/2 ; gap > 0 ; gap /= 2){
            // 从第gap个元素 逐步对其所在组进行
            //直接插入如排序
            for (int i = gap ; i < arr.length ; i++){
                int j = i;

                if (arr[j] < arr[j - gap]){
                    int temp = arr[j];
                    for ( ; j>=gap&&temp < arr[j - gap] ;j-=gap) {
                        arr[j] = arr[j-gap];
                    }
                    // 当退出while后 就给temp
                    //找到插入的位置
                    arr[j] = temp;
                }

            }

        }
        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("希尔插入排序消耗时间："+(end-start));

    }

}
