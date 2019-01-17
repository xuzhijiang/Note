有3中流行的方式再Java中创建文件:

1. File.createNewFile()

When we initialize File object, we provide the file name and then we can call 
createNewFile() method to create new file in Java.

File createNewFile() method returns true if new file is created and false if '
file already exists. This method also throws java.io.IOException when it’s not 
able to create the file. The files created is empty and of zero bytes.

When we create the File object by passing file name, it can be with absolute path, 
or we can only provide the file name or we can provide relative path.
当我们创建文件对象给它传入file name的时候，它可以是绝对路径，或者我们可以只提供文件名，或者我们可以提供相对路径.

For non-absolute path, File object tries to locate files in the project root directory. 
If we run the program from command line, for non-absolute path, File object tries to locate 
files from the current directory.
对于非绝对路径，文件对象尝试在项目的根目录中确定文件的位置，如果我们从 命令行运行程序，对于非绝对路径，文件对象会将当前目录来确定文件的位置

While creating the file path, we should use System property file.separator to make our program platform independent.
当创建文件的时候，我们应该使用系统的文件分隔符，以便独立于平台运行我们的程序。

