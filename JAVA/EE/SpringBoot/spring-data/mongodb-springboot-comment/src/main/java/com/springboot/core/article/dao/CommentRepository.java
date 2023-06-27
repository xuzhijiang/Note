package com.springboot.core.article.dao;

import com.springboot.core.article.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

// MongoRepository<实体类的类型, ID的类型>
public interface CommentRepository extends MongoRepository<Comment, String> {
    // 根据父id,查询子评论的分页列表
    // 函数的名字不能乱写,是有格式的.这里就是findBy.., 也就是findBy是一种固定的语法格式,后面加属性名
    // 第一个参数是findBy后面的属性名对应的参数
    // 带上Pageable就可以帮我们进行分页

    // 函数名是有提示的
    Page<Comment> findByParentid(String parentid, Pageable pageable);
}
