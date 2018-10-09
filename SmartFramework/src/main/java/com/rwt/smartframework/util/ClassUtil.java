package com.rwt.smartframework.util;


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

/*
* 类加载工具类
* */
public class ClassUtil {

    private static final Logger logger=LoggerFactory.getLogger(ClassUtil.class);
    /*
    * 获取类加载器
    * */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /*
    * 加载类
    * */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class <?> cls=null;
        try {
            cls=Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error(className+"class not found",e);
            throw new RuntimeException(e);
        }

        return  cls;
    }


    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        try{
            Enumeration<URL> urls=getClassLoader().getResources(packageName.replace(".","/"));
            while(urls.hasMoreElements()){
                URL url=urls.nextElement();
                String protocol=url.getProtocol();
                if("file".equals(protocol)){
                    String packagePath=url.getPath().replaceAll("%20"," ");
                    addClass(classSet,packagePath,packageName);
                } else if ("jar".equals(protocol)) {
                    JarURLConnection jarURLConnection= (JarURLConnection) url.openConnection();
                    if(jarURLConnection!=null){
                        JarFile jarFile=jarURLConnection.getJarFile();
                        if(jarFile!=null){
                            Enumeration<JarEntry> jarEntries =jarFile.entries();
                            while(jarEntries.hasMoreElements()){
                                JarEntry jarEntry=jarEntries.nextElement();
                                String jarEntryName=jarEntry.getName();
                                if(jarEntryName.endsWith(".class")){
                                    String className=jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                    doAddClass(classSet,className);
                                }
                            }
                        }
                    }

                }
            }

        }catch (IOException e) {
            logger.error("get class set failure",e);
            throw  new RuntimeException(e);
        }
        return classSet;
    }


    private static void addClass(Set <Class<?>> classSet,String packagePath,String packageName){

        File [] files=new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile()&&pathname.getName().endsWith(".class")||pathname.isDirectory();
            }
        });

        for(File file:files){
            String fileName=file.getName();
            if(file.isFile()){
                String className=fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtils.isEmpty(packageName)){
                    className=packageName+"."+className;

                }
                doAddClass(classSet,className);
            }else{
                String subPackagePath=fileName;
                if(StringUtils.isEmpty(packagePath)){
                    subPackagePath=subPackagePath+"/"+subPackagePath;
                }
                String subPackageName=fileName.substring(0,fileName.lastIndexOf("."));//有不同，记得测试
                if(StringUtils.isEmpty(packageName)){
                    subPackageName=packageName+"."+subPackageName;
                }

                addClass(classSet,subPackagePath,subPackageName);

            }

        }
    }

    private static void doAddClass(Set<Class<?>> classSet,String className){
        classSet.add(loadClass(className,false));
    }


}
