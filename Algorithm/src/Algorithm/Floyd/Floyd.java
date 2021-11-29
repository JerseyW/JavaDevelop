package Algorithm.Floyd;

import java.util.Arrays;

/**
 * @author: Jerssy
 * @create: 2021-05-16 19:39
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 弗洛伊德算法
 *
    弗洛伊德算法(Floyd)计算图中各个顶点之间的最短路径
 *
 * 迪杰斯特拉算法用于计算图中某一个顶点到其他顶点的最短路径
 *
 * 弗洛伊德算法 VS 迪杰斯特拉算法：迪杰斯特拉算法通过选定的被访问顶点，求出从出发访问顶点到其他顶点的最短路径；
 * 弗洛伊德算法中每一个顶点都是出发访问点，所以需要将每一个顶点看做被访问顶点，求出从每一个顶点到其他顶点的最短路径
 *
 *
 * 设置顶点vi到顶点vk的最短路径已知为Lik，顶点vk到vj的最短路径已知为Lkj，顶点vi到vj的路径为Lij，
 * 则vi到vj的最短路径为：min((Lik+Lkj),Lij(直连))，vk的取值为图中所有顶点，则可获得vi到vj的最短路径
 *
 * 至于vi到vk的最短路径Lik或者vk到vj的最短路径Lkj，是以同样的方式获得
 *
 *
 *
 *
 * Floyd算法，则修正了dijkstra算法对于边权为负问题的不足，引入了一个外循环，来遍历每个点，从而查询该点是不是在i和j之间，这样的话，无论边权为负值还是正值，都会被考虑进去。对于邻接矩阵A来说，在k-1次迭代后，A(k-1)[i][j]为所有从顶点i到j且不经过k之后的顶点的最小长度，有可能经过k之前的点。所以在遍历过程中需要比较A[i][j]与A[i][k]+A[k][j]的大小，取小值，表示比较经过k点与不经过k点的路径长度大小。

 *
 */
public class Floyd {

    public  static  void main(String[] args){

        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        new Floyd().floyd(vertex);
    }


    public void   floyd( char[] vertex){

        Graph graph = new Graph(vertex);
        graph.addEdge(0,1,5);
        graph.addEdge(0,2,7);
        graph.addEdge(0,6,2);
        graph.addEdge(1,6,3);
        graph.addEdge(1,3,9);
        graph.addEdge(2,4,8);
        graph.addEdge(3,5,4);
        graph.addEdge(4,5,5);
        graph.addEdge(4,6 ,4);
        graph.addEdge(5,6,6);
        int[][] dis = floyd(graph);
        graph.show(dis);

    }

    private  int[][]  floyd(Graph graph){

        int[][] dis = new int[graph.vertexNum][graph.vertexNum];

        for (int i = 0; i < graph.vertexNum; i++) {
            for (int j = 0; j < graph.vertexNum; j++) {

                if (graph.graphMatrix[i][j] == 0){
                     //是邻边将dis数组填充为权值最大值的2倍
                    dis[i][j] = Math.min(graph.maxWeight<<1, Integer.MAX_VALUE);
                }
                else {
                     dis[i][j] = graph.graphMatrix[i][j];
                }

                if (i == j ) dis[i][j] = 0;

            }
        }

        for (int k = 0; k < graph.vertexNum; k++) {//中间顶点

            for (int i = 0; i < graph.vertexNum; i++) {//i顶点

                for (int j = 0; j < graph.vertexNum; j++) {//j 顶点

                    dis[i][j] = Math.min(dis[i][j], dis[i][k]+dis[k][j]);
                }

            }
        }

        return dis;
    }

    static  class Graph {

        int[][] graphMatrix;

        int vertexNum;

        int maxWeight;

        char[] verTex;

        public Graph(char[] vertex){

            this.vertexNum = vertex.length;
            this.verTex = new char[vertex.length];
            System.arraycopy(vertex, 0, this.verTex, 0, vertex.length);
            graphMatrix = new int[vertex.length][vertex.length];

        }

        public  void addEdge(int start, int end,int weight){

            graphMatrix[start][end] = weight;
            graphMatrix[end][start] = weight;

            maxWeight = Math.max(maxWeight,weight);
        }

        public void   show(int [][] graphMatrix){

            for (int[] matrix : graphMatrix) {

                System.out.println(Arrays.toString(matrix));
            }
        }

    }
}
