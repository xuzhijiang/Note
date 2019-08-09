package com.servlet.core.kaptcha;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptcahCode {

    private final static int CAPTCAH_CODE_LENGTH=4;
    private final static int IMAGE_WIDTH=120;
    private final static int IMAGE_HEIGHT=25;

    public static String drawImage(HttpServletResponse response){
        //1:定义以字符串的拼接的StringBuilder
        StringBuilder builder = new StringBuilder();
        //准备产生4个字符的随机字符串
        for(int i=0;i<CAPTCAH_CODE_LENGTH;i++){
            builder.append(randomChar());
        }
        String code = builder.toString();
        //2:定义图片的宽度和高度
        int width = IMAGE_WIDTH;
        int height = IMAGE_HEIGHT;
        //简历bufferedImage对象，制定图片的长度和宽度以及色彩
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        //3:获取到 Graphics2D 绘制对象，开始绘制验证码
        Graphics2D g = bi.createGraphics();
        //4:设置文字的字体和大小
        Font font = new Font("微软雅黑",Font.PLAIN,20);
        //设置字体的颜色
        Color color = new Color(0,0,0);
        //设置字体
        g.setFont(font);
        //设置颜色
        g.setColor(color);
        //设置背景
        g.setBackground(new Color(226,226,240));
        //开始绘制对象
        g.clearRect(0,0,width,height);
        //绘制形状，获取矩形对象
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code,context);
        //计算文件的坐标和间距
        double x = (width - bounds.getWidth())/2;
        double y = (height - bounds.getHeight())/2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code,(int)x,(int)baseY);
        //结束绘制
        g.dispose();
        try {
            ImageIO.write(bi,"jpg",response.getOutputStream());
            //刷新响应流
            response.flushBuffer();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return code;
    }
    /**
     * 此方法用于产生随机数字母和数字
     * @return
     */
    private static char randomChar(){
        //1:定义验证需要的字母和数字
        String string = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        //2：定义随机对象
        Random random = new Random();
        return string.charAt(random.nextInt(string.length()));
    }

    public static void main(String[] args){
        String result=CaptcahCode.drawImage(null);
        System.out.println(result);
    }
}
