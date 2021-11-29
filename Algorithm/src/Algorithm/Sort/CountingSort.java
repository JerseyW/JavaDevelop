package Algorithm.Sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: Jerssy
 * @create: 2021-03-21 13:03
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 计数排序
 *
 * 计数排序（counting sort）就是一种牺牲内存空间来换取低时间复杂度的排序算法，同时它也是一种不基于比较的算法。这里的不基于比较指的是数组元素之间不存在比较大小的排序算法， 而不基于比较的排序算法可以突破这一下界。
 *
 * 对于一个输入数组中的任意一个元素x， 知道了这个数组中比x小的元素的个数，那么我们就可以直接把x放到（x+1）的位置上。这就是计数排序的基本思想。
 * 比如说有5个元素小于x，那就把x放到第六个位置上。当有元素相等时，需要略作修改，因为不能把他们都放在同一个位置上。
 *
 * 基于这个思想，计数排序的一个主要问题就是如何统计数组中元素的个数。再加上输入数组中的元素都是0-k区间的一个整数这个条件，那么就可以通过另外一个数组的地址表示输入元素的值，数组的值表示元素个数的方法来进行统计。

 *
 *
 * 它的优势在于在对一定范围内的整数排序时,它的复杂度为Ο(n+k)(其中k是整数的范围),快于任何比较排序算法.
 * 当然这是一种牺牲空间换取时间的做法,而且当O(k)>O(n*log(n))的时候其效率反而不如基于比较的排序
 * 基于比较的排序的时间复杂度在理论上的下限是O(n*log(n)), 如归并排序,堆排序
 *
 * 计数排序是一个稳定的排序算法.
 * 当输入的元素是n个0到k之间的整数时,时间复杂度是O(n+k),空间复杂度也是O(n+k),其排序速度快于任何比较排序算法.
 * 当k不是很大并且序列比较集中时,计数排序是一个很有效的排序算法.
 * 计数排序的缺点是当最大值最小值差距过大时,不适用计数排序,当元素不是整数值,不适用计数排序.
 *
 * 可以看到辅助数组的长度和桶的数量由最大值和最小值决定，假如两者之差很大，而待排序数组又很小，那么就会导致辅助数组或桶大量浪费。
 *
 *稳定性:计数排序很重要的性质是它是稳定的。即使是相同的数，在输入数组中先出现的数，在输出数组中也位于前面。通常这种稳定性只有当排序数据还附带卫星数据是才很重要。其次，它也是基数排算法的一个子过程，因为基数排序必须要求子程序是稳定的。
 *
 *
 * 计数排序的步骤如下：
 *
 * 统计数组A中每个值A[i]出现的次数，存入C[A[i]]即对应编号的桶中；
 * 从前向后，使数组C中的每个值等于其与前一项相加，C[A[i]]代表数组A中有多少个小于等于A[i]的元素；
 * 反向填充目标数组B：将数组元素A[i]放在数组B的第C[A[i]]个位置（下标为C[A[i]] – 1），每放一个元素就将C[A[i]]递减。

 *适用范围：量大，但数组元素之间范围小 ---2 万名员工的年龄(量大，范围小(0-100岁))；50万人查询高考成绩（0-750分）
 */
public class CountingSort {

    public static void main(String[] args) {

        int[] arr1=new int[] {6,1,2,7,9,6};

        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }
        long start1=System.currentTimeMillis();
        System.out.println(Arrays.toString(countingSort(arr .clone())));
        long end1= System.currentTimeMillis();
        System.out.println("计数排序消耗时间："+(end1-start1));


        long start2=System.currentTimeMillis();
        System.out.println(Arrays.toString(countingSort1(arr.clone())));
        long end2= System.currentTimeMillis();
        System.out.println("计数排序消耗时间："+(end2-start2));

