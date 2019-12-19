
    >示例: org.java.core.advanced.jvm.StringTable.StringTableDemo02

---
    Classfile /D:/projects/computer_science/Note/JAVA/core/target/classes/org/java/core/advanced/jvm/StringTable/StringTableDemo02.class
      Last modified 2019-12-19; size 788 bytes
      MD5 checksum 294077287a2527d5b79ff01551fa608c
      Compiled from "StringTableDemo02.java"
    public class org.java.core.advanced.jvm.StringTable.StringTableDemo02
      minor version: 0
      major version: 52
      flags: ACC_PUBLIC, ACC_SUPER
    Constant pool:
       #1 = Methodref          #10.#30        // java/lang/Object."<init>":()V
       #2 = String             #31            // a
       #3 = String             #32            // b
       #4 = String             #33            // ab
       #5 = Class              #34            // java/lang/StringBuilder
       #6 = Methodref          #5.#30         // java/lang/StringBuilder."<init>":()V
       #7 = Methodref          #5.#35         // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
       #8 = Methodref          #5.#36         // java/lang/StringBuilder.toString:()Ljava/lang/String;
       #9 = Class              #37            // org/java/core/advanced/jvm/StringTable/StringTableDemo02
      #10 = Class              #38            // java/lang/Object
      #11 = Utf8               <init>
      #12 = Utf8               ()V
      #13 = Utf8               Code
      #14 = Utf8               LineNumberTable
      #15 = Utf8               LocalVariableTable
      #16 = Utf8               this
      #17 = Utf8               Lorg/java/core/advanced/jvm/StringTable/StringTableDemo02;
      #18 = Utf8               main
      #19 = Utf8               ([Ljava/lang/String;)V
      #20 = Utf8               args
      #21 = Utf8               [Ljava/lang/String;
      #22 = Utf8               s1
      #23 = Utf8               Ljava/lang/String;
      #24 = Utf8               s2
      #25 = Utf8               s3
      #26 = Utf8               s4
      #27 = Utf8               s5
      #28 = Utf8               SourceFile
      #29 = Utf8               StringTableDemo02.java
      #30 = NameAndType        #11:#12        // "<init>":()V
      #31 = Utf8               a
      #32 = Utf8               b
      #33 = Utf8               ab
      #34 = Utf8               java/lang/StringBuilder
      #35 = NameAndType        #39:#40        // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      #36 = NameAndType        #41:#42        // toString:()Ljava/lang/String;
      #37 = Utf8               org/java/core/advanced/jvm/StringTable/StringTableDemo02
      #38 = Utf8               java/lang/Object
      #39 = Utf8               append
      #40 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
      #41 = Utf8               toString
      #42 = Utf8               ()Ljava/lang/String;
    {
      public org.java.core.advanced.jvm.StringTable.StringTableDemo02();
        descriptor: ()V
        flags: ACC_PUBLIC
        Code:
          stack=1, locals=1, args_size=1
             0: aload_0
             1: invokespecial #1                  // Method java/lang/Object."<init>":()V
             4: return
          LineNumberTable:
            line 3: 0
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0       5     0  this   Lorg/java/core/advanced/jvm/StringTable/StringTableDemo02;
    
      public static void main(java.lang.String[]);
        descriptor: ([Ljava/lang/String;)V
        flags: ACC_PUBLIC, ACC_STATIC
        Code:
          stack=2, locals=6, args_size=1
             0: ldc           #2                  // String a
             2: astore_1
             3: ldc           #3                  // String b
             5: astore_2
             6: ldc           #4                  // String ab
             8: astore_3
             9: new           #5                  // class java/lang/StringBuilder
             // new了一个叫StringBuilder的对象
            12: dup
            13: invokespecial #6                  // Method java/lang/StringBuilder."<init>":()V
            // 调用了一个特殊方法: 是StringBuilder中的init方法,其实就是构造方法.后面的()中没有参数,说明是一个无参的构造
            16: aload_1
            // aload_1和astore_1是相对的,就是把局部变量表中slot为1的内容加载进来
            // 可以看到slot为1的位置存放的是s1 (通过args的name),也就是把s1加载进来
            17: invokevirtual #7                  // Method 
            java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 调用了/StringBuilder.append方法,参数就是aload_1加载的参数
            20: aload_2
            21: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            24: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
            // 调用SringBuilder的toString方法
            27: astore        4
            // 把SringBuilder的toString方法的结果存入slot为4的位置
            29: ldc           #4                  // String ab
            // 到常量池constant pool中寻找#4所代表的内容,可以看出加载的内容是已经拼接好的 ab 这个符号
            31: astore        5
             // 存入局部变量表中slot为5的位置,这个slot为5的 位置其实就是s5
            33: return
          LineNumberTable:
            line 6: 0
            line 7: 3
            line 8: 6
            line 9: 9
            line 17: 29
            line 36: 33
          LocalVariableTable:
            Start  Length  Slot  Name   Signature
                0      34     0  args   [Ljava/lang/String;
                3      31     1    s1   Ljava/lang/String;
                6      28     2    s2   Ljava/lang/String;
                9      25     3    s3   Ljava/lang/String;
               29       5     4    s4   Ljava/lang/String;
               33       1     5    s5   Ljava/lang/String;
    }
    SourceFile: "StringTableDemo02.java"
---