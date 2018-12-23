### arguments

Array-like(like array but not array)

- arguments[index]
- arguments.length

arguments converts to array:

```javascript
function add(i, j){
    var arr = Array.prototype.slice.apply(arguments);
    //可以调用forEach说明arr确实是一个数组
    arr.forEach(function(item){
        console.log(item);
    });
}
add(1, 2, 3);
```

`.slice`除了通过`Array.prototype`访问当然还可以通过对象直接量访问:

`[].slice.call(arguments)`

#### arguments.callee

```javascript
console.log(
    (function(i){
        if(i==0){
            return 1;
        }
        //arguments.callee在函数内部指向函数本身，使用场景是匿名函数递归调用
        return i * arguments.callee(i-1);
    })(5)
);
```