package Algorithm.KnightTour;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImagingOpException;
import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-05-22 18:02
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 骑士周游问题
 *
 *
 * 马踏棋盘游戏代码实现
 *
 * 马踏棋盘问题(骑士周游问题)实际上是图的深度优先搜索(DFS)的应用。
 *
 * 如果使用回溯（就是深度优先搜索）来解决，假如马儿踏了53个点，如图：走到了第53个，坐标（1,0），发现已经走到尽头，没办法，那就只能回退了，查看其他的路径，就在棋盘上不停的回溯…… ，思路分析+代码实现

 *
 *
 * 使用前面的游戏来验证算法是否正确。
 *
 * 心得：递归栈里如果有对引用数据类型的操作如删除，添加可能会造成ConcurrentModificationException 异常，解决：为每一个递归栈开辟空间避免冲突
 *
 */
public class KnightTour {

    private static final int FRAME_WIDTH = 600;//窗口长
    private static final int FRAME_HEIGHT = 600;//窗口宽

    private static final int KNIGHT_LENGTH = 6;//棋盘大小

    private  static  final  String HORSE_SRC = "Algorithm\\src\\Algorithm\\KnightTour\\horse.png";
    private  static  final  String HORSE_IS_VISITED_SRC = "Algorithm\\src\\Algorithm\\KnightTour\\isVisited.png";

    public static void main(String[] args) {

        new  KnightTourFrame();

    }


    /*
     * @param: [i, j, horseJPanel]
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/5/25 13:47
     * @description: 恢复棋盘最初的颜色
     */
    private static void setBackgroundColor(int i, int j, JPanel horseJPanel){

        if ((i + j) % 2 == 0) {

            horseJPanel. setBackground(Color.lightGray);
        } else {

            horseJPanel. setBackground(Color.WHITE);
        }
        horseJPanel.repaint();
    }


    private static class KnightTourFrame extends JFrame {

        private final KnightTourMap[][] map = new KnightTourMap[KNIGHT_LENGTH][KNIGHT_LENGTH];//棋盘数组

        private final boolean[] isVisited; //棋盘是否访问数组

        private JLabel jLabel;//显示统计步数控件

        private  int totalStep; //总步数

        private  boolean isAutoMove = false; //自动运行标志

        private final Point initialPoint;//初始化位置索引坐标

        private boolean isFinished = false;//结束标志

        public KnightTourFrame() {

            setSize(FRAME_WIDTH, FRAME_HEIGHT);

            setLocationRelativeTo(null);

            JPanel panel = new JPanel();

            JPanel topPanel = new JPanel();

            setResizable(false);
            setVisible(true);

            initialJPanel(topPanel,panel);

            isVisited = new boolean[KNIGHT_LENGTH * KNIGHT_LENGTH];

            initialPoint = new Point(KNIGHT_LENGTH/2,KNIGHT_LENGTH/2); //默认在棋盘中间放入马儿

            initialization(panel);

            addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        }

        /*
         * @param: [topPanel, panel]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 20:16
         * @description:初始化JPanel控件
         */
        private  void  initialJPanel(JPanel topPanel,JPanel panel){

            topPanel.setLayout(new GridLayout(1,3,10,10));
            topPanel.setSize(new Dimension(FRAME_WIDTH,40));

            jLabel = new JLabel("",JLabel.CENTER);

            JButton button = new JButton("重来");
            button.setVerticalTextPosition(JButton.CENTER);
            button.setPreferredSize(new Dimension(50,40));
            button.setMargin(new Insets(5,5,5,5));
            button.setRequestFocusEnabled(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JButton autoButton = new JButton("自动运行");
            autoButton.setVerticalTextPosition(JButton.CENTER);
            button.setPreferredSize(new Dimension(50,40));
            button.setMargin(new Insets(5,5,5,5));
            autoButton.setRequestFocusEnabled(false);
            autoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            jLabel.setForeground(Color.red);
            jLabel.setPreferredSize(new Dimension(50,40));

            topPanel.add(autoButton);
            topPanel.add(jLabel);
            topPanel.add(button);

            panel.setLayout(new GridLayout(KNIGHT_LENGTH, KNIGHT_LENGTH));

            add(topPanel,BorderLayout.BEFORE_FIRST_LINE);

            add(panel, BorderLayout.CENTER);

            button.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    clearKnightTourMap();
                }
            });

