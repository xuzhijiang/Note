package com.springboot.core.article.service;

import com.springboot.core.article.dao.CommentRepository;
import com.springboot.core.article.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存一个评论
     * @param comment
     */
    public void saveComment(Comment comment){
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 更新评论
     * @param comment
     */
    public void updateComment(Comment comment){
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 根据id删除评论
     * @param id
     */
    public void deleteCommentById(String id){
        //调用dao
        commentRepository.deleteById(id);
    }

    /**
     * 查询所有评论
     * @return
     */
    public List<Comment> findCommentList(){
        //调用dao
        return commentRepository.findAll();
    }

    /**
     * 根据评论的id查询评论
     * @param id 其实是_id,也就是主键
     * @return
     */
    public Comment findCommentById(String id){
        //调用dao
        return commentRepository.findById(id).get();
    }

    public Page<Comment> findCommentListByParentid(String parentid,int page,int size) {
        // PageRequest.of(page-1,size): 构造分页查询条件
        return commentRepository.findByParentid(parentid, PageRequest.of(page-1,size));
    }

    public void updateCommentLikenum(String id){
        //  查询条件
        // Criteria就是条件的意思
        // 指定条件: Criteria
        Query query = Query.query(Criteria.where("_id").is(id));
        //  更新条件
        Update update = new Update();
        // 列值增长(自增)
        update.inc("likenum");
        mongoTemplate.updateFirst(query,update,Comment.class);
    }
}
