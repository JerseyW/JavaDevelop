package DataStructure.SparseArray;

import java.io.*;
import java.util.Arrays;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 稀疏数组
 * @create 2021-02-05 17:31
 *
 * 稀疏数组----普通数组的压缩，目的是节省存储空间以避免资源的不必要的浪费，在数据序列化到磁盘时，压缩存储可以提高IO效率
 *
 *
 * 当一个数组中大部分元素为０，或者为同一个值的数组时，可以使用稀疏数组来保存该数组。
 *
 * 稀疏数组的处理方法:
  1 记录数组一共有几行几列，有多少个不同的值
  2 把具有不同值的元素的行列及值记录在一个小规模的数组中，从而缩小程序的规模，此小规模的数组叫做稀疏数组

  稀疏数组从第一行保存原来数组的行，列与有效值(非空的值)
  稀疏数组的第二行开始，以后的每行的第一列保存原来数组的行，第二列保存原来数组的列，第三行保存原来数组的值

   二维数组转稀疏数组：
   1 遍历原始的二维数组得到有效数据的个数sum
   2 根据sum可以创建稀疏数组sparseArray int[sum+1][3]
   3 将二维数组的有效数据存入到稀疏数组中

  稀疏数组转原始的二维数组：
   1 先读稀疏数组的第一行获得原始二维数组的行与列，创建原始二维数组 Array int[a][b]
   2 读取稀疏数组后几行的数据赋给原始的二维数组即可

 */
public class SparseArray {

  public static void main(String[] args) {
     //创建一个原始的二维数组11*11来模拟棋盘；0表示没有棋子，1表示黑子，2表示蓝子
     int[][] chessArray =new int[11][11];
     chessArray[1][2]=1;
     chessArray[2][3]=2;
     chessArray[4][5]=2;

      //输出原始的二维数组
      System.out.println("原始的二维数组");
    for (int[] ints : chessArray) {
      for (int anInt : ints) {
        System.out.printf("%d\t",anInt);
      }
        System.out.println();
    }
     //将原始的二维数组转成稀疏数组
     //获取二维数组得到非0的值
      int sum = (int) Arrays.stream(chessArray).flatMapToInt(e -> Arrays.stream(e).filter(i -> i != 0)).count();
    //创建稀疏数组
      int[][] sparseArrays=new int[sum+1][3];
       sparseArrays[0][0]=11;
       sparseArrays[0][1]=11;
       sparseArrays[0][2]=sum;


       int count = 0;
       //将原始二维数组非0值放入稀疏数组
      for (int i = 0; i < chessArray.length; i++) {
          for (int j = 0; j < chessArray[i].length; j++) {
               if(chessArray[i][j]!=0){
                   count++;
                   sparseArrays[count][0]=i;
                   sparseArrays[count][1]=j;
                   sparseArrays[count][2]=chessArray[i][j];
               }
          }
      }
      System.out.println("************************************");
      Arrays.stream(sparseArrays).forEach(i-> {
          Arrays.stream(i).forEach(e ->  System.out.printf("%d\t\t",e));
          System.out.println();
      });

      //将稀疏数组持久化到磁盘
      try(ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream("Algorithm\\src\\DataStructure\\SparseArray\\Text\\sparseArray.data"))){
             s.writeObject(sparseArrays);
             s.flush();
      }catch (IOException e) {
          e.printStackTrace();
      }
      //读取
      try(ObjectInputStream s = new ObjectInputStream(new FileInputStream("Algorithm\\src\\DataStructure\\SparseArray\\Text\\sparseArray.data"))){
          int[][] objArrays = (int[][]) s.readObject();
          System.out.println("**********读取的稀疏数组*************************");
          Arrays.stream(objArrays).forEach(i-> {

              Arrays.stream(i).forEach(e ->  System.out.printf("%d\t\t",e));
              System.out.println();
          });

      }catch (IOException | ClassNotFoundException e) {
          e.printStackTrace();
      }


      //将稀疏数组恢复成原始的二维数组
      //得到原始二维数组的行与列
      System.out.println("-----------------------------------------");
       int[][]newChessArray=new int[sparseArrays[0][0]][sparseArrays[0][1]];
      //读取稀疏数组后几行的数据赋给原始的二维数组即可
      for (int i = 1; i < sparseArrays.length; i++) {

          newChessArray[sparseArrays[i][0]][sparseArrays[i][1]]=sparseArrays[i][2];
      }
      System.out.println("-----------------------------------------");
      //打印原始数组
      Arrays.stream(newChessArray).forEach(i-> {

          Arrays.stream(i).forEach(e ->  System.out.printf("%d\t",e));
          System.out.println();
      });
  }

}
