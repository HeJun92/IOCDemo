package com.example.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by hj on 2019/4/10.
 */

public class LisenterHandler implements InvocationHandler {

    public LisenterHandler(Object target) {
        this.target = target;
    }

    private Object target;
    private HashMap<String,Method> methodmap=new HashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target!=null){
            String name = method.getName();
             method = methodmap.get(name);
             if(method!=null){
                 if(method.getGenericParameterTypes().length==0){
                     return  method.invoke(target);

                 }
                return method.invoke(target,args);
             }
        }
        return null;
    }

    //methodname 需要拦截的方法
    //method 执行的方法
    public void addMethodMap(String methodname,Method method){
        methodmap.put(methodname,method);
    }
}
