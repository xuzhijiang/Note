## 迭代器(iterator)

>迭代器(iterator)是用来遍历集合的。

### 如何实现?

>如果我们直接把node返回给用户，让用户自己从node获取数据，不就可以实现遍历了吗？我们可以提供一个getFirstNode()方法，
然后按照类似以下代码片段进行遍历：

```java
Node node=linkedList.getFirstNode();
while(node!=null){
     Object data=node.getData();
     //. . . 操作数据
    node=node.getNext();//获取下一个node
}
```

>这样的方式，的确是可以遍历链表中的所有元素，但是却不是一个好的设计方式，因为我们把链表的基础数据
结构Node直接暴露给用户了，普遍的做法就是利用迭代器(iterator)来实现链表的迭代功能。

