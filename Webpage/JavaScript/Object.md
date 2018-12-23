#### 对象

* 原生对象

    Boolean
    String
    Number
    Object
    Function
    Array
    Date
    RegExp
    Error
    JSON
    Math
    arguments

* 宿主对象

    document
    navigator
    window
    ....

* 浏览器扩展对象(跟浏览器厂家有关)

    ActiveXObject
    ...

#### JS中如何创建对象:
```
var o = {"id": "0",
        "imgurl": "asmPositionImg/51.jpg",
        "superscript": "0"}
typeof o;//object
o.id;//"0"
o;//{id: "0", imgurl: "asmPositionImg/51.jpg", superscript: "0"}

var car = new Object();
var car = {};//这个对象是空的

var car = {
    color : "red",
    run : function(){alert("run")}
};

访问对象属性: 
car.color;
car["color"];

修改对象属性
car.color = "white";

增加对象属性:
car.type = "suv";

运行对象方法：
car.run();
car["run"]();

增加对象方法：
car.stop = function(){alert("stop")};

修改对象方法：
car.run = function(){alert("run2")};

delete car.color;
car.color; //undefined

var num = new Number(123);
num.constructor;//得到num对象的构造函数

var num = new Number(123);
num.toString();//"123"

obj.valueOf();//获取这个对象的原始值
var num = new Number(123);
num.valueOf();// 123

obj.hasOwnProperty();
car.hasOwnProperty("color");//true
car.hasOwnProperty("logo");//false
//还可以验证这个属性是不是通过继承得来的,返回false的话，就是继承而来的，不是他自己的
```

原型对象: 以一个现成的对象为原型来构造对象

由原型对象构造出对象的方法:

1, 设置对象的原型:

> Object.create(proto[,propertiesObject])

- proto  一个对象，他会作为新创建对象的 原型对象
- propertiesObject   对象上新定义的属性(用的不多)

* 先定义原型对象
```
var landRover = {
    name: 'landRover',
    start: function(){
        alert(this.logo + ' start');
    },
    run: function(){
        alert(this.logo + ' run');
    },
    stop: function(){
        alert(this.logo + ' stop');
    }
};
```

* 使用定义好的原型对象创建新的对象

```
var landWind = Object.create(landRover);
landWind.logo = 'landWind';
landWind.start();
```

2. 构造函数的方式创建对象

- 函数可以作为构造函数，可以用函数来创建自定义的对象

- 函数有个属性叫做prototype,使用prototype设置原型

- 这个prototype属性呢就是这个自定义对象的原型

- 使用new关键字作用于函数，创建对象


Car构造函数
```javascript
function Car(logo){
    this.logo = logo || 'unknown name';
}

//设置Car的prototype属性
Car.prototype = {
    start: function(){
        alert(this.logo + ' start');
    },
    run: function(){
        alert(this.logo + ' run');
    },
    stop: function(){
        alert(this.logo + ' stop');
    }
}
//构造函数
var landRover = new Car('landRover');
var landWind = new Car('landWind');
landRover.start();

使用new key word来创建的时候，分为3步:

1. create a 新的Car 类型的对象landRover
2. 设置landRover的_proto_,_proto_设置成构造函数Car的prototype的属性(很重要), _proto_这个属性是隐式的属性，是不能够在编程的时候被修改的
3. 使用landRover作为this去执行这个构造函数Car.//Car.apply(landRover,arguments)

note: landRover.constructor;//就可以找到这个landRover对象是由哪个方法构建出来的。
```

#### 原型链

```javascript
//Car构造函数
function Car(logo){
    this.logo = logo || 'unknown name';
}
//设置Car的prototype属性
Car.prototype = {
    start : function(){
        console.log('%s start', this.logo);
    },
    run : function(){
        console.log('%s running', this.logo);
    },
    stop : function(){
        console.log('%s stop', this.logo);
    }
}

//landRover构造函数
function landRover(serialno){
    this.serialNumber = serialno;
}
landRover.prototype = new Car('landRover');
//把这个对象赋值给landRover的prototype
//landRover这个构造函数的原型不再是Object，而是一个 Car类型的对象

创建landRover对象
var landRover1 = new landRover(1000);
var landRover2 = new landRover(1001);
console.log(landRover1.serialNumber);//1000
alert(landRover1.logo);//landRover
```

```javascript
Car.prototype = new Object();
alert(Car.prototype.__proto__ === Object.prototype); //car.prototype的原型(_proto_)是Object.prototype

原型链作用,对象的属性的访问，修改，删除和原型链都直接相关
js中访问对象的属性的时候，会优先在对象的本身去查找，那么对象本身没有定义这个属性,
就会顺着这个链去查找，直到找到为止
找landRover1的toString方法，会顺着原型链去查找，直到找到了Object.prototype上有

修改和删除只能修改和delete对象自身的属性，
landRover1上的属性的删除和修改只会影响到landRover1，不会影响到landRover2，
也不会影响到原型上的属性

landRover1.logo = 'landWind';//会在自身创建logo属性来赋值，而不是修改_proto_上的logo
删除属性
delete landRover1.logo;
delete landRover1.name;
//再次删除之前已经删除过的属性
delete landRover1.logo;//没有效果，不会删除掉_proto_上的logo属性
对于landRover2来说，仍然可以访问到logo属性

hasOwnProperty
每一个对象都有一个hasOwnProperty method,
这个方法来自于Object.prototype上，会判断传入的属性是否是对象自身的属性，返回true和false
landRover2.hasOwnProperty('logo');//false

函数function在JS中也是对象，Car是可以通过new Function()来创建出来的
Car既可以访问Function.prototype上的属性，也可以访问Object.prototype上的属性，
也有hasOwnProperty这样的方法的.

Car.__proto__ === Function.prototype//true
Function.prototype.__proto__ === Object.prototype//true
```