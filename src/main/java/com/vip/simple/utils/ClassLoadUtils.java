package com.vip.simple.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by dacheng.liu on 2017/4/27.
 * 类操作工具
 */
public final class ClassLoadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoadUtils.class);

    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className,boolean initialized){

        Class<?> cls;
        try {
            cls = Class.forName(className,initialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassLoadUtils.loadClass() happen exception!",e);
            throw new RuntimeException(e.getMessage());
        }

        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName){

        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(url != null){
                    String protocol = url.getProtocol();

                    if("file".equals(protocol)){  //
                        String packagePath = url.getPath().replaceAll("%20","");
                        addClass(classSet,packagePath,packageName);

                    }else if("jar".equals(protocol)){
                        JarURLConnection jarUrlConnection = (JarURLConnection) url.openConnection();
                        if(jarUrlConnection != null){
                            JarFile jarFile = jarUrlConnection.getJarFile();
                            if(jarFile != null){
                                Enumeration<JarEntry> jarEntrys = jarFile.entries();
                                while (jarEntrys.hasMoreElements()){
                                    JarEntry jarEntry = jarEntrys.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if(jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                        doAddClass(classSet,className);
                                    }

                                }
                            }
                        }

                    }
                }
            }


        } catch (IOException e) {
            LOGGER.error("ClassLoadUtils.getClassSet() happen exceptipon",e);
            throw new RuntimeException(e.getMessage());
        }
        return classSet;

    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });

        for (File file:files) {
            String fileName = file.getName();
            if(file.isFile()){  // 如果是class文件
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtils.isNotEmpty(packageName)){
                    className = packageName + className; // 类的全路径
                }
                // 加载class到内存中
                doAddClass(classSet,className);
            }else{
                String subPackPath = fileName;
                if(StringUtils.isNotEmpty(subPackPath)){
                    subPackPath = packagePath + "/" + subPackPath;
                }

                String subPackName = fileName ;
                if(StringUtils.isNotEmpty(packageName)){
                    subPackName = packageName + "." + subPackName;
                }

                // 循环递归调用判断当前packAgePath是否是文件夹，若是则一直地柜调用下去
                addClass(classSet,subPackPath,subPackName);
            }

        }

    }



    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }

}
