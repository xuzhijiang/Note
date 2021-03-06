# Spring注解缓存

Spring 3.1之后，引入了`注解缓存技术`，其本质上不是一个具体的缓存实现方案，而是一个`对缓存使用的抽象`，通过在既有代码中添加少量自定义的各种annotation，即能够达到使用缓存对象和缓存方法的返回对象的效果。Spring的缓存技术具备相当的灵活性，不仅能够使用SpEL（Spring Expression Language）来定义缓存的key和各种condition，还提供开箱即用的缓存临时存储方案，也支持和主流的专业缓存集成。其特点总结如下：

- 少量的配置annotation注释即可使得既有代码支持缓存；
- 支持开箱即用，不用安装和部署额外的第三方组件即可使用缓存；
- 支持Spring Express Language（SpEL），能使用对象的任何属性或者方法来定义缓存的key和使用规则条件；
- 支持自定义key和自定义缓存管理者，具有相当的灵活性和可扩展性。

和Spring的事务管理类似，`Spring Cache的关键原理就是Spring AOP`，通过Spring AOP实现了在方法调用前、调用后获取方法的入参和返回值，进而实现了缓存的逻辑。而Spring Cache利用了Spring AOP的动态代理技术，即当客户端尝试调用pojo的foo()方法的时候，给它的不是pojo自身的引用，而是一个动态生成的代理类。

