package br.com.cvive.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String methods, Object obj) throws Exception {
		if(obj == null || methods == null) return null;
		Class<?> clazz = obj.getClass();
		String[] methodsArray = methods.split("\\.");
		Method method = null;
		for (int i = 0; i < methodsArray.length; i++) {
			if(clazz == null) {
				obj = null;
				break;
			}

			String methodName = StringUtils.toGetterNameCamelCase(methodsArray[i]);
			try {
				method = clazz.getDeclaredMethod(methodName);
			} catch(NoSuchMethodException e) {
				try {
					methodName = StringUtils.toBooleanNameCamelCase(methodsArray[i]);
					method = clazz.getDeclaredMethod(methodName);
				} catch(NoSuchMethodException ex) {
					try {
						methodName = methodsArray[i];
						method = clazz.getDeclaredMethod(methodName);
					} catch(NoSuchMethodException exx) {
						methodName = methodsArray[i];
						method = clazz.getMethod(methodName);;
					}
				}
			}

			obj = method.invoke(obj);
			clazz = obj != null ? obj.getClass() : null;
		}

		return (T) obj;
	}
	
	public static void setValue(Object value, Object obj, String field) throws Exception {
		Class<?> clazz = obj.getClass();
		Field f1 = findUnderlying(clazz, field.toLowerCase());
		f1.set(obj, value);
	}
	
	public static Field findUnderlying(Class<?> clazz, String fieldName) {
	    Class<?> current = clazz;
	    do {
	       try {
	           return current.getDeclaredField(fieldName);
	       } catch(Exception e) {}
	    } while((current = current.getSuperclass()) != null);
	    return null;
	}
}