package Algorithm.DynamicProgramming;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-05-03 17:25
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 动态规划算法
 *
 * 动态规划算法介绍

 * 1 动态规划(Dynamic Programming)算法的核心思想是：将大问题划分为小问题进行解决，从而一步步获取最优解的处理算法
 *
 * 2 动态规划算法与分治算法类似，其基本思想也是将待求解问题分解成若干个子问题，先求解子问题，然后从这些子问题的解得到原问题的解。
 *
 * 3 与分治法不同的是，适合于用动态规划求解的问题，经分解得到子问题往往不是互相独立的。 ( 即下一个子阶段的求解是建立在上一个子阶段的解的基础上，进行进一步的求解 )
 *
 * 4 动态规划可以通过填表的方式来逐步推进，得到最优解.

 *一般是用动态规划来解决最优问题。而解决问题的过程----多阶段决策最优解模型
 *
 * 1. 最优子结构
 * 最优子结构指的是，问题的最优解包含子问题的最优解。反过来说就是，我们可以通过子问题的最优解，推导出问题的最优解。如果我们把最优子结构，对应到
 * 我们前面定义的动态规划问题模型上，那我们也可以理解为，后面阶段的状态可以通过前面阶段的状态推导出来。
 * 2. 无后效性
 * 无后效性有两层含义，第一层含义是，在推导后面阶段的状态的时候，我们只关心前面阶段的状态值，不关心这个状态是怎么一步一步推导出来的。第二层含义
 * 是，某阶段状态一旦确定，就不受之后阶段的决策影响。无后效性是一个非常 “ 宽松 ” 的要求。只要满足前面提到的动态规划问题模型，其实基本上都会满足无后
 * 效性。
 * 3. 重复子问题
 * 这个概念比较好理解。前面一节，我已经多次提过。如果用一句话概括一下，那就是，不同的决策序列，到达某个相同的阶段时，可能会产生重复的状态。
 *
 *
 */
public class DynamicProgramming {

    //对于一组不同重量、不可分割的物品，我们需要选择一些装入背包，在满足背包最大重量限制的前提下，背包中物品总重量的最大值是多少呢

    static  int  goodNum = 5;
    static int[] goodsWeight = new int[] {2 ,2,4,5,8};
    static  int  maxWeight = 0;
    static  int knapsackMaxWeight = 10;

    static  boolean[][] isVisitedKnapsack = new boolean[goodNum][knapsackMaxWeight+1];

    static  int[] goodsValues = {1000,3000,6000,7000,3000 };

    static  int maxWeightValue = 0;

    //递归回溯求解背包最大重量
    public  static  int  getKnapsackWeight(int currentGoodNum,int  weight) {

         if (currentGoodNum == goodNum || weight >= knapsackMaxWeight){

             return Math.max(weight,maxWeight);
         }

          maxWeight = getKnapsackWeight( currentGoodNum+1, weight); // 决策背包是否能放入下一个weight重量的物品

         //设置背包里当前物品已经访问
         isVisitedKnapsack[currentGoodNum ][weight] = true;
         //如果当前背包物品重量和---若还可以继续放入物品,比如已经放了物品3的情况下决策是否能继续放物品4，物品2 的情况下，能不能放物品4和物品3
         int currentWeight = weight + goodsWeight[currentGoodNum];
         if (currentWeight <= knapsackMaxWeight){

              if (isVisitedKnapsack[currentGoodNum][currentWeight]) {
                   return maxWeight;
              }

             maxWeight = getKnapsackWeight(currentGoodNum+1, currentWeight); // 背包里已经存在的物品重量+当前物品重量


         }

         return maxWeight;
    }

    /*对于一组不同重量、不同价值、不可分割的物品， 将某些物品
    装入背包，在满足背包最大重量限制的前提下，背包中可装入物品的总价值最大是多少呢？*/

    public static int  getMaxValueOfGoodsInKnapsack(int currentGoodNum, int weight, int goodValue){

          if (weight == knapsackMaxWeight || currentGoodNum == goodNum ) {

              return Math.max(goodValue,maxWeightValue);
          }


           maxWeightValue = getMaxValueOfGoodsInKnapsack(currentGoodNum+1, weight,goodValue);

           //设置背包里当前物品已经访问
           isVisitedKnapsack[currentGoodNum][weight] = true;
            int currentWeight = weight + goodsWeight[currentGoodNum];
           if (currentWeight <= knapsackMaxWeight){

               if (isVisitedKnapsack[currentGoodNum][currentWeight]) return maxWeightValue;

               maxWeightValue= getMaxValueOfGoodsInKnapsack(currentGoodNum+1, currentWeight,goodValue+goodsValues[currentGoodNum]);
           }

        return  maxWeightValue;
    }

