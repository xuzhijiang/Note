package com.journaldev.spring.repository;

import org.springframework.data.repository.CrudRepository;
import com.journaldev.spring.model.Person;
import java.util.List;
// Spring Data JPA Repository

// 通过继承CrudRepository，我们可以调用许多方法而无需自己实现它们。 其中一些方法是：

// save
// findOne
// exists
// findAll
// count
// delete
// deleteAll

// 我们也可以定义自己的方法。 这些方法名称应使用特殊关键字，例如“find”，“order”和变量名称。 
// Spring Data JPA开发人员试图考虑您可能需要的大多数可能选项。
// 在我们的示例中，findByFirstName（String firstName）方法返回表中的所有条目，
// 其中字段first_name等于firstName。

// 这是Spring Data JPA最重要的特性之一，因为它减少了很多boiler plate code。
// 此外，错误的可能性较小，因为许多已经使用它们的项目对这些Spring方法进行了很好的测试。

public interface PersonRepository<P> extends CrudRepository<Person, Long> {
    List<Person> findByFirstName(String firstName);
}
