Some of the rename operation behaviours are platform dependent. For example it might 
fail if you move a file from one filesystem to another or if a file already exists 
with the same name at destination directory.

一些重新操作的行为表现是依赖于平台的，例如如果你从一个文件系统移动一个文件到另一个，如果目的文件夹中已经存在了
相同文件名的文件，就有可能操作失败.

In Mac OS, if destination file already exists renameTo override the existing file with source file.

移动文件是通过renameTo函数本身实现的。 移动文件不会改变文件内容，只会改变文件目录的位置。 
文件完整路径包含带文件名的目录信息，renameTo方法也可以更改目录路径，从而移动文件。

我们应该始终检查renameTo返回值以确保重命名文件成功，因为它依赖于平台，并且如果重命名失败则不会抛出IO异常。