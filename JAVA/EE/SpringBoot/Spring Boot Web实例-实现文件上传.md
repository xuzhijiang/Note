### Spring Boot Web实例：实现文件上传

在现代Web应用中， 文件上传几乎是必须具备的功能。Spring MVC对文件上传提供了良好的支持，而在SpringBoot中可以更为简单地配置文件上传所需的内容。

通常我们会在网页上设置一个form元素，里面再放置一个文件上传控件(type='file'的input元素）， 用户选择要上传的文件之后，点击“提交”按钮， 采用MIME值为“multipart/form-data”的方式上传文件到服务端。服务端只需要取出文件数据，再将其保存到硬盘上即可。

#### Spring Boot Web项目中文件上传相关配置

```
# MULTIPART(MultipartProperties)
# 是否启用Spring MVC多部分上传功能
spring.servlet.multipart.enable=true
# 将文件写入磁盘的阈值，值可以使用后缀“MB”或“KB”来表示兆字节或字节大小
spring.servlet.multipart.file-size-threshold=0
# 指定默认上传的文件夹
spring.servlet.multipart.location=d:/upload
# 限制单个文件最大大小，这里设置为5MB
spring.servlet.multipart.max-file-size=5MB
# 限制所有文件最大大小，这里设置为20MB
spring.servlet.multipart.max-request-size=20MB
# 是否延迟多部件文件请求的参数和文件的解析
spring.servlet.multipart.resolve-lazily=false
```
