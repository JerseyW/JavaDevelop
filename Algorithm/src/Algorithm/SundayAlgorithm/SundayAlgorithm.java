package Algorithm.SundayAlgorithm;

import java.util.HashMap;

/**
 * @author: Jerssy
 * @create: 2021-05-10 11:22
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: Sunday算法
 *
 *  Sunday算法由Daniel M.Sunday在1990年提出，它的思想跟BM算法很相似：
 *
 * 只不过Sunday算法是从前往后匹配，在匹配失败时关注的是文本串中参加匹配的最末位字符的下一位字符。
 *  1 如果该字符没有在模式串中出现则直接跳过，即移动位数 = 匹配串长度 + 1；
 *  2 否则，其移动位数 = 模式串中最右端的该字符到末尾的距离+1
 *
 *  1 2 可以直接概括为模式串.length - 模式串.lastIndexOf(c)
 *
 * 假定现在要在文本串"substring searching algorithm"中查找模式串"search"。
 *
 * 1. 刚开始时，把模式串与文本串左边对齐：
 * substring searching algorithm
 * search
 * ^
 * 2. 结果发现在第2个字符处发现不匹配，不匹配时关注文本串中参加匹配的最末位字符的下一位字符，即标粗的字符 i，因为模式串search中并不存在i，所以模式串直接跳过一大片，向右移动位数 = 匹配串长度 + 1 = 6 + 1 = 7，从 i 之后的那个字符（即字符n）开始下一步的匹配，如下图：
 *
 * substring searching algorithm
 * 　　　  search
 * 　　　　^
 * 3. 结果第一个字符就不匹配，再看文本串中参加匹配的最末位字符的下一位字符，是'r'，它出现在模式串中的倒数第3位，于是把模式串向右移动3位（r 到模式串末尾的距离 + 1 = 2 + 1 =3），使两个'r'对齐，如下：
 * substring searching algorithm
 * 　　　　  search
 * 　　　　　　　^

 * 4. 匹配成功。
 *
 * 回顾整个过程，我们只移动了两次模式串就找到了匹配位置，缘于Sunday算法每一步的移动量都比较大，效率很高。完。
 *
 *
 */
public class SundayAlgorithm {

    public static void main(String[] args) {

        System.out.println(sundayMatch("BBC ABCDABD ABCDABCDABDE","DABDE" ));
        System.out.println(sundayMatch("DABCDABDE","DABDE" ));
        System.out.println(sundayMatch("BBC ABCDABD ABCDABCDABDE","ABCDABD" ));
        System.out.println(sundayMatch("BBC ABCDAB ABCDABCDABDE","ABCDABD" ));
        System.out.println(sundayMatch("BBC ABCDAB ABCDABCDABDE","DAB" ));
        System.out.println(sundayMatch("ABCDAB ABCDABCDABDE","ABCDAB ABCDABCDABDE" ));
        System.out.println(sundayMatch("BBC EABCDABD ABCDEABBCDABDE","EABB" ));
    }

    public  static int sundayMatch (String textString, String patternString){

        if ( textString.length() < patternString.length()) return -1;

        int i = 0,j = 0;

        int patternLen = patternString.length();
        int textLen = textString.length();

        HashMap<Character, Integer> matchMap = getMatchIndexMap(patternString);

        while (i < textLen  ){

            if (textString.charAt(i) == patternString.charAt(j)){
                i++;
                j++;

            }
            else  {

                 char c = textString.charAt(patternLen-j+i);//文本串中参加匹配的最末位字符的下一位字符

                 i = i -j + patternLen - matchMap.getOrDefault(c,-1);//移动距离

                 j = 0;
            }

            if (j == patternLen) {
                return  i-j ;
            }
        }

        return -1;
    }

    private static HashMap<Character,Integer> getMatchIndexMap(String patternString){

        int j = 0;
        HashMap<Character,Integer> matchMap = new HashMap<>();
        //注意需要的是上一次出现的位置，即最后次出现的索引位置，如果有相同的字符则取最后一个的索引位置
        while (j < patternString.length()-1){

            int finalJ = j;
            matchMap.compute(patternString.charAt(j++) ,(k, v)-> finalJ);

        }

        return matchMap;
    }
}
