package DataStructure.Graph;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-04-28 10:48
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 图 邻接矩阵
 *
 * 线性表局限于一个直接前驱和一个直接后继的关系
 *
 * 树也只能有一个直接前驱也就是父节点
 *
 * 当我们需要表示多对多的关系时， 这里我们就用到了图
 *
 *图是一种数据结构，其中结点可以具有零个或多个相邻元素。两个结点之间的连接称为边。 结点也可以称为顶点。
 * 在图结构中，每个元素都可以有零个或多个前驱，也可以有零个或多个后继，也就是说，元素之间的关系是任意的。
 *
 *无向图：无向图是由顶点和边构成。
 *有向图：有向图是由顶点和有向边构成。
 *完全图：如果任意两个顶点之间都存在边叫完全图，有向的边叫有向完全图。如果无重复的边或者顶点到自身的边叫简单图。
 *
 *
 * 图的表示方式有两种：二维数组表示（邻接矩阵）；链表表示（邻接表）。
 *
 *
 * 邻接矩阵
 *
 * 邻接矩阵是表示图形中顶点之间相邻关系的矩阵，对于n个顶点的图而言，矩阵是的row和col表示的是1....n个点。
 *
 * 邻接表
 *
 * 邻接矩阵需要为每个顶点都分配n个边的空间，其实有很多边都是不存在,会造成空间的一定损失.
 *
 * 邻接表的实现只关心存在的边，不关心不存在的边。因此没有空间浪费，邻接表由数组+链表组成
 *
 *
 * 拓扑序列：对一个有向无环图(Directed Acyclic Graph简称DAG)G进行拓扑排序，是将G中所有顶点排成一个线性序列，使得图中任意一对顶点u和v，若<u，v> ∈E(G)，则u在线性序列中出现在v之前。
 *    　通常，这样的线性序列称为满足拓扑次序(TopoiSicai Order)的序列
 *    即给定一副有向图，将所有的顶点排序，使得所有的有向边均从排在前面的元素指向排在后面的元素。
 *
 *图的一个概念——入度--就是在有向图中，从别的顶点指向自己本身的边的个数
 *图的一个概念——出度--就是在有向图中，就是从这个顶点出发的边的个数
 *
 * 有向图的拓扑排序的基本思想是：拓扑排序，其实就是寻找一个入度为0的顶点，该顶点是拓扑排序中的第一个顶点序列，将之标记删除，然后将与该顶点相邻接的顶点的入度减1，再继续寻找入度为0的顶点，直至所有的顶点都已经标记删除或者图中有环。
 *  如果队列长度不等于顶点数组长度说明有环

该算法借助队列来实现时，感觉与 二叉树的 层序遍历算法很相似啊。说明这里面有广度优先的思想。

第一步：遍历图中所有的顶点，将入度为0的顶点 入队列。

第二步：从队列中出一个顶点，打印顶点，更新该顶点的邻接点的入度(减1)，如果邻接点的入度减1之后变成了0，则将该邻接点入队列。

第三步：一直执行上面 第二步，直到队列为空。


利用DFS求拓扑序列的抽象算法可描述为：
　　　　void DFSTopSort(G，i，T){
　　　　//在DisTraverse中调用此算法，i是搜索的出发点，T是栈
　　　　 int j；
　　　　 visited[i]=TRUE； //访问i
　　　　 for(所有i的邻接点j)//即<i，j>∈E(G)
　　　　   if(!visited[j])
　　　　   DFSTopSort(G，j，T)；
　　　　   //以上语句完全类似于DFS算法
　　　　  Push(&T，i)； //从i出发的搜索已完成，输出i
　　　　  }

三种有向图的遍历方式：

前序遍历：在递归前将顶点加入队列
后序遍历：在递归完成后将顶点加入队列
逆后序：在递归完成后将顶点加入栈 ，得到的是后序的逆序



有向图中的强连通性
有向图中的连通性
如果v可达w，且w可达v（存在一条路径从v到w，也存在一条路径从w到v），则称v顶点和w顶点是强连通的

如果一幅有向图中任意两点都是强连通的，则称这副图也是强连通的；两个顶点是强连通的，当且仅当它们都在有向图的一个简单环中

由于强连通性是一种等价关系，它将所有顶点分成了不同的等价类，这些等价类称作强连通分量

一个含有V个顶点的有向图含有1~V个强连通分量
一个强连通图含有一个强连通分量
一个有向无环图含有V个强连通分量

计算强连通：

