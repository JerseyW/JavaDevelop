package Algorithm.SlideWindow;

import org.testng.annotations.Test;

import java.util.*;

/**
 * @author: Jerssy
 * @create: 2021-05-30 17:42
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 滑动窗口算法
 *滑动窗口算法
 *
 * Sliding window algorithm is used to perform required operation on specific window size of given large buffer or array.
 *
 * 滑动窗口算法是在给定特定窗口大小的数组或字符串上执行要求的操作。
 *
 * This technique shows how a nested for loop in few problems can be converted to single for loop and hence reducing the time complexity.
 *
 * 该技术可以将一部分问题中的嵌套循环转变为一个单循环，因此它可以减少时间复杂度。
 *
 *
 * 使用模式：数组或者字符串，求解结果是具有某种性质的子数组(连续)或者子串(连续)
 * 需要维护一个或者一组与窗口相关联的状态变量，有效的降低时间复杂度
 *
 * 1我们在字符串 S 中使用双指针中的左右指针技巧，初始化 left = right = 0，把索引闭区间 [left, right] 称为一个「窗口」。
 * 2我们先不断地增加 right 指针扩大窗口 [left, right]，直到窗口中的字符串符合要求（包含了 T 中的所有字符）。
 * 3 此时，我们停止增加 right，转而不断增加 left 指针缩小窗口 [left, right]，直到窗口中的字符串不再符合要求（不包含 T 中的所有字符了）。同时，每次增加 left，我们都要更新一轮结果。
 * 重复第 2 和第 3 步，直到 right 到达字符串 S 的尽头。
 *
 * 非固定大小的滑动窗口，可以简单地写出如下伪码框架：
 *  string s, t;
 *     // 在 s 中寻找 t 的「最小覆盖子串」
 *     int left = 0, right = 0;
 *     string res = s;
 *
 *     while(right < s.size()) {
 *         window.add(s[right]);
 *         right++;
 *         // 如果符合要求，说明窗口构造完成，移动 left 缩小窗口
 *         while (window 符合要求) {
 *             // 如果这个窗口的子串更短，则更新 res
 *             res = minLen(res, window);
 *             window.remove(s[left]);
 *             left++;
 *         }
 *     }
 *     return res;
 *
 *     但是，对于固定窗口大小，可以总结如下：
 *    // 固定窗口大小为 k
 *     string s;
 *     // 在 s 中寻找窗口大小为 k 时的所包含最大元音字母个数
 *     int  right = 0;
 *     while(right < s.size()) {
 *         window.add(s[right]);
 *         right++;
 *         // 如果符合要求，说明窗口构造完成，
 *         if (right>=k) {
 *             // 这是已经是一个窗口了，根据条件做一些事情
 *            // ... 可以计算窗口最大值等
 *             // 最后不要忘记把 right -k 位置元素从窗口里面移除
 *         }
 *     }
 *     return res;
 */
public class SlideWindow {


    public static void main(String[] args) {
        String s = "hellotvshhell8o8sworld";
        System.out.println(lengthOfLongestSubstring(s));
    }


    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int len = s.length();
        int result = 0;
        int[] charIndex = new int[256];
        for (int left = 0, right = 0; right < len; right++) {
            char c = s.charAt(right);
            left = Math.max(charIndex[c], left);
            result = Math.max(result, right - left + 1);
            charIndex[c] = right + 1;
        }

        return result;
    }

    /***
     * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     *
     * 返回滑动窗口中的最大值。输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3输出: [3,3,5,5,6,7]
     */
    @Test
    public void test2() {
        int right = 0;
        int[] nums = {61, 3, 4, -3, 5, 3, 6, 7};
        int k = 3;
        int[] res = new int[nums.length - k + 1];
        int index = 0;
        LinkedList<Integer> list = new LinkedList<>();
        // 开始构造窗口
        while (right < nums.length) {
            // 这里的list的首位必须是窗口中最大的那位--遍历窗口中的每个元素
            while (!list.isEmpty() && nums[right] > list.peekLast()) {
                list.removeLast();
            }
            System.out.print(list);
            // 不断添加
            list.addLast(nums[right++]);
            // 构造窗口完成，这时候需要根据条件做一些操作
            if (right >= k && !list.isEmpty()) {
                res[index++] = list.peekFirst();
                // 如果发现第一个已经在窗口外面了，就移除
                if (list.peekFirst() == nums[right - k]) {
                    list.removeFirst();
                }
            }
        }
        System.out.println(Arrays.toString(res));
    }
    /**
     * 题目
     * 给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
     *
     * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
     * 输入：
     * s = “barfoothefoobarman”,
     * words = [“foo”,“bar”]
     * 输出：[0,9]
     * 解释：
     * 从索引 0 和 9 开始的子串分别是 “barfoo” 和 “foobar” 。
     * 输出的顺序不重要, [9,0] 也是有效答案。
     * foobarfort
     * */

    @Test
    public void test3() {
        long time1 = System.currentTimeMillis();
        String s = "abdafooefooebdarthebdafooessbarmanlbdafooe";
        String[] words = {"bda", "foo"};
        int wordsLen = words.length;
        int wordsNum = 0;
        List<Integer> res = new ArrayList<>();
        for (String word : words) {
            wordsNum += word.length();
        }

        int windowSize = s.length() - wordsNum;
        int right = 0;
        label: while (right < windowSize+1) {
            int isFlag = 0;
            String subStr = s.substring(right, right + wordsNum);//以words数组里字符串长度截取s的子串
            for (String word : words) {
                if (subStr.contains(word)) {
                    isFlag++;
                }
                else{
                    right++;
                    continue label;
                }
            }
            if (isFlag == wordsLen){
                res.add(right++);
            }

        }
        System.out.println(res);
        long time2 = System.currentTimeMillis();
        System.out.println("时间花费为："+(time2-time1));
    }


    @Test
    public void test4() {
        long time1 = System.currentTimeMillis();
        String s = "bdafooefooebdarthefoobdarmanl";
        String[] words = {"bda", "foo"};
        int wordsLen = words[0].length();
        List<Integer> res = new ArrayList<>();
        int wordsNum= words.length*wordsLen;
        HashMap<String, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }
        //构造窗口
        int windowSize = s.length() - wordsNum;
        int right = 0;
        while (right < windowSize + 1) {
            int left = 0;
            HashMap<String, Integer> hasWord = new HashMap<>();
            while (left < wordsLen) {
                String subStr = s.substring(right+left*wordsLen, right +(left+1)*wordsLen);//以words数组里字符串长度截取s的子串
                if (wordsMap.containsKey(subStr)) {//判断s的子串是否出现在wordsMap里
                    hasWord.put(subStr, hasWord.getOrDefault(subStr, 0) + 1);
                    if (hasWord.get(subStr) > wordsMap.get(subStr)) {//说明hasWord存在重复的子串比如foofoofoo这种
                        break;
                    }
                } else {
                    break;
                }
                left++;
            }
            //判断是不是所有的单词都符合条件
            if (left == words.length) {
                res.add(right);
            }
            right++;
        }
        System.out.println(res);
        long time2 = System.currentTimeMillis();
        System.out.println("时间花费为："+(time2-time1));
    }
}
