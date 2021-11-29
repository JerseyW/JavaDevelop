package Algorithm.DivideAndConquer;

/**
 * @author: Jerssy
 * @create: 2021-05-03 11:40
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 分治算法
 *
 * 分治算法介绍
 *
 * 分治法是一种很重要的算法。字面上的解释是“分而治之”，就是把一个复杂的问题分成两个或更多的相同或相似的子问题，再把子问题分成更小的子问题……直到最后子问题可以简单的直接求解，原问题的解即子问题的解的合并。这个技巧是很多高效算法的基础，如排序算法(快速排序，归并排序)，傅立叶变换(快速傅立叶变换)……
 *
 * 分治算法可以求解的一些经典问题
 *
 * 1二分搜索 2大整数乘法  3 棋盘覆盖  4 合并排序 5 快速排序  6线性时间选择 7 最接近点对问题 8 循环赛日程表 9 汉诺塔
 *
 *
 *分治算法的基本步骤

 *  1 分治法在每一层递归上都有三个步骤：
 *
 *  2 分解：将原问题分解为若干个规模较小，相互独立，与原问题形式相同的子问题
 *
 *  3 解决：若子问题规模较小而容易被解决则直接解，否则递归地解各个子问题
 *
 *  4 合并：将各个子问题的解合并为原问题的解。
 *
 *
 *  分治算法能解决的问题，一般需要满足下面这几个条件：
 * 1 原问题与分解成的小问题具有相同的模式；
 * 2 原问题分解成的子问题可以独立求解，子问题之间没有相关性，这一点是分治算法跟动态规划的明显区别，等我们讲到动态规划的时候，会详细对比这两种
 * 算法；
 * 3 具有分解终止条件，也就是说，当问题足够小时，可以直接求解；
 * 4 可以将子问题合并成原问题，而这个合并操作的复杂度不能太高，否则就起不到减小算法总体复杂度的效果了
 *
 *
 *
 * 汉诺塔游戏的演示和思路分析:
 *
 * A 如果是有一个盘， A->C
 *
 * B 如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的盘 2. 上面的盘

 * 1 先把 最上面的盘 A->B
 *
 * 2 把最下边的盘 A->C
 *
 * 3 把B塔的所有盘 从 B->C
 *
 *
 *
 *
 *设n个盘子的移动次数为T(n)
 *
 * T(n)=2T(n-1)+1
 * T(1)=1
 *
 * 所以Hanoi塔算法的时间复杂度为O(2^n)。
 */
public class DivideAndConquer {

    public static void main(String[] args) {

        hanoiProblem(3, 'A', 'B', 'C');

    }


    /* 
     * @param: [discNum, discA, discB, discC] 
     * @return: void
     * @author: Jerssy
     * @dateTime: 2021/5/3 16:47
     * @description:
     */
    public static void   hanoiProblem(int discNum,char discA,char discB,char discC){

        if (discNum == 1) {

            System.out.println("the 1 disc move from "+discA+"-->"+discC);
        }
        else {

            hanoiProblem(discNum-1, discA, discC, discB);

            System.out.println("the "+discNum+" disc move from "+discA+"-->"+discC);

            hanoiProblem(discNum-1, discB, discA, discC);

        }

    }

}