Tarjan算法：
Tarjan算法是基于对图深度优先搜索的算法，每个强连通分量为搜索树中的一棵子树。搜索时，把当前搜索树中未处理的节点加入一个堆栈，回溯时可以判断栈顶到栈中的节点是否为一个强连通分量。

再Tarjan算法中，有如下定义。

时间戳 DFN[ i ] : 在DFS中该节点被搜索的次序(时间戳)

追溯值 LOW[ i ] : 为i或i的子树能够追溯到的最早的栈中节点的次序号

追溯值用来表示从当前节点 x 作为搜索树的根节点出发，能够访问到的所有节点中，时间戳最小的值 —— low[x]。那么，我们要限定下什么是“能够访问到的所有节点”？，其需要满足下面的条件之一即可：

以 x 为根的搜索树的所有节点
通过一条非搜索树上的边，能够到达搜索树的所有节点



当DFN[ i ]==LOW[ i ]时，为i或i的子树可以构成一个强连通分量。

Tarjan 算法的求解无向图的桥

// x 代表当前搜索树的根节点，in_edge 代表其对应的序号（tot）
void tarjan(int x, int in_edge) {
   // 在搜索之前，先初始化节点 x 的时间戳与追溯值
   dfn[x] = low[x] = ++num;
  // 通过 head 变量获取节点 x 的直接连接的第一个相邻节点的序号
  // 通过 Next 变量，迭代获取剩下的与节点 x 直接连接的节点的序号
   for (int i = head[x]; i; i = Next[i]) {
    // 此时，i 代表节点 y 的序号
    int y = ver[i];
     // 如果当前节点 y 没有被访问过
       if (!dfn[y]) {
       // 递归搜索以 y 为跟的子树
         tarjan(y, i);
       // 计算 x 的追溯值
        low[x] = min(low[x], low[y]);
       // 桥的判定法则
            if (low[y] > dfn[x])
              bridge[i] = bridge[i ^ 1] = true; // 标记当前节点是否为桥（具体见下文）
       }
       else if (i != (in_edge ^ 1)) // 当前节点被访问过，且 y 不是 x 的“父节点”（具体见下文）
              low[x] = min(low[x], dfn[y]);
   }
}


无向图的桥判定法则
在一张无向图中，判断边 e （其对应的两个节点分别为 u 与 v）是否为桥，需要其满足如下条件即可：dfn[u] < low[v]

它代表的是节点 u 被访问的时间，要优先于（小于）以下这些节点被访问的时间 —— low[v] 。

以节点 v 为根的搜索树中的所有节点
通过一条非搜索树上的边，能够到达搜索树的所有节点


Tarjan 算法的求解强连通分量
tarjan(u){

　　DFN[u]=Low[u]=++Index // 为节点u设定次序编号和Low初值

　　Stack.push(u)   // 将节点u压入栈中

　　for each (u, v) in E {// 枚举每一条边

　　　　if (v is not visted){// 如果节点v未被访问过

　　　　　　　　tarjan(v) // 继续向下找

　　　　　　　　Low[u] = min(Low[u], Low[v])//比较谁是谁的儿子／父亲，就是树的对应关系，涉及到强连通分量子树最小根的事情,
       }
　　　　else if (v in S) // 如果节点u还在栈内，更新该节点的low为栈中的low

　　　　　　　　Low[u] = min(Low[u], DFN[v])//同样是确定父子关系
    }
　　if (DFN[u] == Low[u]) // 如果节点u经过min()没有改变则说明u是强连通分量的根

　　repeat v = S.pop  // 将v退栈，为该强连通分量中一个顶点

　　print v

　　until (u== v)

}


Kosaraju算法

算法流程：

（1）对原图G取反GT（也称转置图）

（2）从任意一个顶点开始对反向图GT进行逆后续DFS遍历，获得图的拓扑排序reversePost

（3）按照逆后续遍历reversePost中 节点的出栈顺序，对原图G 进行DFS遍历，一次DFS遍历中访问的所有顶点都属于同一强连通分量。

Kosaraju 算法就是分别对原图G 和它的逆图 GT 进行两遍DFS，即：

1).对原图G进行深度优先搜索，找出每个节点的完成时间(时间戳)

2).选择完成时间较大的节点开始，对逆图GT 搜索，能够到达的点构成一个强连通分量

3).如果所有节点未被遍历，重复2). ，否则算法结束




A 对原图G,进行一遍DFS的结果，每个节点有两个时间戳，即节点的发现时间u.d和完成时间u.f

