package org.mybatis.core.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@AllArgsConstructor
// 一定要添加这个无参构造器,因为好多框架就是依赖这个无参构造器工作的,如果不添加框架可能就报错
@NoArgsConstructor
// @Alias("user") 给这个类起别名,然后就可以在Mapper这些xml中直接写别名,不用写全类名了.
// 别名不区分大小写.
public class User {
    private int id;
    private String lastName;
    private String email;
    private String gender;
}