package com.example.library;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hj on 2019/4/10.
 */

public class InJectManager {

    public static void inject(Activity activity) {
        //布局注入
        injectLayout(activity);
        //控件注入
        injectViews(activity);
        //事件注入
        injectEvents(activity);
    }


    private static void injectViews(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的所有属性
        Field[] fields = clazz.getDeclaredFields();
        //循环所有属性
        for (Field field : fields) {
            InjectView annotation = field.getAnnotation(InjectView.class);
            if (annotation != null) {
                int value = annotation.value();
                System.out.println("value==="+value);
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, value);
                    field.setAccessible(true); //设置私有属性的访问权限
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void injectLayout(Activity activity) {

        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取注解
        ContentView annotation = clazz.getAnnotation(ContentView.class);

        if (annotation != null) {
            int layoutId = annotation.value();
//            activity.setContentView(layoutId);

            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(activity, layoutId);
            } catch (Exception e) {


            }
        }
    }
    private static void injectEvents(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if(annotationType!=null){
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    String listenersetter = eventBase.listenersetter();
                    String callbacklisener = eventBase.callbacklisener();
                    Class<?> lisenerType = eventBase.lisenerType();

                    try {
                        Method declaredMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) declaredMethod.invoke(annotation);
                        LisenterHandler lisenterHandler = new LisenterHandler(activity);
                        lisenterHandler.addMethodMap(callbacklisener,method);

                        Object lisenter = Proxy.newProxyInstance(lisenerType.getClassLoader(), new Class[]{lisenerType}, lisenterHandler);

                        for (int viewId : viewIds) {
                            View view = activity.findViewById(viewId);
                            Class<? extends View> aClass = view.getClass();
                            Method method1 = aClass.getMethod(listenersetter, lisenerType);
                            method1.invoke(view,lisenter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
