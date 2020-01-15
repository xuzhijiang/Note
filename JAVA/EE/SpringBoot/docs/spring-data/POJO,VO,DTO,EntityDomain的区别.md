# POJO、VO、DTO、Entity、Domain 的区别

>看上去差不多,表达的含义不同.

- POJO(Plain Old Java Object): 简单的java对象.
- VO(View Object): 视图对象(视图里面要展示的数据应该用视图对象来展示) HTML/JSP等视图
- DTO(Data Transfer Object):数据传输对象,并没有放到页面上做展示,(实体类对象包含完整的数据,但是传给前端的就不一定是完整的数据,因为前端可能用不到有些数据,我们为了节省流量就用DTO)
- Entity: 实体类,一般用实体类表示数据中的表,也就是entity用来做orm的.和数据库一一对应的关系.用来做对象关系映射.
- Domain: 领域模型,(不同领域,比如银行,保险,电商,物流,医疗,对应的模型是不一样的,银行中的Account,User和电商中的User就不一样)
