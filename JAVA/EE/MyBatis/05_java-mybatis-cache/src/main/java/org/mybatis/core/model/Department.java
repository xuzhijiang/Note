package org.mybatis.core.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
// 一定要添加这个无参构造器,因为好多框架就是依赖这个无参构造器工作的,如果不添加框架可能就报错
@NoArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = 123899382L;

    private Integer id;
    private String departmentName;
    private List<User> users;

    public Department(Integer id) {
        this.id = id;
    }
}
