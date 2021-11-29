package Algorithm.Kruskal;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-05-16 9:29
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description:  克鲁斯卡尔算法
 *
 * 克鲁斯卡尔算法介绍
 *
 *
 *
 * 克鲁斯卡尔(Kruskal)算法，是用来求加权连通图的最小生成树的算法。
 *
 * 基本思想：按照权值从小到大的顺序选择n-1条边，并保证这n-1条边不构成回路
 *
 * 具体做法：首先构造一个只含n个顶点的森林，然后依权值从小到大从连通网中选择边加入到森林中，并使森林中不产生回路，直至森林变成一棵树为止
 *
 *
 *
 */
public class Kruskal {

    public static void main(String[] args) {
        Character[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        Kruskal kruskal= new Kruskal();

        Graph<Character>  graph = new Graph<>(vertex);

        graph.addEdge('A','B',12);
        graph.addEdge('A','F',16);
        graph.addEdge('A','G',14);
        graph.addEdge('B','C',10);
        graph.addEdge('B','F',7);
        graph.addEdge('C','D',3);
        graph.addEdge('C','F',6);
        graph.addEdge('C','E',5);
        graph.addEdge('D','E',4);
        graph.addEdge('E','G',8);
        graph.addEdge('E','F',2);
        graph.addEdge('F','G',9);
        System.out.println(kruskal.kruskal(graph));
    }



   public  LinkedList<Graph.Edge<Character>> kruskal(Graph<Character> graph){

       ArrayList<Graph.Edge<Character>> edgesList = graph.edgesList;

       edgesList.sort(Comparator.comparingInt(o -> o.weight));

       LinkedList<Graph.Edge<Character>> minTree = new LinkedList<>();

       int[] ends = new int[graph.edgeNum]; //用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点

       for (Graph.Edge<Character> edge : edgesList) {

           int p1 = getIndex(graph.vertexList, edge.start);

           int p2 = getIndex(graph.vertexList, edge.end);

           //获取p1这个顶点在已有最小生成树中的终点
           int m = loop(ends, p1);
           //获取p2这个顶点在已有最小生成树中的终点
           int n = loop(ends, p2);

           if (m != n) {
               minTree.add(edge);
               ends[m] = n;
           }

       }

       return minTree;
   }

   private int loop(int[] ends, int i ) {

       while (ends[i] != 0) {
           i = ends[i];
       }
       return i;
   }

    private int getIndex(ArrayList<Graph.Vertex<Character>> vertexList,Graph.Vertex<Character> vertex ) {
        for(int i = 0; i < vertexList.size(); i++) {
            if(vertexList.get(i) == vertex) {
                return i;
            }
        }
        return -1;
    }
}

class  Graph<T>{

    int vertexNum;

    int edgeNum;

    ArrayList< Edge<T>>  edgesList ;

    ArrayList< Vertex<T>> vertexList;

    public Graph(T[] vertex){

        vertexList = new ArrayList<>();
        edgesList = new ArrayList<>();
        this.addVertex(vertex);

    }

    static class Vertex<T> {

        T verName;//节点存储的内容

        Edge<T> edgeLink ;//顶点的边链

        public  Vertex(T name){
            verName = name;
            edgeLink = null;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "verName=" + verName +
                    '}';
        }
    }

    static class Edge<T> {

        Vertex<T> start;//边的头节点
        Vertex<T> end;//边的尾部节点
        int weight;//边的权值

        Edge<T> broEdge;// 节点连接的其他边,指向下一个邻接点

        public Edge(Vertex<T> start, Vertex<T> end,int weight){

            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "start=" + start +
                    ", end=" + end +
                    ", weight=" + weight +
                    '}';
        }
    }

    public void addVertex(T[] vertex){

        for (T c : vertex) {

            vertexList.add(new Vertex<>(c));
        }
        vertexNum = vertex.length;
    }

    public  void  addEdge(T start,T end,int weight){

        Vertex<T> startVertex = getVertex(start);
        Vertex<T> endVertex = getVertex(end);


        edgesList.add(concatEdge(startVertex, endVertex,weight));

        concatEdge(endVertex, startVertex,weight);

        edgeNum++;

    }


    private  Edge<T> concatEdge(Vertex<T> startVertex, Vertex<T> endVertex, int weight){

        Edge<T> edge = new Edge<>(startVertex,endVertex,weight);

        if (startVertex.edgeLink != null){
            Edge<T> broEdge = startVertex.edgeLink;
            while (broEdge.broEdge != null){
                broEdge = broEdge.broEdge;
            }
            broEdge.broEdge = edge;
        }
        else{

            startVertex.edgeLink = edge;
        }

        return edge;
    }

    public   Vertex<T> getVertex(T verName){

        return vertexList.stream().filter(e -> e.verName.equals(verName)).findFirst().orElseThrow(()->new NoSuchElementException(verName+" is no present"));
    }
}
