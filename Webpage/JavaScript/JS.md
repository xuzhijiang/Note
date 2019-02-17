#### BOM

页面需要包含一些基本信息来描述这个页面
width=device-width;之后设置initial-scale=1.0;也就是初始缩放为1.0，当写了这个值的时候，这个网站就不会被缩小，这样设置就不会缩小了。user-scalable=no;防止用户手动缩放,一般情况下采用了响应式这种方式的话，我们不需要用户手动缩放的
我们可以用display:none;display:block;去控制他们的显示或者隐藏
避免使用使用import方式引入css，因为每个import都会产生一个同步的请求，就是第一个请求完成后再去请求第二个请求

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

function Car(type, color){this.type = type;this.color = color;}

Car.prototype.start = function(){}

Car.prototype.stop = function(){}

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
//构造函数来创建函数(和eval()一样糟糕）

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
