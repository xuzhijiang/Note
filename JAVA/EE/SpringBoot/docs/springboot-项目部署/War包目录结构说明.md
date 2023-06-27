# war包目录结构说明

    1. 如果想让某一个资源可以被Servlet访问，但不可以被用户访问，那么就要把它放在WEB-INF目录下

    3. War包项目比较特殊的地方在于：他有一个web资源目录，默认位置:src/main/webapp/,(注意文件夹的名字必须叫webapp，否则maven不会将里面的资源打包到war中

    4. 在src/main/webapp目录下除了html、jsp、css等必须包含子目录WEB-INF,且子目录必须包含web.xml文件

```
├── war
│   ├── css (与WEB-INF同级目录下，还可以创建子文件夹，比如js,css,这些静态资源不受保护)
│   ├── img
│   ├── index.html
│   ├── js
│   ├── META-INF
│   ├── sample.jsp
│   └── WEB-INF (WEB INFormation)
│       ├── classes
│       │   ├── config.properties
│       │   ├── FirstServlet.class
│       │   ├── ServeltTest.class
│       │   └── ServletA.class
│       ├── lib (保存jar)
│       │   └── dom.jar
│       └── web.xml ( 部署描述符总是命名为web.xml，并且放在WEB-INF目录下)
```
