默认情况下，Spring bean自动装配已关闭。 Spring bean autowire默认值为“default”，
表示不执行自动装配。 autowire值“no”也具有相同的行为(不执行自动装配)。

Attribute : autowire

Controls whether bean properties are "autowired". 
控制bean属性是否为“自动装配”。

This is an automagical process in which bean references don't need to be 
coded explicitly in the XML bean definition file, but rather the Spring 
container works out dependencies. The effective default is "no". There are 4 modes: 

这是一个自动化过程，这个过程中bean引用不需要被显式的编码在XML bean定义文件中
，but ratherSpring容器解决了依赖关系。 

有效默认值为“否”。 有4种模式：

1. "no": 
The traditional Spring default. No automagical wiring. Bean references 
must be defined in the XML file via the <ref/> element (or "ref" attribute). 
We recommend this in most cases as it makes documentation 
more explicit. Note that this default mode also allows for annotation-driven autowiring, if activated. 
"no" refers to externally driven autowiring only, not affecting any autowiring demands that the bean 
class itself expresses. 
"no"是传统的Spring默认值。 没有自动接线。 必须在XML文件中 通过  <ref />元素(或“ref”属性）定义Bean引用。
在大多数情况下，我们建议使用此文档更明确。 请注意，此默认模式还允许注释驱动的自动装配(如果已激活）。
“否”仅指外部驱动的自动装配，不影bean class本身表达的 任何自动装配要求。


2. "byName" Autowiring by property name. If a bean of class Cat exposes a 
"dog" property, Spring will try to set this to the value of the bean "dog" 
in the current container. If there is no matching bean by name, nothing special happens. 
“byName”按属性名称自动装配。 如果类Cat的bean暴露了一个“dog”属性，
Spring会尝试将此"dog"属性的值 设置为 当前容器中bean "dog"的值。 
如果按名称没有匹配的bean，没有什么特别的事情发生。

3. "byType" Autowiring if there is exactly one bean of the property type in the container. 
If there is more than one, a fatal error is raised, 
and you cannot use byType autowiring for that bean. If there is none, nothing special happens. 
“byType”： 如果这里正好有一个属性类型的bean在容器中，则自动装配 。 如果有多个，则会发生致命错误
提升，并且您不能对该bean使用byType自动装配。 如果没有，没什么特别的发生。

4. "constructor" Analogous to "byType" for constructor arguments. 
If there is not exactly one bean of the constructor argument type in the bean factory, 
a fatal error is raised. Note that explicit dependencies, 
i.e. "property" and "constructor-arg" elements, always override autowiring. 
“constructor”类似于构造函数参数的“byType”。 如果没有构造函数参数类型的确切的bean 在bean工厂中的话，
引发致命错误。 注意显式依赖关系，即“property”和“constructor-arg”元素，总是覆盖自动装配。

Note: This attribute will not be inherited by child bean definitions. 
Hence, it needs to be specified per concrete bean definition. 
It can be shared through the 'default-autowire' attribute at the 'beans' level 
and potentially inherited from outer 'beans' defaults in case of nested 'beans' sections 
(e.g. with different profiles).
注意：子bean定义不会继承此属性。 因此，需要指定每个具体bean定义。 它可以通过'beans'级别的'default-autowire'属性共享
在嵌套的'beans'部分的情况下可能从外部'beans'默认值继承(例如不同的简介）。

Data Type : string
Default Value : default
Enumerated Values : 
- default
- no
- byName
- byType
- constructor