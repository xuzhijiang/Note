#### 全局变量和局部变量

全局变量: 全局变量在js中比较特殊，他可以在程序的任何访问,在程序的任何地方都是可以改写的,3种定义全局变量的方式:

```javascript
var test = 'some value'; //js标准的定义全局变量的方法

window.test = 'some value'; //相当于变量是window对象上的一个属性

(function(){
    var a;//a是局部变量，不是全局变量
    test = 'some value';//写了变量的名字，但是没有使用var定义,这样的变量也会被定义到global上面去
    _config = {dd: ‘dd-value’};//_config是全局变量，可以在外面访问,但是由于是以_开头，所以我们人为的把他看做是protected变量，不要在外面直接访问.(其中dd默认就是字符串)
})();

!function(){
    function Modal(){

    }//Modal是局部函数对象，要想在外面访问，必须window.Modal = Modal;

    var a = 'aa';//局部变量

    b = 'bb';//全局变量
}();

function foo(){
    var tt = 'ty';//局部变量
    gg = 'gy';//局部变量
}

kk = 'k_value';//全局变量
window.kk === kk;//true
```

#### 封装-信息隐藏:

```javascript
function A(){
    var _config = ['A', 'B', 'C'];
    this.getConfig = function(){
        return _config;
    }
}
这个_config在外部是不可以使用，不可以通过this使用,外部通过getConfig来获取，这样就相当于开放api,我们人为的规定:_step1//有_开头的就是protected的，没有_开头的就是public的
```

#### 变量作用域

```javascript
var x = 10;
function foo(y){
    var z = 30;
    function bar(q){
        return x + y + z + q;
    }
    return bar;
}
var bar = foo(20);
bar(40);
```

执行第一行代码之前,首先创建global环境:

|     |     global环境   ||
| ------------- |:-------------:| -----:|
| record      | foo:&lt;function>  ||
|       | x:undefined       ||
|       | bar:undefined     ||
| outer |  null    ||

执行x = 10;

|     |     global环境   ||
| ------------- |:-------------:| -----:|
| record      | foo:&lt;function>  ||
|       | x:10       ||
|       | bar:undefined     ||
| outer |  null    ||

执行foo(20)之前，先创建foo环境:

|     |     foo环境   ||
| ------------- |:-------------:| -----:|
| record      | y:20  ||
|       |       ||
|       |      ||
| outer |  指向global环境    ||

开始执行foo(20)

|     |     foo环境   ||
| ------------- |:-------------:| -----:|
| record      | y:20  ||
|       |  z:undefined     ||
|       | bar:&lt;function>     ||
| outer |  指向global环境    ||

执行foo中的第一行语句:z = 30;

|     |     foo环境   ||
| ------------- |:-------------:| -----:|
| record      | y:20  ||
|       |  z:30     ||
|       | bar:&lt;function>     ||
| outer |  指向global环境    ||

执行完return bar;后

|     |     global环境   ||
| ------------- |:-------------:| -----:|
| record      | foo:&lt;function>  ||
|       | x:10       ||
|       | bar:&lt;function>     ||
| outer |  null    ||

执行bar(40)之前，先创建bar环境:

|     |     bar环境   ||
| ------------- |:-------------:| -----:|
| record      | q:40  ||
| outer |  指向foo环境    ||

顺着outer找x，y，z,之后返回值.

* 环境记录

    - 形参
    - 函数
    - 变量

* 对外部环境变量的引用outer

Try—catch词法环境:

```javascript
try{
    e = 'error'
    throw new Error();
}catch(e){
    function f(){alert(e);}
    
    (function(){alert(e);})();
    f();
}
```

f函数中的e指的是global中的e='error';
匿名函数的中e指的是catch(e)中的e;原理是outer指向
不同

With词法环境:
```javascript


var foo = 'abc';
with({
    foo: 'bar'
}){
    function f(){alert(foo);};
    (function (){alert(foo);})();
    f();
}
```
f函数中的foo指的是global中的foo='abc';
匿名函数的中foo指的是with中的foo;原理是outer指向
不同

```javascript
var x=10;
function foo(){
    alert(x);
}
function bar(){
    var x=20;
    foo();
}
bar();
```

##### 变量的生命周期和作用范围

1. 在其他语言中if语句，for语句，while语句，他会产生一个块级作用域
2. js没有块级作用域的,只有两种作用域:(全局作用域，函数作用域)
3. ES5使用词法环境来管理静态作用域:

##### 函数声明和函数表达式的区别：

在引擎中，函数声明的函数对象是提前创建的，函数表达式创建的函数对象是执行到这个语句create的，2种都会创建一个函数对象

#### 闭包

```javascript
Function add(){
    Var i = 0;
    Return function(){
        Alert(i++);
    };//函数表达式
}

Var f = add();
F();
F();
```

一个函数，在没有执行之前是scope，执行的时候就变成了outer

闭包就是:函数+函数的相关引用环境, add结果的返回函数就是一个闭包。
add结果是一个匿名函数，这个函数同时它又保存了外部的环境，
这个匿名函数有一个scope的指针指向了这个add环境

- 保存现场
- 做封装
