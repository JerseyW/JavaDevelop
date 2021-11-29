package Algorithm.Search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Jerssy
 * @create: 2021-03-23 19:43
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 斐波那契查找：数据必须采用顺序存储结构； 数据必须有序。
 *
 * 斐波那契查找原理与前两种相似，仅仅改变了中间结点（mid）的位置，mid再是中间或插值得到，而是位于黄金分割点附近，即mid=low+F(k-1)-1（F代表斐波那契数列）
 * 斐波那契查找同样是查找算法家族中的一员，它要求数据是有序的（升序或降序）。斐波那契查找采用和二分查找/插值查找相似的区间分割策略，都是通过不断的分割区间缩小搜索的范围。
 *
 * 斐波那契数列中 ，从第三项开始，每一项都等于前两项之和：F(n)=F(n-1)+F(n-2)(n>=2)
 * 其中n的取值是任意长度的，即对任意长度的数组都能找到对应的斐波那契数，
 *
 *
 * 斐波那契查找的整个过程可以分为：
 *
 * 1 构建斐波那契数列；
 * 2 计算数组长度对应的斐波那契数列元素个数--在斐波那契数列找一个等于或略大于查找表中元素个数的数F[n]
 * 3 对数组进行填充；----如果不等于则将原查找表扩展为长度为F[n](如果要补充元素，则补充重复最后一个元素，直到满足F[n]个元素)。
 * 4 循环进行区间分割，查找中间值；
 * 5 判断中间值和目标值的关系，确定更新策略；
 *
 *
 *
 *对F(k-1)-1的理解：
 *
 * 由斐波那契数列 F[k]=F[k-1]+F[k-2] 的性质，可以得到 （F[k]-1）=（F[k-1]-1）+（F[k-2]-1）+1 。该式说明：只要顺序表的长度为F[k]-1，则可以将该表分成长度为F[k-1]-1和F[k-2]-1的两段，即如上图所示。从而中间位置为mid=low+F(k-1)-1
 *
 * 数组长度为F(k) - 1;
 * 左区间长度为F(k - 1) - 1;
 * 右区间长度为F(k - 2) - 1;
 * middle节点只有一个，长度为1
 * 因为公式成立：
 * F(k) - 1 = F(k - 1) + F(k - 2)
 * 所以下面公式也成立：
 * F(k) - 1 = F(k - 1) - 1 + 1 + F(k - 2) - 1
 *
 *  即F(k) - 1 = (F(k - 1) - 1)(左区间元素个数) + 1(middle元素个数) + (F(k - 2) - 1)(右区间元素个数)

    mid=low+F(k-1)-1 的推导：

    假定数组为[1 3 5 7 11 12]---对应斐波那契数列[0 1 1 2 3 5 8]

    数组长度为6 没有合适的斐波那契数匹配，则扩容[1 3 5 7 11 12 12 12]至符合斐波那契数8

    现在数组长度是8，对应斐波那契数F(k=5))=8即F(5),F(5)=F(5-1)+F(5-2),
    则F(5-1)=F(4)即为黄金分割点 映射到对应数组索引长度则为F(5)-1=F(5-1)-1+F(k-2)-1+1(？因为数组从0开始所以需要稍微变形)
    对应数组中的黄金分割点值为 F(5-1)-1=F(4)-1

    则根据斐波那契规则会将数组索引分成[0--4]和[5-7]两部分 即分别为F(k-1)-1和F(k-2)-1两部分

    所以我们对应的middle则为数组首索引+对应黄金分割点长度F(4)-1 即：middle=0+F(4)-1-->middle=low+F(5-1)-1---->middle=low+F(k-1)-1


while(n>fib(k)-1)

k++;
 *
 * 举例：[ 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377]---斐波那契数列
 *      待操作数组[1, 2, 4, 6, 7, 9, 13,16, 17, 21, 23, 25, 27,31, 45, 56, 58, 61, 65]
 *
 *      没有与数组长度19匹配的斐波那契数，则寻找大于数组长度的最近一个斐波那契数值即21来填充数组
 *      使数组长度满足斐波那契数21(使用数组最后一个来填充)
 *
 *      循环进行区间分割，查找中间值
 *
 *    二分查找和插值查找相似，都是不断的缩小搜索区间，进而确定目标值的位置。区间分割公式如“要点”所述，每次分割中间位置的计算如下：
 *    middle=low+F(n-1)-1
 *
 *
 *    判断中间值和目标值的关系，确定更新策略
 *
 * 中间值和目标值有三种大小关系，分别对应三种处理方式：
 *
 * 相等，则查找成功，返回中间位置即可；
 * 中间值小于目标值，则说明目标值位于中间值到右边界之间（即右区间），右区间含有F(n-2)个元素，所以n应该更新为n=n-2；
 * 中间值大于目标值，这说明目标值位于左边界和中间值之间（即左区间），左区间含有F(n-1)个元素，所以n应更新为n=n-1；
 *
 *
 */
public class FibonacciSearch {

    static int[] fibonacciArray;

    static int fibonacciNumber = 0;

    public static void main(String[] args) {

        int[] array=new int[]{1, 2,2,2, 4, 6, 7, 9, 13,16, 17, 21 , 23, 25,27,31, 45, 56, 56,56, 56};

        System.out.println(fibonacciSearch(array, 2));
        System.out.println(fibonacciSearch1(array, 61));

    }