            autoButton.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {

                    if (isFinished) return;

                     isAutoMove = true;

                     int selected = getStepOfHorse(initialPoint.x, initialPoint.y, totalStep = Math.max(totalStep, 1));

                     exitGame(selected ==1 ?1:-1);

                }
            });
        }

        /*
         * @param: [panel]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:22
         * @description:初始化棋盘
         */
        private void initialization(JPanel panel) {

            for (int i = 0; i < KNIGHT_LENGTH; i++) {

                for (int j = 0; j < KNIGHT_LENGTH; j++) {

                    map[i][j] = new KnightTourMap(i, j);

                    panel.add(map[i][j]);
                }
            }

            if (KNIGHT_LENGTH < 5) {

                JOptionPane.showMessageDialog(KnightTourFrame.this,"棋盘格局太小,是无法找到合适的行走路线滴","警告",JOptionPane.WARNING_MESSAGE);
                isFinished = true;
            }

            getStepOfHorse(initialPoint.x, initialPoint.y, totalStep = 1);

        }


        /*
         * @param: [i, j, step]
         * @return: int
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:18
         * @description: 马踏棋盘核心方法，放置马儿，绑定鼠标事件，自动运行
         */
        private int getStepOfHorse(int i, int j, int step) {

            ArrayList<Point> horseStepList = nextSteps(i, j);

            isVisited[i * KNIGHT_LENGTH + j] = true; //标记该位置已经访问

            putHorse(map[i][j],i,j);

            jLabel.setText("Step: "+(totalStep = step));

            if (!isFinished){

                if (isAutoMove) {

                    optimalNextStepSort(horseStepList);
                    if (step < KNIGHT_LENGTH*KNIGHT_LENGTH) removeOldHorse(i,j, step);

                }

                for (Iterator<Point> iterator = horseStepList.iterator();iterator.hasNext();) {

                    Point point  = iterator.next();
                    if (!isVisited[point.x * KNIGHT_LENGTH + point.y]) {
                        if (!isAutoMove) {

                            map[point.x][point.y].setBackground(Color.BLUE);
                            addHorseMouseListener(map[point.x][point.y], i, j, point,horseStepList);

                        } else {

                             iterator.remove();

                             int selected = getStepOfHorse(point.x, point.y,  step + 1);

                             if (!isFinished && step < KNIGHT_LENGTH * KNIGHT_LENGTH){//回溯中

                                isVisited[i * KNIGHT_LENGTH + j] = false;

                             }

                             return  selected;
                        }
                    }
                }

                if (step == KNIGHT_LENGTH * KNIGHT_LENGTH) return 1;

                if (horseStepList.isEmpty()) return  0;
            }

            return -1;
        }


        /*
         * @param: [horseStepList]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 20:12
         * @description: 排序，获取该位置的下一步的下一步最少路径集合，减少回溯次数，使每次选择的都是最优路线
         */
        private void optimalNextStepSort(ArrayList<Point> horseStepList){

            horseStepList.sort(Comparator.comparingInt(o -> nextSteps(o.x, o.y).size()));
        }

        /*
         * @param: [key, i, j, step, point,horseStepList]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:23
         * @description: 给马儿下一步将要走的位置绑定鼠标事件
         */
        private  void  addHorseMouseListener(KnightTourMap key, int i, int j, Point point, ArrayList<Point> horseStepList){

            KnightTourMap component = map[point.x][point.y];

            key.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent event) {

                    if (!isFinished) {

                        removeOldKeyMouseListeners(horseStepList);
                        horseStepList.remove(point);
                        removeOldHorse(i,j, totalStep);

                        int selected = -1;
                        if (component.getBackground() == Color.BLUE &&!isVisited[point.x * KNIGHT_LENGTH + point.y]){

                            initialPoint.setLocation(point.x, point.y);//记录当前走的位置
                            selected = getStepOfHorse(point.x,point.y, totalStep+1);
                        }

                        clearCurrentKnightMapColor(horseStepList,component);

                        if ( selected == 1 || selected == 0) { //回溯结束并满足退出条件

                            exitGame(selected);
                        }
                    }
                }

                public void mouseEntered(MouseEvent e) {

                    if (component.getBackground() == Color.BLUE && !isVisited[point.x*KNIGHT_LENGTH + point.y]){
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                    else setCursor(Cursor.getDefaultCursor());
                }

                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
        }

        /*
         * @param: []
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:24
         * @description: 清除棋盘信息
         */
        private  void  clearKnightTourMap(){

            if (totalStep == 1) return;

            for (int i = 0; i < KNIGHT_LENGTH; i++) {

                for (int j = 0; j < KNIGHT_LENGTH; j++) {

                    if (map[i][j].getComponents().length !=0) {
                        map[i][j].removeAll();
                    }

                    setBackgroundColor(i,j, map[i][j]);
                    isVisited[i * KNIGHT_LENGTH + j] = false;
                }
            }

            isAutoMove = false;
            isFinished = false;

            getStepOfHorse(initialPoint.x = KNIGHT_LENGTH/2, initialPoint.y = KNIGHT_LENGTH/2, totalStep = 1);

        }

        /*
         * @param: [selected]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:24
         * @description: 退出游戏
         */
        private  void  exitGame(int selected){

            if (!isFinished){

                isFinished = true;
                if (selected == 0){

                    JOptionPane.showMessageDialog(KnightTourFrame.this,"很遗憾,你输了,一共走了" + totalStep + "步","Game Over",JOptionPane.INFORMATION_MESSAGE);

                }
                else if (selected == 1){

                    int exitNum = JOptionPane.showConfirmDialog(KnightTourFrame.this, "恭喜，你赢了！是否在来一次？", "Winner",JOptionPane.YES_NO_OPTION);

                    if (exitNum == 0 ) clearKnightTourMap();

                }
                else {

                    JOptionPane.showMessageDialog(KnightTourFrame.this,"此行走路线无法走通","错误路线",JOptionPane.ERROR_MESSAGE);
                    clearKnightTourMap();
                }
            }
        }

        /*
         * @param: [i, j]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:24
         * @description: 获取马儿下一步所需要走的位置集合
         */
        private  ArrayList<Point>  nextSteps(int i, int j){

            ArrayList<Point> horseStepList = new ArrayList<>();//为每个递归栈开辟存储马儿下一步位置的集合

            //八个方向
            nextSteps(j - 2,i - 1, j - 2 >= 0 && i - 1 >= 0,horseStepList);
            nextSteps(j - 1,i - 2, j - 1 >= 0 && i - 2 >= 0,horseStepList);
            nextSteps(j + 1,i - 2, j + 1 < KNIGHT_LENGTH && i - 2 >= 0,horseStepList);
            nextSteps(j + 2,i - 1, j + 2 < KNIGHT_LENGTH && i - 1 >= 0,horseStepList);
            nextSteps(j + 2,i + 1, j + 2 < KNIGHT_LENGTH && i + 1 < KNIGHT_LENGTH,horseStepList);
            nextSteps(j + 1,i + 2, j + 1 < KNIGHT_LENGTH && i + 2 < KNIGHT_LENGTH,horseStepList);
            nextSteps(j - 1,i + 2, j - 1 >= 0 && i + 2 < KNIGHT_LENGTH,horseStepList);
            nextSteps(j - 2,i + 1, j - 2 >= 0 && i + 1 < KNIGHT_LENGTH,horseStepList);

            return  horseStepList;
        }

        /*
         * @param: [horseStepList]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:25
         * @description: 清除已经访问的位置上绑定的鼠标事件
         */
        private  void  removeOldKeyMouseListeners(ArrayList<Point>  horseStepList){

            for (Point key: horseStepList) {

                KnightTourMap knightTourMap;
                if ((knightTourMap = map[key.x][key.y]).getMouseListeners().length > 0){

                    knightTourMap.removeMouseListener(knightTourMap.getMouseListeners()[0]);
                    setCursor(Cursor.getDefaultCursor());

                }
            }
        }

        /*
         * @param: [nextJ, nextI, isSelected,horseStepList]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:26
         * @description: 获取下一步集合
         */
        private  void nextSteps(int nextJ, int nextI, boolean isSelected,  ArrayList<Point> horseStepList){

            if (isSelected) {

                if (!isVisited[nextI * KNIGHT_LENGTH + nextJ]){

                    horseStepList.add(new Point(nextI, nextJ));
                }
            }
        }

        /*
         * @param: [horseStepList,component]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:26
         * @description: 点击下一步时候恢复当前被染蓝色位置棋盘的原来背景颜色
         */
        private  void  clearCurrentKnightMapColor(ArrayList<Point> horseStepList, KnightTourMap component) {

            if (horseStepList.size() > 0 ){

                for ( Point key : horseStepList) {

                    map[key.x][key.y].setBackground(component.getBackground());
                    map[key.x][key.y].setBounds(0,0,0,0);
                }

            }
        }

        /*
         * @param: [panel, i, j]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:27
         * @description: 棋盘网格上放置马儿
         */
        private  void  putHorse(KnightTourMap panel,int i,int j){

            if (panel.getComponents().length == 0){

                HorseJPanel horseJPanel = new HorseJPanel(HORSE_SRC);

                panel.add(horseJPanel, BorderLayout.CENTER);

                panel.setBounds(0, 0, 0, 0);

                setBackgroundColor(i, j, horseJPanel);
                panel.setBackground(horseJPanel.getBackground());

            }
        }

        /*
         * @param: [i,j, step]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:27
         * @description: 清除已经放置的马儿并设置该位置放入已访问图片，加入记录步数控件
         */
        private void removeOldHorse(int i,int j, int step){
            KnightTourMap key = map[i][j];
            if (key.getComponents().length == 1  ){

                key.remove(0);

                HorseJPanel isVisitedJPanel = new HorseJPanel(HORSE_IS_VISITED_SRC);
                JLabel label = new JLabel(String.valueOf(step));
                label.setForeground(Color.DARK_GRAY);
                label.setFont(new Font("宋体",Font.BOLD,13));
                key.add(isVisitedJPanel);
                key.add(label);
                setBackgroundColor(i,j,key);
                isVisitedJPanel.setBackground(key.getBackground());

            }
        }
    }

    private  static  class  KnightTourMap extends  JPanel {

        public KnightTourMap(int i, int j) {

            setBackgroundColor(i,j,this);
            setBounds(0,0,0,0);
        }
    }

    private  static  class HorseJPanel extends JPanel{

        Image icon;

        private final int  maxHeight;

        private final int maxWidth;

        private  int scalingWidth;

        private  int scalingHeight;

        public  HorseJPanel(String src){

            icon = new ImageIcon(src).getImage();

            int srcWidth = icon.getWidth(null);

            if (srcWidth < 0)  throw new ImagingOpException("图片加载失败") ;

            int srcHeight = icon.getHeight(null);

            maxWidth =  FRAME_WIDTH / KNIGHT_LENGTH;
            maxHeight = FRAME_HEIGHT / KNIGHT_LENGTH;

            scalingWidth = srcWidth;
            scalingHeight = srcHeight;

            initialImage(srcWidth);

        }

        /*
         * @param: [srcWidth]
         * @return: void
         * @author: Jerssy
         * @dateTime: 2021/5/25 13:28
         * @description: 缩放图片长度和宽度，使之自适应棋盘网格
         */
        private  void initialImage(int srcWidth){

            float scalingFactor;

            if (scalingWidth > maxWidth/2) {
                scalingFactor = (float) srcWidth/(float) maxWidth;
                scalingWidth = (int) ((scalingWidth-maxWidth/2)/scalingFactor);

                scalingHeight = Math.round((float)scalingHeight/ scalingFactor);
            }
            if (scalingWidth < maxWidth/2) scalingWidth = maxWidth/2;

            int srcHeight = scalingHeight;

            if (scalingHeight > maxHeight/2) {

                scalingFactor = (float) srcHeight /(float) maxHeight;
                scalingHeight = (int) ((scalingHeight-maxHeight/2)/scalingFactor);
                scalingWidth = Math.round((float)scalingWidth/scalingFactor);

            }
            if (scalingHeight < maxHeight / 2) scalingHeight = maxHeight/2;

        }

        protected   void  paintComponent(Graphics g ) {

            super.paintComponent(g);
            setBounds((maxWidth-scalingWidth)/2 ,(maxHeight-scalingHeight)/2 ,scalingWidth,scalingHeight);
            g.drawImage(icon,0,0,scalingWidth , scalingHeight, this);
        }
    }
}
