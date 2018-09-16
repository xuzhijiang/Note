#### css引用标签link中的rel属性有什么作用?

> Defining Relationships. The <link> element creates a link between your HTML document and an external resource. The rel attribute specifies the relationship between the two documents.

> Search engines may also use the information in the ‘rel' attribute to determine how to index or display the page.

#### link标签中的rel=dns-prefetch是什么意思？

* It is a way to speed up web pages by pre-resolving DNS.
* Use of rel=dns-prefetch suggests a browser should resolve the DNS of a specific domain prior to it being explicitly called.

```html
<link rel="dns-prefetch" href="https://cdn.varvy.com">
```
* I want to resolve a domain name before it is called

* The domain name is "cdn.varvy.com"

#### css !important rule

如果将！important添加到不太具体的选择器CSS声明中，它将具有优先级。

#### css selector category

##### Simple selectors

##### Combinators

###### Child combinator

The > combinator selects nodes that are direct children of the first element.

Syntax: A > B

Example: ul > li will match all <li> elements that are nested directly inside a <ul> element.

###### Descendant combinator

The   (space) combinator selects nodes that are descendants of the first element.

Syntax: A B

Example: div span will match all <span> elements that are inside a <div> element.

#### CSS

##### Display

<!--  display: block|inline|inline-block|none -->

inline: 默认宽度是内容的宽度,高度是内容高度,不可设置宽高(改为inline-block就可以设置宽高了)，同行显示, 默认情况下(没有设置display属性)display是inline的元素 span,a,em, label,cite.... 

block是:一个element就占一行的空间

默认情况下(没有设置display属性)display是block的元素: div,p,h1-h6,ul,li,form...

block: 默认宽度是父元素的宽度,默认高度是元素高度,可以设置宽高的，换行显示 

像字体什么的，如果没有设置，就是默认继承父元素的值.
注意：text-align不能作用于block元素，必须要把block变成inline-block元素才可以.
<!--  text-align 只能对行级元素(inline)起效果 -->

<!--  inline-block: 默认宽度是内容宽度,可以设置宽高的，如果前面和后面的元素是inline元素，那么同行显示 ,如果后续是inline-block的元素，并且宽度超过了这行的边界，会换行
,如果是inline的元素，可以在元素内换行，这是不同。 默认的inline-block元素有 -input, textarea,select,button.... -->

<!--  display:none，设置元素不显示. 和visibility:hidden的区别是后者占据空间. -->
display: table;宽度跟着内容走
display: flex;父元素有这个css属性，子元素自然就是flex item，flex item默认的宽度是auto的，也就是跟内容变化，/*justify-content: center;*/（居中对齐）

<!-- Text-align:center指的是line-height的居中，而不是height的居中，所以此时一般都需要设置line-height等于height -->

<!-- height is the vertical measurement of the container. -->

<!-- 行高指的是文本行的基线间的距离。 -->

<!--  position: static|relative|absolute|fixed 默认的是static -->

<!--  relative 是以自己为参照物，仍然在文档流中,top,left是以自己原来在文档流中的位置为参考的，可以提高浮动元素的层级，使浮动元素被选中  -->

<!--  relative使用场景: 绝对定位元素的参照物 （因为relative是不脱离文档流的，对其他元素的位置没有影响,所以当做绝对定位的参照物）-->

<!--  absolute: 默认没有宽度,宽度是内容的宽度，脱离文档流(不再占据他原来占据的位置), 浮在其他元素上面 参照物为第一个有position:relative;的父元素,如果父元素上没有定位元素，那就以body为参照物 -->

<!--  fixed: 脱离文档流（不再占据原来的位置），宽度为内容的宽度,固定定位元素的参照物为视窗，也就是窗口,注意他是相对于视窗来定位的，并不是相对于html元素, 相对于视窗，也就是即使有滚动条，他也不会随着滚动条滚动 -->

<!--  元素叠加的效果用position来实现 -->

css 中 overflow: hidden清楚浮动的真正原因
外层ul设置overflow为hidden, 内层li设置float为left左浮动

为什么ul去掉overflow: hidden之后没在了, 其实不是没在了, 看到由于没有设置高度, height: auto为自动值, 就是根据里面的内容自动设置高度, 但是li设置了左浮动, 已经浮动起来了属于浮动流, 不在普通流中, 但是ul还是在普通流, 他普通流中的内容为空, 所以没有高度.

overflow的意思: 属性规定当内容溢出元素框时发生的事情, w3school解释如下, 简单的说hidden 的意思是超出的部分要裁切隐藏掉
那么如果 float 的元素li不占普通流位置,
普通流的包含的ul设置了overflow: hidden要根据内容高度裁切隐藏, 
并且ul高度是默认值auto, 那么不计算其内浮动元素高度就裁切就有可能会裁掉float的li
，所以li肯定要保留，所以如果没有明确设定容器ul高情况下，它要计算内容全部高度才能确定在什么位置hidden，浮动流的高度就要被计算进去, 就是li的高度, 一旦计算进去就顺带达成了清理浮动的目标
body,ul的高度是不包含浮动元素的高度的
style:display:none;隐藏

##### 浮动Float

float: left | right | none | inherit, none是默认值,

    1, 浮动元素的默认宽度是内容宽度
    2, 是半脱离文档流的,还是会占据旁边非浮动元素的空间，但是不会遮挡非浮动元素里面的内容，旁边的非浮动元素会环绕它，float对于后续的元素来说是脱离文档流的，但是对于后续的内容是在文档流中的
    3，向指定的方向一直移动(移动到不能移动的位置,靠住父元素)
    4，float的元素在同一文档流，按顺序排列 ，包含float的元素的父元素此时没有包含任何内容，因为float脱离了文档流，所以父元素没有了高度。用float来做多列布局
    5，浮动的元素可以同行显示(即使他是div)，所以可以实现块级别元素同行显示，之后在父元素上加一个清除浮动，这样的好处就是后续元素不受浮动的影响 ，这样就可以实现两列布局

height is the vertical measurement of the container.
line-height is the distance from the top of the first line of text
to the top of the second.

##### clear

> clear: both|left|right|none|inherit
 
    1，	clear：默认是none，通常使用both，因为both包含了left和right的功能，直接clear both就好了
    2，	clear用来清除浮动元素对后续元素的影响，一般应用于block level element

clear使用一般有两种方式:

    .clearfix:after{content:'.';display: block;height: 0;overflow: hidden;visibility: hidden;clear: both;}
    在浮动元素的父元素加一个 类名clearfix，他就会在浮动元素的后面，append一个点号，用一个不可见的点号来清除浮动
    参考阮一峰网站
    
Note: 子元素默认会继承父元素的css属性

##### CSS3

transform: translateX(-50%);/*translateX就是自身宽度为参照物的*/
弹性布局

创建弹性布局flex container display: flex;

只有弹性容器中的在文档流中的子元素才是flex item弹性元素,而且必须是直接子元素，不能使孙子元素
一个布局是弹性的布局，那这个容器就叫做弹性容器flex container，flex item，main axis ,cross axis
