JSON代表JavaScript Object Notation。

什么是JSON？

JSON用于从服务器进行数据交换。 因此，了解语法及其可用性非常重要。 
JSON是XML的最佳替代品，它更易被人类阅读。
JSON与语言无关。 我们可以用任何语言(Java / C / C ++）编写JSON。
JSON是目前在服务器/ Web应用程序之间传输数据的公认标准。

JSON数据由4个主要组件组成，如下所示：

数组：JSONArray用方括号([）括起来。 它包含一组对象
对象：用大括号({）括起来的数据是单个JSONObject。 嵌套的JSONObjects是可能的并且是非常常用的
键：每个JSONObject都有一个包含特定值的字符串的键
值：每个键都有一个值(Every key has a single value.)，可以是任何类型的字符串，double，integer，boolean等

reference: https://blog.csdn.net/u013005791/article/details/72904217#24-jsontokener

Android JSONObject用于Android应用程序中的JSON解析。

Android开发中创建和解析Json使用Gson、fastJson等第三方库很方便,
当然也可以直接使用Android自带的JsonObject等类来创建和解析json.

Android自带的Json操作类:

1. JSONObject
2. JSONArray
3. JSONStringer
4. JSONTokener
5. JsonReader
6. JsonWriter