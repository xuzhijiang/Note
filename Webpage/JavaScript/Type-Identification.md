## 类型进阶

```javascript
var obj = {};
var arr = [];
var date = new Date();
```

原始类型(值类型):     
Number                         
String
Boolean
Undefined
Null

引用类型：Object

原始类型和引用类型的区别？

```javascript
var num1 = 123;
var num2 = num1;
num2 = 456;
console.log(num1);//123

var obj1 = {a:1};
var obj2 = obj1;
obj2.a = 3;
console.log(obj1.a);//3
```

数据类型种类(分为原始类型(值类型)和引用类型):

    Number String Boolean Object null Undefined

### Number

- 整数 八进制的数(0开头) 十六进制(0x开头，其他的可以大写或者小写)
- 浮点数 1.2 var num = 3.12e2
- 特殊值 NaN(Not a Number)
- Infinity(无穷,要分正负的,1/0,-1/0)

### String 

```javascript
var str = "";
var str2 = '';
var name = "hello"; 
var name2 = 'hello';
```

### Boolean 

true or false,都必须是小写的，大写会变为标识符

### Object是一组无序的键值对的集合

```javascript
1. var cat = {
    name: 'kitty',
    mew: function{
        console.log('喵喵喵');
    }
}

2.var dog = new Object();
```

### Null(这是一种类型）

    类型说明:
    值： null(只有一个值)
    出现场景：
    表示对象不存在
    var car = null;

Undefined(一种类型)

    值:undefined(只有这一个值)

出现场景:

1,  已声明未赋值的变量   var a; console.log(a);//undefined

2,  获取对象中不存在的属性的时候 var cat = {a:1,b:2};  console.log(cat.c);//undefined

3,  变量没有定义的时候

类型识别:

    typeof
    var num;
    typeof num;//undefined
    var num = 1;
    typeof num;//number
    var num = 'hello';
    typeof num;//string
    var num = true;
    typeof num;//boolean
    var num = null;//注意，很奇葩
    typeof num;//object,虽然表示一个不存在的对象，它也是一个对象，而不是Null
    var object = {a:1};
    typeof object;//object

number、boolean和string都有包装对象,分别是Number, Boolean, String

```javascript
var n = new Number(123); // 123,生成了新的包装类型
var b = new Boolean(true); // true,生成了新的包装类型
var s = new String('str'); // 'str',生成了新的包装类型
```

虽然包装对象看上去和原来的值一模一样，显示出来也是一模一样，但他们的类型已经变为object了！所以，包装对象和原始值用===比较会返回false,所以闲的蛋疼也不要使用包装对象！尤其是针对string类型！！！

```javascript
typeof new Number(123); // 'object'
new Number(123) === 123; // false

typeof new Boolean(true); // 'object'
new Boolean(true) === true; // false

typeof new String('str'); // 'object'
new String('str') === 'str'; // false
```

如果我们在使用Number、Boolean和String时，没有写new会发生什么情况？

