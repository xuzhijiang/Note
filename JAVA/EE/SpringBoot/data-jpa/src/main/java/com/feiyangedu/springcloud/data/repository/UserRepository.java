package com.feiyangedu.springcloud.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.feiyangedu.springcloud.data.domain.User;

/**
 * JpaRepository其实也是继承了PagingAndSortingRepository，然后实现了自己的方法，
 * 我们这里编写自己的Repository，直接继承PagingAndSortingRepository
 *
 * JPA的传统配置在persistence.xml文件中，但是这里我们是在application.yml中
 */
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
