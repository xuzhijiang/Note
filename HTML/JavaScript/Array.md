## 数组

Array的类型也是Object对象，判断Array要使用Array.isArray(arr)；

### 创建数组

```javascript
var array = new Array();
var array = []; //用的更多
var array = [163, "netease", {color:"red"}, [], true];//可以有多种类型
```

### 常用方法(-)

- reverse
- sort
- unshift
- shift
- pop
- push
- splice(拼接)
- indexOf
- forEach
- split

这些方法都改变了原来的array

#### reverse

```javascript
arr.reverse();//把数组中的元素反转`
```

#### sort

```javascript
arr.sort([compareFunction]);//排序的，里面的参数是回掉函数
var students = [{id:1,score:80},{id:2,score:50},{id:3,score:70}];
var byScore = function(a,b){return b.score - a.score;};//返回小于0
students.sort(byScore);
//sort: [{id:1,score:80},{id:3,score:70},{id:2,score:50}];
//sort方法直接改变了原来students这个数组。
var studentNames = ["wq","xl","gp"];
studentNames.sort(); //studentNames;//["gp","wq","xl"];
//比较unicode的编码的顺序来比较
```

#### unshift

```javascript
arr.unshift(element1,...,elementsN);
var students = [{id:1,score:80},{id:2,score:50},{id:3,score:70}];
students.unshift({id:4,score:90},{id:5,score:60});
// [
//     {id:5,score:60},
//     {id:4,score:90},
//     {id:1,score:80},    
//     {id:2,score:50},    
//     {id:3,score:70},
// ];
```

#### shift

```javascript
arr.shift();
var students = [
    {id:1,score:80},    
    {id:2,score:50},    
    {id:3,score:70}
];
students.shift();
// [{id:2,score:50},{id:3,score:70}];
//获取第一个元素，并且原来的第一个元素会被删掉
```

#### pop

```javascript
arr.pop();
var students = [{id:1,score:80},{id:2,score:50},{id:3,score:70}];
students.pop();
//[{id:1,score:80},{id:2,score:50}];
//获取last一个元素，并且原来的last一个元素会被删掉
```

#### push

```javascript
arr.push(element1,...,elementsN);
var students = [{id:1,score:80},{id:2,score:50},{id:3,score:70}];
students.push({id:4,score:90},{id:5,score:60});
// [{id:1,score:80},{id:2,score:50},{id:3,score:70},
//{id:4,score:90},{id:5,score:60}];
```

#### splice

```javascript
arr.splice(index, howMany[,ele1[,...[,eleN]]]);
//在index为某一个值得位置开始删，删多少,之后添加最后的参数中的元素

var students = [{id:1,score:80},{id:2,score:50},{id:3,score:70}];
students.splice(1,2,{id:4,score:90});//在index为1个位置开始删，删除2个，之后插入数据{id:4,score:90}
// [{id:1,score:80},{id:4,score:90}];
students.splice(1,1);//达到删除的效果
students.splice(1,0,{id:4,score:90});//达到add的效果
```

#### indexOf

```javascript
arr.indexOf(searchElement[,fromIndex=0]);//fromIndex默认为0，一般我们都不传第二个
var telephones = [110,120,140];
telephones.indexOf(120);//1
telephones.indexOf(119);//-1
```

#### forEach

arr.forEach(callback[,thisArg])
var score = [60,70,80];
var newScores = [];
var addScore = function(item,index,array){
    newScores.push(item+5);
};
score.forEach(addScore);
newScores;//[65,75,85]
//不改变数组score本身

arr.forEach(callback[, thisArg]);
// 把array中all elements都遍历执行，都传给这个callback执行，第二个参数，
// 是让callback中的this执行thisArg这个对象
var students = [{id:1,score:80},{id:2,score:50},{id:3,score:70}];
var editScore = function(item, index, array){item.score += 5;};
students.forEach(editScore);//传入的参数个数小于3个，所以editScore就表示item
,而且第一个值必须是数组的元素
并不改变这个数组本身

#### split

"aaa;bbb;ccc;ddd".split(";");//用;分割，之后得到数组

### 常用方法(二)

- slice
- concat
- join
- map
- reduce

slice,concat,join,map,reduce,共同的特点是对原来的数组没有修改

#### slice

arr.slice(begin[,end]);
// 从begin到end拷贝，但是包括begin，不包括end
var students = [{id:1,score:80},{id:2,score:50},{id:3,score:70}];
var newStudents = students.slice(0,2);
// [{id:1,score:80},{id:2,score:50}];
//只是复制了一份出来，并没有改变原来的结果

#### concat

arr.concat(value1,...,valueN);可以传入number，string，不一定非要是数组
//连接
var students1 = [{id:1,score:80}];

var students2 = [{id:2,score:80}];

var students3 = [{id:3,score:70}];

var newStudents = students1.concat(students2,students3);
{
    {id:1,score:80},
    {id:2,score:80},    
    {id:3,score:70}
}

#### join

arr.join([separator]);
var email = ["aaa","bbb","ccc"];
email.join(";");//"aaa;bbb;ccc;ddd"

#### map

arr.map(callback[,thisArg]);
var score = [60,70,80];
var addScore = function(item,index,array){
    return item + 5;
};
score.map(addScore);//返回一个array,是[65,75,85]

#### reduce

arr.reduce(callback,[initialValue]);
var sum = function(previousResult,item,index,array){//前面的结果，当前这个元素，当前这个元素的index，数租本身
    return previousResult + item.score;
}
students.reduce(sum,0);