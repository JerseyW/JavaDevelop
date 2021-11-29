package Algorithm.Sort;

import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-03-17 11:01
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 归并排序
 *
 * 归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。
 *
 *归并排序是一种高效的排序算法，在任何情况下时间复杂度都n log n 但是，它需要用额外的内存空间来暂时储存归并过程中的元素，因此我们可以认为归并排序是以牺牲一部分内存空间为代价来获得时间的高效性。
 *
 * 对排序不会出现快排那样最坏情况，且堆排序所需的辅助空间比快排要少，但是这两种算法都不是稳定的，要求排序时是稳定的，可以考虑用归并排序。
 *归并排序可以用于内部排序，也可以使用于排不排序。在外部排序时，通常采用多路归并，并且通过解决长顺串的合并，缠上长的初始串，提高主机与外设并行能力等，以减少访问外存额外次数，提高外排的效率。
 */
public class MergeSort  {

    public static void main(String[] args) {
        var arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }

        var start=System.currentTimeMillis();
        System.out.println(Arrays.toString(mergeSortByLength(arr.clone() )));

        var end= System.currentTimeMillis();
        System.out.println("递归归并排序以数组长度切分消耗时间："+(end-start));
        var start1=System.currentTimeMillis();

        var  arr1=new int[]{8,4,5,7,1,3,6,2};
        System.out.println(Arrays.toString(mergeSortByIndex(arr.clone() )));
        var end1= System.currentTimeMillis();

        System.out.println("递归归并排序以数组索引切分消耗时间："+(end1-start1));
        var start2=System.currentTimeMillis();

        System.out.println(Arrays.toString( mergeSortByGroup(arr.clone())));

        var end2= System.currentTimeMillis();
        System.out.println("非递归归并排序消耗时间："+(end2-start2));

    }

    //以数组长度切分归并排序
    public  static  int[]  mergeSortByLength(int[] arr ){

        if (arr.length<=2){//如果当前arr长度是2直接比较无需归并
            if (arr.length==2&&arr[0]>arr[1]){
                arr[0] = arr[0]^arr[1];
                arr[1] = arr[0]^arr[1];
                arr[0] = arr[0]^arr[1];

            }
            return arr;
        }

        var middle=arr.length>>1;

        if (middle<=10){// 优化：如果不超过10启用插入排序
             return  insertSorts(arr,0,arr.length-1);
        }


        var leftArray= Arrays.copyOfRange(arr, 0, middle);
        var rightArray= Arrays.copyOfRange(arr, middle, arr.length);


        return merge(arr,mergeSortByLength(leftArray),mergeSortByLength(rightArray));

    }

   
          
    //将两个有序数组合并，合并后数组仍然有序
    private static int[]  merge(int[] arr, int[] left, int[] right ){

        var i = 0;
        var tem=0;
        var j=0;


        while (i< left.length &&j< right.length ) {

            arr[tem++] = left[i] <= right[j] ?left[i++] : right[j++];
        }


        if(i== left.length){//说明left已经放完，right存在没有放完的元素
            System.arraycopy(right,j,arr,tem,right.length-j);

        }

        if(j== right.length){//说明right已经放完，left存在没有放完的元素

            System.arraycopy(left,i,arr,tem,left.length-i);

        }

        return arr;
    }

    //基本插入排序
    private static int[]  insertSorts(int[] arr, int m, int n){

        for (var i = m+1; i <=n ; i++) {
            if (arr[i] <arr[i-1]) {
                var temp=arr[i];
                var j=0;

                for (j=i-1; j >=0 &&temp<arr[j]; j--) {
                    arr[j+1] = arr[j];
                 }
                arr[j+1]=temp;
            }
        }

        return arr;
    }

    //以数组索引切分归并排序
    public  static int[] mergeSortByIndex(int[] arr){
        var temp = new int[arr.length];
        return mergeSort(arr,temp,0, arr.length-1);
    }


    private static int[] mergeSort(int[] arr,int[]temp, int left,int right){

        if(left<right){
            var mid = (left+right)/2;

            if (mid<=10){// 优化
                return insertSorts(arr,left,right);
            }

            mergeSort(arr,temp,left,mid);//左边归并排序，使得左子序列有序

            mergeSort(arr,temp,mid+1,right);//右边归并排序，使得右子序列有序

            if (arr[mid] > arr[mid+1]) {//优化：如果middle后面有序则无需再合并
                return  mergeByIndex(arr,left,mid,right,temp);//将两个有序子数组合并操作
            }
        }

        return  arr;
    }

    private static int[] mergeByIndex(int[] arr,int left,int mid,int right,int[] temp){
        var i = left;
        var j = mid+1;
        var tem= 0;

        //left和right两边的有序序列，拷贝到temp[],直到一方处理完毕为止
        while (i<=mid && j<=right){

            temp[tem++] = arr[i] <= arr[j] ?arr[i++] : arr[j++];
        }

        if(i<=mid){//将左边left剩余元素填充进temp中

            System.arraycopy(arr,i,temp,tem,mid-i+1);

        }

        if(j<=right){//将右序列right剩余元素填充进temp中

            System.arraycopy(arr,j,temp,tem,right-j+1);

        }

        //将temp中的元素全部拷贝到原数组中
        System.arraycopy(temp,0,arr,left,right-left+1);


        return arr;
    }


    //非递归归并排序-正向切分，自顶向下，直到无法grep>=len无法切分位置
    public  static  int[]   mergeSortByGroup(int[] arr){

        var len= arr.length;
        //将数组中的相邻元素两两配对。将他们排序，构成n/2组长度为2的排序好的子数组段，然后再将他们合并成长度为4的子数组段，如此继续下去，直至整个数组排好序
        //步长的改变，步长grep=1, [left=0*grep,left+2*grep-1];[left=2*grep,left+2*grep-1];[left=4*grep,left+2*grep-1].....
        //分成len组，每组1个数
         //步长为grep=2  [left=0*grep,left+2*grep-1];[left=2*grep,left+2*grep-1];[left=4*grep,left+2*grep-1]
         //分成len/2组 每组有2个数
         //步长为grep=4 [left=0*grep,left+2*grep-1];[left=2*grep,left+2*grep-1];[left=4*grep,left+2*grep-1]
         //分成len/4组 每组有4个数
        //每次步长均为原来的2倍即 1  2  4  8 16....直到为length为止

        var temp = new int[arr.length];

        for (var grep = 1; grep < len; grep*=2) {

            for (var left = 0; left<len; left+=grep*2 ) {

                var middle = Math.min(left + grep - 1, arr.length-1);

                var right = Math.min(left +2* grep  -1, arr.length-1);
                // 优化--如果中间值小于中间值的下一个，说明当前序列是从小到大有序的无需再归并
                if (arr[middle] > arr[Math.min(middle+1,arr.length-1)]) mergeByIndex  (arr,left, middle,right, temp );
            }
        }

        return arr;
    }


}
