package Algorithm.Sort;

import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-03-31 17:35
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 堆排序
 *
 * 堆排序是利用堆这种数据结构而设计的一种排序算法，堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，它也是不稳定排序。
 *
 * 堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆, 注意 : 没有要求结点的左孩子的值和右孩子的值的大小关系。
 *
 * 每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆
 *
 * 根据顺序存储二叉树：
 *大顶堆特点：arr[i] >= arr[2*i+1] && arr[i] >= arr[2*i+2]  // i 对应第几个节点，i从0开始编号
 *小顶堆特点：arr[i] <= arr[2*i+1] && arr[i] <= arr[2*i+2]  // i 对应第几个节点，i从0开始编号
 *
 * 一般升序采用大顶堆，降序采用小顶堆
 *
 *
 * 堆排序的基本思想是：
 *
 * 将待排序序列构造成一个大顶堆--将树使用数组形式展现
 *
 * 此时，整个序列的最大值就是堆顶的根节点。
 *
 * 将其与末尾元素进行交换，此时末尾就为最大值。
 *
 * 然后将剩余n-1个元素重新构造成一个大顶堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列了。
 *
 * 可以看到在构建大顶堆的过程中，元素的个数逐渐减少，最后就得到一个有序序列了
 *
 *
 * 小结：
 *   1 先将无序序列构建一个堆，升序为大顶堆，降序为小顶堆
 *
 *   2 将堆顶元素与末尾元素进行交换，将最大元素“沉”到数组末端
 *
 *   3 重新调整结构，使其满足堆的定义，然后继续交换堆顶与当前末尾元素。反复执行调整交换步骤，直到整个序列为有序为止
 */
public class HeatSort {
    public static void main(String[] args) {
          int[] arr = {10,8,66,2,90,32,-1,- 8,-33};
         System.out.println(Arrays.toString(heatSort(arr)));
    }


    /*
     * @param: [arr]
     * @return: int[]
     * @author: Jerssy
     * @dateTime: 2021/4/1 9:44
     * @description: 堆排序
     */
    public  static  int[] heatSort(int[] arr){

        int len= arr.length;

           //createMinHeat(arr,len);

         //先将数组调整为大顶堆
          createMaxHeat (arr,len);

         //for(int j=len-1;j>=1;j--){
         for(int j=1;j<j+len;j++){
              swap(arr,0, len-1);//将大顶堆堆顶数据放到数组末尾，处理剩下的len-1个数据

              adjustMaxHeat(arr,0,--len);
             //adjustMinHeat(arr,0,--len);
        }
        return arr;
    }

    /*
     * @param: [arr, len]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/1 9:44
     * @description: 创建大顶堆
     */
    private static void   createMaxHeat(int[] arr, int len){
        for (int i = len/2-1; i>=0; i--) {//调整非叶子节点(下-->上-->左-->右)使其满足大顶堆的特点
            adjustMaxHeat(arr,i, len);
        }
    }

    /*
     * @param: [arr, len]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/1 11:00
     * @description: 创建小顶堆
     */
    private static void   createMinHeat(int[] arr, int len){
        for (int i = len/2-1; i>=0; i--) {//调整非叶子节点(下-->上-->左-->右)使其满足小顶堆的特点
            adjustMinHeat(arr,i, len);
        }
    }

    /*
     * @param: [arr, i, len]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/1 9:36
     * @description:调整树为大顶堆。 i--->非叶子节点在数组中的索引，len-->需要调整的元素个数
     */

    private static void  adjustMaxHeat(int[] arr, int i, int len){

        int left=2*i+1,maxNum;

        //非递归方式
        for (int j =left; j <len; j=j*2+1) {
            if (j+1<len&&arr[j]<arr[j+1]){//先比较左节点和右节点谁大，因为我们需要拿两者最大值和父节点比较确定是否需要与父节点交换
               j++;
            }
            maxNum=j;
            if (arr[maxNum]>arr[i]){
                swap(arr, i, maxNum);
                i=maxNum;//更新i为交换后的maxNum索引，因为后序可能会破坏结构还需要处理maxNum的子节点
            }
            else  break;
        }


          //递归方式
//        if (left+1<len&&arr[left]<arr[left+1])  left++;
//          maxNum = left;
//
//        if (maxNum<len&&arr[maxNum]>arr[i]){
//            swap(arr,i,maxNum);
//            int preLeft=2*maxNum+1,preRight=preLeft+1;
//            if (preRight<len){
//                if (arr[maxNum]<arr[preLeft]||arr[maxNum]<arr[preRight]) // 破坏了大顶堆的结构才调整，避免无用的递归
//                    adjustMaxHeat(arr, maxNum, len);//交换导致前面已经调整好了的结构混乱需要继续调整使其满足大顶堆
//            }
//        }
    }

    /*
     * @param: [arr, i, len]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/1 11:08
     * @description:   调整树为小顶堆
     */
    private  static  void adjustMinHeat(int[] arr, int i, int len){

        int left=2*i+1,minMum;

        if (left+1 < len&&arr[left]>arr[left+1]) left++;

        minMum=left;

        if (minMum<len&&arr[i]>arr[minMum]){
            swap(arr,i,minMum);
            int preLeft=2*minMum+1,preRight=preLeft+1;
            if (preRight<len){
                if (arr[minMum]>arr[preLeft]||arr[minMum]>arr[preRight]) // 破坏了小顶堆的结构，
                    adjustMinHeat(arr, minMum, len);//交换导致前面已经调整好了的结构混乱需要继续调整使其满足小顶堆
            }
        }
    }

    /*
     * @param: [arr, a, b]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/4/1 9:44
     * @description: 交换a和b
     */
    private static void  swap(int[] arr,int a, int b){

        if (a!=b){
            arr[a]=arr[a]^arr[b];
            arr[b]=arr[a]^arr[b];
            arr[a]=arr[a]^arr[b];
        }
    }
}
