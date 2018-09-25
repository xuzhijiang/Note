### JavaScript常用知识

`document.location === window.location; //true`

#### 原型继承

```javascript
(function(){
    var proto = {
        action1: function(){},
        action2: function(){}
    };
    var obj = Object.create(proto);
})();

模拟Object.create(proto),实现clone：
var clone = (function(){
    var F = function(){};
    return function(proto){
        F.prototype = proto;
        return new F();
    };
})();
```

#### 类继承

```javascript
(function(){
    function ClassA(){}
    ClassA.classMethod = function(){}
    ClassA.prototype.api = function(){}
    
    function ClassB(){
        ClassA.apply(this, arguments);
    }
    ClassB.prototype = new ClassA();
    ClassB.prototype.constructor = ClassB;
    ClassB.prototype.api = function(){
        ClassA.prototype.api.apply(this, arguments);
    }
    var b = ClassB();
    b.api();
})();
```

class继承是我们去模拟其他语言的继承
原型继承是js中固有的特性
使用obj = Object.create(proto) 就可以基于原型创建出一个对象
obj对象就以proto对象为原型
obj这个对象有一个隐士的proto一个指针去指向proto这个对象，当我去访问obj对象上的属性的时候，他会顺着这个原型的原型链去查找的，这个就是原型继承的方式

#### BOM

页面需要包含一些基本信息来描述这个页面
width=device-width;之后设置initial-scale=1.0;也就是初始缩放为1.0，当写了这个值的时候，这个网站就不会被缩小，这样设置就不会缩小了。user-scalable=no;防止用户手动缩放,一般情况下采用了响应式这种方式的话，我们不需要用户手动缩放的
我们可以用display:none;display:block;去控制他们的显示或者隐藏
避免使用使用import方式引入css，因为每个import都会产生一个同步的请求，就是第一个请求完成后再去请求第二个请求

#### 函数

```javascript
function foo(a, b) {}
alert(foo === window.foo);// true
```

The This keyword:

In javascript,the thing called this, is the object that “owns” the current code. The value of this, when used in a function, is the object that owns the function.

The Global Object:

function myFunction() {
    return this;
}

myFunction(); // will return the window object

When a function is called without an owner object, the value of this becomes the global object.In a web browser the global object is the browser window.
This example returns the window object as the value of this:

Invoking a Function as a Method:（Object Method）

The following example creates an object (myObject), with two properties (firstName and lastName), and a method (fullName):

var myObject = {
    firstName:"John",
    lastName: "Doe",
    fullName: function () {
        return this.firstName + " " + this.lastName;
    }
}

myObject.fullName(); //will return "John Doe"

the fullName method is a function.The function belongs to the object.myObject is the owner of the function.the thing called this, is the object that “owns” the JavaScript code.In this case the value of this is myObject.
Invoking a function as an object method, causes the value of this to be the object itself.

Invoking a Function with a Function Constructor
If a function invocation is preceded with the new keyword, it is a constructor invocation.
It looks like you create a new function, but since JavaScript functions are objects you actually create a new object:

This is a function constructor:

function myFunction(arg1, arg2) {
    this.firstName = arg1;
    this.lastName  = arg2;
}

// This creates a new object
var x = new myFunction("John", "Doe");
x.firstName;// Will return "John"

A constructor invocation creates a new object. The new object inherits the properties and methods from its constructor.
The this keyword in the constructor does not have a value.
The value of this will be the new object created when the function is invoked.

Functions are Object Methods
With call(), you can use a method belonging to another object.

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

#### 函数的参数

* 实参数量少于形参

function add(num0, num1){}
var x = add(2);//num0=2,num1=undefined
var y = add(2,3,4);//num0=2,num1=3,
调用函数的时候，有一个隐藏的变量arguments
arguments:
		0: 2
		1: 3
		2: 4
		length: 3

参数为原始类型:值传递
参数为对象类型:引用传递(和java一样)

构造函数
function Point(x, y){
	this.x = x;
	this.y = y;
	this.move = function(stepX, stepY){
		this.x += stepX;
		this.y += stepY;
	}
}
var point = new Point(1,1);   //Point(1,1)就是普通的函数调用
//new Point(1,1);就是构造函数的call
当我们进入函数的时候，会传入this这个空对象
之后给this这个空对象增加一个属性x，值为1,....
构造函数的返回结果相当于返回this这个对象

