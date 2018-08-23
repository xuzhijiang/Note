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
