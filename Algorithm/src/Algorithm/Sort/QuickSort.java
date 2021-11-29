package Algorithm.Sort;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author: Jerssy
 * @create: 2021-03-15 16:42
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 快速排序
 *
 *
 * 快速排序存在三种实现：1：基准值+前后指针法  2:基准值+左右指针法 3:基准值+栈
 *
 * 数据规模较大时，应用速度最快的排序算法，可以考虑使用快速排序。当记录随机分布的时候，快速排序平均时间最短，但是出现最坏的情况，这个时候的时间复杂度是O(n^2)，且递归深度为n,所需的占空间为O(n)
 *
 *
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }
        int[] arr1=new int[] {6,1,2,6,7,9,3,4,5,8};
        long start=System.currentTimeMillis();
        System.out.println(Arrays.toString(quickSort  (arr1.clone(), 0, arr1.length-1)));

        long end= System.currentTimeMillis();

        System.out.println("单指针快速排序消耗时间："+(end-start));
        long start1=System.currentTimeMillis();

         System.out.println(Arrays.toString(quickSort1(arr1.clone(), 0, arr1.length-1)));
        long end1= System.currentTimeMillis();

        System.out.println("左右指针填坑法快速排序消耗时间："+(end1-start1));

        long start2=System.currentTimeMillis();
        System.out.println(Arrays.toString(quickSort2(arr.clone(), 0, arr.length-1)));
        long end2= System.currentTimeMillis();
        System.out.println("左右指针交换快速排序消耗时间："+(end2-start2));
        long start3=System.currentTimeMillis();
        System.out.println(Arrays.toString(quickSort3(arr.clone(), 0, arr.length-1)));

        long end3= System.currentTimeMillis();
        System.out.println("非递归快速排序消耗时间："+(end3-start3));

        long start4=System.currentTimeMillis();
        System.out.println(Arrays.toString(betterQuickSort(arr.clone(), 0, arr.length-1)));

        long end4= System.currentTimeMillis();
        System.out.println(" 快速排序三数取中优化消耗时间："+(end4-start4));
    }

    //基准值+单指针(或者前后指针)
    public static int[] quickSort(int[] arr, int left, int right) {

        if (left >= right) {
            return null;
        }

        int prev = left;//前指针
        int next = left+1   ;//后指针
        int key = arr[left];

        while (next<=right) {
            if (arr[next] < key) {//确保prev与next之间的元素都比key大，是之间的，即[prev+1--next]
                prev++;
                if (prev != next) {//^运算如果相等会变成0，其相等也不需要交换
                    arr[prev] = arr[prev] ^ arr[next];
                    arr[next] = arr[prev] ^ arr[next];
                    arr[prev] = arr[prev] ^ arr[next];
                }
            }

            next++;
        }
         if (arr[prev]<arr[left]) {//小于基准值位置则交换与prev指针位置的值
            arr[prev] = arr[prev] ^ arr[left];
            arr[left] = arr[prev] ^ arr[left];
            arr[prev] = arr[prev] ^ arr[left];
         }


         quickSort(arr, left, prev-1  );//对[left---prev-1]进行递归
         quickSort(arr, prev+1, right);//对[prev+1---right]进行递归

          return arr;
    }

    public static int[] quickSort5(int []arr,int low,int high){
        if(low>=high) return arr;   //如果一直拆分到只有一位就返回
        int pivot = arr[high];    //将最右边的数字作为基准值
        int i=low-1 ;    // 慢指针i的作用是标记基准值最后应该在的位置
        for(int j=low  ;j<high ;j++){  //由快指针j一直比较到基准值的前一位
            if(arr[j]<pivot){ //如果该值小于基准值,那么就将i++,将该值,放到i的前面,也就是基准值位置的左面
                i++;
                int tmp = arr[j]; arr[j]=arr[i]; arr[i]=tmp;    //交换位置,将小的数放在左面
            }
        }
        int tmp = arr[i+1]; arr[i+1]=arr[high]; arr[high]=tmp;  //  最后将基准值放到合适的位置,也就是i的前面一位

        quickSort5(arr,low,i);     //递归的排列左边的部分
        quickSort5(arr,i+2,high);  //递归的排列右边的部分
        return  arr;
    }


    //基准值+左右指针填坑法
    public static int[] quickSort1(int[] arr, int left, int right) {

        if (left>= right) {
            return null;
        }

        int prev = left;
        int last = right;
        int key = arr[left];//如果选取arr[right]则先走prev指针

        /*
        * 如果选取最左边的值为基准值，则需要用比它小的值来放在基准值原来的位置，这个值一定是从右侧开始遍历取到的，因为左边指针遇到比基准值大的停下来，右边指针遇到比基准值小的停下来，
        * 这样依此覆盖和往中间遍历，直到左右指针相遇，这时把基准值放到这个下标位置，完成一轮排序，左边的都比它小，右边的都比它大
        *
        * prev先走，next后走呢？---6 1 2 7 9
        *
        * prev最终停在大于基准值的位置，停在 7的位置并prev交换到last的位置，交换后为6 1 2 7 7，last停在倒数第二个7 的位置无法前进(prev<last)，导致无法继续扫描最小值的情况出现，
        *
        * 基准值归位后为6 1 2 6 7 ---完了9不见了跑丢了。所以先左后右行不通
        *
        *
        */

        while (prev <last  ) {

            while (prev < last && arr[last]> key) {//找到右侧小于基准值放入该坑位中，保证当前last位置及其后的都是大的
                last--;
            }
            if (prev<last){//优化：如果还小，加快prev指针移动，因为last在交换前已经和key比较过了， 因此prev交换后，下一个while应该从prev的下一个元素开始遍历
                arr[prev++] = arr[last];//基准值开始在left位置 交换，交换后此位置及以后的值都比基准值小
            }

            while (prev < last && arr[prev] <= key) {//找到左侧大于基准值放入该坑位中，保证当前prev位置及其后的都是比较小的
                prev++;
            }
            if(prev<last){
                // 优化：基准值经过right向左扫描后已经与基准值进行了交换，所以此时last指针位置就是基准值的位置，交换后此位置及之前的值都比基准值大
                arr[last--] = arr[prev];//此时会把从右到左找到的小值的坑位覆盖，确保此坑位的值是比基准值，从右至左，从左到右三者中的最大值
            }
        }

        arr[prev] = key;// 为什么要归位?，因为基准值的位置在上面循环操作中已经被arr[prev]或者 arr[last]替换，不归位会造成基准值的丢失，当然你也可以使用arr[last]=key进行归位，因为此时prev=last

        //循环结束此时，arr[prev]=arr[last]=key，所以我们只需要对prev之前的进行递归，prev之后的在进行递归
         quickSort1(arr, left, prev - 1);//对[left---prev-1]之间进行排序
         quickSort1(arr, prev + 1, right);//对[prev+1---right]之间进行排序

         return arr;
    }


  //基准值+递归+左右指针交换法

  public  static  int[] quickSort2(int[] arr, int left, int right){

        int pivot = arr[(left+right)/2], i = left, j = right; // 以中间的数为基准，i是左指针，往右走，j是右指针，往左走

        if (left >=right){
          return null;
        }

        while (i< j){

           while (arr[j] > pivot&&i< j ) {
               j--;
           }

           // 从右边寻找比基准数小的数
           while (arr[i] <pivot&&i< j) {
              i++;
           }// 从左边寻找比基准数大的数

           if (arr[i]<=arr[j]&&i< j) {//  优化：如果相等或者小不需要交换,继续移动左指针或者右指针
               i++;
           }

          else  {
               // 找完了，交换
                 var temp = arr[i];
                 arr[i] = arr[j];
                 arr[j] = temp;
           }


       }

         //此时已经分成左右两部分，左边比基准小，右边比基准大

          //对左边部分排序
          quickSort2(arr, left, i-1 );

          //对右边部分排序
          quickSort2(arr, i+1 , right);


         return arr;
    }

   //基准值+非递归
   public static int[] quickSort3(int[] arr, int left, int right) {
          var stack = new Stack<Integer>();
          if (left <right) {
              stack.push(left);
              stack.push(right);
              while (!stack.isEmpty()) {

                  var lastIndex = stack.pop();
                  var prevIndex = stack.pop();

                  var last = lastIndex;
                  var prev = prevIndex ;

                  var key = arr[prev];


                  while (prev < last) {
                      while (prev < last && arr[last] > key) {
                          last--;
                      }
                      if (prev<last) arr[prev++] = arr[last];

                      while (prev < last && arr[prev] <= key) {
                          prev++;
                      }
                      if (prev<last) arr[last--] = arr[prev];

                  }

                  arr[prev] = key;

                  if (prev - 1 > prevIndex ) {

                      stack.push(prevIndex );
                      stack.push(prev - 1);
                  }
                  if (prev + 1 < lastIndex) {
                      stack.push(prev + 1);
                      stack.push(lastIndex);
                  }
              }

         }

        return arr;
   }

   //三数取中间数
   /* 每次在排序之前，我们都无法知道数据是逆序或者是正序，如果每次去检测那就太麻烦，
      所以我们选取三个数值（left,right,mid）如果是正序或者逆序那么就直接选取中间的数值，
      反之任意选取两边任意一个数当作基准值。这样就很容易将数据等分开来，大大提高了代码的效率。
   */
  private static int getMid(int[] arr, int left, int right) {
        //获取中间位置
        int mid = left + ((right - left) >> 1);

         if (arr[mid] > arr[right])
         {
             swap(arr, mid, right);
         }
         if (arr[left] > arr[right])
         {
             swap(arr, left, right);
         }
         if (arr[mid] > arr[left])
         {
             swap(arr, mid, left);
         }
         return arr[left];
     }


     public static int[] betterQuickSort(int[] arr, int left, int right){


         if (right - left + 1 < 10) {// 优化
             insertSort(arr, left, right);
             return arr;
         }

         if(left>=right){
             return null;
         }

         int key = getMid(arr, left, right);// 优化
         int prev=left;
         int last=right;

         while (prev < last) {

            while (prev < last&&arr[last]>= key) {
                last--;
            }
            if (prev<last) arr[prev++] = arr[last];

            while (prev < last&&arr[prev]< key) {
               prev++;
            }
             if (prev<last) arr[last--] = arr[prev];
        }

        arr[prev]= key;
        betterQuickSort(arr,left,prev-1);
        betterQuickSort(arr,prev+1,right);

        return  arr;
    }

    public  static  void  insertSort(int[] arr,int m,int n){

        for (int i = m+1; i <=n ; i++) {

            if (arr[i] <arr[i-1]) {//如果当前元素比前一个小
                int temp=arr[i];
                int j;

                for (j=i-1; j >=0 &&temp<arr[j]; j--) {//找当前位置temp的最终插入位置
                    arr[j+1] = arr[j];//下一个元素值赋予当前元素

                }
                arr[j+1] = temp; //需要插入的值，因为退出for多减了1
            }
        }

    }

    //交换
    public static void swap(int[] arr, int left, int right)
    {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }
}