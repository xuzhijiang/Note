FileWriter:

FileWriter是java中往文件中写内容的最简单方法，它提供了重载的write方法来将int，
byte数组和String写入File。 您还可以使用FileWriter写部分字符串或字节数组到文件中,
(write part of the String or byte array using FileWriter)
FileWriter直接写入文件，只有在写入次数较少时才应使用。

BufferedWriter：

BufferedWriter几乎与FileWriter相似，但它使用内部缓冲区将数据写入File。 
因此，如果写入操作的数量更多，则实际的IO操作会更少，性能也会更好。 
当写操作次数更多时，您应该使用BufferedWriter。

FileOutputStream：

FileWriter和BufferedWriter用于将text写入文件，
但是当您需要将原始流数据写入文件时，
您应该使用FileOutputStream在java中写入文件。

Files：

Java 7引入了文件实用程序类，我们可以使用它的写函数向文件中写内容,
在Files的内部使用了OutputStream将byte array 写入文件。