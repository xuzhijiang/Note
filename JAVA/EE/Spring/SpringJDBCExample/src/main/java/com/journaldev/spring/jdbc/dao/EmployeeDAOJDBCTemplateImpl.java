package com.journaldev.spring.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.journaldev.spring.jdbc.model.Employee;

//Spring JdbcTemplate示例

// 如果你看看DAO实现类，我们打开和关闭数据库Connection，PreparedStatements和ResultSet有很多样板代码。
// 如果有人忘记正确关闭资源，这可能会导致资源泄漏。
// 我们可以使用org.springframework.jdbc.core.JdbcTemplate类来避免这些错误。
// Spring JdbcTemplate是Spring JDBC核心包中的中心类，
// 它提供了许多方法来执行查询并自动解析ResultSet以获取Object或Object列表。

// 我们所需要的只是将参数作为Object数组提供，
// 并实现Callback接口，如PreparedStatementSetter和RowMapper，用于映射参数或将ResultSet数据转换为bean对象。

// 让我们看一下EmployeeDAO的另一个实现，我们将使用Spring JdbcTemplate类来执行不同类型的查询。


//查看Spring JdbcTemplate的上述代码的重点是：

//1. 使用Object数组传递PreparedStatement参数，我们也可以使用PreparedStatementSetter实现，
// 但传递Object数组似乎很容易使用。

//2. 没有与打开和关闭连接， statements or result set相关的代码。
// 所有这些都是由Spring JdbcTemplate类内部处理的。

//3. RowMapper匿名类实现 以便将ResultSet数据映射到queryForObject(）方法中的Employee bean对象。

// queryForList() method returns list of Map whereas Map contains the row 
// data mapped with key as the column name and value from the database row matching the criteria.
//4. queryForList(）方法返回Map列表，而Map包含使用key映射的行数据作为列名和匹配条件的数据库行中的值。
public class EmployeeDAOJDBCTemplateImpl implements EmployeeDAO {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void save(Employee employee) {
		String query = "insert into Employee (id, name, role) values (?,?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] args = new Object[] {employee.getId(), employee.getName(), employee.getRole()};
		
		int out = jdbcTemplate.update(query, args);
		
		if(out !=0){
			System.out.println("Employee saved with id="+employee.getId());
		}else System.out.println("Employee save failed with id="+employee.getId());
	}

	public Employee getById(int id) {
		String query = "select id, name, role from Employee where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		//using RowMapper anonymous class, we can create a separate RowMapper for reuse
		Employee emp = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Employee>(){

			public Employee mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setRole(rs.getString("role"));
				return emp;
			}});
		
		return emp;
	}

	public void update(Employee employee) {
		String query = "update Employee set name=?, role=? where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] {employee.getName(), employee.getRole(), employee.getId()};
		
		int out = jdbcTemplate.update(query, args);
		if(out !=0){
			System.out.println("Employee updated with id="+employee.getId());
		}else System.out.println("No Employee found with id="+employee.getId());
	}

	public void deleteById(int id) {

		String query = "delete from Employee where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		int out = jdbcTemplate.update(query, id);
		if(out !=0){
			System.out.println("Employee deleted with id="+id);
		}else System.out.println("No Employee found with id="+id);
	}

	public List<Employee> getAll() {
		String query = "select id, name, role from Employee";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Employee> empList = new ArrayList<Employee>();

		List<Map<String,Object>> empRows = jdbcTemplate.queryForList(query);
		
		for(Map<String,Object> empRow : empRows){
			Employee emp = new Employee();
			emp.setId(Integer.parseInt(String.valueOf(empRow.get("id"))));
			emp.setName(String.valueOf(empRow.get("name")));
			emp.setRole(String.valueOf(empRow.get("role")));
			empList.add(emp);
		}
		return empList;
	}

}
