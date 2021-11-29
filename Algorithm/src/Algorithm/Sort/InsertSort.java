package Algorithm.Sort;

import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-03-12 17:21
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 插入排序
 *
 * 插入排序（Insertion Sorting）的基本思想是：把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有n-1个元素，排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表
 *
 *当文件的初态已经基本有序，可以用直接插入排序和冒泡排序。
 */
public class InsertSort {

    public static void main(String[] args) {

        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }

          insertSort(arr.clone());

          insertSwapSort(arr.clone());

          doubleInsertSort(arr.clone());
    }

    public  static  void  insertSort(int[] arr){

          long start=System.currentTimeMillis();
          for (int i = 1; i <arr.length ; i++) {

               if (arr[i] <arr[i-1]) {//如果当前元素比前一个小
                  int temp=arr[i];
                  int j;int k=i ;

                  for (j=i-1; j >=0 &&temp<arr[j]; j--) {//找当前位置temp的最终插入位置
                       arr[j+1] = arr[j];//下一个元素值赋予当前元素
                         k--;
                  }
//                   if(k!=i){
//                       System.arraycopy(arr,k,arr,k+1,i-k);
//                       arr[k] = temp;
//                   }
                     arr[j+1] = temp; //需要插入的值，因为退出for多减了1
             }

          }

        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("基本插入排序消耗时间："+(end-start));
    }

    //插入交换排序
    public  static  void  insertSwapSort(int[] arr){
        long start=System.currentTimeMillis();
        for (int i = 1; i <arr.length ; i++) {
            for (int l = i; l>0&&arr[l]<arr[l-1]; l--) {//此方法lower,因为前面都是有序数据，没必要拿当前位置与前一个进行比较然后交换，所以性能比直接找位置插入差，但毕竟也是一种方法

                arr[l]=arr[l]^arr[l-1];
                arr[l-1]=arr[l]^arr[l-1];
                arr[l]=arr[l]^arr[l-1];

            }
        }

        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("插入交换排序消耗时间："+(end-start));
    }


    //折半插入排序
    public  static  void  doubleInsertSort(int[] arr){
        long start=System.currentTimeMillis();
        for (int i = 1; i < arr.length; i++) {

            int left=0,right = i-1;
            int temp=arr[i];
            while (left<=right){
                int mid=(left+right)>>>1;

                if (arr[mid]>temp) {
                    right=mid-1;
                }
                else {
                    left=mid+1;
                }
            }

            if (left!=i){//left本身就是i无需copy

                 System.arraycopy(arr,left,arr,left+1,i-left);

                 arr[left]=temp;
            }
        }

        long end= System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("折半插入排序消耗时间："+(end-start));

    }
}
