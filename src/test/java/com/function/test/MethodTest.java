package com.function.test;

import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodTest {
    public static void main(String[] args) {
        Method[] methods = M.class.getMethods();
//        System.out.println(methods[0].equals(methods[1]));
        Map<String, Map<Integer, Method>> allMethods = new HashMap<>();
        HashMap<Integer, Method> methodHashMap = new HashMap<Integer, Method>() {{
            Stream.of(methods).forEach(method -> {
                Class<?>[] parameterTypes = method.getParameterTypes();
                put(Lists.newArrayList(parameterTypes).hashCode(), method);
            });
        }};
        allMethods.put("t", methodHashMap);

        Object[] params1 = new Object[]{"123"};
        Object[] params2 = new Object[]{"123", "234"};
        Object[] params3 = new Object[]{"123", "234", "345"};

        List<Class<?>> p1 = Stream.of(params1).map(Object::getClass).collect(Collectors.toList());
        List<Class<?>> p2 = Stream.of(params2).map(Object::getClass).collect(Collectors.toList());
        Method m1 = allMethods.get("t").get(p1.hashCode());
        Method m2 = allMethods.get("t").get(p2.hashCode());
        System.out.println(m1);
        System.out.println(m2);
    }

    public static class M {
        public void t(String a) {

        }

        public void t(String a, String aa) {

        }

        public void t(long l) {

        }

    }
}