我们将完成时间较大的，按大小加入堆栈

B
1)每次从栈顶取出元素

2)检查是否被访问过

3)若没被访问过，以该点为起点，对逆图进行深度优先遍历

4)否则返回第一步，直到栈空为止

 *
 */
public class Graph<T> {

    private final boolean[] isVisited;//记录每个顶点是否已经访问过

    private final int[][] graphMatrix;//邻接矩阵

    private ArrayList<T> vertexList;//保存顶点的数组

    private  final  int[] circleStatus;//顶点状态

    private final LinkedList<Integer> queue; //保存排序结果

    private final Stack<Integer> stack;//使用栈逆拓扑排序

    boolean isCircle = false;//是否有环

    int[] degree;//统计顶点的入度

    int numOfVertex;// 当前图中顶点的数量

    private int stackNum;    //stackNum表示在栈中的编号


    public  Graph(int num) {
        isVisited = new boolean[num];
        graphMatrix = new int[num][num];
        vertexList= new ArrayList<>(num);
        circleStatus=new int[num];

        queue = new LinkedList<>();
        stack =new Stack<>();
        degree = new  int[num];

    }



    public void addVertex(T t){
        vertexList.add(t);
        numOfVertex++;
    }

    //连接边
    public void addEdge(int start, int end) {
        graphMatrix[start][end] = 1;
        //加入下段代码则为无向图
         graphMatrix[end][start] = 1;
        degree[end]++;
    }

   //获取index节点下一个邻节点
   private  int getNoVisitedEdge(int index){
       for (int i = 0; i < vertexList.size() ; i++) {
           if (graphMatrix[index][i] == 1 ){

               return i;
           }
       }
       return - 1;
   }
    //显示图对应的矩阵
    public void showGraph() {
        for(int[] link : graphMatrix) {
            System.err.println(Arrays.toString(link));
        }
    }

    //深度优先遍历
    public void  matrixDFS( ){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < vertexList.size() ; i++) {//其实此循环可有可无,因为任意一个图经过深度优先从根节点出发都会访问到图中的每个顶点，但广度优先必须要

            if (!isVisited[i])
              matrixDFS(i,list);
        }

        System.out.println(String.join("-->",  list ));

