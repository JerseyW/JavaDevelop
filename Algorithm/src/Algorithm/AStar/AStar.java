package Algorithm.AStar;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * @author: Jerssy
 * @create: 2021-05-28 11:01
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: A*算法
 *Dijkstra是一种盲目的搜索算法，它会遍历整个解空间然后找到其中的最优解。Dijkstra算法可以准确的找到最优解，但它是以时间为代价的，其时间复杂度为O(n^2)。
 * 我们仔细观察Dijkstra算法会发现，它每次得到的解都是距原点最近的那条路径，这样当它遍历到目标点时，得到的路径自然是最优路径
 * A*算法是对Dijkstra算法的优化和改造
 * 我们都不需要非得求最优解（也就是最短路径）。在权衡路线规划质量和执行
 * 效率的情况下，我们只需要寻求一个次优解就足够了。那如何快速找出一条接近于最短路线的次优路线
 *
 * 公式表示为： f(n)=g(n)+h(n),
 *
 * 其中 f(n) 是从初始点经由节点n到目标点的估价函数，
 *
 * g(n) 是在状态空间中从初始节点到n节点的实际代价，
 *
 * h(n) 是从n到目标节点最佳路径的估计代价。
 *1)当h(n)=0时，A星算法就转化为了Dijkstra算法，即：每次只考虑到源节点最近的节点。
2)当g(n)=0时，A星算法就转化为了BFS算法，即：每次只考虑到目的节点最近的节点
 *
 * 遍历到某个顶点的时候，从起点走到这个顶点的路径长度是确定的，我们记作 g(i) （ i 表示顶点编号）。但是，从这个顶点到终点的路径长度，我们是未知
 * 的。虽然确切的值无法提前知道，但是我们可以用其他估计值来代替。
 *
 * 这里我们可以通过这个顶点跟终点之间的直线距离， ，来近似地估计这个顶点跟终点的路径长度（注意：路径长度跟直线距离是两个概
 * 念）。我们把这个距离记作h(i)（i表示这个顶点的编号），专业的叫法是启发函数（heuristic function）
 *
 * 可通过一个简单的距离计算公式，那就是曼哈顿距离（Manhattan distance）。曼哈顿距离是两点之间横纵坐标的距离之和。计算的过
 * 程只涉及加减法、符号位反转
 *
 *通过两者之和 f(i)=g(i)+h(i) ，来判断哪个顶点该最先出队列。 这里f(i)的专业叫法是估价函数（evaluation function）。
 *
 *
 * 它跟 Dijkstra 算法的代码实现，主要有 3 点区别：
 * 优先级队列构建的方式不同。A*算法是根据f值（也就是刚刚讲到的f(i)=g(i)+h(i)）来构建优先级队列，而Dijkstra算法是根据dist值（也就是刚刚讲到的g(i)）
 * 来构建优先级队列；
 * A*算法在更新顶点dist值的时候，会同步更新f值；
 * 循环结束的条件也不一样。Dijkstra算法是在终点出队列的时候才结束，A*算法是一旦遍历到终点就结束。
 *
 *
 * 伪代码
 * Best_First_Search()
 *
 * 　　{
 * 　　　　Open = [起始节点];
 *
 * 　　　　Closed = [];
 *
 * 　　　　while ( Open表非空 )
 *
 * 　　　　{
 * 　　　　　　从Open中取得一个节点X, 并从OPEN表中删除.
 *
 * 　　　　　　if (X是目标节点)
 *
 * 　　　　　　{
 * 　　　　　　　　求得路径PATH;
 *
 * 　　　　　　　　返回路径PATH;
 *
 * 　　　　　　}
 *
 * 　　　　　　for (每一个X的子节点Y)
 *
 * 　　　　　　{
 * 　　　　　　　　if( Y不在OPEN表和CLOSE表中 )
 *
 * 　　　　　　　　{
 * 　　　　　　　　　　求Y的估价值;
 *
 * 　　　　　　　　　　并将Y插入OPEN表中;　//还没有排序
 *
 * 　　　　　　　　}
 *
 * 　　　　　　　　else if( Y在OPEN表中 )
 *
 * 　　　　　　　　{
 * 　　　　　　　　　　if( Y的估价值小于OPEN表的估价值 )
 *
 * 　　　　　　　　　　　　更新OPEN表中的估价值;
 *
 * 　　　　　　　　}
 *
 * 　　　　　　　　else //Y在CLOSE表中
 *
 * 　　　　　　　　{
 * 　　　　　　　　　　if( Y的估价值小于CLOSE表的估价值 )
 *
 * 　　　　　　　　　　{
 * 　　　　　　　　　　　　更新CLOSE表中的估价值;
 *
 * 　　　　　　　　　　　　从CLOSE表中移出节点, 并放入OPEN表中;
 *
 * 　　　　　　　　　　}
 *
 * 　　　　　　　　}
 *
 * 　　　　　　　　将X节点插入CLOSE表中;
 *
 * 　　　　　　　　按照估价值将OPEN表中的节点排序;
 *
 * 　　　　　　}　//end for
 *
 * 　　　　}　//end while
 *
 * 　　}　//end func

 *
 *
 * 一个用于记录可被訪问的结点，成为Open表。一个是记录已訪问过的结点，称为Close表。
 * 这两个表决定了算法的结束：条件是终于结点在Close表中(找到路径)或Open表为空(找不到了路径)。
 * 邻结点规则：
 * (1) 当邻结点不在地图中，不增加Open表；
 * (2) 当邻结点是障碍物，不增加Open表。
 * (3) 当邻结点在Close表中。不增加Open表。
 * (4) 当邻结点不在Open中，增加Open表，设该邻结点的父节点为当前结点；
 * (5) 当邻结点在Open表中，我们须要做个比較:假设邻结点的G值>当前结点的G值+当前结点到这个邻结点的代价，那么改动该邻结点的父节点为当前的结点(由于在Open表中的结点除了起点。都会有父节点)，改动G值=当前结点的G值+当前结点到这个邻结点的代价
 *
 */


