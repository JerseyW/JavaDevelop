package Algorithm.BMAlgorithm;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * @author: Jerssy
 * @create: 2021-05-10 16:17
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: BM 算法
 *
 * KMP的匹配是从模式串的开头开始匹配的，而1977年，德克萨斯大学的Robert S. Boyer教授和J Strother Moore教授发明了一种新的字符串匹配算法：Boyer-Moore算法，简称BM算法。该算法从模式串的尾部开始匹配，且拥有在最坏情况下O(N)的时间复杂度。在实践中，比KMP算法的实际效能高。
 *，
 * "文本串"与"模式串"头部对齐，从尾部开始比较.BM算法大量使用在文本编辑器中
 *
 * BM算法定义了两个规则：
 *
 * 坏字符规则：当文本串中的某个字符跟模式串的某个字符不匹配时，我们称文本串中的这个失配字符为坏字符，此时模式串需要向右移动，
 *            移动的位数 = 坏字符在对应模式串中的位置 - 坏字符在模式串中上次出现的位置。此外，如果"坏字符"不包含在模式串之中，则最右出现位置为-1。
 * 好后缀规则：当字符失配时，后移位数 = 好后缀对应在模式串中的位置 - 好后缀在模式串上一次出现的位置，且如果好后缀在模式串中没有再次出现，则为-1。
 *
 * 每次后移这两个规则之中的较大值。这两个规则的移动位数，只与模式串有关，与原文本串无关。
 *
 *
 * 坏字符没出现在模式串中，这时可以把模式串移动到坏字符的下一个字符，继续比较，如下图：
 *
 *
 * 坏字符出现在模式串中，这时可以把模式串第一个出现的坏字符和母串的坏字符对齐，当然，这样可能造成模式串倒退移动
 *
 *
 * 好后缀规则移动模式串，shift（好后缀）分为三种情况：
 * 模式串中有子串匹配上好后缀，此时移动模式串，让该子串和好后缀对齐即可，如果超过一个子串匹配上好后缀，则选择最靠左边的子串对齐。
 *
 *
 * 模式串中没有子串匹配上后后缀，此时需要寻找模式串的一个最长前缀，并让该前缀等于好后缀的后缀，寻找到该前缀后，让该前缀和好后缀对齐即可。
 *
 *
 * 模式串中没有子串匹配上后后缀，并且在模式串中找不到最长前缀，让该前缀等于好后缀的后缀。此时，直接移动模式到好后缀的下一个字符。
 */
public class BMAlgorithm {
    public static void main(String[] args) {
        System.out.println(bMAlMatch("HERE IS A SIMPLE EXAMPLE","EXAMPLE" ));
        System.out.println(bMAlMatch("DABCDABDE","DABDE" ));
        System.out.println(bMAlMatch("BBC ABCGDABD FGGABCDABCDABDE","ABCDABC" ));
        System.out.println(bMAlMatch("EABCDD ABCDEABBCD","EABB" ));
        System.out.println(bMAlMatch("BBC ABCDAB ABCDABCDABDE","DAB" ));
        System.out.println(bMAlMatch("DAB","BBC ABCDAB ABCDABCDABDE" ));
         getMatchIndexMap("ABDEABDDEABCDDEABD");
         getMatchIndexMap("ABDADBBD");

    }

    public  static  int  bMAlMatch(String textString, String patternString){

        if (textString.length() < patternString.length()) return -1;

        int patternLen = patternString.length();
        int textLen = textString.length();

        int i = patternLen -1 ,j = i;

        HashMap<String, Integer> matchMap = getMatchIndexMap(patternString);

        while (i < textLen){

            char badCharacter;
            int  oldShiftLen = patternLen - j -1;//原来好的后缀最大长度，同时也记录了i 和 j 的前移动次数

            if ((badCharacter = textString.charAt(i)) != patternString.charAt(j)){

                //int  badIndex = patternString.lastIndexOf(badCharacter); //这里效率会低下，所以我们使用hashMap的哈希表来优化下，避免每次都去查找
                int  badIndex = matchMap.getOrDefault(String.valueOf(badCharacter),-1);

                int moveIndex = j - badIndex;

                j += oldShiftLen;

                i += oldShiftLen; //这两个注意，如果发生匹配失败，i 和 j 的索引还需要还原回去

                int goodIndex = 0,lastShiftIndex = j;

                if (oldShiftLen  > 0 ){ //存在好的后缀

                    //检查每个后缀字符串在模式串上次出现的位置，即寻找模式串的一个最长前缀,  目的是让该对应的前缀移动到好后缀的位置
                    while (oldShiftLen >= 0){
                        goodIndex = matchMap.getOrDefault(patternString.substring(j - oldShiftLen+1),-1);
                        //好后缀在就无需再次找了，因为后面都是好后缀长度比当前好后缀短的
                        if (goodIndex > -1 ){
                            //获得好后缀对应的索引位置
                            lastShiftIndex = j - oldShiftLen+1 ;

                            break;
                        }
                        oldShiftLen--;
                    }

                    moveIndex = Math.max(moveIndex,lastShiftIndex - goodIndex);

                }

                i = i + moveIndex ;

            }
            else {

                i--;
                j--;
            }

            //判断首字符与母串对应字符是否相等,为什么要加此条件？因为索引比模式串长度小1，当索引为0的时候 j =0 对应的字符还没有和母串匹配就被返回，这是不正确的
            if (j == 0 && patternString.charAt(0) == textString.charAt(i)) {
                return  i ;
            }
        }

        return  -1;
    }

