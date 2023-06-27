package springboot.security.core.domain;

import lombok.Data;
import java.util.Date;

// 对应数据库中的t_role表
@Data
public class RoleDto {
    private String id;
    private String roleName;
    private String description;
    private Date createTime;
    private Date updateTime;
    private String status;
}
