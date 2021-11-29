package Algorithm.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jerssy
 * @create: 2021-03-22 17:25
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 二分查找 ---要求查找的数组有序
 *
 * 1 确定数组中间的下标middle=(left+right)>>1
 *
 * 2 searchValue>arr[middle] searchValue在middle位置的右边则需要从middle+1向右开始查找
 *
 * 3 searchValue< arr[middle] searchValue在middle位置的左边则需要从middle-1向左开始查找
 *
 * 4 searchValue== arr[middle] searchValue已经找到
 */
public class HalfSearch {

    public static void main(String[] args) {

        int[] arr=new int[]{1, 2, 4, 6, 7, 9, 13,16, 17, 21,21, 23, 25, 27,31, 45, 56, 58, 61};
        System.out.println(halfSearch(arr, 21 ));
        System.out.println(halfSearch1(arr, 0, arr.length-1, 21));
    }

    //折半查找非递归方式
    public static ArrayList<Integer> halfSearch(int[] arr, int searchValue){

        int left=0,right =arr.length-1;
        ArrayList<Integer> list = new ArrayList<>();
        while (left<=right){
            int middle=(left+right)>>1;
            if (arr[middle] == searchValue) {
                list.add(middle);
            }
            if (arr[middle]>searchValue){
                right=middle-1;
            }
            else   {

                left=middle+1;
            }
        }

       return list;
    }


    //折半查找递归方式
    public  static List<Integer> halfSearch1(int[] arr, int leftIndex, int rightIndex, int searchValue) {

        int middle=(leftIndex + rightIndex)>>1;

        if (leftIndex>rightIndex){//rightIndex<0||leftIndex>arr.length-1
            return new ArrayList<>();
        }

        if (arr[middle]>searchValue) return halfSearch1(arr, leftIndex, middle-1, searchValue);

        else if (arr[middle]<searchValue) return halfSearch1(arr, middle+1, rightIndex, searchValue);

        else {
              //searchValue==arr[middle],找到了但不一定找完全，因为递归满足条件就会返回,不会继续递归寻找
             ArrayList<Integer> list = new ArrayList<>();

             int lIndex = middle - 1;
             while (lIndex > 0) {//向左扫描将剩余满足条件的元素索引加入集合
                 if (searchValue == arr[lIndex]) {
                     list.add(lIndex);
                 }
                 lIndex--;
             }

             list.add(middle);

             int rIndex = middle + 1;
             while (rIndex < arr.length) {//向右扫描将剩余满足条件的元素索引加入集合
                 if (searchValue == arr[rIndex]) {
                     list.add(rIndex);
                 }
                 rIndex++;
             }

            return list;
        }
    }

    //折半查找递归方式
    public  static  int  halfSearch2(int[] arr, int leftIndex, int rightIndex, int searchValue) {

        int middle=(leftIndex + rightIndex)>>1;

        if (leftIndex>rightIndex){//rightIndex<0||leftIndex>arr.length-1
            return -1;
        }

        if (arr[middle]>searchValue) return halfSearch2(arr, 0, middle-1, searchValue);

        else if (arr[middle]<searchValue) return halfSearch2(arr, middle+1, rightIndex, searchValue);

        else {

            return middle;
        }
    }

}
