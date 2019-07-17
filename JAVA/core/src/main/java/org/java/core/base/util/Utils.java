package org.java.core.base.util;

import java.io.File;
import java.io.InputStream;
import java.net.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;

public class Utils {

    /**
     * 测试随机数的生成
     */
    public static void testRandom() {
        // 使用单个种子创建新的随机数生成器,
        // 种子是伪随机数生成器的内部状态的初始值，它由方法{@link #next}维护。
        Random random = new Random(1);
        // 返回下一个伪随机数
        System.out.println("随机获得一个int: " + random.nextInt());
    }

    /**
     * 如果获取本机mac地址
     *
     * MAC地址(网卡地址)是由网卡决定的，是固定的。MAC地址共六个字节（48位），每个字节在展示的时候，都采用十六进制数表示。
     *
     * 在Linux操作系统中，我们可以通过ifconfig命令查看Mac地址:
     *
     * eth0      Link encap:Ethernet  HWaddr 00:16:3E:02:0E:EA
     *           inet addr:10.144.211.78  Bcast:10.144.223.255  Mask:255.255.240.0
     *
     * eth1      Link encap:Ethernet  HWaddr 00:16:3E:02:4A:0B
     *           inet addr:115.28.171.77  Bcast:115.28.171.255  Mask:255.255.252.0
     *
     * 上面两个网卡，eth0和eth1，这两个网卡的mac地址不同.其中HWaddr(硬件地址)部分标记的就是网卡的Mac地址。
     * 可以看到，网卡的Mac地址每个字节的16进制表示，用":"进行了区分。用什么符号区分是无关紧要的，
     * 例如另外一种常见的方式是使用"-"来区分，如：00-16-3E-02-0E-EA
     *
     * 通常不同的网卡绑定的ip地址是不同的，如这里显示eth0绑定的ip地址是：10.144.211.78，eth1绑定的ip地址是：115.28.171.77
     *
     * 在Java中也提供了NetworkInterface接口来获取网卡的Mac地址。最常见的使用方式如下:
     *
     * 获取所有的网卡信息，返回是一个枚举，可以进行迭代:
     * Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
     *
     * 获取指定ip对应的网卡信息:
     * InetAddress ip= InetAddress.getByName("115.28.171.77");
     * NetworkInterface networkInterface =NetworkInterface.getByInetAddress(ip);
     *
     */
    public static void getMacAddr() {
        try {
            // 首先获取想要查看的ip地址，这个地址唯一对应一个网卡信息
            InetAddress ip = InetAddress.getLocalHost();
            // 根据ip地址获得对应的网卡信息
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
            // 获取网卡的mac地址字节数组，这个字节数组的长度是6
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            // 将字节数组转换成16进制
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<hardwareAddress.length;i++){
                // 将每个字节的值转换成16进制
                String byteToHex = Integer.toHexString(hardwareAddress[i] & 0xff);
                sb.append(byteToHex);
                // 使用-来 区分每个字节的16进制数表示
                if(i != hardwareAddress.length - 1){
                    sb.append("-");
                }
            }
            System.out.println(sb.toString());
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException("get mac addr error", e);
        }
        System.out.println("--- end ---");
    }

    /**
     * 将域名解析为IP
     * @return
     */
    public static String resolveIp(String host) {
        String ip = null;
        System.out.println("ip: " + ip);
        try {
            InetAddress address = InetAddress.getByName(host);
            System.out.println("HostAddress: " + address.getHostAddress());
            ip = address.getHostAddress();
            for (InetAddress addr : InetAddress.getAllByName(host)) {
                System.out.println("Address: " + addr.getHostAddress() + ", HostName: " + addr.getHostName());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static void fileLoad() throws URISyntaxException {
        // getClassLoader().getResource("") 方法返回了编译出的class的根路径
        System.out.println("class.getClassLoader().getResource(\"\") : " + Utils.class.getClassLoader().getResource(""));

        // getClassLoader().getResource("/") 方法返回null，说明getClassLoader().getResource不支持以"/"开头的参数;
        System.out.println("class.getClassLoader().getResource(\"/\") : " + Utils.class.getClassLoader().getResource("/"));

        // class.getResource("") 方法返回了Test.class所在的路径;
        System.out.println("class.getResource(\"\") : " + Utils.class.getResource(""));

        // class.getResource("/")与getClassLoader().getResource("")表现一致，返回编译出的class的根路径
        System.out.println("class.getResource(\"/\") : " + Utils.class.getResource("/"));

        // class.getResourceAsStream最终还是调用了ClassLoader.getResourceAsStream
        // ,那么ClassLoader.getResourceAsStream是如何查找资源呢？
        // 是通过classloader的双亲委托机制：首先使用自己的父类加载器寻找资源，如果父类加载器为null，
        // 则在内置到虚拟机的类加载器的路径搜索，如果还为null，则
        // 调用该类的的类加载器(ClassLoader)的findResource(name)来搜索.
        // 对于我们的应用而言，资源往往存放于在classpath(*.class路径)的路径上，
        // 在eclipse的java工程中，classpath指向eclipse自动生成的/bin目录

        // 读取classpath根目录下的input.txt:
        try {
            // 第一种方法
            InputStream in1 = Utils.class.getResourceAsStream("/input.txt");
            System.out.println("in1: " + in1);
            // 第二种方法
            InputStream in2 = ClassLoader.getSystemClassLoader().getResourceAsStream("input.txt");
            System.out.println("in2: " + in1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 读取Test.class同级目录下的文件:
        // 第一种方法
        InputStream in3 = Utils.class.getResourceAsStream("input.txt");
        System.out.println("in3: " + in3);
        // 第二种方法
        InputStream in4 = ClassLoader.getSystemClassLoader().getResourceAsStream("com/test/input.txt");
        System.out.println("in4: " + in4);
        //第三种方法
        InputStream in5 = Utils.class.getResourceAsStream("/com/test/input.txt");
        System.out.println("in5: " + in5);

        // 我们如何在eclipse中添加我们自定义的classpath?

        // 方法1:
        // 工程右键->Build Path->Configure Build Path->Source->AddFolder可以将工程目录下的文件夹设置为Source目录
        // 常见的Maven中的resources目录，就是Source目录
        // 设置为Source目录之后，eclipse在编译源文件时，
        // 会自动将Source目录下的文件拷贝到/bin目录，自然也也就可以在classpath下找到原来Source目录下的资源了
        // 这种方法的限制就是只能把工程目录下的文件夹添加进去

        // 方法2:
        // 菜单栏 -》 run->Run Configurations->Classpath
        // 选中User Entries, 点击右侧Advanced，可以添加文件夹到运行时classpath
        // 当然，如果是在命令行下直接运行java程序的话，可以使用-classpath选项指定classpath

        // 在maven中，可以使用下面的方法指定jar包的classpath：
	     /*
		     <plugin>
		     <groupId>org.apache.maven.plugins</groupId>
		     <artifactId>maven-jar-plugin</artifactId>
		     <configuration>
		         <finalName>ReleaseTool</finalName>
		         <archive>
		             <manifest>
		                 <!-- 为依赖包添加路径, 这些路径会写在MANIFEST文件的Class-Path下 -->
		                 <addClasspath>true</addClasspath>
		                 <classpathPrefix>lib/</classpathPrefix>
		                 <mainClass>com.oscar.releasetool.app.App</mainClass>
		             </manifest>
		             <manifestEntries>
		                 <!-- 在Class-Path下添加配置文件的路径 -->
		                 <Class-Path>./</Class-Path>
		             </manifestEntries>
		         </archive>
		         <excludes>
		             <exclude>config.json</exclude>
		         </excludes>
		     </configuration>
		 	</plugin>
	     */

        // 我们可以将配置文件放到任何需要的地方，然后将配置文件所在的目录添加到classpath，
        // 使用classloader.getResourceAsStream方法来读取。
        // 利用这种方法可以做到配置文件与jar包分离，并且配置文件所在的目录是可以自定义的。
        // Spring读取application.properties使用的是同样的原理。

        // 可以使用下面的代码查看当前classpath:
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        for (String str1 : classpathEntries) {
            System.out.println(str1);
        }

        // 读取jar包所在的位置
        // 有时候需要知道jar包所在的位置，比如我们的项目需要一个默认的日志文件输出路径，
        // 这个路径就可以是运行时jar包所在的目录。如何获取jar包所在的目录？
        URL url = Utils.class.getProtectionDomain().getCodeSource().getLocation();
        String path = url.toURI().getPath();
        System.out.println("path: " + path);
    }

    public static void testCalendar(int fixedHours,int fixedMinutes,int fixedSeconds) {
        System.out.println("---result: " + TimeUnit.HOURS.toMillis(4L));

        Calendar date = Calendar.getInstance();
        System.out.println("------date: " + date.get(Calendar.YEAR));
        System.out.println("------date: " + date.get(Calendar.MONTH));
        System.out.println("-------date: " + date.get(Calendar.DATE));
        System.out.println("-------date: " + date.getTime());
        System.out.println("-------date: " + date.getTimeZone());
        System.out.println("--------date: " + date.get(Calendar.HOUR_OF_DAY));
        System.out.println("--------date: " + date.get(Calendar.MINUTE));
        System.out.println("--------date: " + date.get(Calendar.SECOND));

        if (date.get(Calendar.HOUR_OF_DAY) > fixedHours) {
            int time = 24 - (date.get(Calendar.HOUR_OF_DAY) - fixedHours);
            // after time
            System.out.println("11111------------time: " + time);
        } else {
            int time = fixedHours - date.get(Calendar.HOUR_OF_DAY);
            // after time
            System.out.println("2222------------time: " + time);
        }

//		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 19, 0);
//		date.set(date., Calendar.MONTH, Calendar.DAY_OF_MONTH, 3, 0, 0);
    }

    /**
     * 开机后，在一天中某一个固定时间做某件事情
     * @param hours
     * @param minutes
     * @param seconds
     */
    public static void startFixedTimeToDoSomethingTimer(int hours, int minutes, int seconds) {
        long fixedMillis = toMillis(hours, minutes, seconds);

        long currentMillis = toMillis(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND));

        long totalMillis = toMillis(24, 0, 0);

        long delayMillis = -1L;
        if (currentMillis > fixedMillis) {
            delayMillis = totalMillis - currentMillis + fixedMillis;
        } else {
            delayMillis = fixedMillis - currentMillis;
        }
        System.out.println("--------delayMillis: " + delayMillis);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // do sth
            }
        }, delayMillis);
    }

    public static long currentTimeToMillis() {
        Calendar date = Calendar.getInstance();
        int hours = date.get(Calendar.HOUR_OF_DAY);
        int minutes = date.get(Calendar.MINUTE);
        int seconds = date.get(Calendar.SECOND);
        System.out.println("------- HOUR: " + hours);
        System.out.println("------- MINUTE: " + minutes);
        System.out.println("------- SECOND: " + seconds);

        long hours2Seconds = TimeUnit.HOURS.toSeconds(hours);
        long minutes2Seconds = TimeUnit.MINUTES.toSeconds(minutes);
        System.out.println("------- HOUR to SECOND: " + hours2Seconds);
        System.out.println("------- MINUTE TO SECOND: " + minutes2Seconds);
        System.out.println("------- SECOND: " + seconds);

        long totalSeconds = hours2Seconds + minutes2Seconds + seconds;
        System.out.println("------------total seconds: " + totalSeconds);

        long totalMillis = TimeUnit.SECONDS.toMillis(totalSeconds);
        System.out.println("------------total millis: " + totalMillis);

        return totalMillis;
    }

    /**
     * 将具体的时分秒转换成毫秒
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public static long toMillis(int hours, int minutes, int seconds) {
        long total = TimeUnit.HOURS.toMillis(hours) + TimeUnit.SECONDS.toMillis(seconds) + TimeUnit.MINUTES.toMillis(minutes);
        return total;
    }

    public static final String IP_REGULAR_EXPRESS = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";

    public static void testRegularExpression() {
        Pattern pattern = Pattern.compile(IP_REGULAR_EXPRESS);
        Matcher matcher = pattern.matcher("172.16.8.1 2ms 2ms 0ms");
        try {
            if (matcher != null) {
                System.out.println("-----matcher.find(): " + matcher.find());
                System.out.println("-----" + matcher.group());
                System.out.println(matcher.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每次触摸遥控器后重新启动定时器
     */
    private void startUntouchControllerTimer() {
        Timer timer = new Timer();
        // 取消原来的定时器
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // do sth
            }
        }, TimeUnit.SECONDS.toMicros(60L));

    }

}
