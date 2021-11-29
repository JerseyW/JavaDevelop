package Algorithm.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: Jerssy
 * @create: 2021-03-21 14:28
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 桶排序
 *
 * 桶排序是计数排序的扩展版本，计数排序可以看成每个桶只存储相同元素，而桶排序每个桶存储一定范围的元素，通过映射函数，将待排序数组中的元素映射到各个对应的桶中，对每个桶中的元素进行排序，最后将非空桶中的元素逐个放入原序列中
 *
 * 其核心是将将数组分到有限数量的桶子里。每个桶子再分别排序。
 *
 *
 * 桶排序过程中存在三个关键环节：
 * 元素值域的划分，也就是元素到桶的映射规则。映射规则需要根据待排序集合的元素分布特性进行选择，若规则设计的过于模糊、宽泛，则可能导致待排序集合中所有元素全部映射到一个桶上，则桶排序向比较性质排序算法演变。若映射规则设计的过于具体、严苛，则可能导致待排序集合中每一个元素值映射到一个桶上，则桶排序向计数排序方式演化。
 * 排序算法的选择，从待排序集合中元素映射到各个桶上的过程，并不存在元素的比较和交换操作，在对各个桶中元素进行排序时，可以自主选择合适的排序算法，桶排序算法的复杂度和稳定性，都根据选择的排序算法不同而不同
 *  尽量保证元素分散均匀，否则当所有数据集中在同一个桶中时，桶排序失效
 *
 *  O(N)+O(M*(N/M)*log(N/M))=O(N+N*(logN-logM))=O(N+N*logN-N*logM)
 * 当N=M时，即极限情况下每个桶只有一个数据时。桶排序的最好效率能够达到O(N)。

 *  桶排序的平均时间复杂度为线性的O(N+C)，其中C=N*(logN-logM)。如果相对于同样的N，桶数量M越大，其效率越高，最好的时间复杂度达到O(N)。
 *
 *    由于需要申请额外的空间来保存元素，并申请额外的数组来存储每个桶，所以空间复杂度为 O(N+M)。算法的稳定性取决于对桶中元素排序时选择的排序算法。
 */
public class BucketSort {

    public static void main(String[] args) {
        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }

        long start1=System.currentTimeMillis();
        System.out.println(Arrays.toString(bucketSort(arr .clone())));

        long end1= System.currentTimeMillis();
        System.out.println("桶排序消耗时间："+(end1-start1));

    }

    public static int[] bucketSort(int[] arr){

        boolean isLegal=arr==null|| arr.length ==0;

        if (isLegal) {
            return arr;
        }

        int max= Arrays.stream(arr).max().getAsInt();
        int min= Arrays.stream(arr).min().getAsInt();

        //桶数
        int bucketNum = (max - min) / arr.length + 1;//映射关系
        ArrayList<Integer>[] bucketArr = new ArrayList[bucketNum];

        Arrays.fill(bucketArr, new ArrayList<>());

        //将每个元素放入桶
        for (int j : arr) {
            int num = (j - min) / (arr.length);
            bucketArr[num].add(j);
        }

        //对每个桶进行排序
        for (ArrayList<Integer> integers : bucketArr) {
            Collections.sort(integers);
        }

        int arrNum=0;
        for (ArrayList<Integer> list : bucketArr) {
            for (Integer integer : list) {
                arr[arrNum++]=integer;
            }
        }

        return arr;
    }
}
