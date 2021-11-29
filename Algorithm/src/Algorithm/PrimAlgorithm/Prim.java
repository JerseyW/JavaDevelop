package Algorithm.PrimAlgorithm;

import DataStructure.Graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-05-14 14:01
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 普里姆算法
 *
 * 修路问题本质就是就是最小生成树问题， 先介绍一下最小生成树(Minimum Cost Spanning Tree)，简称MST。
 *
 * 给定一个带权的无向连通图,如何选取一棵生成树,使树上所有边上权的总和为最小,这叫最小生成树
 *
 * 1 N个顶点，一定有N-1条边
 *
 * 2 包含全部顶点
 *
 * 3 N-1条边都在图中

 *
 * 求最小生成树的算法主要是普里姆
 * 算法和克鲁斯卡尔算
 *
 * 普里姆算法介绍
 *
 * 普利姆(Prim)算法求最小生成树，也就是在包含n个顶点的连通图中，找出只有(n-1)条边包含所有n个顶点的连通子图，也就是所谓的极小连通子图
 *
 * 普利姆的算法如下:
 *
 * 1 设G=(V,E)是连通网，T=(U,D)是最小生成树，V,U是顶点集合，E,D是边的集合
 *
 * 2 若从顶点u开始构造最小生成树，则从集合V中取出顶点u放入集合U中，标记顶点v的visited[u]=1
 *
 * 3 若集合U中顶点ui与集合V-U中的顶点vj之间存在边，则寻找这些边中权值最小的边，但不能构成回路，将顶点vj加入集合U中，将边（ui,vj）加入集合D中，标记visited[vj]=1
 *
 * 4 重复步骤②，直到U与V相等，即所有顶点都被标记为访问过，此时D中有n-1条边
 *
 *
 *
 * 将顶点分成两部分 U(选中的部分) V(未选中的部分)
 * 每次从两个部分中找出权值最小的边
 * 相连的顶点放入u中
 * 重复直到顶点全部进入u
 *
 *
 */
public class Prim {

    public static void main(String[] args) {

        Prim prim = new Prim();
        prim.prim(new char[]{'A','B','C','D','E','F','G'},0);
    }

   public  void  prim(char[] vertex,int startVertex){

       Graph graph =new Graph(vertex.length);
       graph.addVertex(vertex);
       graph.addLink(0,1,5);
       graph.addLink(0,2,7);
       graph.addLink(0,6,2);
       graph.addLink(1,3,9);
       graph.addLink(1,6,3);
       graph.addLink(2,4,8);
       graph.addLink(3,5,4);
       graph.addLink(4,5,5);
       graph.addLink(4,6,4);
       graph.addLink(5,6,6);
       graph.showGraph();
       boolean[] visited = new boolean[graph.vertexNum];

       visited[startVertex] = true;//startVertex作为最小生成树的起点

       for (int i = 0; i < graph.vertexNum-1; i++) {//graph.vertexNum-1 条边
           int start = -1;
           int end = -1;
           int minWeight = Integer.MAX_VALUE;

           for (int j = 0; j < graph.vertexNum; j++) {//在j以访问的基础上即是最小生成树的起点基础上，遍历所有邻节点寻找最小权值最小的边，即可找到最小生成树的j终点

               if (visited[j]){//每次需要处理最小生成树里的所有顶点，从最小生成树里的所有顶点中选取权值最小的边

                   for (int k = 0; k < graph.vertexNum; k++) {

                       if (graph.graphMatrix[j][k] != 0 ) {//是邻节点即存在边

                           if (!visited[k] && graph.graphMatrix[j][k] < minWeight) {

                               minWeight = graph.graphMatrix[j][k];
                               start = j;
                               end = k;
                           }
                       }
                   }
               }
           }

           if (start > -1){
               //找到一条边是最小
               System.out.println("边<" + graph.vertexList.get(start) + "," + graph.vertexList.get(end) + "> 权值:" + graph.graphMatrix[start][end]);
               //将当前这个结点标记为已经访问，即放入最小生成树中
               visited[end] = true;
           }
       }
   }

    static  class  Graph{

        private final int vertexNum;

        private final int[][] graphMatrix;

        private final ArrayList<Character> vertexList;


        public  Graph(int vertexNum){

            this.vertexNum = vertexNum;

            graphMatrix = new int[vertexNum][vertexNum];

            vertexList = new ArrayList<>();

        }

        private void addVertex(char[] vertex){
               for (char c : vertex) {
                vertexList.add(c);
            }
        }

        private void addLink(int start,int end,int weight){
            graphMatrix[start][end] = weight;
            graphMatrix[end][start] = weight;
        }

        //显示图的邻接矩阵
        private void showGraph() {
            for(int[] link: graphMatrix) {
                System.out.println(Arrays.toString(link));
            }
        }
    }
}
