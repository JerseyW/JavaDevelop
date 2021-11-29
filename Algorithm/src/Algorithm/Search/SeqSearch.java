package Algorithm.Search;

/**
 * @author: Jerssy
 * @create: 2021-03-22 17:12
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description:
 */
public class SeqSearch {

    public static void main(String[] args) {

    }

    public  static  int  seqSearch(int[] arr,int searchValue){

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == searchValue) {
                return i;
            }

        }
        return  -1;
    }
}
