Spring @Autowired注释用于"自动依赖注入"。 Spring框架是基于依赖注入构建的，
我们通过spring bean配置文件注入类依赖项。

通常我们在spring bean配置文件中提供bean配置细节，我们还使用"ref"属性指定将要被注入到其他bean中的bean。
但Spring框架也提供了自动装配功能(autowiring features)，在那里我们不需要明确提供bean注入细节。

我们可以通过不同的方式自动装配一个spring bean:

1. autowire byName  - 对于这种类型的自动装配，setter方法用于依赖注入。
在我们将注入依赖项的类和spring bean配置文件中，变量名也应该相同。

2. autowire byType  - 对于这种类型的自动装配，使用类类型(class type)。
因此在spring bean配置文件中应该只有一个为此类型(for this type)配置的bean。

3. for this type(通过构造函数自动装配) - 这几乎类似于autowire byType，唯一的区别是构造函数用于注入依赖项。

4. autowire by autodetect  - 如果你使用的是Spring 3.0或更早版本，
这是可用的autowire选项之一。此选项被用于由“构造函数”或by “Type”的autowire，由Spring容器确定。
由于我们已经有这么多选项，因此不推荐使用此选项。我不会在本教程中介绍此选项。

5. @Autowired注释 - 我们可以使用Spring @Autowired注释进行spring bean自动装配。 
@Autowired注释可以应用于 byType自动装配的(can be applied on variables and methods for autowiring byType)
 变量和方法。
 我们还可以在构造函数上使用@Autowired注释来构造基于Spring的自动装配。
要使@Autowired注释起作用，我们还需要在spring bean配置文件中启用基于annotation的配置。
这可以通过context：annotation-config元素或通过定义
org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor类型的bean来完成。

6. @Qualifier注释 - 此注释用于避免bean映射中的冲突，我们需要提供将用于自动装配的bean名称。
这样我们就可以避免为同一类型定义多个bean的问题。此注释通常与@Autowired注释一起使用。
对于具有多个参数的构造函数，我们可以将此注释与方法中的参数名称一起使用。

默认情况下，Spring bean自动装配已关闭。 Spring bean autowire默认值为“default”，
表示不执行自动装配。 autowire值“no”也具有相同的行为(不执行自动装配)。

为了展示(showcase)Spring Bean自动装配的使用，让我们创建一个简单的Spring Maven项目:

Spring @Autowired，Spring autowiring，@Autowired annotation

让我们逐个研究每个autowire选项。为此，我们将创建一个Model bean和一个服务类(我们将在服务类中注入模型bean)。