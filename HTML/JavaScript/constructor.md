## 构造函数

### this关键字

在js中，this指的是拥有当前代码的对象，当用在函数中的时候，this的值是指拥有这个
函数的对象,当一个函数在没有拥有者对象的情况下被调用的时候，this的值就变成了全局对象，在浏览器中这个全局对象就是window对象。

```javascript
function myFunction() {return this;}
myFunction(); // will return the window object(全局对象:)
```

将函数作为一个方法被调用,也就是作为对象方法被调用:

```javascript
var myObject = {
    firstName:"John",
    lastName: "Doe",
    fullName: function () {
        return this.firstName + " " + this.lastName;
    }
}
//fullName严格被称为方法，而不是函数
myObject.fullName(); //will return "John Doe"
```

fullName方法是一个函数.这个函数属于对象myObject,myObject是这个函数的拥有者，叫做this的东西就是拥有这些js代码的对象,在这个例子中，this就是myObject

如果函数调用前面带有new关键字，则它是构造函数调用。看起来你创建了一个新函数，但由于JavaScript函数是对象，你实际上创建了一个新对象,也就是构造函数调用会创建一个新对象。 新对象从其构造函数继承属性和方法。构造函数中的this关键字没有值。
它的值将是调用函数时创建的新对象。以下就是一个函数构造器:

```javascript
function myFunction(arg1, arg2) {
    this.firstName = arg1;
    this.lastName  = arg2;
}
//创建一个新的对象
var x = new myFunction("John", "Doe");
x.firstName;// Will return "John"

function Point(x, y){
    this.x = x;
    this.y = y;
    this.move = function(stepX, stepY){
        this.x += stepX;
        this.y += stepY;
    }
}
var point = new Point(1,1);
```

`Point(1,1)`就是普通的函数调用,`this`是`window`,`new Point(1,1);`就是构造函数的调用
,当我们进入函数的时候，会传入`this`这个空对象,之后给`this`这个空对象增加一个属性x，值为1,....,构造函数的返回结果相当于返回this这个对象
