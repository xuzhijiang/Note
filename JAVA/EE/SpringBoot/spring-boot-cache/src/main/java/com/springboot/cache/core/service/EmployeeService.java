package com.springboot.cache.core.service;

import com.springboot.cache.core.domain.Employee;
import com.springboot.cache.core.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

// 这个类的方法中,如果使用的缓存组件的名字是emp,那么缓存组件的名字就都可以不用写了.
@CacheConfig(cacheNames="emp"/*,cacheManager = "employeeCacheManager"*/)
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

//    @Cacheable(cacheNames = {"emp"}/*,keyGenerator = "myKeyGenerator",condition = "#a0>1",unless = "#a0==2"*/)
    @Cacheable(cacheNames = {"emp"})
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    @CachePut(/*value = "emp",*/key = "#result.id") // key: 返回的employee对象的id
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp:"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    @CacheEvict(value="emp",beforeInvocation = true/*key = "#id",*/)
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
        employeeMapper.deleteEmpById(id);
//        int i = 10/0;
    }

    // @Cacheable: 按照key为lastName 进行员工的缓存.
    // @CachePut: 把方法运行之后返回的Employee,根据返回的Employee的id,和email,分别作为key,进行缓存.
    // 这样组的结果: 按照lastName查询, 结果是把id作为key,也存放了缓存, 把email作为key, 也存放到了缓存.
    // 其他地方,可以根据key为lastName/id/email, 都可以查询到缓存.
    @Caching(
            cacheable = {@Cacheable(/*value="emp",*/key = "#lastName")},
            put = {@CachePut(/*value="emp",*/key = "#result.id"), // result表示运行之后的Employee
                    @CachePut(/*value="emp",*/key = "#result.email")}
    )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }

}
