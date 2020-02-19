package com.hm.rsgl.basesalary.entity;

import java.lang.reflect.Method;

/**
 * Created by panjs008 on 2019-07-17.
 */
public class TestReflect {
    public static void main(String[] args)  {
        try {
            //获取class对象
            Class c = Class.forName("com.hm.rsgl.basesalary.entity.Person");
            /**
             * 獲取方法并执行方法
             */
            Method show = c.getMethod("showInfo");//获取showInfo()方法
            Object object = show.invoke(c.newInstance());//调用showInfo()方法
            System.out.println(object);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
