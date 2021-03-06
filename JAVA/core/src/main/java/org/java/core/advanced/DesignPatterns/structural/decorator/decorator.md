### 为什么会出现这个装饰器设计模式

给对象扩展行为的方法有两种，一种是通过继承，通过使用继承，可以使得子类有自己的行为，还可以获得父类的行为方法，
但是使用继承是静态的，在编译的时候就已经决定了子类有哪些行为。另一种就是使用装饰器模式,
这是一种动态的方式，我们可以在程序中动态的决定和控制。

### 认识装饰者模式

例如为咖啡店设计一个点咖啡的程序，采用饮料为主体，在运行时以调料来‘装饰’饮料，
如果顾客要摩卡和奶泡的深焙咖啡，那么:

1. 拿一个深焙咖啡（DarkRoast）对象
2. 以摩卡（Mocha）对象装饰它
3. 一奶泡（Whip）对象装饰它
4. 调用cost方法，并依赖委托（delegate）将调料的钱加上去,就可以得出总的价格

### 总结

1. 装饰者可以提供比继承更多的灵活性。
2. 可以通过一种动态的方式来扩展一个对象的功能，在运行时选择不同的的装饰器，从而实现不同的行为。
3. 具体组件类和装饰类可以独立变化，用户可以根据自己的需要增加具体的组件类和装饰类，
原有的代码无需改变，符合”开闭原则“。
4. 但是也会产生很多的小对象，增加了系统的负责性。
5. 建议在不影响其他对象的时候使用，以动态，透明的方式给单个对象添加职责。
