package com.mystudy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class BeanUtil {
	public static Object getPropValue(Object obj, String filed) {
		try {
			Class clazz = obj.getClass();
			PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
			Method getMethod = pd.getReadMethod();
			if (pd != null)
				return getMethod.invoke(obj, new Object[0]);
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static Object setPropValue(Object obj, String filed, String value) {
		try {
			Class clazz = obj.getClass();
			PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
			Method getMethod = pd.getWriteMethod();
			if (pd != null)
				return getMethod.invoke(obj, new Object[] { value });
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public static Object loadClass(String jarfilepath,String classname) throws Exception{
		URL url1 = new URL("file:"+jarfilepath);
		URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 },
				Thread.currentThread().getContextClassLoader());
		Class<?> myClass1 = myClassLoader1.loadClass(classname);
		Object obj = (Object) myClass1.newInstance();
		myClassLoader1.close();
		return obj;
	}
	
	public static void main(String[] args) throws Exception {
		String path="C:/Users/Administrator/Desktop/tmppy/redis/po.jar";
		String classname = "com.mystudy.po.Notice";
		Object obj = BeanUtil.loadClass(path, classname);
		BeanUtil.setPropValue(obj, "title", "hello");
		BeanUtil.setPropValue(obj, "content", " this is content");
		System.out.println(GsonSerialUtil.serial(obj));
	}
}
