package com.sakura.testng;

import org.testng.annotations.Test;

public class BasicAnnotation {

    //最基本注解，将方法标记为测试的一部分
    @Test
    public void testCase1(){
        System.out.println("这是第一个测试用例");
    }

}