![Spring动态代理调用图](https://awps-assets.meituan.net/mit-x/blog-images-bundle-2017/fceabe48.png)

如图，实际客户端获取的是一个代理的引用，在调用foo()方法的时候，会首先调用proxy的foo()方法，这个时候proxy可以整体控制实际的pojo.foo()方法的入参和返回值，比如缓存结果，比如直接略过执行实际的foo()方法等，都是可以轻松做到的。

Spring Cache主要使用三个注释标签，即`@Cacheable、@CachePut和@CacheEvict`，主要`针对方法上注解使用`，部分场景也可以直接类上注解使用，`当在类上使用时，该类所有方法都将受影响`。我们总结一下其作用和配置方法，如表1:

---

<table>
    <thead>
        <tr>
            <th>标签类型</th>
            <th>作用</th>
            <th>主要配置参数说明</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>@Cacheable</td>
            <td>主要针对方法配置，能够根据方法的请求参数对其结果进行缓存</td>
            <td><strong>value：</strong>缓存的名称，在 Spring 配置文件中定义，必须指定至少一个； <strong>key：</strong>缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则默认按照方法的所有参数进行组合； <strong>condition：</strong>缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存</td>
        </tr>
        <tr>
            <td>@CachePut</td>
            <td>主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用</td>
            <td><strong>value：</strong>缓存的名称，在 spring 配置文件中定义，必须指定至少一个; <strong>key：</strong>缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则默认按照方法的所有参数进行组合； <strong>condition：</strong>缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存</td>
        </tr>
        <tr>
            <td>@CacheEvict</td>
            <td>主要针对方法配置，能够根据一定的条件对缓存进行清空</td>
            <td><strong>value：</strong>缓存的名称，在 Spring 配置文件中定义，必须指定至少一个； <strong>key：</strong>缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则默认按照方法的所有参数进行组合； <strong>condition：</strong>缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存； <strong>allEntries：</strong>是否清空所有缓存内容，默认为
                false，如果指定为 true，则方法调用后将立即清空所有缓存； <strong>beforeInvocation：</strong>是否在方法执行前就清空，默认为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，默认情况下，如果方法执行抛出异常，则不会清空缓存</td>
        </tr>
    </tbody>
</table>

---

## 怎样利用Spring提供的扩展点实现我们自己的缓存

可扩展支持：Spring注解cache能够满足一般应用对缓存的需求，`但随着应用服务的复杂化，大并发高可用性能要求下，需要进行一定的扩展`. Spring预先有考虑到这点，那么怎样利用Spring提供的扩展点实现我们自己的缓存，且在不改变原来已有代码的情况下进行扩展？

是否在方法执行前就清空，默认为false，如果指定为true，则在方法还没有执行的时候就清空缓存，默认情况下，如果方法执行抛出异常，则不会清空缓存。

我们先不考虑如何持久化缓存，毕竟这种第三方的实现方案很多，`我们要考虑的是，怎么利用Spring提供的扩展点实现我们自己的缓存，且在不改原来已有代码的情况下进行扩展。`

这需要简单的三步骤，首先需要提供一个CacheManager接口的实现（继承至AbstractCacheManager），管理自身的cache实例；其次，实现自己的cache实例MyCache(继承至Cache)，在这里面引入我们需要的第三方cache或自定义cache；最后就是对配置项进行声明，将MyCache实例注入CacheManager进行统一管理。

## 酒店商家端自定义注解缓存

注解缓存的使用，可以有效增强应用代码的可读性，同时统一管理缓存，提供较好的可扩展性，为此，酒店商家端在Spring注解缓存基础上，自定义了适合自身业务特性的注解缓存。

>主要使用两个标签，即@HotelCacheable、@HotelCacheEvict，其作用和配置方法见表2。

---

<table>
    <thead>
        <tr>
            <th>标签类型</th>
            <th>作用</th>
            <th>主要配置参数说明</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>@HotelCacheable</td>
            <td>主要针对方法配置，能够根据方法的请求参数对其结果进行缓存</td>
            <td><strong>domain：</strong>作用域，针对集合场景，解决批量更新问题； <strong>domainKey：</strong>作用域对应的缓存key； <strong>key：</strong>缓存对象key 前缀； <strong>fieldKey：</strong>缓存对象key，与前缀合并生成对象key； <strong>condition：</strong>缓存获取前置条件，支持spel语法； <strong>cacheCondition：</strong>缓存刷入前置条件，支持spel语法； <strong>expireTime：</strong>超时时间设置</td>
        </tr>
        <tr>
            <td>@HotelCacheEvict</td>
            <td>主要针对方法配置，能够根据一定的条件对缓存进行清空</td>
            <td><strong>同上</strong>
            </td>
        </tr>
    </tbody>
</table>

---

增加作用域的概念，解决商家信息变更下，多重重要信息实时更新的问题。

![域缓存处理图](https://awps-assets.meituan.net/mit-x/blog-images-bundle-2017/83e3118e.png)

如图13，按旧的方案，当cache0发送变化时，为了保持信息的实时更新，需要手动删除cache1、cache2、cache3等相关处的缓存数据。增加域缓存概念，cache0、cache1、cache2、cache3是以账号ID为基础，相互存在影响约束的集合体，我们作为一个域集合，增加域缓存处理，当cache0发送变化时，整体的账号ID domain域已发生更新，自动影响cache1、cache2、cache3等处的缓存数据。将相关联逻辑缓存统一化，有效提升代码可读性，同时更好服务业务，账号重点信息能够实时变更刷新，相关服务响应速度提升。

另外，增加了cacheCondition缓存刷入前置判断，有效解决商家业务多重外部依赖场景下，业务降级有损服务下，业务数据一致性保证，不因为缓存的增加影响业务的准确性；自定义CacheManager缓存管理器，可以有效兼容公共基础组件Medis、Cellar相关服务，在对应用程序不做改动的情况下，有效切换缓存方式；同时，统一的缓存服务AOP入口，结合接入Mtconfig统一配置管理，对应用内缓存做好降级准备，一键关闭缓存。几点建议：

上面介绍过Spring Cache的原理是基于动态生成的proxy代理机制来进行切面处理，关键点是对象的引用问题，如果对象的方法是类里面的内部调用（this引用）而不是外部引用的场景下，会导致proxy失败，那么我们所做的缓存切面处理也就失效了。因此，应避免已注解缓存的方法在类里面的内部调用。
使用的key约束，缓存的key应尽量使用简单的可区别的元素，如ID、名称等，不能使用list等容器的值，或者使用整体model对象的值。非public方法无法使用注解缓存实现。

总之，注释驱动的Spring Cache能够极大的减少我们编写常见缓存的代码量，通过少量的注释标签和配置文件，即可达到使代码具备缓存的能力，且具备很好的灵活性和扩展性。但是我们也应该看到，Spring Cache由于基于Spring AOP技术，尤其是动态的proxy技术，导致其不能很好的支持方法的内部调用或者非public方法的缓存设置，当然这些都是可以解决的问题。