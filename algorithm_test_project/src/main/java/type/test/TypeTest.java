package type.test;

import json.tomap.JsonToMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.HashMap;

public class TypeTest {
    public static void main(String[] args) {
        Class type1 = HashMap.class;

        Type type = type1;

        if (type instanceof Class<?>) {
            System.out.println("Class");

        }

        if (type instanceof ParameterizedType) {
            System.out.println("ParameterizedType");
        }

        if (type instanceof WildcardType) {
            System.out.println("WildcardType");
        }
    }
}
