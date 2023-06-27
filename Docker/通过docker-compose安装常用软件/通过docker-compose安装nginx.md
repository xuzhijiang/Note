# Docker 来安装和运行 Nginx

    docker-compose.yml 配置如下：

```yaml
version: '3.1'
services:
  nginx:
    restart: always
    image: nginx
    container_name: nginx
    ports:
      - 81:80
    volumes:
      # 点代表当前yml的所在的目录,左边是宿主机目录
      - ./conf/nginx.conf:/etc/nginx/nginx.conf
      - ./wwwroot:/usr/share/nginx/wwwroot
```