package com.anderl.hibernate.ext.refactored;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ga2unte on 8.9.2014.
 */
public class Helper {

    public static <T> List<T> invokeGettersByReturnType(Class<T> clazz, Object object) {
        List<T> list = new ArrayList<>();
        if (object == null) {
            return list;
        }
        for (Method method : object.getClass().getMethods()) {
            if (method.getName().startsWith("get")
                    && method.getReturnType().getName().equals(clazz.getName())) {
                try {
                    Object result = method.invoke(object);
                    if (result != null) {
                        list.add((T) result);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static Class getGenericInterfaceType(Class clazz) {
        return(Class<?>) clazz.getGenericInterfaces()[0];
    }
}
