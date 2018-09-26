### JavaScript常用知识

```javascript
document.location === window.location; //true
function foo(a, b) {}
alert(foo === window.foo);// true
```

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