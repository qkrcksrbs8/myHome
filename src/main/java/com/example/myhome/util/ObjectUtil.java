package com.example.myhome.util;

import java.lang.reflect.Constructor;

public class ObjectUtil {
    public static Class<?> loadClass(String className) throws ClassNotFoundException, Exception {

        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);

        if (clazz == null) {
            clazz = Class.forName(className);
        }

        return clazz;
    }

    public static Object instantiate(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
        Class<?> clazz;

        try {
            clazz = loadClass(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
//			log.error("{} : Class is can not instantialized.", className);
            throw new ClassNotFoundException();
        } catch (InstantiationException e) {
//			log.error("{} : Class is can not instantialized.", className);
            throw new InstantiationException();
        } catch (IllegalAccessException e) {
//			log.error("{} : Class is not accessed.", className);
            throw new IllegalAccessException();
        } catch (Exception e) {
//			log.error("{} : Class is not accessed.", className);
            throw new Exception(e);
        }
    }

    public static Object instantiate(String className, String[] types, Object[] values) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
        Class<?> clazz;
        Class<?>[] classParams = new Class[values.length];
        Object[] objectParams = new Object[values.length];

        try {
            clazz = loadClass(className);

            for (int i = 0; i < values.length; i++) {
                classParams[i] = loadClass(types[i]);
                objectParams[i] = values[i];
            }

            Constructor<?> constructor = clazz.getConstructor(classParams);
            return constructor.newInstance(values);

        } catch (ClassNotFoundException e) {
//			log.error("{} : Class is can not instantialized.", className);
            throw new ClassNotFoundException();
        } catch (InstantiationException e) {
//			log.error("{} : Class is can not instantialized.", className);
            throw new InstantiationException();
        } catch (IllegalAccessException e) {
//			log.error("{} : Class is not accessed.", className);
            throw new IllegalAccessException();
        } catch (Exception e) {
//			log.error("{} : Class is not accessed.", className);
            throw new Exception(e);
        }
    }

    public static boolean isNull(Object object) {
        return ((object == null) || object.equals(null));
    }

    public static String isNullStr(Object object, String convert) {
        String returnVal = "";
        if ((object == null) || object.equals(null)) {
            returnVal = convert;
        } else {
            returnVal = object.toString();
        }
        return returnVal;
    }

    public static String isEmptyStr(Object object) {
        String returnVal = "";
        if (object.toString() == null || object.toString() == "" || object.toString().equals(null) || object.toString().equals("")) {
            returnVal = null;
        } else {
            returnVal = object.toString();
        }
        return returnVal;
    }

    public static Object replaceEmptyNull(Object object) {
        if (object.toString() == "" || object.equals("")) {
            object = null;
        }
        return object;
    }
}
