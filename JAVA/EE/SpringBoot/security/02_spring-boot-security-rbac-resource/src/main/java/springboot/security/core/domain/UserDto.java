package springboot.security.core.domain;

import lombok.Data;

// 对应数据库中的t_user表
@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