        Arrays.fill(isVisited,false);

    }

   //深度优先遍历
   private void  matrixDFS( int index, List<String> list ){

       list.add(vertexList.get(index).toString());
       isVisited[index]=true;

       for (int i = 0; i < vertexList.size() ; i++) {
           if (graphMatrix[index][i] == 1 ){//是邻节点

               if (!isVisited[i]) matrixDFS(i,list);

           }
       }
       stack.push(index);//递归完成之后加入栈，实现反向，逆后序
   }


   //广度优先遍历
   public  void  matrixBFS(){

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        isVisited[0] = true;
        List<String> list = new ArrayList<>();

       list.add(vertexList.get(0).toString());

       while (queue.size() > 0) {

           int index = queue.poll();

           for (int i = 0; i < vertexList.size() ; i++) {
               if (graphMatrix[index][i] == 1 ){//是邻节点
                   if (!isVisited[i]) {
                       isVisited[i]=true;
                       list.add(vertexList.get(i).toString());
                       queue.offer(i);
                   }
               }
           }

       }
       System.out.println(String.join("-->", list));
       Arrays.fill(isVisited,false);
   }


   private  void  topologyDfs(int index){

       isVisited[index]=true;

       //在递归之前加入队列，前序拓扑排序
       // queue.offer(index);

       for (int i = 0; i < vertexList.size() ; i++) {
           if (graphMatrix[index][i] == 1 ){//是邻节点

               if (isCircle  ) throw new RuntimeException("Graph has circle");

               else if (!isVisited[i]){
                   topologyDfs(i);
               }

               else  if (circleStatus[i] == 0) {

                   isCircle = true;
                   return;
               }

           }
       }

       circleStatus[index] = 1;
       //在递归之后加入队列，后序拓扑排序
       queue.offer(index);
       stack.push(index);//递归完成之后加入栈，实现反向，逆后序
   }


    //有向图的拓扑排序--深度优先
    public  void topologyDfsSort(){

        stack.clear();

        topologyDfs(0);

        Arrays.fill(isVisited,false);


        StringJoiner joiner = new StringJoiner("-->");

        for (var  tVertex : queue) {
            joiner.add(vertexList.get(tVertex).toString());

        }
        System.out.println(joiner);
        System.out.println("逆拓扑排序");
        StringJoiner joinerReverse = new StringJoiner("-->");
        while (!stack.isEmpty()){

            joinerReverse.add(vertexList.get( stack.pop()).toString());
        }
        System.out.println(joinerReverse);
    }

    //有向图的拓扑排序--广度优先(Kahn算法)
    public  void  topologySort(){
        Queue<Integer> queue = new LinkedList<>();
        StringJoiner joiner = new StringJoiner("-->");
        int count = 0;

        for (int i = 0; i < degree.length; i++) {

            if (degree[i] == 0){
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()){
            int poll = queue.poll();
            count++;
            joiner.add(vertexList.get(poll).toString());

            for (int i = 0; i < vertexList.size() ; i++) {
                if (graphMatrix[poll][i] == 1 ){//是邻节点
                    if (--degree[i] == 0){
                        queue.offer(i);
                    }
                }
            }
        }


        if (count != vertexList.size()) {

            throw new RuntimeException("Graph has circle");
        }

        System.out.println(joiner);
    }

   //利用Trajan 算法求解有向图的强连通分量
    private void  tarjan(int index, Stack<Integer> stack,int[] dns,int[] low, List<ArrayList<T>> result){

        dns[index] = low[index] = stackNum++;

        isVisited[index] = true;

        stack.push(index);

        //该顶点的邻接点
        for (int i = 0; i < vertexList.size() ; i++) {

            if (graphMatrix[index][i] == 1 ) {//是邻节点

                if (dns[i] == -1) {

                    tarjan(i,stack, dns, low,result);

                    //递归完更新index与i 的父子关系即更新low值
                    low[index] = Math.min(low[index],low[i]);

                }
                else {

                    if (stack.contains(i)){//在栈里同样更新回溯的low[index]为dns[i]
                        low[index] = Math.min(low[index],dns[i]);
                        //因为如果是强连通回溯顶点必然会回溯到根节点，这样把回溯得节点置成根节点的low,这样回溯节点和根节点的low一样代表强连接,求强连通时下面代码也成立
                        //low[index] = Math.min(low[index],low[i]);
                    }
                }

            }
        }

        if (dns[index] == low[index]){ //index是强连通分量的根节点
            ArrayList<T> temp = new ArrayList<>();
            int j = -1;

            while(index != j){
                j = stack.pop();
                isVisited[j] = false;
                temp.add(vertexList.get(j));

            }
            result.add(temp);

        }
    }

    //利用Trajan算法求解无向图的桥
    private void  tarjan(int index, int father,int[] low,StringBuilder builderBridge){

         low[index] = stackNum++;

        //该顶点的邻接点
        for (int i = 0; i < vertexList.size() ; i++) {

            if (graphMatrix[index][i] == 1 ) {//是邻节点

                if (low[i] == -1) {

                    tarjan(i, index,  low,builderBridge);

                    //递归完更新index与i 的父子关系即更新low值
                    low[index] = Math.min(low[index],low[i]);
                    //如果存在low相等的说明这些顶点是连成环的
                    if (low[index] < low[i]){//表示index 肯定不在i所在的环中,就是index--i的边就是桥

                        builderBridge.append(vertexList.get(index)).append("--").append(vertexList.get(i)).append(";");
                    }

                }
                else {

                    if (i != father){//如果i已经访问过说明 index ,  i 在无向图中可以互相访问，更新index的low为i的low

                        low[index] = Math.min(low[index],low[i]);
                    }
                }

            }
        }

    }

    public void  tarjan(){

        Stack<Integer> s = new Stack<>();

        int[] dns = new int[numOfVertex];
        int[] low = new int[numOfVertex];

        Arrays.fill(dns,-1);
        Arrays.fill(low,-1);

        List<ArrayList<T>> result=new ArrayList<>();
        //for (int i = 0; i < vertexList.size() ; i++) {//其实此循环可有可无,因为任意一个图经过深度优先从根节点出发都会访问到图中的每个顶点，但广度优先必须要
           // if (dns[i] == -1)
                tarjan(0,s, dns, low, result);
        //}

        Arrays.fill(isVisited,false);

        System.out.println(result);
    }

    //使用tarjan算法求无向图的桥
    public  void  tarjanBridge(){


        int[] low = new int[numOfVertex];

        Arrays.fill(low,-1);
        stackNum=0;
        StringBuilder bufferBridge=new StringBuilder();//保存桥

        tarjan(0, 0,   low, bufferBridge);

        System.out.println(bufferBridge);
    }


    //使用Kosaraju算法求解有向图的强连通分量
    public  void kosarajus(){

        //1将有向图逆序
        Graph<T> reverseGraph =reverse();

        List<List<T>> result=new ArrayList<>();

        //2 dfs 原图

        matrixDFS();

        Arrays.fill(isVisited,false);

        // 3 检查栈中顶点是否被访问过若没被访问过，以该点为起点，对逆图进行深度优先遍历

        while (stack.size() > 0){
            int pop = stack.pop();

            if (!isVisited[pop]){
                List<T> sccList = new ArrayList<>();

                kosarajus(pop, sccList,reverseGraph);

                result.add(sccList);

            }

        }
        Arrays.fill(isVisited,false);
        System.out.println(result);
    }


    private  void  kosarajus(int index, List<T>  sccList, Graph<T> graph  ) {


        isVisited[index]=true;

        for (int i = 0; i < graph.vertexList.size(); i++) {//其实此循环可有可无,因为任意一个图经过深度优先从根节点出发都会访问到图中的每个顶点，但广度优先必须要

            if (graph.graphMatrix[index][i] == 1){

                if (!isVisited[i])
                    kosarajus(i,sccList,graph);

            }
        }
        sccList.add(graph.vertexList.get(index));
    }


    //反转有向图
    private Graph<T> reverse(){
        Graph<T> reverse= new Graph<>(numOfVertex);
        reverse.vertexList= (ArrayList<T>) vertexList.clone();
        for (int i = 0; i < graphMatrix.length; i++) {
            for (int j = 0; j < graphMatrix[i].length; j++) {
                 if (graphMatrix[i][j] == 1){
                     reverse.addEdge(j,i);
                 }
            }
        }

        return reverse;
    }

    public static void main(String[] args) {

        Graph<String> graph = new Graph<>(6);
             String[] Vertexs = {"A", "B", "C", "D", "E"};
           //String[] Vertexs = {"0", "1", "2", "3", "4", "5"};
        //循环的添加顶点
        for(String vertex: Vertexs) {
            graph.addVertex(vertex);
        }

        graph.addEdge(0, 1); // A-B
		graph.addEdge(0, 2); //
		graph.addEdge(1, 2); //
		graph.addEdge(1, 3); //
 		graph.addEdge(1, 4); //

//        graph.addEdge(0, 1); // A-B
//        graph.addEdge(1, 2); //
//        graph.addEdge(1, 3); //
//        graph.addEdge(2, 3); //
//        graph.addEdge(0, 4);

//        graph.addEdge(0, 1);
//        graph.addEdge(0, 2);
//        graph.addEdge(1, 3);
//        graph.addEdge(2, 3);
//        graph.addEdge(2, 4);
//        graph.addEdge(3, 0);
//        graph.addEdge(3, 5);
//        graph.addEdge(4, 5);

        graph.showGraph();

        System.out.println("强连通分量");
        graph.tarjan();
        System.out.println("kosarajuDfs强连通分量");
        graph.kosarajus();
        System.out.println("无向图的桥");
        graph.tarjanBridge();

        System.out.println("深度遍历");
         graph.matrixDFS(); // A->B->C->D->E [1->2->4->8->5->3->6-7-8]
        System.out.println();
        System.out.println("广度遍历");
         graph.matrixBFS();// A->B->C->D->E [1->2->3->4->5->6->7->8]
        System.out.println();
        System.out.println("深度优先拓扑排序");
        graph.topologyDfsSort();
        System.out.println();

        System.out.println("广度优先拓扑排序");
         graph.topologySort();

    }


    public void dfs() {
        // 从第一个顶点开始访问
        isVisited[0] = true;// 访问之后标记为true
        System.out.print(vertexList.get(0)+"-->");
        var  stack = new ArrayDeque<>();
        stack.push(0);// 将第一个顶点放入栈中
        while (!stack.isEmpty()) {
            // 找到栈当前顶点邻接且未被访问的顶点
            var  v = getNoVisitedEdge((Integer) stack.peek());
            if (v == -1) {// 如果当前顶点值为-1，则表示没有邻接且未被访问顶点，那么出栈顶点
                stack.pop();
            } else {// 否则访问下一个邻接顶点
                isVisited[v] = true;
                System.out.print(vertexList.get(v)+"-->");
                stack.push(v);
            }
        }
        // 搜索完毕，重置所有标记位
        for (int i = 0; i < vertexList.size(); i++) {
            isVisited[i] = false;
        }
    }
}
