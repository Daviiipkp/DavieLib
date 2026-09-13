package org.daviipkp.davielib;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;

public class DReflection {
    
    // PUBLIC SECTION
    // PUBLIC SECTION
    // PUBLIC SECTION
    // PUBLIC SECTION
    // PUBLIC SECTION

    public static List<Class<?>> listClasses(String packageName, boolean useCache, boolean saveCache) throws IOException, ClassNotFoundException {
        List<Class<?>> l = new ArrayList<>();
        
        if(useCache) {
            l = CacheSystem.retrieve(l, CacheSystem.formatCacheName(packageName));
            if(!l.isEmpty()) {
                return l;
            }
        }


        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = cl.getResources(path);


        while (resources.hasMoreElements()) {
            URL res = resources.nextElement();
            if(res.getProtocol().equals("file")) {
                File f = new File(URLDecoder.decode(res.getFile(), StandardCharsets.UTF_8));
                l.addAll(listClassesInDir(f, packageName));
            }else if(res.getProtocol().equals("jar")) {
                l.addAll(listClassesInJar(res, packageName));
            }
        }

        if(saveCache) {
            CacheSystem.save(CacheSystem.formatCacheName(packageName), l);
        }

        return l;
    }

    public static <T extends Annotation> List<Class<?>> listClassesWithAnnotation(String packageName, Class<T> ann, boolean useCache, boolean saveCache) throws ClassNotFoundException, IOException {
        List<Class<?>> l = new ArrayList<>();
        
        if(useCache) {
            l = CacheSystem.retrieve(l, CacheSystem.formatCacheName(packageName, ann));
            if(!l.isEmpty()) {
                return l;
            }
        }
        
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = cl.getResources(path);

        

        while (resources.hasMoreElements()) {
            URL res = resources.nextElement();
            if(res.getProtocol().equals("file")) {
                File f = new File(URLDecoder.decode(res.getFile(), StandardCharsets.UTF_8));
                List<Class<?>> l2 = listClassesInDir(f, packageName);
                for(Class<?> c : l2) {
                    if(c.isAnnotationPresent(ann)) {
                        l.add(c);
                    }
                }
            }else if(res.getProtocol().equals("jar")) {
                List<Class<?>> l2 = listClassesInJar(res, packageName);
                for(Class<?> c : l2) {
                    if(c.isAnnotationPresent(ann)) {
                        l.add(c);
                    }
                }
            }
        }

        if(saveCache) {
            CacheSystem.save(CacheSystem.formatCacheName(packageName, ann), l);
        }

        return l;
    }





    //PRIVATE SECTION
    //PRIVATE SECTION
    //PRIVATE SECTION
    //PRIVATE SECTION
    //PRIVATE SECTION






    private static List<Class<?>> listClassesInDir(File dir, String packageName) throws ClassNotFoundException {
        List<Class<?>> ret = new ArrayList<>();
        if(!dir.exists())return ret;
        File[] files = dir.listFiles();
        if(files==null)return ret;

        for(File file : files) {
            if(file.isDirectory()) {
                ret.addAll(listClassesInDir(file, packageName + "." + file.getName()));
            }else if(file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                ret.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
            }
        }
        return ret;
    }

    private static List<Class<?>> listClassesInJar(URL resource, String packageName) throws IOException {
        List<Class<?>> ret = new ArrayList<>();
        String rPath = packageName.replace('.', '/');
        JarURLConnection jrc = (JarURLConnection)resource.openConnection();

        try(JarFile jf = jrc.getJarFile()) {
            //TODO
        }

        return ret;
    }

}
