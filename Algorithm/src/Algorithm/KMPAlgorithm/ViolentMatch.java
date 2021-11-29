package Algorithm.KMPAlgorithm;

/**
 * @author: Jerssy
 * @create: 2021-05-08 18:31
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: kmp算法
 *
 *  ，暴力匹配每次移动一个，kmp算法，每次移动j- k（j之前的最大相同前缀后缀）？ 因为我们需要将前缀相同的值位置移动到后缀相同值的位置。前缀相同位置---后缀相同位置之间的都已经比较了无需再次比较，所以从后缀相同的值位置开始匹配。如果没有最大相同前缀后缀即K为0,就移动j个位置呗；不难吧
 *
 *  当匹配失败时，j要移动的下一个位置k。存在着这样的性质：最前面的k个字符和j之前的最后k个字符是一样的。
 *
 * 如果用数学公式来表示是这样的
 *
 * P[0 ~ k-1] == P[j-k ~ j-1]
 *
 *
 * 流程：
 * 1 假设现在文本串S匹配到 i 位置，模式串P匹配到 j 位置
 * 2 如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++，继续匹配下一个字符；
 * 3 如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]。此举意味着失配时，模式串P相对于文本串S向右移动了j - next [j] 位。
 *      换言之，当匹配失败时，模式串向右移动的位数为：失配字符所在位置 - 失配字符对应的next 值（next 数组的求解会在下文的3.3.3节中详细阐述），即移动的实际位数为：j - next[j]，且此值大于等于1。
 *
 *
 * 小结：
 * 1 如果模式串中存在相同前缀和后缀，即pj-k pj-k+1, ..., pj-1 = p0 p1, ..., pk-1，那么在pj跟si失配后，让模式串的前缀p0 p1...pk-1对应着文本串si-k si-k+1...si-1，而后让pk跟si继续匹配。
 * 2 之前本应是pj跟si匹配，结果失配了，失配后，令pk跟si匹配，相当于j 变成了k，模式串向右移动j - k位。
 * 3 因为k 的值是可变的，所以我们用next[j]表示j处字符失配后，下一次匹配模式串应该跳到的位置。换言之，失配前是j，pj跟si失配时，用p[ next[j] ]继续跟si匹配，相当于j变成了next[j]，所以，j = next[j]，等价于把模式串向右移动j - next [j] 位。
 * 4 而next[j]应该等于多少呢？next[j]的值由j 之前的模式串子串中有多大长度的相同前缀后缀所决定，如果j 之前的模式串子串中（不含j）有最大长度为k的相同前缀后缀，那么next [j] = k。
 *
 */
public class ViolentMatch {

    public static void main(String[] args) {

        System.out.println(violentMatch("BBC ABCDAB ABCDABCDABDE","ABCDABD" ));
        System.out.println(kmpMatch("BBC ABCDAB ABCDABCDABDE","ABCDABD" ));
        System.out.println(kmpMatch("BBC ABCDAB ABCDABCDABDE","DAB" ));
        System.out.println(kmpMatch("DAB","BBC ABCDAB ABCDABCDABDE" ));
        System.out.println(kmpMatch("BBC EABCDABD ABCDEABBCDABDE","EABB" ));
    }


    public  static  int violentMatch(String textString, String patternString) {

        if ( textString.length() < patternString.length()) return -1;

        int j = 0;
        int i = 0;
        while (i < textString.length()&& j < patternString.length()) {

            if (textString.charAt(i) == patternString.charAt(j)){
                i++;
                j++;
            }
            else {

                 i = i - j +1;
                 j = 0;
            }


        }

        if (j == patternString.length()) {
            return  i-j;
        }

        return -1;
    }

    public static int  kmpMatch(String textString, String patternString){

        if ( textString.length() < patternString.length()) return -1;

        int j = 0;
        int i = 0;
        int[] next = new int[patternString.length()];
        next[0] = -1;
        getNext(next,patternString);
        while (i < textString.length() && j < patternString.length()){

            if (j == -1|| textString.charAt(i) == patternString.charAt(j)){
                i++;
                j++;
            }
            else {
                j = next[j];
            }

        }
        if (j == patternString.length()) {
            return  i-j;
        }

        return -1;
    }


    private static void getNext(int[] next, String childStr){

        int j = 0;
        int k = -1;
        while (j < childStr.length() - 1){

            if (k == -1 || childStr.charAt(k) == childStr.charAt(j)){


                if (childStr.charAt(++k) != childStr.charAt(++j)){
                    next[j] = k;
                }
                else  next[j] = next[k];

            }

            else k = next[k];

        }
    }
}
