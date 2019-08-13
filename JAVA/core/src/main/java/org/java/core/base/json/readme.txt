JSON广泛用于Web应用程序或服务器响应，因为它比XML轻量级且更紧凑。 JSON对象易于读写，大多数技术都支持JSON对象。 
这就是Java Web服务中的JSON非常受欢迎的原因。

JSON API提供了两种JSON处理方式：

对象模型API - 它类似于DOM Parser，适用于小对象。
Streaming API - 它与StaX Parser类似，适用于您不希望将整个对象保留在内存中的大型对象。

JSON API provides two ways for JSON processing:

1. Object Model API – It’s similar to DOM Parser 
and good for small objects.
2. Streaming API – It’s similar to StaX Parser 
and good for large objects where you don’t want to keep whole object in memory.

Java JSON API的一些重要接口是：

javax.json.JsonReader：我们可以使用它来将JSON对象或数组读取到JsonObject。 我们可以从Json类或JsonReaderFactory获取JsonReader。
javax.json.JsonWriter：我们可以使用它来将JSON对象写入输出流。
javax.json.stream.JsonParser：这可以作为拉解析器，并为读取JSON对象提供流支持。
javax.json.stream.JsonGenerator：我们可以使用它来以流方式将JSON对象写入输出源。
javax.json.Json：这是用于创建JSON处理对象的工厂类。 此类提供了创建这些对象及其相应工厂的最常用方法。 工厂类提供了创建这些对象的各种方法。
javax.json.JsonObject：JsonObject表示不可变的JSON对象值。

