USE user_db;

# 把客户端信息和授权码都存在数据

# oauth_client_details存放客户端信息
DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
`client_id` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端标
识',
`resource_ids` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
COMMENT '接入资源列表',
`client_secret` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
COMMENT '客户端秘钥',
`scope` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`authorized_grant_types` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT
NULL,
`web_server_redirect_uri` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT
NULL,
`authorities` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`access_token_validity` INT(11) NULL DEFAULT NULL,
`refresh_token_validity` INT(11) NULL DEFAULT NULL,
`additional_information` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
`create_time` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE
CURRENT_TIMESTAMP(0),
`archived` TINYINT(4) NULL DEFAULT NULL,
`trusted` TINYINT(4) NULL DEFAULT NULL,
`autoapprove` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接入客户端信息'
ROW_FORMAT = DYNAMIC;

INSERT INTO `oauth_client_details` VALUES ('c1', 'res1',
'$2a$10$6ssZiEe58WMs175XurYQIedwsgAgl9mEz6IZ.DtGBYinB3JgoBn0a', 'ROLE_ADMIN,ROLE_USER,ROLE_API',
'client_credentials,password,authorization_code,implicit,refresh_token', 'http://localhost',
NULL, 7200, 259200, NULL, '2019-09-09 16:04:28', 0, 0, 'false');

# $2a$10$6ssZiEe58WMs175XurYQIedwsgAgl9mEz6IZ.DtGBYinB3JgoBn0a对应密钥明文: secret
INSERT INTO `oauth_client_details` VALUES ('c2', 'res2',
'$2a$10$6ssZiEe58WMs175XurYQIedwsgAgl9mEz6IZ.DtGBYinB3JgoBn0a', 'ROLE_API',
'client_credentials,password,authorization_code,implicit,refresh_token', 'http://localhost',
NULL, 31536000, 2592000, NULL, '2019-09-09 21:48:51', 0, 0, 'false');

# oauth_code表，Spring Security OAuth2使用，用来存储授权码
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
`create_time` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
`code` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
`authentication` BLOB NULL,
INDEX `code_index`(`code`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;