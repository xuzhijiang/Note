# df

```shell
# 查看硬盘的大小以及使用情况
df -T -h

# Filesystem(文件系统) Type(类型) (Mounted on)把文件系统挂载在哪个目录下

Filesystem     Type      Size  Used Avail Use% Mounted on
udev           devtmpfs   47G     0   47G   0% /dev
/dev/sda3      ext4       22T  2.4T   19T  12% /
tmpfs          tmpfs      47G     0   47G   0% /sys/fs/cgroup
/dev/sda1      ext4      454M  139M  288M  33% /boot

# 把/dev/sda3这块硬盘挂载到/下，也就是/下的内容都存放在/dev/sda3这块硬盘上.

# 查看某一个文件夹的大小
df -T -h /boot/

df -a

# 挂载软硬光区
mount -t /dev/fdxhdax/mnt/目录名

# 解除挂载
umount /mnt/目录名

# 解除所有挂载(此命令慎用)
umount -a
```

# LVM硬盘扩容技术

![](pics/LVM硬盘扩容技术.png)