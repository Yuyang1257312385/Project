package com.example.yu.baselibrary.IOC;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/4/30.
 */

//@Target(ElementType.FIELD) 注解所在的位置 FIELD:属性 TYPE:类 METHOD:方法 CONSTRUCTOR:构造函数
@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.RUNTIME) 什么时候生效  RUNTIME:运行时 CLASS：编译时 SOURCE:源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    //ViewById(R.id.xxx)  中的R.id.xxx,就是注解的值
    int value();
}