    /*
     * @param: [array]
     * @return: int[]
     * @author: Jerssy
     * @data: 2021/3/24 14:15
     * @description: 创建一个斐波那契数列并使数组与之匹配，返回匹配数组
     */


    private static int[] createFibonacci(int[] array) {

        //与数组匹配的斐波那契数列公式：F(k) - 1 = F(k - 1) - 1 + 1 + F(k - 2) - 1
        fibonacciArray=new int[array.length];
        fibonacciArray[0]=fibonacciArray[1]=1;

        for (int i = 2; i < array.length; i++) {

            fibonacciArray[i]=fibonacciArray[i-1]+fibonacciArray[i-2];
            if (fibonacciArray[i]>=array.length) {//创建数的列长度只需满足数列里的某个值大于等于数组长度即可,这样数列的最后一个数就是匹配的数组长度的斐波那契数
                fibonacciArray=Arrays.stream(fibonacciArray).takeWhile(e->e!=0).toArray();//删除数组为0的元素节省空间
                break;
            }
        }

        //匹配数组长度的斐波那契数
        fibonacciNumber = fibonacciArray.length-1;
        int[] copyArray;
        if (fibonacciArray[fibonacciNumber]>array.length){//如果数列最后一个斐波那契数大于数组长度，说明没有合适的数列值等于数组长度，需要对数组进行填充

            copyArray=Arrays.copyOf(array, fibonacciArray[fibonacciNumber]);
            Arrays.fill(copyArray, array.length, copyArray.length,array[array.length - 1]);
        }
        else  copyArray=array;//如果相等直接操作原数组

        return copyArray;
    }


    /*
     * @param: [arr,searchValue]
     * @return: java.util.ArrayList<java.lang.Integer>
     * @author: Jerssy
     * @dateTime: 2021/3/24 14:16
     * @description: 斐波那契非递归查找
     */
    public static ArrayList<Integer> fibonacciSearch(int[] arr, int searchValue){
        ArrayList<Integer> list = new ArrayList<>();

        if (arr==null||arr.length == 0) return null;
        if ( arr.length ==1) {
             if(arr[0]==searchValue){
                 list.add(0);
             }
             return list;
        }

        //创建一个符和的斐波那契数列
        int[] copyArray = createFibonacci(arr);
        int left=0,right = arr.length-1;

        while (left <= right) {

            int middle=left+fibonacciArray[Math.max(fibonacciNumber-1,0)]-1;//黄金分割点F(k-1)-1

            if (copyArray[middle]==searchValue) {

                int leftIndex =left;
                while ( leftIndex <= right) {//因为步长不是1，如果所查找的元素位于黄金分割点前面则会将当前符和条件的middle的前面元素给过滤掉.继续查找left- right的剩余是否满足

                    if( copyArray[leftIndex] == searchValue){
                        list.add(leftIndex);
                    }

                    leftIndex++;
                }

                break;
            }

            if (copyArray[middle] >searchValue) {

                right=middle-1;
                fibonacciNumber-=1;//在左半区间F(n-1)，需要在f[k-1] 的前面进行查找 k -=1,则下一次middle=F(k-1-1)-1
            }
            else {

                left = middle +1;
                fibonacciNumber-=2;//在右半区间F(n-2),需要在f[k-2] 的后面进行查找 k -=2，则下一次middle=F(k-2-2)-1
            }
        }

        return list;
    }

    /*
     * @param: [arr,searchValue]
     * @return: java.util.List<java.lang.Integer>
     * @author: Jerssy
     * @dateTime:2021/3/24 14:16
     * @description: 斐波那契递归查找
     */
    public static List<Integer> fibonacciSearch1(int[] arr, int searchValue){

        if (arr==null||arr.length == 0) return null;
        if ( arr.length ==1) {
            List<Integer> list = new ArrayList<>();
            if(arr[0]==searchValue){

                list.add(0);
            }
            return list;
        }
        //创建一个符和的斐波那契数列
        return fibonacciSearch(createFibonacci(arr),0,arr.length-1,searchValue);
    }


    /*
     * @param: [arr, left, right, searchValue]
     * @return: java.util.List<java.lang.Integer>
     * @author: Jerssy
     * @dateTime: 2021/3/24 14:31
     * @description:递归查找,将满足查找的索引放入集合List
     */
    private  static List<Integer> fibonacciSearch(int[] arr,int left,int right,int searchValue){


           if (left>right){

             return new ArrayList<>();
           }

          int middle=left+fibonacciArray[Math.max(fibonacciNumber-1,0)]-1;

           if (arr[middle]>searchValue){
               fibonacciNumber-=1;
               return fibonacciSearch(arr, left, middle-1, searchValue);
           }

           else if (arr[middle]<searchValue){

               fibonacciNumber-=2;
               return fibonacciSearch(arr, middle+1, right, searchValue);
           }

           else {
                 List<Integer> list = new ArrayList<>();
                 middle=Math.min(middle,right);
                 int leftIndex=middle-1,rightIndex = middle+1;

                 while (leftIndex>=0) {

                     if (searchValue==arr[leftIndex]){

                         list.add(leftIndex);
                     }

                     leftIndex--;
                 }


                 list.add(middle);

                 while (rightIndex<=right) {
                     if (searchValue==arr[rightIndex]) list.add(rightIndex);

                     rightIndex++;
                 }

                 return list;
          }
    }
}