原型
function Point(x, y){
  this.x = x;
  this.y = y;
  this.move = function(stepx, stepy){
      this.x += stepx;
      this.y += stepy;
  }
}
//这样的构造函数有什么问题呢?
var point = new Point(1,1); {x:1,y:1,move:function{}}
var point2 = new Point(2,2);  {x:1,y:1,move:function{}}
var point3 = new Point(3,3);   {x:1,y:1,move:function{}}
//造成了每个对象都有自己的move function，浪费，这就引出了原型的概念

Point.prototype.move = function(stepx, stepy){
    this.x += stepx;
    this.y += stepy;
};
function Point(x, y){
  this.x = x;
  this.y = y;
}
var point = new Point(1,1); {x:1,y:1}
var point2 = new Point(2,2);  {x:2,y:2}
var point3 = new Point(3,3);   {x:3,y:3}//构造出来的对象有一个隐藏的属性，指向构造函数的原型的公共属性

1. 函数声明与函数表达式，对象实例化的区别:
-函数声明(可以在声明之前调用)
function add(i, j){
	return i + j;
}
-函数表达式(可以在表达式之后调用)
var add = function(i, j){
	return i + j;
}

2. 对象实例化与函数声明，函数表达式的区别:
(function(){
	var i = 10;
	function add(j){
		j = 1;
		console.log(i+j);
		debugger;
	}
	add(1);
})();
//可以访问到父函数(闭包引用环境)上的变量
//11

(function(){
	var i = 10;
	var add = new Function("j", "console.log(i+j);debugger;");
	add(1);
})();
会抛出i is undefined error
通过对象实例化的方式不可以访问到父函数上的变量(也就是引用环境里面的变量)
通过对象实例化的方式定义的函数都会定义在全局作用域,因此无法访问到他的父函数上面的所有的变量

可以在console打印某一个对象的属性(console.dir(add);)，实例对象是没有prototype原型属性的，这样一个属性的，这说明原型属性prototype是函数的专利，只有函数有prototype这样一个属性

构造函数和普通函数的区别：
本质上是没有区别
我们前面定义的add的函数也可以用new来创建对象
虽然没有本质区别，但是我们有默认的习惯:
1, 构造函数通常会有this指定的实例属性，原型对象上通常有一些公共方法
比如start，stop等，而通常普通方法是不需要有这些属性和方法的，
2，构造函数命名通常首字母大写， because 构造函数代表了一系列对象 属性以及行为的封装，这些在oop中成为类型，实际上构造函数代表了 一系列对象的类型，
我们通过用首字母大写表明这是一种类型，比如Boolean等

我们所定义的函数在chrome调试器中的结构：
我们通过console.dir(add);来打印一下我们所定义的add函数
有2个重要的属性:
第一个是:prototype(函数的原型对象属性):
这个prototype上面只有constructor和__proto__这样一个原型链属性，其他没有任何有意义的属性
第二个很重啊哟的属性就是他的原型链属性
__proto__:
这个属性是一个隐藏的属性，在编码时我们不能通过显示的方法去调用。
__proto__这样的一个原型链属性，但是原型链属性上面的方法可以被我们所创建的
对象所直接调用到，这个所隐藏的原型链属性实际上来自于我们的Function构造函数的原型对象prototype,我们可以看到这两部分是完全一模一样的
__proto__这样的一个隐藏的属性实际上来自于实例化生成add这样一个函数时，他引用了Function构造函数上的原型对象属性prototype，因此这两个是一模一样的,
Function构造函数

function Car(type, color){
	this.type = type;
	thie.color = color;
}

Car.prototype.start = function(){
	
}

Car.prototype.stop = function(){
	
}

函数调用模式:

函数调用时，在函数内部会自动生成2个parameters，this、arguments

根据函数调用时this参数做一个分类4类:

1,  构造函数的调用模式
2， 方法调用模式
3， 函数调用模式
4， apply(call)调用模式

1, 构造函数的调用模式:
new Car('LandRover')中的 this指向创建出的对象

2，方法调用模式：也就是对象调用方法,方法就是作为对象的属性,方法内部的this是指向对象本身的

