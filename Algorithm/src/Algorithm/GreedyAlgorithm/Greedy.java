package Algorithm.GreedyAlgorithm;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-05-13 13:23
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 贪心算法
 *
 * 贪心算法介绍
 *
 * 贪婪算法(贪心算法)是指在对问题进行求解时，在每一步选择中都采取最好或者最优(即最有利)的选择，从而希望能够导致结果是最好或者最优的算法

 * 贪婪算法所得到的结果不一定是最优的结果(有时候会是最优解)，但是都是相对近似(接近)最优解的结果
 * 在对问题求解时，总是做出在当前看来是最好的选择。也就是说，不从整体最优上加以考虑，它所做出的仅仅是在某种意义上的局部最优解。
 *
 * 贪心算法使用场景
 * 1 当我们看到这类问题的时候，首先要联想到贪心算法：针对一组数据，我们定义了限制值和期望值，希望从中选出几个数据，在满足限制值的情况下，
 * 期望值最大
 * 2 我们尝试看下这个问题是否可以用贪心算法解决：每次选择当前情况下，在对限制值同等贡献量的情况下，对期望值贡献最大的数据
 * 3 贪心算法产生的结果是否是最优的。大部分情况下，举几个例子验证一下就可以了。严格地证明贪心算法的正确性，是非常复杂
 * 的，需要涉及比较多的数学推理。而且，从实践的角度来说，大部分能用贪心算法解决的问题，贪心算法的正确性都是显而易见的，也不需要严格的数学推导证
 * 明。

 * 三个算法解决问题的模型，都可以抽象成我们今天讲的那个多阶段决策最优解模型，而分治算法解决的问题尽管大部分也是最优解问题，但是，大部分都不能抽
 * 象成多阶段决策模型。
 * 回溯算法是个 “ 万金油 ” 。基本上能用的动态规划、贪心解决的问题，我们都可以用回溯算法解决。回溯算法相当于穷举搜索。穷举所有的情况，然后对比得到最
 * 优解。不过，回溯算法的时间复杂度非常高，是指数级别的，只能用来解决小规模数据的问题。对于大规模数据的问题，用回溯算法解决的执行效率就很低了。
 * 尽管动态规划比回溯算法高效，但是，并不是所有问题，都可以用动态规划来解决。能用动态规划解决的问题，需要满足三个特征，最优子结构、无后效性和重
 * 复子问题。在重复子问题这一点上，动态规划和分治算法的区分非常明显。分治算法要求分割成的子问题，不能有重复子问题，而动态规划正好相反，动态规划
 * 之所以高效，就是因为回溯算法实现中存在大量的重复子问题。
 * 贪心算法实际上是动态规划算法的一种特殊情况。它解决问题起来更加高效，代码实现也更加简洁。不过，它可以解决的问题也更加有限。它能解决的问题需要
 * 满足三个条件，最优子结构、无后效性和贪心选择性（这里我们不怎么强调重复子问题）。
 * 其中，最优子结构、无后效性跟动态规划中的无异。 “ 贪心选择性 ” 的意思是，通过局部最优的选择，能产生全局的最优选择。每一个阶段，我们都选择当前看起
 * 来最优的决策，所有阶段的决策完成之后，最终由这些局部最优解构成全局最优解。
 *
 *
 * 贪心算法存在问题：
 *
 * 1 不能保证求得的最后解是最佳的；
 *
 * 2 不能用来求最大或最小解问题；
 *
 * 3 只能求满足某些约束条件的可行解的范围。
 */
public class Greedy {

  /*  钱币找零问题
    这个问题在我们的日常生活中就更加普遍了。假设1元、2元、5元、10元、20元、50元、100元的纸币分别有c0, c1, c2, c3, c4, c5, c6张。现在要用这些钱来支付K元，至少要用多少张纸币？用贪心算法的思想，很显然，每一步尽可能用面值大的纸币即可。在日常生活中我们自然而然也是这么做的。
    在程序中已经事先将Value按照从小到大的顺序排好。
    */

