package Algorithm.Recursion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Jerssy
 * @version V1.0
 * @Description 模拟八皇后游戏
 * @create 2021-02-28 15:33
 *
 * repaint()方法是重绘，而validate()是重载，一般来说，从一个容器中删除某个组件需要调用repaint()，而把某个组件添加到某一容器中，则需调用validate()
 * 当你改变一个会影响它们外观的属性时调用repaint();当你改变一个会影响它们宽度/高度的属性时， revalidate()被调用
 *
 * invalidate()将容器标记为无效。意味着内容在某种程度上是错误的，必须重新布局。但它只是一种标志/旗帜。有可能以后必须刷新多个无效容器。
 *
 * validate()执行重新布局。这意味着要求所有大小的无效内容，并且LayoutManager将所有子组件的大小设置为正确的值。

 * revalidate()只是两者的总和。它将容器标记为无效并执行容器的布局。
 *
 */
public class EightQueenGame {

    private static final int FRAME_WIDTH=600;//窗口长
    private static final int FRAME_HEIGHT=600;//窗口宽
    private static final int MAX_QUEEN_LENGTH=8;//棋盘最大容量

    public static void main(String[] args) {

        new QueenFrame();
    }

   static class QueenFrame extends Frame {

       private final QueenPanelMap[][] queenMap=new QueenPanelMap[MAX_QUEEN_LENGTH][MAX_QUEEN_LENGTH];//棋盘数组

       private  int  totalNum=0;//记录步数

       private  int lastLine=0;//上一行

       private boolean isChecked=false;//是否校验过了

       private  int checkedLen=0;//校验次数

       public  QueenFrame() {

          setSize(FRAME_WIDTH,FRAME_HEIGHT);
          setLocationRelativeTo(null);//窗口居中

          JPanel queenPanel = new JPanel();

          //棋盘初始化
          initialization(queenPanel);

          queenPanel.setLayout(new GridLayout(MAX_QUEEN_LENGTH,MAX_QUEEN_LENGTH));

          add(queenPanel,BorderLayout.CENTER);
          setResizable(false);
          setVisible(true);

          //关闭窗口事件
          addWindowListener(new WindowAdapter() {
              @Override
              public void windowClosing(WindowEvent e) {
                  System.exit(0);
              }
           });
       }

      //初始化棋盘
       private  void  initialization(JPanel queenPanel) {

           for (int i=0;i<MAX_QUEEN_LENGTH;i++){
               for (int j=0;j<MAX_QUEEN_LENGTH;j++){
                   queenMap[i][j]=new QueenPanelMap(i,j);
                   queenMap[i][j].unConflict=0;
                   int finalI = i;
                   int finalJ = j;

                   //为每个棋格绑定鼠标点击事件
                   queenMap[i][j].addMouseListener(new MouseAdapter() {
                       @Override
                       public void mouseClicked(MouseEvent e) {
                           setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//设置鼠标为小手型
                           putQueens(e, finalI, finalJ);
                       }
                   });

                   queenPanel.add(queenMap[i][j]);
               }
           }
       }

      //放置皇后
      private  void putQueens(MouseEvent e, int n , int j) {

         JPanel  panel=(JPanel) e.getComponent();

         if (panel.getComponents().length==0) {//如果当前棋格里没有放皇后

             QueenImage queenImage = new QueenImage();
             queenImage.setBackground(panel.getBackground());
             panel.add(queenImage,BorderLayout.CENTER);

             panel.validate();//添加了皇后组件重载此棋格容器

             lastLine=n;
             isChecked=false;
             checkedLen=0;
            if (!checkLegal(n, j)){
                System.out.println("游戏结束");
                JOptionPane.showMessageDialog(QueenFrame.this,"很遗憾,你输了");
                clearQueenMap();
                return;
            }

            queenMap[n][j].unConflict=1;
            totalNum++;

            if (totalNum==MAX_QUEEN_LENGTH) {
                //1 5 8 6 3 7 2 4
                int selectI = JOptionPane.showConfirmDialog(QueenFrame.this, "恭喜，你赢了！是否在来一次？", "提示",JOptionPane.YES_NO_OPTION);

                if (selectI == 0) {
                    clearQueenMap();
                }
            }
        }

         else {//再次点击删除已放置的皇后

            panel.remove(panel.getComponents()[0]);
            panel.repaint();
            queenMap[n][j].unConflict=0;
            totalNum--;
        }

     }

