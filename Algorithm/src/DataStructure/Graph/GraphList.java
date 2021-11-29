package DataStructure.Graph;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-04-29 14:17
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 图邻接表
 */
public class GraphList<T>  {

    int numOfVertex;// 图的节点个数

    int edgeNum;//图的边的条数

    LinkedList<Vertex<T>> verList;//图的邻接表中存储节点的数组

    private final LinkedList<Vertex<T>> queue; //保存排序结果

    private final Stack<Vertex<T>> stack;//使用栈逆拓扑排序

    private  int stackNum;//表示在栈中的编号

    boolean isCircle = false;//是否有环

    static class Edge<T> {

        Vertex<T> tail;//边的尾部节点

        int weight;//边的权值

        Edge<T> broEdge;// 节点连接的其他边,指向下一个邻接点
    }

    static class Vertex<T> {

        T verName;//节点存储的内容

        Edge<T> edgeLink;//顶点的边链

        boolean isVisited = false;

        int inDegree;//  顶点的入度
        int outDegree;//  顶点的出度

        int  circleStatus=1;//节点状态 1 正常访问结束状态  -1 存在闭环

    }

    public  GraphList(){

        verList = new LinkedList<>();
        queue = new LinkedList<>();
        stack=new Stack<>();

    }

    public  void showGraph(){
        for (Vertex<T> vertex : verList) {
            System.out.print(vertex.verName);

            Edge<T> current = vertex.edgeLink;
            while (current != null) {
                System.out.print("--" + current.weight + "-->" + current.tail.verName);
                current = current.broEdge;
            }
            System.out.println();
        }
    }

    public  void addVertex(T verName){

        Vertex<T> vertex = new Vertex<>();
        vertex.edgeLink = null;
        vertex.verName = verName;
        verList.add(vertex);
        numOfVertex++;

    }

    public  void  insertEdge(T start,T end,int weight){

        this.edgeNum++;
        Vertex<T> preV = getVertex(start);
        Vertex<T> endV = getVertex(end);
        preV.outDegree++;
        concatEdge(preV, endV,weight);
        //加入则为无向图
         //concatEdge(endV, preV,weight);
    }

    private void concatEdge(Vertex<T> preV,Vertex<T> endV, int weight){

        Edge<T> edge = new Edge<>();
        endV.inDegree++;
        edge.tail = endV;
        edge.weight = weight;

        if (preV.edgeLink != null){
            Edge<T> broEdge = preV.edgeLink;
            while (broEdge.broEdge != null){
                broEdge = broEdge.broEdge;
            }
            broEdge.broEdge = edge;
        }
        else{

            preV.edgeLink = edge;
        }
    }

    public  void   graphListDfs(){

        for (Vertex<T> tVertex : verList) {


            if (!tVertex.isVisited){
                dfs(tVertex);
            }

            //访问完重置为false
            tVertex.isVisited = false;
        }
    }

    private  void  dfs(Vertex<T> indexVertex){

        //在递归之前加入队列，前序拓扑排序
       // queue.offer(indexVertex);
        indexVertex.isVisited=true;

        System.out.print(indexVertex.verName+"-->");

        Edge<T> edgeLink = indexVertex.edgeLink;

        while (edgeLink!= null){

            Vertex<T> vertex =  edgeLink.tail;

            if (isCircle) return;

            else  if (!vertex.isVisited) {

                dfs(vertex);

            }
            else if (vertex.circleStatus == 1){

                isCircle = true;

                return;
            }

            edgeLink = edgeLink.broEdge;
        }
        indexVertex.circleStatus = -1;
        stack.push(indexVertex);//递归完成之后加入栈，实现反向，逆后序
        //在递归之后说明出度为0或者其他邻节点已经访问过则该顶点加入队列，后序拓扑排序
        queue.offer(indexVertex);
    }


    public  void  graphListBfs(){

        Queue<Vertex<T>>  queue= new ArrayDeque<>();
        Vertex<T> first = verList.getFirst();
        first.isVisited=true;
        System.out.print(first.verName+"-->");
        queue.offer(first);

        while (queue.size() > 0) {
            Edge<T> edgeLink = queue.poll().edgeLink;

            while (edgeLink != null) {

                Vertex<T> vertex = edgeLink.tail;

                if (!vertex.isVisited){
                    System.out.print(vertex.verName+"-->");
                    vertex.isVisited = true;
                    queue.offer(vertex);
                }

                edgeLink = edgeLink.broEdge;
            }
        }

        for (Vertex<T> tVertex : verList) {

            //访问完重置为false
            tVertex.isVisited = false;
        }
    }


    public Vertex<T> getVertex(T verName){

        return verList.stream().filter(e -> e.verName.equals(verName)).findFirst().orElseThrow(()->new NoSuchElementException(verName+" is no present"));
    }


    //有向图的拓扑排序--深度优先
    public  void topologyDfsSort(){

        if (isCircle  ) throw new RuntimeException("Graph has circle");

        for (Vertex<T> tVertex : queue) {
            System.out.print(tVertex.verName+"-->");
        }
        System.out.println( );
        System.out.println("逆拓扑排序");
        while (!stack.isEmpty()){
            Vertex<T> pop = stack.pop();
            System.out.print(pop.verName+"-->");
        }
    }


