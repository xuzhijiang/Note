# 查询部门编号大于等于90的员工信息,按入职时间的先后顺序进行排序
SELECT * FROM employees WHERE department_id >= 90 ORDER BY hiredate ASC;

# 按年薪的高低显示员工的信息和年薪
SELECT *, salary*12*(1+IFNULL(commission_pct,0)) 年薪 FROM employees ORDER BY 年薪 ASC;

# 按姓名的长度显示员工的姓名和工资[按函数排序]
SELECT LENGTH(last_name) 姓名长度,last_name,salary FROM employees ORDER BY 姓名长度 DESC;

# 查询员工信息,要求先按工资升序,再按员工编号降序[按多个字段排序]
# 也就是如果工资相同,就按员工编号显示