      //清空棋盘
      private  void clearQueenMap() {

          for (int i=0;i<MAX_QUEEN_LENGTH;i++){
              for (int j=0;j<MAX_QUEEN_LENGTH;j++){
                  if (queenMap[i][j].getComponents().length!=0) {
                      queenMap[i][j].remove(queenMap[i][j].getComponents()[0]);
                      queenMap[i][j].repaint();
                  }
                  queenMap[i][j].unConflict=0;
              }
          }

          totalNum=0;
      }

       //校验皇后放的位置合法性
       private  boolean checkLegal(int n, int j)  {

           if (n ==MAX_QUEEN_LENGTH||n<0) {

               return true;
           }

           for (int k = 0; k < MAX_QUEEN_LENGTH; k++) {
               //校验当前行所有的列及当前列所有的行
               if (lastLine==n){
                   if (queenMap[n][k].unConflict == 1||queenMap[k][j].unConflict==1) {
                       return  false;
                   }
               }
               else {
                   //校验对角线
                   if (Math.abs(j-k)==Math.abs(lastLine-n)) {
                       if (queenMap[n][k].unConflict == 1) {
                           return false;
                       }
                   }
               }
           }

           //校验当前行后面的行①
           checkedLen++;
           if (!isChecked&&!checkLegal(n+1,j)) {
               return false;
           }
           //这里定位到①位置递归开始的位置，否则②位置又从n=7的位置开始向上递归，这是完全不必要的因为这些位置已经在①步骤检查过了
           isChecked=true;

           return checkLegal(MAX_QUEEN_LENGTH-checkedLen - 1, j);//②校验当前行前面的行
       }
  }

   private  static class  QueenPanelMap extends  JPanel {

        private  int unConflict=0;

        public QueenPanelMap(int i,int j) {

            if ((j+i) %2==0) {
                setBackground(Color.WHITE);
            }
            else setBackground(Color.BLUE);

        }
  }

   private static class  QueenImage extends  JPanel {

        Image imageIcon;
        private static int dstWidth=0;
        private static int dstHeight=0;
        private static int maxWidth=0;
        private static int maxHeight=0;

        public QueenImage() {

            imageIcon= new ImageIcon("Algorithm\\src\\Algorithm\\Recursion\\queen.png").getImage();

            int srcWidth= imageIcon.getWidth(null);
            if (srcWidth<0) {
                throw  new RuntimeException("加载图片失败");
            }

            int srcHeight= imageIcon.getHeight(null);

            maxWidth=FRAME_WIDTH/MAX_QUEEN_LENGTH;
            maxHeight=FRAME_HEIGHT/MAX_QUEEN_LENGTH;
            dstWidth=srcWidth;
            dstHeight=srcHeight;
            float k;

            if (srcWidth>maxWidth){
                dstWidth=maxWidth;
                k=(float)srcWidth/(float)maxWidth;
                dstHeight=Math.round((float)srcHeight/k);
            }
            else if (srcWidth<maxWidth/2) {
                dstWidth=maxWidth/2;
            }
            srcHeight=dstHeight;
            if (srcHeight>maxHeight){
                dstHeight=maxHeight;
                k=(float)srcHeight/(float)maxHeight;
                dstWidth=Math.round((float)dstWidth/k);
            }
            else if (srcHeight < maxHeight / 2) {
                dstHeight=maxHeight;
            }
        }

       public void paintComponent(Graphics g){

          super.paintComponent(g);
          setBounds((maxWidth-dstWidth)/2 ,(maxHeight-dstHeight)/2 ,dstWidth,dstHeight);
          g.drawImage(imageIcon,0,0,dstWidth , dstHeight, this);

      }
   }
}
