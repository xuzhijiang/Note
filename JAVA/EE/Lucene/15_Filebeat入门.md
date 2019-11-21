![](pics/Beats作用.png)

![](pics/Beats的常用产品.png)

[Filebeat安装](https://www.elastic.co/guide/en/beats/filebeat/current/filebeat-installation.html)
    
![](pics/FileBeat说明.png)

![](pics/FileBeat说明02.png)

```yaml
filebeat.inputs:
# 输入来自: 标准的输入stdin
- type: stdin
  enabled: true
setup.template.settings:
  index.number_of_shards: 1
# 输出到控制台
output.console:
  pretty: true
  enable: true
```

![](pics/FileBeat说明03.png)
