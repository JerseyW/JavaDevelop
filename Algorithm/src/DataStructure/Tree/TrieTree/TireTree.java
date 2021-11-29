package DataStructure.Tree.TrieTree;

import java.util.HashMap;

/**
 * @author: Jerssy
 * @create: 2021-04-26 16:28
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @description: 字典树
 *
 * 字典树又称为前缀树或Trie树，是处理字符串常见的数据结构。假设组成所有单词的字符仅是“a”~“z”，
 *字典树是一种树形结构，优点是利用字符串的公共前缀来节约存储空间，比如加入“abc”、“abcd”、“adb”、“b”、“bcd”、“efg”、“hik”之后，字典树如下图所示，其中橙色节点表示一个终止节点。

字典树的基本性质如下：
根节点没有字符路径。除根节点外，每一个节点都被一个字符路径找到；
从根节点出发到任何一个节点，如果将沿途经过的字符连接起来，一定为某个加入过的字符串的前缀；
每个节点向下的所有字符串路径上的字符都不同；

 */
public class TireTree {
    //字典树节点
    static class TrieNode {
        public int path;//路径标识符
        public int end;//代表字典树中一个完整字符串位置标识符
        public HashMap<Character, TrieNode> map;

        public TrieNode() {
            path = 0;
            end = 0;
            map = new HashMap<>();
        }
    }

    private final TrieNode root;

    public TireTree() {
        root = new TrieNode();
    }

    public void insert(String word) {
        if (word == null)
            return;
        TrieNode node = root;
        node.path++;
        char[] words = word.toCharArray();
        for (char c : words) {
            node.map.putIfAbsent(c, new TrieNode());
            node = node.map.get(c);
            node.path++;
        }
        node.end++;
    }

    public boolean search(String word) {

        if (word == null) return false;
        TrieNode node = root;
        char[] words = word.toCharArray();
        for (char c : words) {
            if (node.map.get(c) == null)
                return false;
            node = node.map.get(c);
        }
        return node.end > 0;
    }

    public void delete(String word) {
        if (search(word)) {
            char[] words = word.toCharArray();
            TrieNode node = root;
            node.path--;
            for (char c : words) {
                if (--node.map.get(c).path == 0) {
                    node.map.remove(c);
                    return;
                }
                node = node.map.get(c);
            }
            node.end--;
        }
    }

    //返回以字符串pre为前缀的单词数量
    public int prefixNumber(String pre) {
        if (pre == null)
            return 0;
        TrieNode node = root;
        char[] pres = pre.toCharArray();
        for (char c : pres) {
            if (node.map.get(c) == null)
                return 0;
            node = node.map.get(c);
        }
        return node.path;
    }

    public static void main(String[] args) {
        TireTree trie = new TireTree();
        System.out.println(trie.search("how"));//f
        trie.insert("how");
        trie.insert("so");
        trie.insert("hi");
        trie.insert("her");
        trie.insert("hello");
        trie.insert("see");
        trie.insert("hello2");
        System.out.println(trie.search("her"));//t
        trie.delete("so");
        System.out.println(trie.search("so"));//f

        System.out.println(trie.prefixNumber("hello"));//2
    }

}
