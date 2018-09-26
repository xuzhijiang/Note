## 函数的参数

### 实参数量少于形参

```javascript
function add(num0, num1){}

var x = add(2);//num0=2,num1=undefined

var y = add(2,3,4);//num0=2,num1=3,
```

Note: 调用函数的时候，有一个隐藏的变量arguments,例如`add(200,300,400);`调用的时候，
arguments为: 0: 200，1: 300，2: 400，length: 3

### 参数传递

- 参数为原始类型:值传递
- 参数为对象类型:引用传递(和java一样)

### 函数调用模式

函数调用时，在函数内部会自动生成2个parameters: this和arguments

根据函数调用时this参数做一个分类4类:

- 构造函数的调用模式
- 方法调用模式
- 函数调用模式
- apply(call)调用模式

1, 构造函数的调用模式:

`new Car('LandRover')`中的this指向创建出的对象

2，方法调用模式：也就是对象调用方法,方法就是作为对象的属性,方法内部的this是指向对象本身的

3, 函数调用模式

在函数调用模式当中，在函数内部创建的函数，在这个函数调用时，函数内部this它的值仍然是指向window这个对象，而不是它的上级的对象。

```javascript
var myNumber = {
    value: 1,
    double: function(){
        var helper = function(){
            this.value = add(this.value, this.value);//this指向window
        }
        helper();
    }
}
```

double就是作为方法被调用，也就是2，作为方法调用时，方法内部的this就是指向对象本身的。

```javascript
//解决方法一:
var myNumber = {
    value: 1,
    double: function(){         
        this.value = add(this.value, this.value);//this指向myNumber
    }
}

//解决方法二: 闭包
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
```
任何在函数内部定义的子函数，他在调用是，函数内部的 this是window，而不是上一级对象