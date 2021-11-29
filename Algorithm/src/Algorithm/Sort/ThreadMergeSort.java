package Algorithm.Sort;

import java.io.Serial;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author: Jerssy
 * @create: 2021-03-17 17:44
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 多线程模式下归并排序
 */
public class ThreadMergeSort extends RecursiveAction {

    public static void main(String[] args) {
        int[] arr = new int[8000];
        for (int i = 0; i <8000; i++) {
            arr[i]= (int) (Math.random() * 8000);
        }

        long start=System.currentTimeMillis();
        System.out.println(Arrays.toString(threadMergeSort(arr)));

        long end= System.currentTimeMillis();
        System.out.println("多线程归并排序消耗时间："+(end-start));
    }

    @Serial
    private static final long serialVersionUID = -4369231251235987766L;
    private final int[] meargeArr;

    public ThreadMergeSort(int[] meargeArr) {
        this.meargeArr = meargeArr;
    }


    @Override
    protected void compute() {
        if (meargeArr.length<=2){
            if (meargeArr.length==2&&meargeArr[0]>meargeArr[1]){
                meargeArr[0] = meargeArr[0]^meargeArr[1];
                meargeArr[1] = meargeArr[0]^meargeArr[1];
                meargeArr[0] = meargeArr[0]^meargeArr[1];

            }
            return ;

        }
        int middle=meargeArr.length>>1;

        int[] leftArray= Arrays.copyOfRange(meargeArr, 0, middle);
        int[] rightArray= Arrays.copyOfRange(meargeArr, middle, meargeArr.length);

        invokeAll(new ThreadMergeSort(leftArray), new ThreadMergeSort(rightArray));


         merge(leftArray, rightArray,meargeArr);
    }


    public static int[]  threadMergeSort(int[] arr){

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ThreadMergeSort(arr));
        return arr;

    }

    //将两个有序数组合并，合并后数组仍然有序
    private static void merge(int[] left, int[] right,int[]  temp){

        int i = 0;
        int tem=0;
        int j=0;


        while (i< left.length &&j< right.length ) {
            if (left[i]<= right[j]){//比较left元素与right元素大小
                temp[tem++] = left[i++];
            }
            else {
                temp[tem++] = right[j++];

            }
        }

        if(i== left.length){//说明left已经放完，right存在没有放完的元素
            System.arraycopy(right,j,temp,tem,right.length-j);

        }
        if(j== right.length){//说明right已经放完，left存在没有放完的元素

            System.arraycopy(left,i,temp,tem,left.length-i);

        }

    }
}