3, 函数调用模式
	在函数调用模式当中，在函数内部创建的函数，在这个函数调用时，函数内部this它的值仍然是指向window这个对象，而不是它的上级的对象，要注意
	//
	var myNumber = {
			value: 1,
			double: function(){
				var helper = function(){
					this.value = add(this.value, this.value);//this指向window
				}
				helper();
			}
		}
	怎么解决呢?
	function就是作为方法被调用，刚刚讲过，也就是2，作为方法调用时，方法内部的this就是指向对象本身的。
	var myNumber = {
		value: 1,
		double: function(){			
			this.value = add(this.value, this.value);//this指向myNumber
		}
	}
	or 使用闭包
	var myNumber = {
			value: 1,
			double: function(){
				var that = this;
				var helper = function(){
					that.value = add(that.value, that.value);
				}
				helper();
			}
		}

	任何在函数内部定义的子函数，他在调用是，函数内部的 this是window，而不是上一级对象

### The Difference Between call() and apply()

The only difference is:

- call() takes any function arguments separately.
- apply() takes any function arguments as an array.

The apply() method is very handy if you want to use an array instead of an argument list.

Note: While the syntax of this function is almost identical to that of apply(), the fundamental difference is that call() accepts an argument list, while apply()accepts a single array of arguments.

### apply

apply指的是Function构造函数原型对象上面的方法(Function.prototype.apply),这说明所有的函数都可以调用apply这样
一个方法,

Object.prototype.toString.apply("123");//"[object String]"

Function.prototype.apply: 借用函数的功能

### bind的使用

Function.prototype.bind: 创建绑定函数

bind() 最简单的用法是创建一个函数，使这个函数不论怎么调用都有同样的 this 值。

JavaScript新手经常犯的一个错误是将一个方法从对象中拿出来，然后再调用，希望方法中的 this 是原来的对象。（比如在回调中传入这个方法。）如果不做特殊处理的话，一般会丢失原来的对象。从原来的函数和原来的对象创建一个绑定函数，则能很漂亮地解决这个问题：

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

闭包:
在函数内部定义了一个函数，这个函数调用到了父函数上面的临时变量，
这个临时变量就会被放到闭包里面
闭包作用:
属性隐藏，对象封装
记忆函数(可以减少函数的计算量，看例子)
function (){
	var a = 0;
	function b(){
		a = 1;
		debugger;
	}
	b();
}

// curry函数柯里化
function add(value){
   var helper = function(next){
      value = typeof(value)==="undefined"?next:value+next;
      return helper;
   }
   helper.valueOf = function(){
     return value;
   }
   return helper
}

var add = new Function('a, b', 'return a + b');//我们通过new Function()
//构造函数来创建一个函数，这时可以明显看出函数是对象：
alert(add(1, 3));//4
//在这段代码中，毫无疑问add()是一个对象，因为它是由构造函数创建的。这里并不推荐使用Function()
//构造函数来创建函数（和eval()一样糟糕）

alert(add.name);//anonymous

// 具名函数表达式
var add1 = function add1(a, b) {
    return a + b;
};
alert(add1.name);//add1

// 匿名函数表达式，又称匿名函数
var add2 = function (a, b) {
    return a + b;
};
alert(add2.name);//add2

##### sublime usage

    (ctrl+shift+p ssp，把语法设置为python)
    ctrl+p(可以快速找到某文件)
    ctrl+G(跳到某一行)
    ctrl + R(可以定位到一个具体的js函数名，css的选择器,python 函数..)
    ctrl+shift+p
    alt+f3 选择全部的变量。
    Ctrl + shift + k:删除一行.
    Ctrl + L,select line.
    Ctrl + end:go to the last line.
    Ctrl + home:go to the first line.
    Ctrl + shift + enter: insert line before
    Ctrl + enter: insert line after
    Ctrl + m: jump to matching brackets
    F12: go to definition
    Ctrl + shift + f: find in files
    ctrl+n: create new file
    ctrl+M:find matching bracket
    sources →  ctrl + o(查找某一个文件), ctrl+shift+o(查找函数)
    esc调出console
    console.dir(function name);

#### google search

搜文档： max site:http://python.org
问问题： how to find max value in a list site:http://stackoverflow.com