    //有向图的拓扑排序--广度优先(Kahn算法)
    public  void  topologySort(){
         Queue<Vertex<T>> queue = new LinkedList<>();

         int count = 0;//如果最后输出的个数不等于总结点数，那么一定存在闭环。

         for (Vertex<T> tVertex : verList) {
               if (tVertex.inDegree == 0){//入度为0的顶点入队列
                   queue.offer(tVertex);
               }
         }
         while (queue.size() > 0) {
             Vertex<T> poll = queue.poll();
             System.out.print(poll.verName+"-->");
             Edge<T> edgeLink = poll.edgeLink;
             count++;
             while (edgeLink != null) {//对该顶点的每个尾部节点入度--，如果入度为0则入队列

                 Vertex<T> vertex = edgeLink.tail;

                 if (--vertex.inDegree == 0){

                     queue.offer(vertex);
                 }

                 edgeLink = edgeLink.broEdge;
             }

         }
        if (count != verList.size() ){
            throw new RuntimeException("Graph has circle");
        }
    }

    //利用Trajan 算法求解有向图的强连通分量
    public  void  tarJan(){

        int[] dfs = new int[numOfVertex];

        int[] low = new int[numOfVertex];

        Arrays.fill(dfs,-1);
        Arrays.fill(low,-1);

        Stack<Integer> stack = new Stack<>();
        List<ArrayList<T>> result = new ArrayList<>();

        for (int i = 0; i < verList.size(); i++) {
            if (!verList.get(i).isVisited) tarJan(i,stack,dfs,low, result);
        }

        System.out.println(result);
    }

    private  void  tarJan(int index, Stack<Integer> stack,int[] dfs,int[] low,List<ArrayList<T>> result) {

         dfs[index] = low[index] = stackNum++;

         Vertex<T> tVertex = verList.get(index);

         tVertex.isVisited = true;

         stack.push(index);
         Edge<T> edgeLink = tVertex.edgeLink;

         while ( edgeLink != null){

             Vertex<T> nextVertex = edgeLink.tail;
             int nextIndex = verList.indexOf(nextVertex);

             if (!nextVertex.isVisited){

                 tarJan(nextIndex,stack,dfs,low,result);

                 low[index] = Math.min(low[index],low[nextIndex]);
             }
             else if (stack.contains(nextIndex)) {

                 low[index] = Math.min(low[index], dfs[nextIndex]);
             }

             edgeLink = edgeLink.broEdge;

         }

         if (dfs[index] == low[index]){

             int cot = -1;

             ArrayList<T> list = new ArrayList<>();
             while ( cot != index){

                 int  pop = stack.pop();

                 list.add(verList.get(pop).verName);

                 cot = pop;

             }
             result.add(list);
         }

    }


    public static void main(String[] args) {

        GraphList<String> graphList = new GraphList<>();
         String[] Vertexs = {"A", "B", "C", "D", "E"};
          // String[] Vertexs = {"1", "2", "3", "4", "5", "6", "7", "8"};

        //String[] Vertexs = {"0", "1", "2", "3", "4", "5"};
        //循环的添加顶点
        for(String vertex: Vertexs) {
            graphList.addVertex(vertex);
        }

        graphList.insertEdge("A", "B",0); // A-B
        graphList.insertEdge("A", "C",0); //
        graphList.insertEdge("B", "C",0); //
        graphList.insertEdge("B", "D",0); //
        graphList.insertEdge("B", "E",0); //

//        graphList.insertEdge("1", "2",0);
//        graphList.insertEdge("1","3",0);
//        graphList.insertEdge("2", "4",0);
//        graphList.insertEdge("2", "5",0);
//        graphList.insertEdge("4", "8",0);
//        graphList.insertEdge("5", "8",0);
//        graphList.insertEdge("3", "6",0);
//        graphList.insertEdge("3", "7",0);
//        graphList.insertEdge("6", "7",0);

//        graphList.insertEdge("0", "1",0);
//        graphList.insertEdge("0", "2",0);
//        graphList.insertEdge("1", "3",0);
//        graphList.insertEdge("2", "3",0);
//        graphList.insertEdge("2", "4",0);
//        graphList.insertEdge("3", "0",0);
//        graphList.insertEdge("3", "5",0);
//        graphList.insertEdge("4", "5",0);


        graphList.showGraph();

        System.out.println("深度遍历");
          graphList.graphListDfs(); // A->B->C->D->E [1->2->4->8->5->3->6-7]

        System.out.println();
        System.out.println("广度遍历");
        graphList.graphListBfs(); //A->B->C->D->E [1->2->3->4->5->6->7->8]

        System.out.println();
        System.out.println("有向图的强连通分量");
        graphList.tarJan();
        System.out.println();
        System.out.println("有向图广度优先拓扑排序");
        graphList.topologySort();
        System.out.println();
        System.out.println("有向图拓扑排序深度优先");
        graphList.topologyDfsSort();


    }
}
