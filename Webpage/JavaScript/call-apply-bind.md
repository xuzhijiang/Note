### call() 和 apply()的不同

唯一的区别是:

- call() 分别获取任何函数的参数，也就是call接受的是一个参数列表
- apply() 将函数的参数作为数组而传入.也就是将所有参数组成一个array传入apply的

如果你想用一个数组而不是一个参数列表的时候，apply方法是非常方便的

### call

用`call()`, you can use a method belonging to another object.(你可以使用属于另一个对象的方法。)

```javascript
var person = {
    firstName:"John",
    lastName: "Doe",
    fullName: function() {
        return this.firstName + " " + this.lastName;
    }
}

var myObject = {
    firstName:"Mary",
    lastName: "Doe",
}

person.fullName.call(myObject);  // Will return "Mary Doe"
```

### apply

apply指的是Function构造函数原型对象上面的方法(Function.prototype.apply),这说明所有的函数都可以调用apply这样
一个方法,

Object.prototype.toString.apply("123");//"[object String]"

Function.prototype.apply: 借用函数的功能

### bind的使用

Function.prototype.bind: 创建绑定函数

bind() 最简单的用法是创建一个函数，使这个函数不论怎么调用都有同样的 this 值。

JavaScript新手经常犯的一个错误是将一个方法从对象中拿出来，然后再调用，希望方法中的 this 是原来的对象。(比如在回调中传入这个方法。）如果不做特殊处理的话，一般会丢失原来的对象。从原来的函数和原来的对象创建一个绑定函数，则能很漂亮地解决这个问题：

```javascript
this.x = 9; //"this"指向全局作用域
var module = {
  x: 81,
  getX: function() { return this.x; }
};

module.getX(); // 返回 81

var retrieveX = module.getX;
retrieveX(); // 返回 9, 在这种情况下，"this"指向全局作用域

// 创建一个新函数，将"this"绑定到module对象
// 新手可能会被全局的x变量和module里的属性x所迷惑

var boundGetX = retrieveX.bind(module);//返回值是一个函数,因为已经bind了对象，所以可以直接调用
boundGetX(); // 返回 81

// bind示例
function Point(x, y){
    this.x = x;
    this.y = y;
}
Point.prototype.move = function(x, y) {
    this.x += x;
    this.y += y;
}
var p = new Point(0,0);
var circle = {x:1, y:1, r:1};
var circleMove = p.move.bind(circle, 2, 1);//circleMove是一个函数
circleMove();
```