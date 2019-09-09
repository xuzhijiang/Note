package org.lucene.core.createIndex;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.lucene.core.entity.Article;
import java.io.File;

/**
 * 1. 对于大段的文本，IndexWriter并不能针对其建立索引，必须要经过Analyzer进行分词之后才能建立索引。
 * 2. Analyzer只能针对纯文本的文件内容进行分词，如果文件不是纯文本，我们必须先将文件中的文本内容提取出来，
 * Tika框架可以帮助我们将文本内容从不同格式的文件中提取出来。
 *
 * 3. Analyzer是一个抽象类，Lucene为其提供了一实现类。其中有一些Analyzer是处理停用词stopping word，
 * 例如 a、an、the这些词语对于建立索引是没有作用的。一些Analyzer将所有的关键词全部转为小写，
 * 这样Lucene就不需要考虑大小写的问题了。等等。
 *
 * Lucene只处理文本内容，这是由于搜索的时候，用户只会输入文本内容进行搜索。
 * 因此对于不同类型的文档，我们在建立索引的时候，会将其的文本内容提取出来，针对图片建立索引没有任何意义。
 */
public class CreateIndex {

    /** 建立索引,发表过文章过后，不仅数据库中有存储记录 索引库中也必须有一条*/
    public static void main(String args[]) throws Exception {

        // 模拟一条数据库中的记录
        Article artical = new Article(1, "Lucene全文检索框架",
                "Lucene-信息检索系统在用户发出了检索请求后再去网上找答案","xxx");

        // 建立索引实际上是针对Field的value建立索引，当指定value建立索引后，
        // Lucene会根据value的值计算得到一个Token，这是Lucene使用一些算法得到的。
        Document doc = new Document();

        doc.add(new LongField("id", artical.getId(), Field.Store.YES));
        doc.add(new StringField("author", artical.getAuthor(), Field.Store.YES));
        doc.add(new TextField("title", artical.getTitle(), Field.Store.YES));
        // Store.YES和Store.NO的作用是，如果选择了YES，在建立索引后，会把这个字段的原始值也保存在数据库中，
        // 而NO则是不保存。这将会影响到搜索的结果的展示。例如我们希望文章的内容content可以进行检索，
        // 但是我们不希望将其原始内容保存到索引库中，因为一个文章的内容通常都是很大的。我们搜索的结果只是展示部分信息，
        // 而不是展示文章的所有信息，因为我们对文章的id使用Store.YES，所以检索到的结果我们可以获取到文章的id，
        // 当检索结果展示在页面上时，只要超链接后面跟上这个id作为参数，在数据库中检索是非常快的，因为有主键索引。
        doc.add(new TextField("content", artical.getContent(), Field.Store.NO));

        // 2、建立索引
        Directory directory = FSDirectory.open(new File("./indexDir/")); // 指定索引库的位置，本例为项目根目录下indexDir
        // 分词器，不同的分词器有不同的规则
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        // 每次使用IndexWriter在操作索引库的时候，都会给索引库加上一把锁，当关闭这个IndexWriter时，才会把锁释放掉。
        // 而我们开发的web应用是多线程的，这就意味着一个线程在操作索引库的时候，索引库就会被锁住，其他线程无法访问。
        // 如果A线程操作完索引库后，B线程也想操作索引库，那么A线程必须关闭IndexWriter,释放锁，但是如果每次一个线程
        // 操作完索引库后，都关闭IndexWriter,会造成资源浪费.
        // IndexWriter类似于数据库的SessionFactory，每次使用完都关闭，是非常浪费资源的，
        // 因此，我们应该保证在全局范围内只使用一个IndexWriter。

        // 此时有两种方法来解决这个问题：
        // 1. 针对每个线程都创建一个IndexWriter，强烈不建议
        // 2. 全局范围内使用一个IndexWriter，但是每次使用完不是close掉，是使用commit方法，
        // 当操作提交之后，锁就会被释放掉，别的线程就可以操作索引库了。
        // 在多线程并发访问时，只要保证每个人的结果集不是全局的，就不会出现数据混乱的情况。

        // IndexWriter只有一个构造函数，接受一个Directory和IndexWriterConfig对象。
        // Directory指的是：索引库的路径，也就是建立索引后，索引的存放位置
        // IndexWriterConfig是创建索引时的配置信息，例如指定使用的分词器
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.addDocument(doc);
        indexWriter.close();

        // indexDir目录下生成的文件就是Lucene的索引库文件，我们可以通过lukeall工具来查看索引库中的内容
    }

}
