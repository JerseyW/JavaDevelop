package Algorithm.Sort;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-03-19 9:37
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 基数排序
 *
 *
 * 基数排序（radix sort）属于“分配式排序”（distribution sort），又称“桶子法”（bucket sort）或bin sort，顾名思义，它是透过键值的部份资讯，将要排序的元素分配至某些“桶”中，藉以达到排序的作用，基数排序法是属于稳定性的排序，其时间复杂度为O
 * (nlog®m)，其中r为所采取的基数，而m为堆数，在某些时候，基数排序法的效率高于其它的稳定性排序法
 *
 *基数排序(RadixSort)是在桶排序的基础上发展而来的，两种排序都是分配排序的高级实现。分配排序(DistributiveSort)的基本思想：排序过程无须比较关键字，而是通过“分配”和“收集”过程来实现排序。它们的时间复杂度可达到线性阶：O(n)。
 *
 * 第一趟桶排序将数字的个位数分配到桶子里面去，然后回收起来，此时数组元素的所有个位数都已经排好顺序了
 * 第二趟桶排序将数字的十位数分别分配到桶子里面去，然后回收起来，此时数组元素的所有个位数和十位数都已经排好顺序了(如果没有十位数、则补0)
 * 第三趟桶排序将数字的百位数分别分配到桶子里面去，然后回收起来，此时数组元素的所有个位数和十位数和百位数都已经排好顺序了(如果没有百位数、则补0)
 * ..................................
 * 直至全部数(个、十、百、千位...)排好顺序，那么这个数组就是有序的了。
 *
 *
 *为什么要从低位到高位排：首先是为了保证相同高位的数字可以能够按照低位排序，而且如果高位优先，那么高位排好的序在低位又会被打乱
 *
 * 典型的空间换时间的排序算法
 *
 * 最高位优先(Most Significant Digit first)法，简称MSD法：先按k1排序分组，同一组中记录，关键码k1相等，再对各组按k2排序分成子组，之后，对后面的关键码继续这样的排序分组，直到按最次位关键码kd对各子组排序后。再将各组连接起来，便得到一个有序序列。
 * 最低位优先(Least Significant Digit first)法，简称LSD法：先从kd开始排序，再对kd-1进行排序，依次重复，直到对k1排序后便得到一个有序序列。
 * 基数排序的方式可以采用LSD（Least significant digital）或MSD（Most significant digital），LSD的排序方式由键值的最右边开始，而MSD则相反，由键值的最左边开始。
 *
 *
 *
 */
public class RadixSort  {

    public static void main(String[] args){
        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }

        long start1=System.currentTimeMillis();
        System.out.println(Arrays.toString(radixSort(arr .clone())));

        long end1= System.currentTimeMillis();
        System.out.println("基数排序消耗时间："+(end1-start1));

        long start2=System.currentTimeMillis();

        int[] arr1=new int[]{23,466,890,43,0};

        System.out.println(Arrays.toString(radixSort1(arr .clone())));


        long end2= System.currentTimeMillis();
        System.out.println("基数排序消耗时间："+(end2-start2));

