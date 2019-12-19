
    >示例: org.java.core.advanced.jvm.StringTable.StringTableDemo01

---
     "C:\Program Files\Java\jdk1.8.0_101\bin\javap.exe" -v org.java.core.advanced.jvm.StringTable.StringTableDemo01
     Classfile /D:/projects/computer_science/Note/JAVA/core/target/classes/org/java/core/advanced/jvm/StringTable/StringTableDemo01.class
        Last modified 2019-12-19; size 581 bytes
        MD5 checksum f7edc208e0986b45431a93a02851bd0f
        Compiled from "StringTableDemo01.java"
     public class org.java.core.advanced.jvm.StringTable.StringTableDemo01
        minor version: 0
        major version: 52
        flags: ACC_PUBLIC, ACC_SUPER
     Constant pool:
     // 常量池最初是存储在字节码文件里面的,当运行的时候,会被加载到运行时常量池中,但是要注意
     // 加载到运行时常量池的时候,还没有成为对象,也就是下面的a,b,ab仅仅是常量池中的一些符号,
     // 还没有成为java中的字符串对象,那么什么时候变为真正的字符串对象呢? 答案是: 当你真正执行到引用它的那行代码的时候
     // 比如: 下面执行到: ldc #2的时候,会把a符号变为"a"的字符串对象,同时会做一件事情,会准备
     // 一块空间,这块空间叫StringTable(串池),刚开始StringTable是空的,里面没有内容,数据结构是哈希表,
     // 长度一开始是固定的,并且是不能扩容的,后面我们会设置它的大小
     // 当a要变为对象时候,会把a当作key,在StringTable中寻找,
     // 看看有没有a对应的字符串对象"a",如果有就直接拿来,没有就创建,并且把"a"字符串对象存放到StringTable串池中
     /// 下面的b,ab都是类似,一定要注意,字符串对象不是事先创建好放到StringTable中的,而是执行到用到它的代码的时候才开始创建
     // 这个行为是懒惰的行为
         #1 = Methodref          #6.#24         // java/lang/Object."<init>":()V
         #2 = String             #25            // a
         #3 = String             #26            // b
         #4 = String             #27            // ab
         #5 = Class              #28            // org/java/core/advanced/jvm/StringTable/StringTableDemo01
         #6 = Class              #29            // java/lang/Object
         #7 = Utf8               <init>
         #8 = Utf8               ()V
         #9 = Utf8               Code
         #10 = Utf8               LineNumberTable
         #11 = Utf8               LocalVariableTable
         #12 = Utf8               this
         #13 = Utf8               Lorg/java/core/advanced/jvm/StringTable/StringTableDemo01;
         #14 = Utf8               main
         #15 = Utf8               ([Ljava/lang/String;)V
         #16 = Utf8               args
         #17 = Utf8               [Ljava/lang/String;
         #18 = Utf8               s1
         #19 = Utf8               Ljava/lang/String;
         #20 = Utf8               s2
         #21 = Utf8               s3
         #22 = Utf8               SourceFile
         #23 = Utf8               StringTableDemo01.java
         #24 = NameAndType        #7:#8          // "<init>":()V
         #25 = Utf8               a
         #26 = Utf8               b
         #27 = Utf8               ab
         #28 = Utf8               org/java/core/advanced/jvm/StringTable/StringTableDemo01
         #29 = Utf8               java/lang/Object
     {
        public org.java.core.advanced.jvm.StringTable.StringTableDemo01();
         descriptor: ()V
         flags: ACC_PUBLIC
         Code:
            stack=1, locals=1, args_size=1
                 0: aload_0
                 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
                 4: return
            LineNumberTable:
                line 5: 0
            LocalVariableTable:
              Start  Length  Slot  Name   Signature
                0       5     0  this   Lorg/java/core/advanced/jvm/StringTable/StringTableDemo01;
    
        public static void main(java.lang.String[]);
             descriptor: ([Ljava/lang/String;)V
             flags: ACC_PUBLIC, ACC_STATIC
             Code:
                 stack=1, locals=4, args_size=1
                 0: ldc           #2                  // String a
                 // 解释: 到常量池中#2位置加载一个信息,当然这个信息可能是一个常量,可能是一个对象的引用,在这里就是一个字符串的字面量
                 2: astore_1
                 // 解释: 把加载好的东西存入局部变量表中1号对应的Slot槽,对应下面的LocalVariableTable局部变量表中Slot为1的槽
                 3: ldc           #3                  // String b
                 5: astore_2
                 6: ldc           #4                  // String ab
                 8: astore_3
                 9: return
                 LineNumberTable:
                     line 12: 0
                     line 13: 3
                     line 14: 6
                     line 15: 9
                LocalVariableTable: // main方法栈帧运行时的局部变量表
                                       操   对应的变量name
                       Start  Length  Slot  Name   Signature
                         0      10     0  args   [Ljava/lang/String;
                         3       7     1    s1   Ljava/lang/String;
                         6       4     2    s2   Ljava/lang/String;
                         9       1     3    s3   Ljava/lang/String;
     }
     SourceFile: "StringTableDemo01.java"
---