    public static int knapsack2(int[] items, int n, int w) {
        boolean[] states = new boolean[w+1]; // 默认值false
        int maxWeight = 0;

        states[0] = true; // 第一行的数据要特殊处理，可以利用哨兵优化
        states[items[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划
            for (int j = w-items[i]; j >=0; --j) {//把第i个物品放入背包
                //在前一个的基础上决策放
                if (states[j]){
                    states[j+items[i]] = true;
                }
            }
        }


        for (int i = w; i >0; i--  ) { // 输出结果
            if (states[i]) {
               maxWeight = i;
               break;
            }

        }

        return maxWeight;
    }

    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w+1];//保存背包物品状态，值为价值
        for (int i = 0; i < n; ++i) { // 初始化states
            for (int j = 0; j < w+1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        states[0][weight[0]] = value[0];
        for (int i = 1; i < n; ++i) { //动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第i个物品--上个规划背包物品的价值更大，直接采用上一个规划
                if (states[i-1][j] >= 0) states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= w-weight[i]; ++j) { // 选择第i个物品
                if (states[i-1][j] >= 0) {
                    int v = states[i-1][j] + value[i];
                    if (v > states[i][j+weight[i]]) {
                        states[i][j+weight[i]] = v;
                    }

                }
            }
        }
      // 找出最大值
        int maxvalue = -1;

        for (int j = 0; j <= w; ++j) {
            if (states[n-1][j] > maxvalue) {
                maxvalue = states[n-1][j];

            }
        }



        return maxvalue;
    }


    public  static void  knapsack4(int[] goodWeights,int[] goodsValues,int maxWeight){

        int goodNum = goodWeights.length;

        int[][] knapsackValues = new int[goodNum][maxWeight+1];

        for (int i = 1; i < goodNum; i++) { //物品层数

            for (int j = 1; j <=maxWeight ; j++) { //动态的背包大小
                 if (j >= goodWeights[0])   knapsackValues[0][j] = goodsValues[0]; //定义背包初始状态为第一个物品的价值

                    if (j >= goodWeights[i]) {

                        //goodsValues[i] 当前物品价值  knapsackValues[i-1][j] 上一层规划装入的前i-1个物品最大价值；knapsackValues[i-1][j-goodWeights[i]] 装入当前物品后剩余空间装入的最大物品价值

                        knapsackValues[i][j] = Math.max(knapsackValues[i - 1][j], goodsValues[i] + knapsackValues[i - 1][j - goodWeights[i]]);
                    }
                    else knapsackValues[i][j] = knapsackValues[i - 1][j];

            }

        }

        int j = knapsackValues[--goodNum].length-1;

        int maxKnapsackWeight = 0;
        //从最右下角开始回溯，发现当前n个物品最佳组合价值== 前n-1个物品最佳组合价值说明当前n没有放入背包，反之放入了背包，则从背包去除当前物品重量继续回溯
        while (j >=0&& goodNum>=0) {
            int end =  knapsackValues[goodNum][j];
            if (end >0 ) {
                maxKnapsackWeight= Math.max(end,maxKnapsackWeight);
                if (goodNum -1 >=0) {
                    int current = knapsackValues[goodNum-1][j];
                    if ( current!= end  ){

                        System.out.printf("第%d个商品放入到背包\n", goodNum );
                        j -= goodWeights[goodNum];
                    }
                }
                else System.out.printf("第%d个商品放入到背包\n", goodNum );

            }
            goodNum--;
        }
        System.out.println("最大价值为："+maxKnapsackWeight);
    }


    public static int repeatKnapsack(int goodNum, int[] goodWeights, int[] goodsValues, int maxWeight) {

        int[] dp = new int[maxWeight + 1];

        for (int i = 0; i <  goodNum; i++) {

               if (goodWeights[i] <= maxWeight) {//超过最大值的肯定无法放入了


                   //这里不能正序？？--其实我们使用一维数组来当做二维数组来使用；每一层物品，就对应着一个一维数组；下一层规划则复用上一层的一维数组，即在上一层的规划基础上更新本层的物品价值
                   //如果我们正序遍历，假如该物品为0即对应第一个物品，重量为2 价值为1000；在任意背包大小下只能放入物品0，因为此时只有一个物品0；
                   //动态规划每一个物品的放入依赖上一层规划的结果，而本层的不同背包重量下物品放入互相独立不收影响。
                   //但事实上我们是一维数组后面不同背包重量下放入的物品 会依赖前一背包重量，造成物品价值的重复计算。
                   for (int j = maxWeight; j > 0; j--) {

                       if (j >= goodWeights[i]) {

                           if (dp[j] < dp[j - goodWeights[i]] + goodsValues[i]) {
                               dp[j] = dp[j - goodWeights[i]] + goodsValues[i];

                           }
                       }
                   }
               }
            }

        return dp[maxWeight];

    }

//    3                            3                            3                            3
//    1    5                       4    8                       4    8                       4    8
//    8    4    3                  8    4    3                  12   12  11                  12   12   11
//    2    6    7    9             2    6    7    9             2    6    7    9             14   18   19   20
//    6    2    3    5    1        6    2    3    5    1        6    2    3    5    1        20   20   22   25   21
    public static void  minNumberInRotateArray(int[][] n){

        int max = 0;
        int[][] dp = new int[n.length][n.length];
        dp[0][0] = n[0][0];
        for(int i=1;i<n.length;i++){
            for(int j=0;j<=i;j++){
                if(j==0){
                    //如果是第一列，直接跟他上面数字相加
                    dp[i][j] = dp[i-1][j] + n[i][j];
                }else{
                    //如果不是第一列，比较他上面跟上面左面数字谁大，谁大就跟谁相加，放到这个位置
                    dp[i][j] = Math.max(dp[i-1][j-1], dp[i-1][j]) + n[i][j];
                }
                max = Math.max(dp[i][j], max);
            }
        }

        System.out.println(max);
    }


