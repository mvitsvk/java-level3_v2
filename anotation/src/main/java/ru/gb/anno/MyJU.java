package ru.gb.anno;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyJU {

    public static void start(Class cl) throws IllegalAccessException {

        Method[] methods = cl.getDeclaredMethods();

        SortedMap<Integer, Method> sortedMap = new TreeMap<>(Comparator.comparingInt(o -> o));
        boolean BS = false;
        boolean AS = false;

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class) && BS == false){
                sortedMap.put(0,m);
                BS = true;
            }
            else if (m.isAnnotationPresent(BeforeSuite.class) && BS == true) {
                throw new IllegalAccessException("two anotation BeforeSuite");
            }

            if (m.isAnnotationPresent(AfterSuite.class) && AS == false){
                sortedMap.put(11,m);
                AS = true;
            }
            else if (m.isAnnotationPresent(AfterSuite.class) && AS == true) {
                throw new IllegalAccessException("two anotation AfterSuite");
            }

            if (m.isAnnotationPresent(Test.class)){
                sortedMap.put(m.getAnnotation(Test.class).priority(), m);
            }
        }

        for (Map.Entry<Integer,Method> map :sortedMap.entrySet()) {
            System.out.println("key=" + map.getKey());
            try {
                map.getValue().invoke(null);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

}
