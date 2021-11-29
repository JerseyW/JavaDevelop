package Algorithm.Sort;

import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-03-12 10:29
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 选择排序
 *
 *
 *
 *
 * 选择排序（Select Sort） 是直观的排序，通过确定一个最大或最小值，再从带排序的的数中找出最大或最小的交换到对应位置。再选择次之。双重循环时间复杂度为 O(n^2)
 *
 * 第一次从arr[0]~arr[n-1]中选取最小值，与arr[0]交换，第二次从arr[1]~arr[n-1]中选取最小值，与arr[1]交换，第三次从arr[2]~arr[n-1]中选取最小值，与arr[2]交换，…，第i次从arr[i-1]~arr[n-1]中选取最小值，与arr[i-1]交换，…, 第n-1次从arr[n-2]~arr[n-1]中选取最小值，与arr[n-2]交换，总共通过n-1次，得到一个按排序码从小到大排列的有序序列。
 *
 * 算法描述：
 *
 * 在一个长度为 length 的无序数组中，第一次遍历 length-1 个数找到最小的和第一个数交换。
 * 第二次从下一个数开始遍历 length-2 个数，找到最小的数和第二个数交换。
 * 重复以上操作直到第 length-1 次遍历最小的数和第 length-1 个数交换，排序完成。
 *
 * 小结：从头至尾扫描序列，找出最小的一个元素，和第一个元素交换，接着从剩下的元素中继续这种选择和交换方式，最终得到一个有序序列。
 *
 *优点：
 * 易于理解，而且操作简单，进行n-1轮比较，每次比较n-i+1个数。无论什么数据进去都是O(n2)的时间复杂度，数据越小越好。
 *缺点：
 * 每次进行两个for循环的遍历，时间复杂度高，O(n2)
 * 不稳定，可能会使得某些元素的相对位置发生变化。
 *
 *改进：双向选择排序，每次先从当前位置i向右扫描找到最小值和当前位置(left指针位置)交换，找到最大值和right指针位置元素交换；下一次从剩下的元素继续寻找最小值和最大值与left指针和right指针位置交换；扫描结束即排序完成
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }

        selectSort(arr.clone());
        doubleSelectSort(arr.clone());

    }

    public static void selectSort(int[] arr){
        long start=System.currentTimeMillis();
        int len= arr.length;
        for (int i = 0; i < len-1; i++) {//找最小值次数
            int minIndex=i;
            int min=arr[i];
            for (int j = i+1; j < len; j++) {
                 if (arr[j]<min) {
                     min = arr[j];
                     minIndex=j;
                 }
            }

             if (minIndex!=i) {//当前位置已经是最小值不需要交换
                 arr[minIndex]=arr[i];
                 arr[i] = min;
             }
        }

        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("选择排序消耗时间："+(end-start));
    }

    //双向选择排序--每次找最小值与最大值，最小值放最左边，最大值放最右边
    public static void doubleSelectSort(int[] arr){
        long start=System.currentTimeMillis();
        for (int left = 0, right = arr.length - 1 ; left<right;left++,right--) {
            int minIndex=left,maxIndex = right;

            for (int i = left; i <=right; i++) {//这里注意和单向选择的区别，不能从left+1开始或者right-1结束，否则可能会忽略了最大值在第left的位置或者第right的位置
                if (arr[i]<arr[minIndex]) {

                    minIndex=i;
                }
                if (arr[i]>arr[maxIndex]) {

                    maxIndex=i;
                }
            }
            if (minIndex!=left) {//当前位置已经是最小值不需要交换
                arr[minIndex]=arr[minIndex]^arr[left];
                arr[left]=arr[minIndex]^arr[left];
                arr[minIndex]=arr[minIndex]^arr[left];

            }

             // 最大值（arr[max]）在最小位置（left）的情况,如果最大值先交换则需要考虑最小值在最大值位置的情况
            if (left == maxIndex) {
                maxIndex = minIndex;
            }

            if (maxIndex!=right) {//当前位置已经是最大值不需要交换
                arr[maxIndex]=arr[maxIndex]^arr[right];
                arr[right]=arr[maxIndex]^arr[right];
                arr[maxIndex]=arr[maxIndex]^arr[right];
            }
        }

        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("双向选择排序消耗时间："+(end-start));
    }

}
