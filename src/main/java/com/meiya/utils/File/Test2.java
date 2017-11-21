package com.meiya.utils.File;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Test2 {
    public static void main(String[] args) throws IOException {
        Test2 t = new Test2();
        int[] intArray = {-128,-127,-56,0,97,127,128,255,256,280};
        for(int x : intArray){
            t.contrast(x);
            System.out.println("______________________");
        }
    }

    public void contrast(int a) throws IOException {
        System.out.println("正在测试的int数是："+a);

        OutputStream os = new ByteArrayOutputStream();
        //通过OutputStream的write(int)方法，将一个int值写入流。
        os.write(a);
        System.out.println("通过OutputStream的write(int)方法写到流中为:"+os.toString()+"\n");

    }
}
