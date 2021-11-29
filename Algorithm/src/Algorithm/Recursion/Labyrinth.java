package Algorithm.Recursion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 递归回溯迷宫问题
 * @create 2021-02-23 15:07
 *
 * 回溯法的基本思想：
 * 对一个包括有很多结点，每个结点有若干个搜索分支的问题，把原问题分解为对若干个子问题求解的算法。
 * 当搜索到某个结点、发现无法再继续搜索下去时，就让搜索过程回溯（即退回）到该结点的前一结点，继续搜索这个结点的其他尚未搜索过的分支；
 * 如果发现这个结点也无法再继续搜索下去时，就让搜索过程回溯到这个结点的前一结点继续这样的搜索过程；
 * 这样的搜索过程一直进行到搜索到问题的解或搜索完了全部可搜索分支没有解存在为止。

 *
 * 思考: 如何求出最短路径
 *
 */
public class Labyrinth {

    public static void main(String[] args) {

        new LabyrinthFrame();
    }

    private  static  class  LabyrinthFrame extends Frame{

        private static final  int ROW=8;//迷宫地图的行
        private static final  int COLUMN=8;//迷宫地图的列

        private final LabyrinthMap[][] map=new LabyrinthMap[ROW][COLUMN];
        private final JPanel circlePanel;//小球组件
        private  final ArrayList<LabyrinthMap> list= new ArrayList<>();

        private LabyrinthMap exitJPanel;

        private  boolean  isFind=false;

        public  LabyrinthFrame() {
            setSize(500,400);
            setLocationRelativeTo(null);
            JPanel jpUp = new JPanel();
            JPanel jpBut = new JPanel();

            for (int i=0;i<ROW;i++){
                for (int j=0;j<COLUMN;j++){
                    map[i][j]=new LabyrinthMap();
                    jpUp.add(map[i][j]);
                }
            }

            //设置墙
            setBlocked();

            //设置3个出口
            setEnters(6,7);
            setEnters(7,2);
            setEnters(0,6);

            //初始化小球
            circlePanel=new Circle();

            map[1][1].add(circlePanel,0);

            jpUp.setLayout(new GridLayout(ROW,COLUMN,1,1));

            add(jpUp,BorderLayout.CENTER);

            JButton findButton = new JButton("find");
            jpBut.add(findButton);
            JButton clearButton = new JButton("clear");
            jpBut.add(clearButton);

            add(jpBut,BorderLayout.SOUTH);

            setVisible(true);
            setResizable(false);

            //添加事件监听
            findButton.addActionListener(e -> findPath());
            clearButton.addActionListener(e -> clearPath());

            //关闭窗口
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        }

        //设置墙和挡板
        private  void setBlocked(){
            //设置迷宫的墙--1为墙
            for (int i=0;i<COLUMN;i++){
                map[0][i].setBlocked(1);

                map[COLUMN-1][i].setBlocked(1);
                map[0][i].setBackground(Color.RED);
                map[COLUMN-1][i].setBackground(Color.RED);
            }
            for (int j=0;j<ROW;j++){
                map[j][0].setBlocked(1);
                map[j][ROW-1].setBlocked(1);
                map[j][0].setBackground(Color.RED);
                map[j][ROW-1].setBackground(Color.RED);
            }

            //挡板
            map[3][1].setBlocked(1);
            map[3][2].setBlocked(1);
            map[3][1].setBackground(Color.RED);
            map[3][2].setBackground(Color.RED);

        }

        //设置出口
        private void setEnters(int enters, int exit) {

            map[enters][exit].setBlocked(0);
            map[enters][exit].setUnblocked(0);
            map[enters][exit].setBackground(Color.ORANGE);
            map[enters][exit].isExitPanel=true;

            list.add(map[enters][exit]);
        }

        //清除路径
        private void clearPath(){

            for (int i=0;i<ROW;i++){

                for (int j=0;j<COLUMN;j++){
                    if (map[i][j].getBackground()!=Color.RED){
                        map[i][j].remove(circlePanel);
                        map[i][j].unSelectedCell();
                        map[i][j].setUnblocked(0);
                    }
                    if (map[i][j].isExitPanel) {
                        map[i][j].setBackground(Color.ORANGE);
                    }

                }
            }

            map[1][1].add(circlePanel);
            map[1][1].invalidate();
            isFind=false;

        }

        //获取随机出口
        private LabyrinthMap getRandomExit(){

            return list.get(new Random().nextInt(list.size()));
        }

        //查找路径
        private  void findPath(){

            exitJPanel= !isFind ?getRandomExit():exitJPanel;

            if (findPath(1,1)){
                exitJPanel.add(circlePanel);
                exitJPanel.setBackground(Color.GREEN);
                exitJPanel.invalidate();
                isFind=true;
                System.out.println("小球找到出路");
            }

            else {
                System.out.println("死在里面");
                isFind=false;
            }
        }

        private boolean  findPath(int row, int col) {
            if (isFind||map[row][col]==exitJPanel ) {//出口
                return true;
            }

           else {

                if (map[row][col].unblocked==0&&map[row][col].blocked!=1) { //如果当前这个点还没有走过，且不是墙
                     map[row][col].setUnblocked(2);//设置该点已走过
                     if (findPath(Math.min(row+1, ROW-1),col)){//向下走
                         map[row][col].selectedCell();
                         return true;
                     }
                     else if (findPath(row,Math.min(col+1, COLUMN-1))){//向右走
                        map[row][col].selectedCell();
                        return true;
                    }

                    else if (findPath(Math.max(row-1, 0),col)){//向上走
                        map[row][col].selectedCell();
                        return true;
                    }
                    else if (findPath(row,Math.max(col-1, 0))){//向左走
                        map[row][col].selectedCell();
                        return true;
                    }
                    else {//改点走过但是是死路
                        map[row][col].setUnblocked(3);
                        return false;
                    }
                 //返回时死路或者已经走过的路或者是墙
                } else return false;
           }
        }
    }

    static class  LabyrinthMap extends  JPanel {

        private int blocked=0;//1代表墙
        private int unblocked=0;// 0 为未走过，2 可以走,3已走过的路，是死路

        private  boolean isExitPanel=false;//是出口组件

        public LabyrinthMap() {

           setBackground(Color.gray);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
        }

        public void setBlocked(int blocked) {
            this.blocked = blocked;
        }

        public void setUnblocked(int unblocked) {
            this.unblocked = unblocked;
        }

        public void selectedCell(){
             setBackground(Color.green);
             repaint();
        }

        public void unSelectedCell(){
            setBackground(Color.gray);
            repaint();
        }
    }

    //小球
    static  class  Circle extends JPanel {

        public void paintComponent(Graphics g){

            setBounds(10,3,100,100);
            setBackground(Color.gray);
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(Color.pink);
            g.fillOval(0,0,35,35);
        }
    }
}
