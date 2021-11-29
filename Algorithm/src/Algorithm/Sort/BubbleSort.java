package Algorithm.Sort;

import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-03-11 16:57
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 冒泡排序
 *
 * 冒泡排序：
 * 冒泡排序（Bubble Sorting）的基本思想是：通过对待排序序列从前向后（从下标较小的元素开始）,依次比较相邻元素的值，
 * 若发现逆序则交换，使值较大的元素逐渐从前移向后部，就象水底下的气泡一样逐渐向上冒
 *
 *当数据规模较小时候，可以使用简单的直接插入排序或者直接选择排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] ints = new int[8000];
        for (int i = 0; i <8000 ; i++) {
            ints[i]= (int) (Math.random() * 8000);
        }

        //System.out.println("原数组"+Arrays.toString(ints));
        bubbleSort(ints.clone());

        //优化1--某轮后已经有序的，下一轮不需要再次排序--加一个标记，如果那一趟排序没有交换元素，说明这组数据已经有序，不用再继续下去。例如：(1，2，3 ，4 ，7，6，5),此种优化只适用于连片有序而整体无序的数据，对于前面大部分是无序而后边小半部分有序的数据优化不明显
         bubbleSort1(ints.clone());

        //优化2--记录上一次最后交换的位置，作为下一次循环的结束边界,最后一次交换的位置，后边没有交换，必然是有序的，然后下一次排序从第一个比较到上次记录的位置结束即可。
         bubbleSort2(ints.clone());

         //优化3---双向冒泡排序是一次排序可以确定两个值，正向扫描找到最大值交换到最后，反向扫描找到最小值交换到最前面，每次确定了最大值或者最小值，向前或者向后移动指针，最大或者最小值无需再比较。即先从左往右比较一次，再从右往左比较一次，然后又从左往右比较一次,以此类推。
        doubleBubbleSort(ints.clone());

        //优化4-双向冒泡排序加入标志位和记录最后次交换位置---超级优化
        doubleBubbleSort1(ints.clone());
    }

    public static void bubbleSort(int[] arr){
        long start=System.currentTimeMillis();
        for (int i= 0; i < arr.length-1 ; i++) {
            for (int j = 0; j <arr.length-1-i; j++) {
                if(arr[j]>arr[j+1]){
                    arr[j] = arr[j]^arr[j+1];
                    arr[j+1]=arr[j]^arr[j+1];
                    arr[j]=arr[j]^arr[j+1];
                }
            }
        }
        long end=System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("普通冒泡排序消耗时间："+(end-start));
    }

    public static void bubbleSort1(int[] arr){
        long start=System.currentTimeMillis();
        for (int i= 0; i < arr.length-1 ; i++) {
            boolean flag = false;
            for (int j = 0; j <arr.length-1-i; j++) {
                if(arr[j]>arr[j+1]){
                    arr[j] = arr[j]^arr[j+1];
                    arr[j+1]=arr[j]^arr[j+1];
                    arr[j]=arr[j]^arr[j+1];
                    flag=true;
                }
            }
            if (!flag) {
                break;
            }
        }
        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("加入标志位冒泡排序消耗时间："+(end-start));
    }

    public static void bubbleSort2(int[] arr){
        long start=System.currentTimeMillis();
        int len = arr.length-1;
        for (int i= 0; i < arr.length-1 ; i++) {
            boolean flag = false;
            int pos = 0;
            for (int j = 0; j <len; j++) {
                if(arr[j]>arr[j+1]){
                    arr[j] = arr[j]^arr[j+1];
                    arr[j+1]=arr[j]^arr[j+1];
                    arr[j]=arr[j]^arr[j+1];
                    flag=true;
                    pos = j;//交换元素，记录最后一次交换的位置
                }
            }
            if (!flag) {
                break;
            }
            len=pos;

        }
        long end=System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("记录最后次交换位置消耗时间："+(end-start));
    }


    //双向冒泡排序
    public static void doubleBubbleSort(int[] arr) {

        long start=System.currentTimeMillis();

        int left = 0;
        int right = arr.length - 1;
        while (left < right) {

            for (int i = left; i < right; i++) { // 保证 a[right] 是最大的
               if(arr[i]>arr[i+1]){
                    arr[i] = arr[i]^arr[i+1];
                    arr[i+1]=arr[i]^arr[i+1];
                    arr[i]=arr[i]^arr[i+1];
                }
            }

            right--;
            for (int j = right; j > left; j--) { // 保证 a[left] 是最小的
                if(arr[j]<arr[j-1]) {
                    arr[j]=arr[j]^arr[j-1];
                    arr[j-1]=arr[j]^arr[j-1];
                    arr[j]=arr[j]^arr[j-1];
                }
            }
            left++;
        }
            long end =System.currentTimeMillis();
            System.out.println(Arrays.toString(arr));
            System.out.println("双向冒泡排序消耗时间：" + (end - start));
    }


    //双向冒泡排序+加入优化
    public static void doubleBubbleSort1(int[] arr) {

        long start=System.currentTimeMillis();

        int left = 0;
        int right = arr.length - 1;
        boolean isFlag=false;
        int lastLen=0;
        while (left < right) {
            for (int i = left; i < right; i++) { // 保证 a[right] 是最大的
                if(arr[i]>arr[i+1]){
                    arr[i] = arr[i]^arr[i+1];
                    arr[i+1]=arr[i]^arr[i+1];
                    arr[i]=arr[i]^arr[i+1];
                    isFlag=true;
                    lastLen=i;
                }
            }
            right=lastLen;
            if(!isFlag){
                break;
            }
            lastLen=0;
            isFlag=false;
            for (int j = right; j > left; j--) { // 保证 a[left] 是最小的
                if(arr[j]<arr[j-1]) {
                    arr[j]=arr[j]^arr[j-1];
                    arr[j-1]=arr[j]^arr[j-1];
                    arr[j]=arr[j]^arr[j-1];
                    isFlag=true;
                    lastLen=j;
                }
            }
            left=lastLen;
            if(!isFlag){
                break;
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("双向冒泡排序加入标志位和记录最后次交换位置后消耗时间：" + (end - start));
    }
}
