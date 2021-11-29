package Algorithm.Dijkstra;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-05-16 17:23
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 迪杰斯特拉算法
 *
 *
 * 迪杰斯特拉(Dijkstra)算法是典型最短路径算法，用于计算一个结点到其他结点的最短路径。
 * 它的主要特点是以起始点为中心向外层层扩展(广度优先搜索思想)，直到扩展到终点为止。
 *
 *
 *
 * 迪杰斯特拉(Dijkstra)算法过程
 *
 *
 *
 * 1设置出发顶点为v，顶点集合V{v1,v2,vi...}，v到V中各顶点的距离构成距离集合Dis，Dis{d1,d2,di...}，Dis集合记录着v到图中各顶点的距离(到自身可以看作0，v到vi距离对应为di)
 *
 * 从Dis中选择值最小的di并移出Dis集合，同时移出V集合中对应的顶点vi，此时的v到vi即为最短路径
 *
 * 2 更新Dis集合，更新规则为：比较v到V集合中顶点的距离值，与v通过vi到V集合中顶点的距离值，保留值较小的一个(同时也应该更新顶点的前驱节点为vi，表明是通过vi到达的)
 *
 * 3 重复执行两步骤，直到最短路径顶点为目标顶点即可结束
 *
 *
 */
public class Dijkstra {

    boolean[] isVisited;

    int[] dis;

    public static void main(String[] args) {

        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.dijkstra(vertex,2);
        System.err.println(Arrays.toString(dijkstra.dis));
    }

   public  void  dijkstra(char[] vertex,int beginIndex){

       DijkstraGraph graph = new DijkstraGraph(vertex.length);
       isVisited = new boolean[vertex.length];
       dis = new int[vertex.length];
       Arrays.fill(dis,Integer.MAX_VALUE);

       graph.addEdge(0,1,8);
       graph.addEdge(0,2,7);
       graph.addEdge(0,6,2);
       graph.addEdge(1,6,3);
       graph.addEdge(1,3,9);
       graph.addEdge(2,4,8);
       graph.addEdge(3,5,4);
       graph.addEdge(4,5,5);
       graph.addEdge(4,6 ,4);
       graph.addEdge(5,6,6);

       showGraph(graph);

       dijkstra(graph,beginIndex);
       showDijkstra(vertex);
   }

    // 显示图
    public void showGraph(DijkstraGraph graph) {
        for (int[] link :graph. matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    public void showDijkstra( char[] vertex ) {
        int count = 0;
        for (int i : dis) {
            if (i != Integer.MAX_VALUE) {
                System.out.print(vertex[count] + "("+i+") ");
            }
            count++;
        }
        System.out.println();
    }

    private   void  dijkstra(DijkstraGraph graph,int beginIndex){

        int minDistance = 0;
        dis[beginIndex] = 0;
        isVisited[beginIndex] = true;

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.offer(beginIndex);

        while (queue.size() > 0) {

            int  index = queue.poll();

            isVisited[index] = true;

            for (int j = 0; j < graph.vertexNum; j++) {

                if ( graph.matrix[index][j] != 0 && !isVisited[j]){//先统计邻节点所对应的路径权值
                    //当有更短的路径方案时，替换路径值和方案
                   dis[j] = Math.min(graph.matrix[index][j] + minDistance,dis[j]);
                }
            }

            minDistance = Integer.MAX_VALUE;

            for (int j = 0; j < dis.length; j++) {//从邻节点路径权值找出dis的最小值， 下一次从未访问过、并且可以访问的最近的dis最短的结点开始(广度优先)

                if(!isVisited[j] && dis[j] < minDistance) {
                    minDistance = dis[j];
                    index = j;
                }
            }

            if (!isVisited[index]) queue.offer(index);

        }
    }

}

class  DijkstraGraph{

    int[][] matrix;

    int vertexNum;

    public DijkstraGraph(int vertexNum) {

        this.vertexNum = vertexNum;
        matrix = new int[vertexNum][vertexNum];
    }

    public void addEdge(int start,int end,int weight){

        matrix[start][end] = weight;
        matrix[end][start] = weight;
    }

}