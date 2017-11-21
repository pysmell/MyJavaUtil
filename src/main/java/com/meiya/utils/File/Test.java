package com.meiya.utils.File;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *OutputStream的write(int)方法，当int在byte类型的取值范围时，可以顺利写出byte值。当取值超出byte范围，其值是未知的。
 */
public class Test {

    public void contrast(int a) throws IOException{
        System.out.println("正在测试的int数是："+a);

        OutputStream os = new  ByteArrayOutputStream();
        //通过OutputStream的write(int)方法，将一个int值写入流。
        os.write(a);
        System.out.println("通过OutputStream的write(int)方法写到流中为:"+os.toString()+"\n");

        //OutputStream的write(int)方法的实现就是通过类型转换实现的，我自己转型和上面的结果对比一下。
        byte b = (byte)a;
        System.out.println("将测试十进制数直接转换为byte类型为:"+b);
        System.out.println("将byte类型转换为字符类型为： "+(char)b+"\n");

        //write(int)方法明确说明了，它是取二进制的低8位来实现取一个字节的，那么我就是取低8位，再转换成字符
        String binaryStr = Integer.toBinaryString(a);
        //打印二进制数
        System.out.println("十进制数："+ a +" 转换成二进制数为:"+binaryStr);
        int x = binaryStr.length();
        //取整数的低8位二进制：也就是将其转换为一个字节
        int y = 0;
        if(x%8>=0 && x%8<=7){
            int n = x/8;
            if(n>1)
                y=(n-1)*8+x%8;
            else if(n==0)
                y=0;
            else
                y=x%8;
        }
        String substr = binaryStr.substring(y, x);
        //打印低8位二进制
        System.out.println("二进制的低8位为:"+substr);
        //将低8位二进制转换成十进制并打印出来
        System.out.println("将低8位的二进制数  转换成十进制数为:"+Integer.valueOf(substr, 2)+"       十进制转换成byte类型为:"+(byte)(Integer.valueOf(substr, 2)).intValue()+"        byte类型转换成字符类型:"+(char)(byte)(Integer.valueOf(substr, 2)).intValue());
    }
    public static void main(String args[]) throws IOException{
        Test t6 = new Test();
        int[] intArray = {-128,-127,-56,0,97,127,128,255,256,280};
        for(int x : intArray){
            t6.contrast(x);
            System.out.println("______________________");
        }
    }}
