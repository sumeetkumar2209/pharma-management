package com.reify.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class CommonUtils {

    public static <T> T removeWhiteSpace(T t){

        Class cls = t.getClass();
        Object obj = t;

        Field[] fields = cls.getDeclaredFields();

        for ( Field field :fields) {
            field.setAccessible(true);
            try {

                    if (field.getType().equals(String.class) && StringUtils.isBlank((String) field.get(obj)))
                    {
                        field.set(obj,null);
                    }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T)obj;
    }
}
