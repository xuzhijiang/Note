package springboot.security.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.security.core.dao.UserDao;
import springboot.security.core.domain.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao userDao;

	// 根据 账号查询用户信息,把这个username所具有的信息以及权限都封装到UserDetails中
	// spring security会自动调用这个方法来查询用户信息
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		//将来连接数据库根据账号查询用户信息
		UserDto userDto = userDao.getUserByUsername(username);
		if(userDto == null){
			//如果用户查不到，返回null，由provider来抛出异常
			return null;
		}

		//根据用户的id查询用户的权限
		List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());

		//将permissions转成数组
		String[] permissionArray = new String[permissions.size()];
		permissions.toArray(permissionArray);

		// 如果需要用户的角色,也可以查询出这个用户的角色
		List<String> roleList = userDao.findRolesByUserId(userDto.getId());
		String[] roleArray = new String[roleList.size()];
		roleList.toArray(roleArray);

		UserDetails userDetails = User.withUsername(userDto.getUsername())
				.password(userDto.getPassword())
//				.authorities(permissionArray)
				// 权限和role只能选择一个,不能2个都用,如果都设置到UserDetails,后者会覆盖前者
				.roles(roleArray) // 基于角色的访问控制
				.build();

		return  userDetails;
	}

}
