package Algorithm.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jerssy
 * @create: 2021-03-23 9:34
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 插值查找算法
 *
 *
 * 插值查找原理
 *
 * 插值查找算法类似于二分查找，不同的是插值查找每次从自适应mid处开始查找。
 *
 * 将折半查找中的求mid 索引的公式 , low 表示左边索引left, high表示右边索引right.
 * key 就是前面我们讲的  searchValue
 *
 * middle=(height+low)/2---->low+(height-low)/2--->low+((key-arr[low])/(arr[height]-arr[low]))*(height-low)
 *
 * --->low+(key-arr[low])*(height-low)/(arr[height]-arr[low])
 *
 * 对于数据量较大，关键字分布比较均匀的查找表来说，采用插值查找, 速度较快.
 *
 * 关键字分布不均匀的情况下(跳跃性比较大)，该方法不一定比折半查找要好
 *
 * 要求：数组需要有序
 *
 */
public class InsertSearch {

    public static void main(String[] args) {
        int[] array = new int[] {  8, 9, 12, 13, 22, 24, 33, 38, 41, 42, 44, 44, 44, 45, 48, 49, 53, 60, 74, 74, 86 };

        System.out.println(insertSearch(array, 44));
        System.out.println(insertSearch1(array, 0, array.length-1,44));
    }

     public  static  List<Integer> insertSearch(int[] arr, int searchValue){

         int left=0,right = arr.length-1;
         List<Integer> list = new ArrayList<>();
         while (left <=right) {

            int middle=left+(searchValue-arr[left])*(right-left)/(arr[right]-arr[left]);
            if (arr[middle] == searchValue) {
                list.add(middle);
                left=middle+1;
            }

            else  if (arr[middle]>searchValue){
                right=middle-1;
            }
            else   {
                left=middle+1;
            }

         }
            return list;
     }

     public  static  List<Integer> insertSearch1(int[] arr,int left,int right, int searchValue){


         //因为middle自适应需要防止越界及递归终止条件
         if(searchValue<arr[0]||searchValue>arr[arr.length-1]||left>right){
             return new ArrayList<>();
         }

         //自适应
         int middle=left+(searchValue-arr[left])*(right-left)/(arr[right] - arr[left]);

         if (arr[middle]>searchValue) {
             return insertSearch1(arr, left, middle-1, searchValue);
         }

         else  if (arr[middle]<searchValue) {
             return insertSearch1(arr, middle+1, right, searchValue);
         }

         else {

               List<Integer> list = new ArrayList<>();

               int leftIndex=middle-1;
               int rightIndex=middle+1;
               while (leftIndex >=0 ) {
                   if (arr[leftIndex] == searchValue) {
                       list.add(leftIndex);
                   }
                   leftIndex--;
               }
              list.add(middle);


               while (rightIndex <=right ) {
                 if (arr[rightIndex] == searchValue) {
                     list.add(rightIndex);
                 }
                 rightIndex++;
             }
             return list;
         }

     }
}