此时，Number()、Boolean和String()被当做普通函数，把任何类型的数据转换为number、boolean和string类型(注意不是其包装类型）：

```javascript
var n = Number('123'); // 123，相当于parseInt()或parseFloat()
typeof n; // 'number'

var b = Boolean('true'); // true
typeof b; // 'boolean'

var b2 = Boolean('false'); // true! 'false'字符串转换结果为true！因为它是非空字符串！
var b3 = Boolean(''); // false

var s = String(123.45); // '123.45'
typeof s; // 'string'
```
总结一下:

- 不要使用new Number()、new Boolean()、new String()创建包装对象；
- 用parseInt()或parseFloat()来转换任意类型到number；
- 用String()来转换任意类型到string，或者直接调用某个对象的toString()方法；
- 通常不必把任意类型转换为boolean再判断，因为可以直接写if (myVar) {...}；
- typeof操作符可以判断出number、boolean、string、function和undefined；
- 判断Array要使用Array.isArray(arr)；
- 判断null请使用myVar === null；
- 判断某个全局变量是否存在用typeof window.myVar === 'undefined'；
- 函数内部判断某个变量是否存在用typeof myVar === 'undefined'。

类型识别的方法:
typeof
instanceof
Object.prototype.toString.call
constructor

typeof是一个操作符,而不是一个方法,它总是返回一个字符串

typeof("a")     //string
typeof "a"      //string

typeof 123; // 'number'
typeof NaN; // 'number'
typeof 'str'; // 'string'
typeof true; // 'boolean'
typeof undefined; // 'undefined'
typeof Math.abs; // 'function'
typeof null; // 'object'
typeof []; // 'object'
typeof {}; // 'object'
typeof {name: "jerry"};//"object"

typeof 可以识别标准类型(Null除外)

typeof function(){};//"function"
typeof [];//"object"
typeof new Date;//"object"
typeof /\d/;//"object"
function Person(){};
typeof new Person;//"object"

typeof 不能识别具体的对象类型(Function除外)

#### instanceof

能够判别内置对象类型:
```javascript
[] instanceof Array;//true
/\d/ instanceof RegExp;//true
```

不能判别原始类型:
```javascript
1 instanceof Number;//false
"jerry" instanceof String;//false
```

可以识别自定义对象类型及父子类型
instanceof 可以识别所有的对象类型

Object.prototype.toString.call

constructor
是构造这个对象的构造函数本身
在浏览器控制台console.dir(new Date());
我们用构造函数本身来识别这个对象是什么类型
//判断原始类型
注意：.会把原始类型转换成对应的对象类型
"jerry".constructor(取到的是字符串对象的构造函数,我们知道字符串对象的构造函数就是String)
"jerry".constructor === String;//true
(1).constructor === Number;//true
true.constructor === Boolean;//true
({}).constructor === Object;//true

constructor总结:
可以判别标准类型(Undefined/Null除外,因为Undefined和Null没有构造函数)
//判断内置对象类型
new Date().constructor === Date;//true
[].constructor === Array;//true
//判断自定义对象类型
function Person(name){
    this.name = name;
}
new Person("jerry").constructor === Person;//true
可以判断自定义对象类型

//获取对象构造函数名称
function getConstructorName(obj){
    return obj && obj.constructor && obj.constructor.toString().match(/function\s*([^(]*)/)[1];
}

getConstructorName([]) === "Array";//true

obj.constructor.toString().match(/function\s*([^(]*)/)[1];  //获取对象构造函数的名称

//将构造函数转换成字符串
obj.constructor.toString()  //"function Number(){[native code]}"

//提取Number
.match(/function\s*([^(]*)/)[1];//"Number"

obj.constructor就是为了保证后面的表达式(obj.constructor.toString().match(/function\s*([^(]*)/)[1])能够被执行的，obj.constructor如果存在，才会执行后面那一句，如果不存在，他就不执行了，如果不存在，后面的就不会执行
obj是为了保证如果我的入参是null or undefined时，他也能够正常返回，因为我们知道null和undefined他是没有constructor的，因此如果我传入null和undefined,直接去执行obj.constructor就会报错了，所以要做判断，如果是null或者undefined，就直接返回他们本身

#### 隐式类型转换 

js是一种弱类型语言，因此语言本身会做一系列的隐式类型转换，因此我们需要了解js会在什么情况下做隐式类型转换，这些转换的结果会是什么样子的，在这些隐式类型转换不满足我们的需求时，我们应该如何显示的进行我们的类型转换

![类型转换](../img/js_img/23.png)

### 运算符

* +运算符 

在做+运算的时候，会把数字转换成字符串，之后连接，
除了 +，其他运算符都会把字符串转为数字运算

* 比较运算符

==: 它会自动转换数据类型再比较，很多时候，会得到非常诡异的结果.
===: 它不会自动转换数据类型，如果数据类型不一致，返回false，如果一致，再比较.

由于JavaScript这个设计缺陷，不要使用==比较，始终坚持使用===比较
另一个例外是NaN这个特殊的Number与所有其他值都不相等，包括它自己：

`NaN === NaN; // false`

唯一能判断NaN的方法是通过isNaN()函数：

`·isNaN(NaN); // true`

* 逗号运算符

```javascript
if(a,b,c){}
a = (b=2, c=3, 4==4);//a最后的值是true，也就是4==4的结果
(s=a, b, c, ...., n);//s的值就是n的值，与前面的都没有关系
```

逗号运算符，之前所有的运算表达式都会执行，但整个语句的值是最后一个表达式的值

数字型直接量和字符串直接量要看Number和String原型链上有多少构造方法就知道了
所有的直接量用.(点号）去调用某一个方法的时候，js运行环境会将这个直接量转换成对应的对象类型，来调用对象类型的方法。

if语句:系统会把括号中的result强制的转换成boolean值来判断, 由于 && 的功能：
如果前面的表达式的隐式转换为boolean的结果是false，那么后面的都不会计算了，直接为false

隐式类型转换的结果表, 有一些是不符合预期的：

    type        原值      Boolean   Number  String   Object
    Undefined undefined               NaN
    Null                              0
    String      ""          false
    非空字符串在任何情况下转换成boolean都是true
    带有非数字("1a")的字符串，转成Number是NaN
    Number      Nan(特殊的数字类型值NaN)转换成Boolean是false
    Object      {}          true      NaN
    所有的对象转成Boolean都是true
    all对象转成Number是NaN

当隐式类型结果转换不满足我们的预期时，我们应该进行怎么样的显示类型转换。
Number(string) + 10

显示类型转换的方法:
Number()转成数字,String()转换成字符串,Boolean()转换成boolean
parseInt(),parseFloat() 将字符串转换成数字整型和浮点数
!,  !!(!!取到所对应的对象的boolean值)