# 条件注解

    SpringBoot提供了特有的注解：条件注解(Conditional Annotation)
    比如@ConditionalOnBean、@ConditionalOnClass、@ConditionalOnExpression、@ConditionalOnMissingBean

---

    SpringBoot还提供了其他比如ConditionalOnJava、ConditionalOnNotWebApplication、
    ConditionalOnWebApplication、ConditionalOnResource、ConditionalOnProperty、ConditionalOnExpression等条件注解

---

<table>
    <thead>
        <tr>
            <th>条件注解</th>
            <th>对应的Condition处理类</th>
            <th>处理逻辑</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>@ConditionalOnBean</td>
            <td>OnBeanCondition</td>
            <td>Spring容器中是否存在对应的实例。可以通过实例的类型、类名、注解、昵称去容器中查找(可以配置从当前容器中查找或者父容器中查找或者两者一起查找)这些属性都是数组，通过”与”的关系进行查找</td>
        </tr>
        <tr>
            <td>@ConditionalOnClass</td>
            <td>OnClassCondition</td>
            <td>类加载器中是否存在对应的类。可以通过Class指定(value属性)或者Class的全名指定(name属性)。如果是多个类或者多个类名的话，关系是”与”关系，也就是说这些类或者类名都必须同时在类加载器中存在</td>
        </tr>
        <tr>
            <td>@ConditionalOnExpression</td>
            <td>OnExpressionCondition</td>
            <td>判断SpEL 表达式是否成立</td>
        </tr>
        <tr>
            <td>@ConditionalOnJava</td>
            <td>OnJavaCondition</td>
            <td>指定Java版本是否符合要求。内部有2个属性value和range。value表示一个枚举的Java版本，range表示比这个老或者新于等于指定的Java版本(默认是新于等于)。内部会基于某些jdk版本特有的类去类加载器中查询，比如如果是jdk9，类加载器中需要存在java.security.cert.URICertStoreParameters；如果是jdk8，类加载器中需要存在java.util.function.Function；如果是jdk7，类加载器中需要存在java.nio.file.Files；如果是jdk6，类加载器中需要存在java.util.ServiceLoader</td>
        </tr>
        <tr>
            <td>@ConditionalOnMissingBean</td>
            <td>OnBeanCondition</td>
            <td>Spring容器中是否缺少对应的实例。可以通过实例的类型、类名、注解、昵称去容器中查找(可以配置从当前容器中查找或者父容器中查找或者两者一起查找)这些属性都是数组，通过”与”的关系进行查找。还多了2个属性ignored(类名)和ignoredType(类名)，匹配的过程中会忽略这些bean</td>
        </tr>
        <tr>
            <td>@ConditionalOnMissingClass</td>
            <td>OnClassCondition</td>
            <td>跟ConditionalOnClass的处理逻辑一样，只是条件相反，在类加载器中不存在对应的类</td>
        </tr>
        <tr>
            <td>@ConditionalOnNotWebApplication</td>
            <td>OnWebApplicationCondition</td>
            <td>应用程序是否是非Web程序，没有提供属性，只是一个标识。会从判断Web程序特有的类是否存在，环境是否是Servlet环境，容器是否是Web容器等</td>
        </tr>
        <tr>
            <td>@ConditionalOnProperty</td>
            <td>OnPropertyCondition</td>
            <td>应用环境中的屬性是否存在。提供prefix、name、havingValue以及matchIfMissing属性。prefix表示属性名的前缀，name是属性名，havingValue是具体的属性值，matchIfMissing是个boolean值，如果属性不存在，这个matchIfMissing为true的话，会继续验证下去，否则属性不存在的话直接就相当于匹配不成功</td>
        </tr>
        <tr>
            <td>@ConditionalOnResource</td>
            <td>OnResourceCondition</td>
            <td>是否存在指定的资源文件。只有一个属性resources，是个String数组。会从类加载器中去查询对应的资源文件是否存在</td>
        </tr>
        <tr>
            <td>@ConditionalOnSingleCandidate</td>
            <td>OnBeanCondition</td>
            <td>Spring容器中是否存在且只存在一个对应的实例。只有3个属性value、type、search。跟ConditionalOnBean中的这3种属性值意义一样</td>
        </tr>
        <tr>
            <td>@ConditionalOnWebApplication</td>
            <td>OnWebApplicationCondition</td>
            <td>应用程序是否是Web程序，没有提供属性，只是一个标识。会从判断Web程序特有的类是否存在，环境是否是Servlet环境，容器是否是Web容器等</td>
        </tr>
    </tbody>
</table>

---

# 示例

---

<table>
    <thead>
        <tr>
            <th>例子</th>
            <th>例子意义</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>@ConditionalOnBean(javax.sql.DataSource.class)</td>
            <td>Spring容器或者所有父容器中需要存在至少一个javax.sql.DataSource类的实例</td>
        </tr>
        <tr>
            <td>@ConditionalOnClass
                <br>({ Configuration.class,
                <br>FreeMarkerConfigurationFactory.class })</td>
            <td>类加载器中必须存在Configuration和FreeMarkerConfigurationFactory这两个类</td>
        </tr>
        <tr>
            <td>@ConditionalOnExpression
                <br>(“‘${server.host}’==’localhost’”)</td>
            <td>server.host配置项的值需要是localhost</td>
        </tr>
        <tr>
            <td>ConditionalOnJava(JavaVersion.EIGHT)</td>
            <td>Java版本至少是8</td>
        </tr>
        <tr>
            <td>@ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)</td>
            <td>Spring当前容器中不存在ErrorController类型的bean</td>
        </tr>
        <tr>
            <td>@ConditionalOnMissingClass
                <br>(“GenericObjectPool”)</td>
            <td>类加载器中不能存在GenericObjectPool这个类</td>
        </tr>
        <tr>
            <td>@ConditionalOnNotWebApplication</td>
            <td>必须在非Web应用下才会生效</td>
        </tr>
        <tr>
            <td>@ConditionalOnProperty(prefix = “spring.aop”, name = “auto”, havingValue = “true”, matchIfMissing = true)</td>
            <td>应用程序的环境中必须有spring.aop.auto这项配置，且它的值是true或者环境中不存在spring.aop.auto配置(matchIfMissing为true)</td>
        </tr>
        <tr>
            <td>@ConditionalOnResource
                <br>(resources=”mybatis.xml”)</td>
            <td>类加载路径中必须存在mybatis.xml文件</td>
        </tr>
        <tr>
            <td>@ConditionalOnSingleCandidate
                <br>(PlatformTransactionManager.class)</td>
            <td>Spring当前或父容器中必须存在PlatformTransactionManager这个类型的实例，且只有一个实例</td>
        </tr>
        <tr>
            <td>@ConditionalOnWebApplication</td>
            <td>必须在Web应用下才会生效</td>
        </tr>
    </tbody>
</table>    

---