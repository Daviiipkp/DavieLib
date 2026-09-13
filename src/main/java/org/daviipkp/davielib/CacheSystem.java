package org.daviipkp.davielib;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CacheSystem {

    private static StringBuilder b = new StringBuilder();

    public static Map<String,List<Class<?>>> mappedClasses = new HashMap<>();

    public static <T> void save(String name, T object) {
        Map<String, T> map = whereDidISaveThatType(object);
        if(map != null) {
            map.put(name, object);
        }
    }

    public static <T extends Annotation> String formatCacheName(String packageName) {
        b.append(packageName);
        String ret = b.toString();
        b.setLength(0);
        return ret;
    }

    public static <T extends Annotation> String formatCacheName(String packageName, Class<T> ann) {
        b.append(packageName).append(".annotatedWith.").append(ann.getCanonicalName());
        String ret = b.toString();
        b.setLength(0);
        return ret;
    }

    public static <T> T retrieve(T type, String name) {
        Map<String, T> where = whereDidISaveThatType(type);
        if(where == null) return type;
        
        for(String s : where.keySet()) {
            if(name.equals(s)) {
                return where.get(s);
            }
        }

        return type;

    }

    private static <T> Map<String, T> whereDidISaveThatType(T object) {
        if(object instanceof List) {
            if(!(((List<?>)object).isEmpty())) {
                if((((List<?>)object).get(0)) instanceof Class<?>) {
                    return (Map<String, T>) mappedClasses;
                }

                //add here more options of lists

            }
        }


        return null;
    }
    
    
}
