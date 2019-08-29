package org.java.core.base.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetworkUtils {


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

}