        String[] arr2 = new String[] {"Kobe","James","Wide","JRSmith","Nowitzki","Nash","Duncan"};
        System.out.println(Arrays.toString(radixSort(arr2 .clone())));
    }

    //使用list类型数组存储桶位，每个list的长度，避免数组中出现多余的无效数字
    public  static  int[] radixSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        int max  =Arrays.stream(arr).max().getAsInt();
        int min  =Arrays.stream(arr).min().getAsInt();

        int maxNumLen = String.valueOf(max).length();
        int minNumLen = String.valueOf(Math.abs(min)).length();
        maxNumLen= Math.max(maxNumLen, minNumLen);

        ArrayList<Integer>[] bucket =new ArrayList [10];//使用list类型数组存储桶位，每个list的长度即为桶位的有效数字个数

        for (int i = maxNumLen; i > 0; i--) {

            for (int value : arr) {
                if (min<0) value-=min;

                String quantileStr = String.valueOf(value);

                String newQuantileString = "0".repeat(maxNumLen - (quantileStr.length())).concat(quantileStr);

                int bucketIndex = Integer.parseInt(String.valueOf(newQuantileString.charAt(i-1)));

                if (Objects.isNull(bucket[bucketIndex])){
                    // if (Objects.isNull(bucket[9-bucketIndex])) {//大到小
                    bucket[bucketIndex] = new ArrayList<>();
                    //bucket[9-bucketIndex] = new ArrayList<>();//大到小

                }
                bucket[bucketIndex].add(Integer.parseInt(quantileStr));
                //bucket[9-bucketIndex].add(Integer.parseInt(quantileStr));

            }

            int n=0;
            //对每个桶子里的元素进行回收
            for (ArrayList<Integer> integers : bucket) {
                if (!Objects.isNull(integers)) {
                    for (Integer value : integers) {
                        arr[n] = value;
                        if (min<0) arr[n]+=min;
                        n++;
                    }
                }
            }

            Arrays.fill(bucket, null);
        }

        return arr;
    }




    public static int[] radixSort1(int[] arr) {

        if (arr==null||arr.length==0){
            return null ;
        }
        int max  =Arrays.stream(arr).max().getAsInt();
        int min  =Arrays.stream(arr).min().getAsInt();

        int maxNumLen = String.valueOf(max).length();
        int minNumLen = String.valueOf(Math.abs(min)).length();//考虑负数情况,因为负数也有可能位数长度最大
        maxNumLen= Math.max(maxNumLen, minNumLen);//最大值的位数长度


        int[] effectArr = new int[10];//记录每个桶中存放的有效数字个数，即存放了多少个数字
        int[][] buckets = new int[10][arr.length];//桶位数组，每个桶位最坏的是存放length个数
        int n=1;

        for (int i = 0; i <maxNumLen ; i++) {

            for (int value : arr) {

                if (min<0) value-=min;//如果存在负数则先将其变成正数

                int quantileNum = value / n % 10;//获取位数

                //向特定的桶位放位数，effectArr的索引与buckets索引一一对应，为后续从桶位获取数据做准备

                buckets[quantileNum][effectArr[quantileNum]++] = value;
                //buckets[9-quantileNum][effectArr[9-quantileNum]++] = value; //从大到小排序
            }

            int arrNum=0;
            for (int j = 0; j <buckets.length ; j++) {

                for (int k = 0; k <effectArr[j]; k++) {
                    arr[arrNum]=buckets[j][k];//按桶位里的顺序赋值给数组
                    if (min<0) arr[arrNum]+=min;//如果存在负数则再将其变成负数
                    arrNum++;
                }
                effectArr[j]=0;//清空桶的记录数组,否则会影响下次排序桶的计数
            }
            n*=10;
        }
        return arr;
    }


    public static String[] radixSort(String[] arr){
        if (arr==null||arr.length==0){
            return null ;
        }

       //元素最长位数
        int maxLength  =Arrays.stream(arr).max(Comparator.comparing(String::length)).get().length() ;

        //排序结果数组
        int[] effectsNumArr = new int[128];
        String[][] buckets=new String[128][arr.length];

        //从个位开始比较，一直比较到最高位
        for(int k = maxLength;k >0;k--) {

            for (String s : arr) {
                int index = 0;
                //w位数不足的位置补0
                if (s.length() >= k ) {
                    index = s.charAt(k-1);
                }

                buckets[index][effectsNumArr[index]++]=s; ;
            }


            int arrNUmber=0;
            //遍历原始数列，进行排序
            for (int i = 0; i <buckets.length ; i++) {
                for (int j = 0; j <effectsNumArr[i] ; j++) {
                      arr[arrNUmber++]=buckets[i][j];
                }
                effectsNumArr[i]=0;
            }
        }
        return arr;
    }

    public int[] sort(int[] A, int n) {
        int max = A[0];
        for (int i = 1; i < n; i++) {
            if (max < A[i])
                max = A[i];
        }
        int maxL = String.valueOf(max).length();  //获取数组中最长元素长度

        int k = new Double(Math.pow(10, maxL - 1)).intValue();
        int[][] t = new int[10][n];  //桶
        int[] num = new int[n];      //记录每个桶中存入数的个数

        for (int a : A) {              //按最高位入桶
            int m = (a / k) % 10;
            t[m][num[m]] = a;
            num[m]++;
        }
        int c = 0;
        for (int i = 0; i < n; i++) {
            if (num[i] == 1) {        //如果桶中只有一个数则直接取出
                A[c++] = t[i][0];
            } else if (num[i] > 1) {   //如果桶中不止一个数，则另存如数组B递归
                int[] B = new int[num[i]];
                for (int j = 0; j < num[i]; j++) {
                    B[j] = t[i][j];
                    sort(B, num[i]);   //递归方法
                }
            }
        }
        return A;
    }
}
