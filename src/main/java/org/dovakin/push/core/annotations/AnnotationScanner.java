package org.dovakin.push.core.annotations;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by liuhuanchao on 2017/7/28.
 */
public class AnnotationScanner {

    public static Set<Class<?>> find(Class annotationType, String pack){
        Set<Class<?>> annotationClasses = new LinkedHashSet<Class<?>>();
        Set<Class<?>> classes = scan(pack);
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()){
            Class<?> cls = iterator.next();
            cls.isAnnotationPresent(annotationType);
            annotationClasses.add(cls);
        }
        return annotationClasses;
    }

    private static Set<Class<?>> scan(String pack){
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        boolean recursive = true;
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try{
            dirs = Thread.currentThread()
                    .getContextClassLoader().getResources(packageDirName);
            while(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackage(packageName, filePath,
                        recursive, classes);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return classes;
    }

    private static void findAndAddClassesInPackage(String packageName,
         String packagePath, final boolean recursive, Set<Class<?>> classes){
        File dir = new File(packagePath);
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (recursive && file.isDirectory()
                    || (file.getName().endsWith(".class")));
            }
        });
        for(File file : dirfiles){
            if(file.isDirectory()){
                findAndAddClassesInPackage(packageName + "." + file.getName(),
                        file.getAbsolutePath(), recursive, classes);
            } else {
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader()
                    .loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