    //求解最长公共子序列
 /*   我们从 a[0] 和 b[0] 开始，依次考察两个字符串中的字符是否匹配。
    如果a[i]与b[j]互相匹配，我们将最大公共子串长度加一，并且继续考察a[i+1]和b[j+1]。
    如果a[i]与b[j]不匹配，最长公共子串长度不变，这个时候，有两个不同的决策路线：
    删除a[i]，或者在b[j]前面加上一个字符a[i]，然后继续考察a[i+1]和b[j]；
    删除b[j]，或者在a[i]前面加上一个字符b[j]，然后继续考察a[i]和b[j+1]。
    反过来也就是说，如果我们要求 a[0…i] 和 b[0…j] 的最长公共长度 max_lcs(i, j) ，我们只有可能通过下面三个状态转移过来：
            (i-1, j-1, max_lcs)，其中max_lcs表示a[0…i-1]和b[0…j-1]的最长公共子串长度；
            (i-1, j, max_lcs)，其中max_lcs表示a[0…i-1]和b[0…j]的最长公共子串长度；
            (i, j-1, max_lcs)，其中max_lcs表示a[0…i]和b[0…j-1]的最长公共子串长度。*/


    public static String   maximumCommonSubsequence(String str1, String str2) {

        int len1 = str1.length();
        int len2 = str2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {

            for (int j = 1; j <= len2; j++) {

                boolean isEqual = str1.charAt(i - 1) == str2.charAt(j - 1);

                if (isEqual) {

                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

                }
            }
        }

       //回溯公共子序列
        int i = len1;
        int j = len2;
        StringBuilder sb = new StringBuilder();
        while (i > 0 && j > 0) {

            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                sb.append(str1.charAt(i - 1));
                i--;
                j--;

            } else if (dp[i][j - 1] >= dp[i - 1][j]) {
                j--;

            } else {
                i--;
            }
        }

        return sb.reverse().toString();
    }


    //最大连续公共字串-- 要求在母串中连续地出现
    public static List<String> lcs(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int result = 0;     //记录最长公共子串长度
        LinkedList<String> strList = new LinkedList<>();

        int[][] c = new int[len1+1][len2+1];
        for (int i = 1; i <= len1; i++) {
            for( int j = 1; j <= len2; j++) {
                boolean isEqual = str1.charAt(i-1) == str2.charAt(j-1);
                   if (isEqual) {
                      c[i][j] = c[i-1][j-1] + 1;

                      if ( c[i][j] >= result ) {
                        result = c[i][j];
                        String substring = str1.substring(i - result, i );
                        if (!strList.contains(substring)){

                            strList.addFirst(substring);
                        }
                     }

                  } else {
                     c[i][j] = 0;

                 }
            }
        }


        return   strList.stream().takeWhile(e->e.length() >= strList.getFirst().length()).toList();
    }


    public static void main(String[] args) {
        //为什么是0不是goodsWeight[0]--因为考虑可能背包最大重量太小连第一个物品都装不下，其实也可以这么理解，背包里的放入的物品在实时变化，我们需要给不同背包状态对应的物品重量初始化默认重量
        System.out.println(getKnapsackWeight(0, 0));

        for (boolean[] booleans : isVisitedKnapsack) {
            Arrays.fill(booleans, false);
        }
        System.out.println(getMaxValueOfGoodsInKnapsack(0, 0, 0));

        System.out.println(knapsack2(goodsWeight, goodNum, knapsackMaxWeight));
        System.out.println(knapsack3(goodsWeight,goodsValues, goodNum, knapsackMaxWeight));



        knapsack4(goodsWeight,goodsValues,knapsackMaxWeight);
        System.out.println(repeatKnapsack(goodNum,goodsWeight,goodsValues , knapsackMaxWeight));


        int[] []  arr = new int[][]{{3 },{1,5},{8,4,3},{2,6,7,9},{6,2,3,5,1}};

        minNumberInRotateArray(arr);

         System.out.println(lcs("abcbcbcef", "abcbced"));
        System.out.println(maximumCommonSubsequence("ABCBDAB", "BDCABA"));

    }
}



