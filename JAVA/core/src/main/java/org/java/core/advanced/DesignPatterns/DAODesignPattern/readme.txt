DAO stands for Data Access Object.

DAO设计模式用于将数据持久性逻辑放在单独层中， 这样，
服务对于如何完成访问数据库的低级操作完全不了解。 这被称为逻辑分离原理。

With DAO design pattern, we have following components on which our design depends:
使用DAO设计模式，我们的设计依赖于以下组件：

从一层转移到另一层的模型。
接口提供灵活的设计。
接口实现是持久性逻辑的具体实现。

示例:

Book模型从一层转移到另一层。
BookDao界面提供灵活的设计和API实现。
BookDaoImpl具体类，是BookDao接口的一个实现。

Books								BookDao:							Main:
- ISBN									- GetAllBooks	<--Calls		  - Uses the interface
			<-------Transfers----  		- saveBook
- Name
										^
										|
										|
										|implements
										|
										|
								    BookDaoImpl:
								    	- getAllBooks
								    	- saveBook
								    	
使用DAO模式有许多优点:

1. 在更改持久性机制时，服务层甚至不必知道数据的来源。
例如，如果您正在考虑从使用MySQL转移到MongoDB，则只需要在DAO层中完成所有更改。

2. DAO模式强调应用程序的不同组件之间的低耦合。因此，View层不依赖于DAO层，
只有Service层依赖于它，即使是接口而不是具体实现。

3. 由于持久性逻辑是完全独立的，因此为单个组件编写单元测试要容易得多。
例如，如果您使用JUnit和Mockito来测试框架，那么很容易模拟应用程序的各个组件。

4. 当我们使用DAO模式中的接口时，它还强调“使用接口而不是实现”的风格，这是一种优秀的OOP编程风格。
DAO模式结论

使用DAO设计模式来强调保持持久性逻辑分离，因此，我们的组件松散耦合。								    	