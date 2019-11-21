# 如何阅读Spring源码

[如何阅读Spring源码
](https://zhuanlan.zhihu.com/p/72581899)

## 条件注解的作用

>条件注解存在的意义在于动态识别。比如@ConditionalOnClass会检查`类加载器中是否存在对应的类`，如果有的话被注解修饰的类就有资格被Spring容器所注册，否则会被skip。springBoot内部提供了特有的注解：条件注解(Conditional Annotation)。比如@ConditionalOnBean、@ConditionalOnClass、
@ConditionalOnExpression、@ConditionalOnMissingBean等。

比如FreemarkerAutoConfiguration这个自动化配置类的定义如下：

```java
// 这个自动化配置类被@ConditionalOnClass条件注解修饰，这个条件注解存在的意义在于判断类加载器中是
// 否存在freemarker.template.Configuration和FreeMarkerConfigurationFactory这两个类，
// 如果都存在的话会在Spring容器中加载这个FreeMarkerAutoConfiguration配置类；否则不会加载。
@Configuration
@ConditionalOnClass({ freemarker.template.Configuration.class,
		FreeMarkerConfigurationFactory.class })
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(FreeMarkerProperties.class)
public class FreeMarkerAutoConfiguration{}
```

## 条件注解内部的一些基础

>@ConditionalOnClass注解:// 它有2个属性，分别是类数组和字符串数组(作用一样，类型不一样)，而且被@Conditional注解所修饰

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {
  Class<?>[] value() default {}; // 需要匹配的类
  String[] name() default {}; // 需要匹配的类名
}
```

>这个@Conditional注解有个名为values的Class<? extends Condition>[]类型的属性.

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {
	Class<? extends Condition>[] value();
}
```

>这个Condition是个接口，用于匹配组件是否有资格被容器注册,也就是说@Conditional注解属性中可以持有多个Condition接口的实现类，所有的Condition接口需要全部匹配成功后这个@Conditional修饰的组件才有资格被注册。

```java
public interface Condition {
  // ConditionContext内部会存储Spring容器、应用程序环境信息、资源加载器、类加载器
  boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
}
```

>Condition接口有个子接口ConfigurationCondition：这个子接口是一种特殊的条件接口，多了一个`getConfigurationPhase方法`，也就是条件注解的生效阶段。只有在ConfigurationPhase中定义的两种阶段下才会生效。

```java
public interface ConfigurationCondition extends Condition {

  ConfigurationPhase getConfigurationPhase();

  public static enum ConfigurationPhase {

  	PARSE_CONFIGURATION,

  	REGISTER_BEAN
  }

}
```

>Condition接口有个实现抽象类SpringBootCondition，SpringBoot中所有条件注解对应的条件类都继承这个抽象类。它实现了matches方法：

```java
@Override
public final boolean matches(ConditionContext context,AnnotatedTypeMetadata metadata) {
    String classOrMethodName = getClassOrMethodName(metadata); // 得到类名或者方法名(条件注解可以作用的类或者方法上)
      try {
        ConditionOutcome outcome = getMatchOutcome(context, metadata); // 抽象方法，具体子类实现。ConditionOutcome记录了匹配结果boolean和log信息
        logOutcome(classOrMethodName, outcome); // log记录一下匹配信息
        recordEvaluation(context, classOrMethodName, outcome); // 报告记录一下匹配信息
        return outcome.isMatch(); // 返回是否匹配
      }
  catch (NoClassDefFoundError ex) {
  	throw new IllegalStateException("", ex);
  }
  catch (RuntimeException ex) {
  	throw new IllegalStateException("" + getName(metadata), ex);
  }
}
```

## 基于Class的条件注解

>SpringBoot提供了两个基于Class的条件注解：`@ConditionalOnClass(类加载器中存在指明的类)`或者`@ConditionalOnMissingClass(类加载器中不存在指明的类)`。
@ConditionalOnClass或者@ConditionalOnMissingClass注解对应的`条件类是OnClassCondition`，定义如下：

```java
@Order(Ordered.HIGHEST_PRECEDENCE) // 优先级、最高级别
class OnClassCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(ConditionContext context,
  		AnnotatedTypeMetadata metadata) {

  	StringBuffer matchMessage = new StringBuffer(); // 记录匹配信息

  	MultiValueMap<String, Object> onClasses = getAttributes(metadata,
  			ConditionalOnClass.class); // 得到@ConditionalOnClass注解的属性
  	if (onClasses != null) { // 如果属性存在
  		List<String> missing = getMatchingClasses(onClasses, MatchType.MISSING,
  				context); // 得到在类加载器中不存在的类
  		if (!missing.isEmpty()) { // 如果存在类加载器中不存在对应的类，返回一个匹配失败的ConditionalOutcome
  			return ConditionOutcome
  					.noMatch("required @ConditionalOnClass classes not found: "
  							+ StringUtils.collectionToCommaDelimitedString(missing));
  		}
                // 如果类加载器中存在对应的类的话，匹配信息进行记录
  		matchMessage.append("@ConditionalOnClass classes found: "
  				+ StringUtils.collectionToCommaDelimitedString(
  						getMatchingClasses(onClasses, MatchType.PRESENT, context)));
  	}
        // 对@ConditionalOnMissingClass注解做相同的逻辑处理(说明@ConditionalOnClass和@ConditionalOnMissingClass可以一起使用)
  	MultiValueMap<String, Object> onMissingClasses = getAttributes(metadata,
  			ConditionalOnMissingClass.class);
  	if (onMissingClasses != null) {
  		List<String> present = getMatchingClasses(onMissingClasses, MatchType.PRESENT,
  				context);
  		if (!present.isEmpty()) {
  			return ConditionOutcome
  					.noMatch("required @ConditionalOnMissing classes found: "
  							+ StringUtils.collectionToCommaDelimitedString(present));
  		}
  		matchMessage.append(matchMessage.length() == 0 ? "" : " ");
  		matchMessage.append("@ConditionalOnMissing classes not found: "
  				+ StringUtils.collectionToCommaDelimitedString(getMatchingClasses(
  						onMissingClasses, MatchType.MISSING, context)));
  	}
        // 返回全部匹配成功的ConditionalOutcome
  	return ConditionOutcome.match(matchMessage.toString());
	}

  private enum MatchType { // 枚举：匹配类型。用于查询类名在对应的类加载器中是否存在。

  	PRESENT { // 匹配成功
  		@Override
  		public boolean matches(String className, ConditionContext context) {
  			return ClassUtils.isPresent(className, context.getClassLoader());
  		}
  	},

  	MISSING { // 匹配不成功
  		@Override
  		public boolean matches(String className, ConditionContext context) {
  			return !ClassUtils.isPresent(className, context.getClassLoader());
  		}
  	};

  	public abstract boolean matches(String className, ConditionContext context);

  }

}
```

>比如FreemarkerAutoConfiguration中的@ConditionalOnClass注解中有value属性是freemarker.template.Configuration.class和FreeMarkerConfigurationFactory.class。在OnClassCondition执行过程中得到的最终ConditionalOutcome中的log message如下：

```
@ConditionalOnClass classes found: freemarker.template.Configuration,org.springframework.ui.freemarker.FreeMarkerConfigurationFactory
```

## 基于Bean的条件注解

>@ConditionalOnBean(Spring容器中存在指明的bean)、@ConditionalOnMissingBean(Spring容器中不存在指明的bean)以及ConditionalOnSingleCandidate(Spring容器中存在且只存在一个指明的bean)都是基于Bean的条件注解，它们对应的条件类是ConditionOnBean:

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnBeanCondition.class)
public @interface ConditionalOnBean {
  Class<?>[] value() default {}; // 匹配的bean类型
  String[] type() default {}; // 匹配的bean类型的类名
  Class<? extends Annotation>[] annotation() default {}; // 匹配的bean注解
  String[] name() default {}; // 匹配的bean的名字
  SearchStrategy search() default SearchStrategy.ALL; // 搜索策略。提供CURRENT(只在当前容器中找)、PARENTS(只在所有的父容器中找；但是不包括当前容器)和ALL(CURRENT和PARENTS的组合)
}
```

>OnBeanCondition条件类的匹配代码如下：

```java
@Override
public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    
      StringBuffer matchMessage = new StringBuffer(); // 记录匹配信息
      if (metadata.isAnnotated(ConditionalOnBean.class.getName())) {
          // 构造一个BeanSearchSpec，会从@ConditionalOnBean注解中获取属性，然后设置到BeanSearchSpec中
        BeanSearchSpec spec = new BeanSearchSpec(context, metadata, ConditionalOnBean.class); 
        List<String> matching = getMatchingBeans(context, spec); // 从BeanFactory中根据策略找出所有匹配的bean
        if (matching.isEmpty()) { // 如果没有匹配的bean，返回一个没有匹配成功的ConditionalOutcome
          return ConditionOutcome.noMatch("@ConditionalOnBean " + spec + " found no beans");
        }
        // 如果找到匹配的bean，匹配信息进行记录
        matchMessage.append("@ConditionalOnBean " + spec + " found the following " + matching);
      }
      
      if (metadata.isAnnotated(ConditionalOnSingleCandidate.class.getName())) { // 相同的逻辑，针对@ConditionalOnSingleCandidate注解
        BeanSearchSpec spec = new SingleCandidateBeanSearchSpec(context, metadata,
            ConditionalOnSingleCandidate.class);
        List<String> matching = getMatchingBeans(context, spec);
        if (matching.isEmpty()) {
          return ConditionOutcome.noMatch(
              "@ConditionalOnSingleCandidate " + spec + " found no beans");
        }
        else if (!hasSingleAutowireCandidate(context.getBeanFactory(), matching)) { // 多了一层判断，判断是否只有一个bean
          return ConditionOutcome.noMatch("@ConditionalOnSingleCandidate " + spec
              + " found no primary candidate amongst the" + " following "
              + matching);
        }
        matchMessage.append("@ConditionalOnSingleCandidate " + spec + " found "
            + "a primary candidate amongst the following " + matching);
      }
      
      if (metadata.isAnnotated(ConditionalOnMissingBean.class.getName())) { // 相同的逻辑，针对@ConditionalOnMissingBean注解
        BeanSearchSpec spec = new BeanSearchSpec(context, metadata,
            ConditionalOnMissingBean.class);
        List<String> matching = getMatchingBeans(context, spec);
        if (!matching.isEmpty()) {
          return ConditionOutcome.noMatch("@ConditionalOnMissingBean " + spec
              + " found the following " + matching);
        }
        matchMessage.append(matchMessage.length() == 0 ? "" : " ");
        matchMessage.append("@ConditionalOnMissingBean " + spec + " found no beans");
      }
    return ConditionOutcome.match(matchMessage.toString()); //返回匹配成功的ConditonalOutcome
}
```

>SpringBoot还提供了其他比如ConditionalOnJava、ConditionalOnNotWebApplication、ConditionalOnWebApplication、ConditionalOnResource、ConditionalOnProperty、ConditionalOnExpression等条件注解，有兴趣的读者可以自行查看它们的底层处理逻辑。

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

## 示例

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
<td>@ConditionalOnClass<br>({ Configuration.class,<br>FreeMarkerConfigurationFactory.class })</td>
<td>类加载器中必须存在Configuration和FreeMarkerConfigurationFactory这两个类</td>
</tr>
<tr>
<td>@ConditionalOnExpression<br>(“‘${server.host}’==’localhost’”)</td>
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
<td>@ConditionalOnMissingClass<br>(“GenericObjectPool”)</td>
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
<td>@ConditionalOnResource<br>(resources=”mybatis.xml”)</td>
<td>类加载路径中必须存在mybatis.xml文件</td>
</tr>
<tr>
<td>@ConditionalOnSingleCandidate<br>(PlatformTransactionManager.class)</td>
<td>Spring当前或父容器中必须存在PlatformTransactionManager这个类型的实例，且只有一个实例</td>
</tr>
<tr>
<td>@ConditionalOnWebApplication</td>
<td>必须在Web应用下才会生效</td>
</tr>
</tbody>
</table>