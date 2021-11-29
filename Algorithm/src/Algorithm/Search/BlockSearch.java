package Algorithm.Search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Jerssy
 * @create: 2021-03-23 10:21
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 分块查找(顺序索引查找)
 *
 * 要求是顺序表，分块查找又称索引顺序查找，它是顺序查找的一种改进方法。
 * 将n个数据元素"按块有序"划分为m块（m ≤ n）。
 * 每一块中的结点不必有序，但块与块之间必须"按块有序"；
 * 即第1块中任一元素的关键字都必须小于第2块中任一元素的关键字；
 * 而第2块中任一元素又都必须小于第3块中的任一元素，……
 *
 * 步骤：
 * 1、先选取各块中的最大关键字构成一个索引表---索引表中每个元素含有各个块的最大关键字和各个块的第一个元素地址，索引表按关键字有序排列
 * 2、查找分两个部分：先对索引表进行二分查找或顺序查找，以确定待查记录在哪一块中；
 * 3、在已确定的块中用顺序法进行查找。
 *

 * 时间复杂度：O(log(m)+N/m)
 *
 */
public class BlockSearch {

    public static void main(String[] args) {

        int[] array = new int[] { 22, 12, 13, 8, 9, 41,44, 33, 42, 44, 38, 24, 45,48, 60, 44,74, 74, 49, 86, 53};


        int blockNum=3;
        Block [] blocks = new Block[blockNum];

        int index=array.length/blockNum;

        int arrNum = 0;
        for (int i = 0,len = array.length/blockNum-1; i <blockNum; i++,len+=index) {

            if (i==blockNum-1){
                len=array.length-1;
            }

            int max=array[len];

            for( int j=len;j>(len-index);j--){
                if (max<array[j]){
                    max=array[j];
                }
            }

            blocks[arrNum++]=new Block(len-index+1,len, max);
        }

        System.out.println(blockSearch(array,blocks,blockNum,74));

    }


    private static ArrayList<Integer>  getBlockIndex( Block[]  arr,int blockNum,int searchValue) {


        ArrayList<Integer> list = new ArrayList<>();

        int i=0;

        while(i<blockNum ) {
            if (arr[i].maxNum>=searchValue)
            list.add(i );
            i++;
        }


        return list;
    }

    public  static List<Integer> blockSearch(int[] arr, Block[] blocks, int blockNum,int searchValue){

        List<Integer> searchList = getBlockIndex(blocks,blockNum, searchValue);
        if (searchList.size()==0) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        for (Integer integer : searchList) {
            int start = blocks[integer].startIndex;
            int end = blocks[integer].endIndex;

            for (int j = start; j <= end; j++) {
                if (arr[j] == searchValue) {

                    list.add(j);
                }
            }
        }


        return list;
    }

    public static class Block {
        int maxNum;// 块中的最大值
        int startIndex;// 块在数组中的起始索引位置
        int endIndex;// 块在数组中的结束索引位置

        public Block(int startIndex, int endIndex, int maxNum) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.maxNum = maxNum;
        }

        @Override
        public String toString() {
            return "[" + startIndex + "~" + endIndex + "]" +
                    " maxNum=" + maxNum;
        }
    }

}
