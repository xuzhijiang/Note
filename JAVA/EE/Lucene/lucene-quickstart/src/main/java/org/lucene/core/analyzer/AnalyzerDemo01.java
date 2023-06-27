package org.lucene.core.analyzer;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import java.io.IOException;
import java.io.StringReader;

/**
 * 在"建立索引"和"用户搜索"时，都会用到分词器，建立索引时和搜索时使用的分词器应该是同一个，
 * 否则可能会出现搜不到的情况。
 *
 * 实际中，我们不会去开发分词器，都是使用别人开发好的分词器，因此我们主要是学会如何使用这些分词器。
 *
 * 目前所有的分词器，对英文都有很好的处理。例如对于这样的一句话：
 * An IndexWriter creates and maintains an index.
 *
 * 存储时,第一步：切分关键词：分词器首先会根据空格将这段话拆分为一个个单独的英语单词，
 * 并将标点去掉:
 *
 * An
 * IndexWriter
 * creates
 * and
 * maintains
 * an
 * index
 *
 * 第二步：去除停用词(stopping word):创建索引时，有些单词在文本中出现的概率非常高，
 * 但是对文本所带的信息基本不产生影响，例如：英文中的“a、an、the、of”，中文中的“了、的、着”。
 * 停用词通常会被过滤掉，不会被进行索引。用户检索时，检索系统也会将停用词过滤掉。
 *
 * 第三步：对于英文单词，把所有的单词转为小写(搜索时不区分大小写)
 *
 * indexwriter
 * creates
 * maintains
 * index
 *
 */
public class AnalyzerDemo01 {

    // 查看某段内容的分词结果
    public static void main(String[] args) throws IOException {
//        analyzeEnglish(); // 测试English的分词效果

//        analyzeChinese(); // 测试Chinese的分词效果

        testIKAnalyzer();

        // testPaoding();
    }

    private static void testPaoding() throws IOException{
        // 注意classpath:dic/下一定要有文件，如果只有一个dic文件夹的话，就会一直报错，
        // 说实话PaodingAnalyzer源码写的真不咋地...,调试半天，提示的错误有问题，
        // 一直报文件夹不存在，其实是文件不存在!!!
        Analyzer analyzer = new PaodingAnalyzer("classpath:paoding/paoding-analysis.properties");
        String text = "我爱北京天安门";
        TokenStream tokenStream = analyzer.tokenStream("", text);
        // 重置到流的开始位置
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenStream
                    .addAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute);

        }
    }

    /**
     * 对于lucene自带分词器，没有一个能很好的处理中文的分词，
     * 因此，我们使用的分词的时候，往往会使用国人开发的一个分词器IKAnalyzer，使用非常简单.
     *
     * 如果需要扩展词库或者停用词，只需要在src下放入扩展的词库(*.dic文件)，并在配置文件中引用即可，
     * 注意，新增的词库必须满足的要求是：一个词语占一行。
     */
    private static void testIKAnalyzer() throws IOException{
        // 中文分词
        String text = "图书信息检索系统";

        // 单词分词StandardAnalyzer, ChineseAnalyzer
        //Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);// 也是单词分词
        //Analyzer analyzer1 = new ChineseAnalyzer();

        // 相连的2个字组合在一起
        //Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_30);

        // 词库分词IKAnalyzer,需要扩展词库
//        Analyzer analyzer = new IKAnalyzer();
//
//        System.out.println("当前使用的分词器：" + analyzer.getClass());
//
//        // 有问题
//        TokenStream ts = analyzer.tokenStream("content", new StringReader(text));
//        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
//
//        while(ts.incrementToken()){
//            System.out.print(term.toString()+"|");
//        }
    }

    /**
     * 对于中文，标准分词器StandardAnalyzer实际上是一个字一个字的进行分词，这样明显是不行的。
     * 即使官方提供的Analyzer子类ChineseAnalyzer，分词也打不到我们想要的效果
     *
     * 在使用Lucene建立索引的时候，官方提供的分词器由于对中文支持不友好，
     * 所以我们需要使用另外的分词器，比较火的是庖丁解牛分词器和IKAnalyzer分词
     */
    private static void analyzeChinese() throws IOException{
        Analyzer analyzer = new StandardAnalyzer();
        String text = "爪哇是使用最广泛的语言之一";
        TokenStream tokenStream = analyzer.tokenStream("", text);
        tokenStream.reset();
        while(tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute);
        }
    }

    // 测试 "An IndexWriter creates and maintains an index"这个句子的分词效果
    private static void analyzeEnglish() throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        String text = "An IndexWriter creates and maintains an index";
        TokenStream tokenStream = analyzer.tokenStream("", text);
        tokenStream.reset();
        while(tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute);
        }
    }

}