public class AStar {

    public final static int BAR = 1; // 障碍值
    public final static int PATH = 2; // 路径
    public final static int DIRECT_VALUE = 10; // 横竖移动代价
    public final static int OBLIQUE_VALUE = 14; // 斜移动代价

    Queue<Node> openList = new PriorityQueue<>(); // 优先队列
    List<Node> closeList = new ArrayList<>();


    public static void main(String[] args) {

        int[][] maps = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }
        };
        printMap(maps);
        MapInfo info=new MapInfo(maps,maps[0].length, maps.length,new Node( 1, 5), new Node(13, 2));
        new AStar().start(info);
        System.out.println();
        printMap(maps);

    }

    public static void printMap(int[][] maps)
    {
        for (int[] map : maps) {
            for (int i : map) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
    /**
     * 开始算法
     */
    public void start(MapInfo mapInfo)
    {
        if(mapInfo==null) return;
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.start);
        moveNodes(mapInfo);
    }

    /**
     * 移动当前结点
     */
    private void moveNodes(MapInfo mapInfo)
    {

        while (!openList.isEmpty())
        {
            if (isPointInClose(mapInfo.end.point))
            {
                drawPath(mapInfo.maps, mapInfo.end);
                break;
            }
            Node current = openList.poll();
            closeList.add(current);
            addNeighborNodeInOpen(mapInfo,current);
        }
    }

    /**
     * 在二维数组中绘制路径
     */
    private void drawPath(int[][] maps, Node end)
    {
        if(end==null||maps==null) return;
        System.out.println("总代价：" + end.G);
        while (end != null)
        {
            Point c = end.point;
            maps[c.y][c.x] = PATH;
            end = end.parent;
        }
    }

    /**
     * 添加所有邻结点到open表
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current)
    {
        int x = current.point.x;
        int y = current.point.y;
        // 左
        addNeighborNodeInOpen(mapInfo,current, x - 1, y, DIRECT_VALUE);
        // 上
        addNeighborNodeInOpen(mapInfo,current, x, y - 1, DIRECT_VALUE);
        // 右
        addNeighborNodeInOpen(mapInfo,current, x + 1, y, DIRECT_VALUE);
        // 下
        addNeighborNodeInOpen(mapInfo,current, x, y + 1, DIRECT_VALUE);
        // 左上
        addNeighborNodeInOpen(mapInfo,current, x - 1, y - 1, OBLIQUE_VALUE);
        // 右上
        addNeighborNodeInOpen(mapInfo,current, x + 1, y - 1, OBLIQUE_VALUE);
        // 右下
        addNeighborNodeInOpen(mapInfo,current, x + 1, y + 1, OBLIQUE_VALUE);
        // 左下
        addNeighborNodeInOpen(mapInfo,current, x - 1, y + 1, OBLIQUE_VALUE);
    }

    /**
     * 添加一个邻结点到open表
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int x, int y, int value)
    {
        if (canAddNodeToOpen(mapInfo,x, y)) //能否放入Open列表
        {
            Node end=mapInfo.end;
            Point point = new Point(x, y);
            int G = current.G + value; // 当前结点的G值+当前结点到这个邻结点的代价
            Node child = findNodeInOpen(point);
            if (child == null)//不open列表中
            {
                int H=calcH(end.point,point); // 计算H值
                if(isEndNode(end.point,point))
                {
                    child=end;
                    child.parent=current;
                    child.G=G;
                    child.H=H;
                }
                else
                {
                    child = new Node(point, current, G, H);
                }
                openList.add(child);
            }
            else if (child.G > G) //当邻结点在Open表中，邻结点的G值>当前结点的G值+当前结点到这个邻结点的代价,并将Y插入OPEN表中;
            //那么改动该邻结点的父节点为当前的结点(由于在Open表中的结点除了起点。都会有父节点)，改动G值=当前结点的G值+当前结点到这个邻结点的代价
            {
                child.G = G;
                child.parent =  current;
                openList.add(child);
            }
        }
    }

    /**
     * 从Open列表中查找结点
     */
    private Node findNodeInOpen(Point point)
    {
        if (point == null || openList.isEmpty()) return null;
        for (Node node : openList)
        {
            if (node.point.equals(point))
            {
                return node;
            }
        }
        return null;
    }


    /**
     * 计算H的估值：“曼哈顿”法，坐标分别取差值相加:曼哈顿距离是两点之间横纵坐标的距离之和
     */
    private int calcH(Point end,Point point)
    {
        return Math.abs(end.x - point.x) + Math.abs(end.y - point.y);
    }

    /**
     * 判断结点是否是最终结点
     */
    private boolean isEndNode(Point end,Point point)
    {
        return end.equals(point);
    }

    /**
     * 判断结点能否放入Open列表
     */
    private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y)
    {
        // 是否在地图中
        if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight) return false;
        // 判断是否是不可通过的结点
        if (mapInfo.maps[y][x] == BAR) return false;
        // 判断结点是否存在close表
        return !isPointInClose(x, y);
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isPointInClose(Point point)
    {
        return point!=null&&isPointInClose(point.x, point.y);
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isPointInClose(int x, int y)
    {
        if (closeList.isEmpty()) return false;
        for (Node node : closeList)
        {
            if (node.point.x == x && node.point.y == y)
            {
                return true;
            }
        }
        return false;
    }


    private static class MapInfo {
        public int[][] maps; // 二维数组的地图
        public int width; // 地图的宽
        public int hight; // 地图的高
        public Node start; // 起始结点
        public Node end; // 最终结点

        public MapInfo(int[][] maps, int width, int high, Node start, Node end)
        {
            this.maps = maps;
            this.width = width;
            this.hight = high;
            this.start = start;
            this.end = end;
        }
    }
    private static class Node implements Comparable<Node> {

        public Point point; // 坐标
        public Node parent; // 父结点
        public int G; // G：是个准确的值，是起点到当前结点的代价
        public int H; // H：是个估值，当前结点到目的结点的估计代价

        public Node(int x, int y)
        {
            this.point = new Point(x, y);
        }

        public Node(Point point,  Node parent, int g, int h)
        {
            this.point = point;
            this.parent = parent;
            G = g;
            H = h;
        }

        @Override
        public int compareTo(@NotNull Node o)
        {
            if (G + H > o.G + o.H)
                return 1;
            else if (G + H < o.G + o.H) return -1;
            return 0;
        }
    }
}
