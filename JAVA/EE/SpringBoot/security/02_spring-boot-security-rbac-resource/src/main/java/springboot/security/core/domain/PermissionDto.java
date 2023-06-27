package springboot.security.core.domain;

import lombok.Data;

// 对应数据库中的t_permission表
@Data
public class PermissionDto {
    private String id;
    private String code;
    private String description;
    private String url;
}
