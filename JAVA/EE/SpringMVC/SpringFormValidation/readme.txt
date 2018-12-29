## Spring Validation(验证) Example – Spring MVC Form Validator

当我们在任何Web应用程序中接受用户输入时，就有必要对它们进行验证。 
我们可以使用JavaScript验证客户端的用户输入，但是还需要在服务器端验证它们，
以确保我们正在处理有效数据，以防用户禁用javascript。

### Spring Validation

Spring MVC Framework默认支持JSR-303规范，我们只需要在Spring MVC应用程序中
添加JSR-303及其实现依赖项。 Spring还提供了@Validator注释和BindingResult类，
通过它我们可以在“控制器” 请求处理程序方法中获取  由Validator实现引发的错误。

我们可以通过两种方式创建custom validator implementations - 

	1. 第一种是创建一个注解，这个注解确认JSR-303规范并实现它的Validator类。
	2. 第二种方法是实现org.springframework.validation.Validator接口，
	并使用@InitBinder注释将其设置为Controller类中的验证器(validator)。

	让我们在Spring Tool Suite中创建一个简单的Spring MVC项目，
	我们将使用它的实现工件hibernate-validator来使用JSR-303规范。
	
	我们将使用基于注解的表单验证，并基于JSR-303规范标准创建我们自己的自定义验证器。
	
	我们还将通过实现Validator接口创建自己的自定义验证器类( custom validator)，
	并在其中一个控制器处理程序方法中使用它。