    public static void main(String[] args) {
        //人民币面值集合
      int[] values = { 1, 2, 5, 10, 20, 50, 100 };
     //各种面值对应数量集合
      int[] counts = { 3, 1, 2, 1, 1, 3, 5 };
    //求335元人民币需各种面值多少张
      getNum(335, values, counts);


      knapsack( new float[] {2 ,2,4,5,8},new float[] {1000,3000,6000,7000,3000 },7);


        System.out.println();

        HashMap<String, HashSet<String>> radiosMamp = new HashMap<>();

        HashSet<String> key1 = new HashSet<>(Arrays.stream(new String[]{"北京", "上海", "天津"}).toList());
        HashSet<String> key2 = new HashSet<>(Arrays.stream(new String[]{"广州", "北京", "深圳"}).toList());
        HashSet<String> key3 = new HashSet<>(Arrays.stream(new String[]{"成都", "上海", "杭州"}).toList());
        HashSet<String> key4 = new HashSet<>(Arrays.stream(new String[]{ "上海", "天津"}).toList());
        HashSet<String> key5 = new HashSet<>(Arrays.stream(new String[]{ "杭州", "大连"}).toList());

        radiosMamp.put("K1",key1);
        radiosMamp.put("K2",key2);
        radiosMamp.put("K3",key3);
        radiosMamp.put("K4",key4);
        radiosMamp.put("K5",key5);

        System.out.println(minimumRadioCoverage(radiosMamp));
    }

    private  static  void getNum(int price , int[] values, int[] counts){
        int [] nums = new  int[counts.length];
        for(int j = values.length-1;j>= 0;j--){

            int c= Math.min(price/values[j],counts[j]);

            price = price - c*values[j];

            nums[j] = c;

        }
        for (int i = 0; i < values.length; i++) {
              if (nums[i] != 0) {
                  System.out.println("需要面额为" + values[i] + "的人民币" + nums[i] + "张");
              }
        }
    }


//  背包问题：有一个背包，背包容量是M=30。有3个物品，要求尽可能让装入背包中的物品总价值最大，但不能超过总容量物品是可以拆分的，那么此背包问题就可以用贪心算法来解决，因此可以拆分，使得背包空间得到充分利用

    private  static  void  knapsack(float[] goodsWeight, float[] goodValues, int knapsackMaxWeight) {

        float[] goodsX = new float[goodsWeight.length];
        Arrays.sort(goodsWeight);

        int weight = knapsackMaxWeight;
        float allPrice = 0;
        int i = 0;
        for (; i < goodsWeight.length; i++) {
            if (goodsWeight[i] <= weight) {
                goodsX[i] = 1;

                weight -= goodsWeight[i];
                allPrice += goodValues[i];

            } else break;
        }
        //如果可以装入，但是不能整个装入
        if (i < goodsWeight.length) {
            goodsX[i] = weight / goodsWeight[i];  //此时的goodsX[i]接着上面的goodsX[i]
            allPrice = goodsX[i] * goodValues[i];
        }

        for (i = 0; i < goodsWeight.length; i++) {
            if (goodsX[i] < 1) {
                break;
            } else {
                System.out.println("装入的重量为：" + goodsWeight[i] + "\t 价值为：" + goodValues[i]);
            }
        }

        if (goodsX[i] > 0 && goodsX[i] < 1) {  //能装入，但不能整个装入
            System.out.println("装入的重量为：" + goodsX[i] * goodsWeight[i] + "\t 效益为：" + goodValues[i] * goodsX[i] + "是原来的" + goodsX[i]);
        }

        System.out.println("一共装入重量" + knapsackMaxWeight + "\t 总收益为" + allPrice);
    }


  //假设存在如下表的需要付费的广播台，以及广播台信号可以覆盖的地区。 如何选择最少的广播台，让所有的地区都可以接收到信号
//
//    广播台           覆盖地区
//
//    K1              "北京", "上海", "天津"
//
//    K2              "广州", "北京", "深圳"
//
//    K3              "成都", "上海", "杭州"
//
//    K4              "上海", "天津"
//
//    K5              "杭州", "大连"


   private static  ArrayList<String>  minimumRadioCoverage (HashMap<String, HashSet<String>> radiosMap){

        HashSet<String> coverage = new HashSet<>();

        HashMap<String,Integer>  radiosNum = new HashMap<>();

        radiosMap.forEach((key,value)-> coverage.addAll(value));

        ArrayList<String>  result = new ArrayList<>();

        while (!coverage.isEmpty()){

            radiosMap.forEach((key,value)->{

                value.retainAll(coverage);

                radiosNum.put(key, value.size());//将覆盖的电台及覆盖地区数量放入集合
            });


            //贪心算法的思想，每次选择最大的电台覆盖地区
            Optional<Map.Entry<String, Integer>> maxRadios = radiosNum.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).stream().findFirst();

            if (maxRadios.isPresent()) {

                String key = maxRadios.get().getKey();
                result.add(key);
                radiosNum.remove(key);
                coverage.removeAll(radiosMap.remove(key));
            }

        }

        return result;
   }

}
