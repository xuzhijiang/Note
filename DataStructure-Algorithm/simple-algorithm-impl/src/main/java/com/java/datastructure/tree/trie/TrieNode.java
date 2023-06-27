package com.java.datastructure.tree.trie;

/**
 * 字典树
 */
public class TrieNode {

    /**
     * 字母表的大小,一般来说包含: 26个小写字符,26个大写字符,以及一些特殊的符合,
     * 这里直接把ascii码全都用上了.
     *
     * 所有ascii码,总共256个: https://www.ascii-code.com/
     * https://www.asciitable.com/
     */
    private static final int ALPHABET_SIZE = 256;

    /**
     * 记录所有他的儿子,长度就是ALPHABET_SIZE
     */
    private TrieNode[] children = new TrieNode[ALPHABET_SIZE];

    /**
     * 表示现在是不是单词的终结,如果不是终结说明走过来的路径只是一个前缀,
     * 如果是终结说明当前到这个节点已经可以代表一个单词.
     * 默认是false
     */
    private boolean isEndOfWord = false;

    public TrieNode() {}
}
