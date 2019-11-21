# 读取文件

![](pics/Filebeat读取文件.png)

```yaml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /learn/beats/logs/*.log
setup.template.settings:
  index.number_of_shards: 1
output.console:
  pretty: true
  enable: true
```

# Filebeat添加自定义字段

![](pics/Filebeat添加自定义字段.png)

```yaml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /learn/beats/logs/*.log
  tags: ["web"]
  fields:
    from: from-fields-xzj
  fields_under_root: true
setup.template.settings:
  index.number_of_shards: 1
output.console:
  pretty: true
  enable: true
```

# Filebeat输出到es中

![](pics/Filebeat输出到es中.png)

```yaml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /learn/beats/logs/*.log
  tags: ["web"]
  fields:
    from: from-fields-xzj
  fields_under_root: true
setup.template.settings:
  index.number_of_shards: 1
output.elasticsearch:
  hosts: ["192.168.32.128:9200"]
```

# Filebeat工作原理

![](pics/Filebeat工作原理.png)
![](pics/Filebeat工作原理02.png)
![](pics/Filebeat工作原理03.png)

# Filebeat读取nginx日志

![](pics/Filebeat读取nginx日志.png)

```yaml
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /usr/local/nginx/logs/*.log
  tags: ["nginx"]
  fields:
    from: nginx-fields-xzj
  fields_under_root: true
setup.template.settings:
  index.number_of_shards: 1
output.elasticsearch:
  hosts: ["192.168.32.128:9200"]
```

# Filebeat的nginx Module

![](pics/Filebeat的Module.png)
![](pics/Filebeat的Module02.png)

```yaml
# cd /usr/local/filebeat/modules.d
# vim nginx.yml

# Module: nginx
- module: nginx
  # Access logs
  access:
    enabled: true
    var.paths: ["/usr/local/nginx/logs/access.log*"]

  # Error logs
  error:
    enabled: true
    var.paths: ["/usr/local/nginx/logs/error.log*"]
```

![](pics/Filebeat的Module03.png)
![](pics/Filebeat的Module04.png)

![](pics/Filebeat的Module05.png)

```yaml

filebeat.inputs:
#- type: log 
#  enabled: true
#  paths:
#    - /usr/local/nginx/logs/*.log
#  tags: ["nginx"]
#  fields: 
#    from: nginx-fields-xzj
#  fields_under_root: true
setup.template.settings:
  index.number_of_shards: 1
output.elasticsearch:
  hosts: ["192.168.32.128:9200"]
filebeat.config.modules:
  path: ${path.config}/modules.d/*.yml
  reload.enabled: false
```

![](pics/Filebeat的Module06.png)
![](pics/Filebeat的Module07.png)