    private static HashMap<String,Integer>  getMatchIndexMap(@NotNull String patternString){

        int j = 0; int len =patternString.length();
        HashMap<String,Integer> matchMap = new HashMap<>();

        //注意我们的BM 算法需要的是上一次出现的位置，即最后次出现的索引位置，如果有相同的字符则取最后一个的索引位置
        while (j < len -1){

            int finalJ = j;
            matchMap.compute(String.valueOf(patternString.charAt(j++)) ,(k, v)-> finalJ);

        }

        String lastStr;
        int lastIndex = len - 2; int skipIndex = 1; boolean isSkip;

        label: for (int m = len-2;m>=0;){
            for (int n = m-skipIndex;n>= 0;n--){
                isSkip = false;
                if (patternString.charAt(n) == patternString.charAt(m)){

                    if (patternString.substring(n, n + len - m).equals(lastStr = patternString.substring(lastIndex))){

                        matchMap.put(lastStr,n);
                        skipIndex = m - n;//找到一个对应前缀，后面肯定都是不符合下一个对应的前缀，所以下次从该前缀开始逆遍历，
                        lastIndex = --m;
                        isSkip = true;//由于是找最后一次出现的前缀，逆序下找到一个就跳出
                    }
                }

                if (n == 0) break label;//已经找完
                if (isSkip) continue label;
            }
             m--;
        }

        return matchMap;
    }

    private static final int SIZE = 256; // 全局变量或成员变量
    private static void generateBC(char[] b, int m, int[] bc) {
        for (int i = 0; i < SIZE; ++i) {
            bc[i] = -1; // 初始化bc
        }
        for (int i = 0; i < m; ++i) {
            int ascii = b[i]; // 计算b[i]的ASCII值

            bc[ascii] = i;
        }
    }
    public static int bm(String a1, String b1) {
        char[] a = a1.toCharArray();
        char[] b = b1.toCharArray();
        int[] bc = new int[SIZE]; // 记录模式串中每个字符最后出现的位置
        int n = a.length;
        int m =b.length;
        generateBC(b, m, bc); // 构建坏字符哈希表
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        generateGS(b, m, suffix, prefix);
        int i = 0; // j表示主串与模式串匹配的第一个字符
        while (i <= n - m) {
            int j;
            for (j = m - 1; j >= 0; --j) { // 模式串从后往前匹配
                if (a[i+j] != b[j]) break; // 坏字符对应模式串中的下标是j
            }
            if (j < 0) {
                return i; // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            }
            int x = j - bc[a[i+j]];
            int y = 0;
            if (j < m-1) { // 如果有好后缀的话

                y = moveByGS(j, m, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }
    // j表示坏字符对应的模式串中的字符下标; m表示模式串长度
    private static int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        int k = m - 1 - j; // 好后缀长度
        if (suffix[k] != -1) return j - suffix[k] +1;
        for (int r = j+2; r <= m-1; ++r) {
            if (prefix[m - r]) {
                return r;
            }
        }
        return m;
    }


    private static void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < m; ++i) { // 初始化
            suffix[i] = -1;
            prefix[i] = false;
        }
        for (int i = 0; i < m - 1; ++i) { // b[0, i]
            int j = i;
            int k = 0; // 公共后缀子串长度
            while (j >= 0 && b[j] == b[m-1-k]) { // 与b[0, m-1]求公共后缀子串
                --j;
                ++k;
                suffix[k] = j+1; //j+1表示公共后缀子串在b[0, i]中的起始下标

            }

            if (j == -1) prefix[k] = true; //如果公共后缀子串也是模式串的前缀子串
        }
    }
}
