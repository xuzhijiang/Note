package org.lucene.core.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lucene.core.entity.Article;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Term是索引库中最基本的搜索单元。因为在建立索引的时候，如果指定不分词(如案例中的文件名和文件路径)，
 * 那么整个内容就是一个Term，而如果指定分词(如案例中的文件内容)，内容就会被拆分成多个Term，
 * 这些过程是在建立索引的过程中由Lucene框架自动完成的，因此Term是Lucene中最基本的搜索单元，
 * 在建立索引的时候我们不需要考虑Term。
 *
 * 在搜索的时候，我们可以使用Term配合TermQuery进行查询。
 *
 * Query q = new TermQuery(new Term("contents", "lucene"));
 * TopDocs hits = searcher.search(q, 10);
 *
 * Term中的参数对应建立索引时Field中指定的索引的名称(name)，第二个参数是检索的关键字。
 *
 * TermQuery与Query对象的区别是：Query对象是通过QueryParser解析用户输入的搜索内容解析得到的，
 * 会将用户输入的内容分词后再进行搜索。
 * 而由于TermQuery搜索的是"最小的分词单元"，因此不会对搜索关键字再次进行分词。
 *
 * Lucene的查询需要根据搜索关键字构建一个Query对象，进行查询.
 * Query对象是一个抽象类，代表用户的查询，其有很多子类：
 *
 * TermQuery.
 * BooleanQuery,
 * PhraseQuery,
 * PrefixQuery,
 * PhrasePrefixQuery,
 * TermRangeQuery,
 * NumericRangeQuery,
 * FilteredQuery,
 * SpanQuery.
 *
 * 每一种查询都有着不同的作用
 *
 * 1、字符查询时，会使用分词器将查询语句进行分词，分词之后，默认使用的是OR的关系。
 * 意味着索引库中，只要匹配一个词，就能被搜索出来。如果显示指定AND ，
 * 则两个词必须连在一起，才能被搜索出来。
 *
 * Query对象本身也实现了一些方法，setBoost(float)方法是一个比较有趣的方法，
 * 当我们使用多个子查询对象进行查询的时候，如果某个子查询对象使用了这个方法，
 * 可以提高这个子查询对象查询出来的数据的相关度得分。
 *
 */
public class Searcher {

    public static void main(String args[]) throws Exception {
//        String queryString = "compass";

        Directory directory = FSDirectory.open(new File("./indexDir/"));// 索引库目录
        Analyzer analyzer = new StandardAnalyzer();

        // 1、把查询字符串转为查询对象(存储的都是二进制文件，普通的明文String肯定无法查询，因此需要转换)
        QueryParser queryParser = new QueryParser("title",analyzer); // 只在标题里面查询
        // 构建要查询的字符串(不区分大小写)
        String queryString = "lucene";
        // 要在哪些字段上查询存放在queryParser，要查询的内容就存放在query里
        Query query = queryParser.parse(queryString);

        // 2、查询，得到中间结果
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 在dirctory目录下，查找要查找的内容query
        // // 根据指定查询条件查询，只返回前n条(这里是前100条)结果
        TopDocs topDocs = indexSearcher.search(query, 100);
        int count = topDocs.totalHits;// 总结果数
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;// 按照得分进行排序后的前n条结果的信息

        // 3、处理中间结果
        List<Article> articalList = new ArrayList<Article>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            float score = scoreDoc.score;// 相关度得分
            System.out.println("相关度得分: " + score);
            // Document在数据库的内部编号(是唯一的，由lucene自动生成)
            int docId = scoreDoc.doc; 
            // 根据编号取出真正的Document数据
            Document doc = indexSearcher.doc(docId);
            // 把Document转成Article
            Article artical = new Article(
                    Integer.parseInt(doc.getField("id").stringValue()),//需要转为int型
                    doc.getField("title").stringValue(),
                    null,
                    doc.getField("author").stringValue());

            articalList.add(artical);
        }

        indexReader.close();
        // ============查询结束====================


        // 显示结果
        System.out.println("总结果数量为:" + articalList.size());
        for (Article artical : articalList) {
            System.out.println("id="+artical.getId());
            System.out.println("title="+artical.getTitle());
            System.out.println("content="+artical.getContent());
        }

    }

}
