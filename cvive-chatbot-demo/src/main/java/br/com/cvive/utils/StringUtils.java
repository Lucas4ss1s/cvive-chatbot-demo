package br.com.cvive.utils;


public class StringUtils {
	public static String toCamelCase(Class<?> klass) {
		String str = klass.getSimpleName();
		str = Character.toString(str.charAt(0)).toLowerCase() + str.substring(1, str.length());
		return str;
	}

	public static String toGetterNameCamelCase(String value) {
		value = value.toLowerCase();
		value = Character.toString(value.charAt(0)).toUpperCase() + value.substring(1, value.length());
		return "get" + value;
	}
	
	public static String toSetNameCamelCase(String value) {
		value = value.toLowerCase();
		value = Character.toString(value.charAt(0)).toUpperCase() + value.substring(1, value.length());
		return "set" + value;
	}
	
	public static String toBooleanNameCamelCase(String value) {
		value = Character.toString(value.charAt(0)).toUpperCase() + value.substring(1, value.length());
		return "is" + value;
	}

	public static String toJSFExpression(Class<?> klass) {
		String str = klass.getSimpleName();
		str = Character.toString(str.charAt(0)).toLowerCase() + str.substring(1, str.length());
		return "#{" + str + "}";
	}
	
	public static String getMimeType(String fileName) {
		if(fileName.toLowerCase().endsWith(".xls")) {
			return "application/vnd.ms-excel";
		}
		if(fileName.toLowerCase().endsWith(".pdf")) {
			return "application/octet-stream";
		}
		return null;
	}
}