        String[] arr2 = new String[] {"Kobe","James","Wide","JRSmith","Nowitzki","Nash","Duncan"};
        System.out.println(Arrays.toString(countingSortWithStr(arr2 .clone())));
    }


    //将对应的元素值作为数组的索引放入数组中并计数，然后按根据计数器的个数从数组中一次取出，即为有序排列，缺点不稳定,不能保证相同元素在排序后前后位置依然相同

    public static int[] countingSort(int[] arr){
        if (arr == null || arr.length == 0) {
            return null;
        }

        int max= Arrays.stream(arr).max().getAsInt();
        int min= Arrays.stream(arr).min().getAsInt();

        int[] buckets = new int[max-min+1];
        //找出每个数字出现的次数
        for (int j : arr) {
            buckets[j-min]++;
        }

        int arrIndex = 0;
        for(int i = 0; i < buckets.length; i++){
            while(buckets[i]> 0){
                arr[arrIndex++] = i+min;
                buckets[i]--;
            }
        }

        return arr;
    }

    public  static  int[] countingSort1(int[] arr){
        boolean isLegal=arr==null|| arr.length ==0;

        if (isLegal) {
            return arr;
        }
        int max= Arrays.stream(arr).max().getAsInt();
        int min= Arrays.stream(arr).min().getAsInt();
        int countBucket=max-min+1;//计数

        int[] countBucketArr=new int[countBucket];//计数桶
        int[] sortedArr=new int[arr.length];

        for (int j : arr) {//记录元素比min小的元素出现次数
            //countBucketArr[max-j]++;//大到小
             countBucketArr[j-min]++;//兼容负数，和节省空间

        }

        /*  arr[6,1,2,7,9,6]--> buckets[j-min(1)]++ -->       countBucketArr[1,1,0,0,0,2,1,0,1]①
         *  --> countBucketArr[i]+=countBucketArr[i-1]   ②--->countBucketArr[1,2,2,2,2,4,5,5,6]③---->countBucketArr[arr[j]-min]-1]=arr[j]--> [1, 2, 6, 6, 7, 9]
         *
         * 解析：
         *        ③中的4元素对应着①中的2元素。而①中的2则映射着arr值=6的元素(如果倒叙遍历则先取最后位置的6) 所以我们给临时数组赋值的索引即为4-1=3的位置
         * 推导：

         *   比如arr中最后的6元素 在①中位于索引为5的位置且出现2次，经过②操作后在③中表示①中2的元素(即arr中的元素6)最终会出现在元素location=4减一的位置即新数组索引3的位置
         *        而location=arr[5]-min=6-1=5-->countBucketArr[5]=4-->综合下得到countBucketArr[arr[j]-min]-1]=arr[j]=6
         *
             试想如果在最终赋值的时候正序遍历，则最开始获取的就是arr中第一个元素6放在新数组sortedArr的索引3的位置，然后计数器从4减一为3，则
             第arr中最后一个元素6则被放在sortedArr的索引2的位置，则新数组中第一6元素在第二个6元素后。就失去了算法的稳定性，所以必须倒序遍历才能保证算法的稳定性

         *  注：减一因为数组索引为0开始索引赋值的时候需要减一

         * */
       //计算数组中小于等于每个元素的个数,因为小于等于0的数的个数就是等于0的数的个数， 即从countBucketArr中的第一个元素开始，每一项和前一项相加
        //标记数组里当前元素前面有几个元素，这个值就是在原来数组的位置，如果存在重复的值则优先会放置最右边的数，保证了排序的稳定性
        for (int i = 1; i < countBucketArr.length; ++i) {
            countBucketArr[i]+=countBucketArr[i-1];
        }

        //填充数组
        //保证最后一个等于数组countBucketArr下标j的元素排在最后，然后countBucketArr[i]–，倒数第二个等于下标i的元素排在这个元素之前
        //记录每个桶位上的最后个元素出现的位置

        for(int j=arr.length-1;j>=0;j--){//保证排序的重复的元素放置在最右边
            //因为数组的下标从0开始所以小于等于arr[j]的数的个数为x就应该将该数放在数组sortedArr的第x-1个位置

            sortedArr[countBucketArr[arr[j]-min]-1] = arr[j] ;

            countBucketArr[arr[j]-min]--;

//            sortedArr[countBucketArr[max-arr[j]]-1] = arr[j];//大到小
//            countBucketArr[max-arr[j]]--;//大到小
        }



        return sortedArr.clone();

    }



    //计数排序字符串
    public static String[] countingSortWithStr(String[] arr){
        if (arr==null||arr.length==0){
            return null ;
        }

        //元素最长位数
        int maxLength  =Arrays.stream(arr).max(Comparator.comparing(String::length)).get().length();


        //排序结果数组
        String[] sortedArr = new String[arr.length];
        int[] buckets = new int[128];//最大ASCII 127 最小0 极值128
         //从个位开始比较，一直比较到最高位
        for(int k = maxLength;k >0;k--) {

            for (String s : arr) {
                int index = 0;
                //w位数不足的位置补0
                if (s.length() >= k ) {
                    index = s.charAt(k-1);
                }

                buckets[index]++;
            }
            //将各个桶中的数字个数，转化成各个桶中最后一个数字的下标索引

            for (int i = 1; i < buckets.length; i++) {

                buckets[i]+=buckets[i-1];
            }
            //遍历原始数列，进行排序

            for(int j=arr.length-1;j>=0;j--){
                int index = 0;

                if (arr[j].length() >= k) {
                    index = arr[j].charAt(k - 1);
                }
                sortedArr[buckets[index] - 1] =arr[j];
                buckets[index]--;
            }


            Arrays.fill(buckets, 0);
        }
        return sortedArr;
    }

}
