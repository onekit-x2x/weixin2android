package cn.onekit.js;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import cn.onekit.js.core.JsBoolean;
import cn.onekit.js.core.JsObject;

import cn.onekit.js.core.JsString;
import cn.onekit.js.core.function;

public class JSON {
	private static Gson gson = new Gson();

	public static JsObject parse(String string) {
		try {
			if (string == null) {
				return null;
			}
			String str = string.trim();
			if (str.equals("")) {
				return null;
			}
			if (str.equals("null")) {
				return null;
			}
			if (str.equalsIgnoreCase("true")) {
				return new JsBoolean( true);
			}
			if (str.equalsIgnoreCase("false")) {
				return new JsBoolean( false);
			}
			switch (str.substring(0, 1)) {
				case "{":
					return gson.fromJson(str, (Type) Dict.class);
				case "[":
					return gson.fromJson(str, (Type) Array.class);
				default:
					if (string.startsWith("\"") && string.endsWith("\"")) {
						string = string.substring(1, string.length() - 1);
					}
					return new JsString( string);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String _entry(int depth, String key, JsObject value, JsObject replacer, int space){
		return String.format("\"%s\":%s",key,_stringify(depth+1,value,replacer,space));
	}
	public static String _stringify(int depth,JsObject json, JsObject replacer, int space) {
		if(space>10){
			space=10;
		}
		if(json==null){
			return null;
		}
		String tab;
		if( depth>0 && space>0){
			String format = "%"+depth*space+"s";
			tab =String.format(format," ");
		}else {
			tab = "";
		}
		if(json instanceof JsString){
			String string = ((JsString)json).THIS;
			return  String.format("\"%s\"",string);
		}
		StringBuilder result = new StringBuilder();
		if(json instanceof Array){
			Array array = (Array) json;
			result.append(tab+"[\r\n");
			int i=0;
			for(JsObject item: array){
				result.append(_stringify(depth+1,item,replacer,space));
				if(i<array.size()-1) {
					result.append(",");
				}
				result.append("\r\n");
				i++;
			}
			result.append(tab+"]");
		}else if(json instanceof Dict){
			Dict dict = (Dict) json;
			result.append(tab+"{\r\n");
			int i=0;
			for(String key : dict.keySet()){
				JsObject value = dict.get(key);
				if(replacer!=null){
					if(replacer instanceof function) {
						result.append(_entry(depth,key,value, replacer,space));
					}else if(replacer instanceof List){
						if(((List) replacer).contains(key)){
							result.append(_entry(depth,key,value,replacer,space));
						}
					}else {
						throw new java.lang.Error(replacer.toString());
					}
				}else{
					result.append(_entry(depth,key,value,replacer,space));
				}
				if(i<dict.keySet().size()-1) {
					result.append(",");
				}
				result.append("\r\n");
				i++;
			}
			result.append(tab+"}");
		}else{
			String str;
			if(replacer!=null){
				if(replacer instanceof function) {
					str = _stringify( depth+1,((function)replacer).invoke(json),replacer,space);
				}else if(replacer instanceof List){
					str=json.toString();
				}else {
					throw new java.lang.Error(replacer.toString());
				}
			}else{
				str=json.toString();
			}
			result.append(str);
		}
		return result.toString();
	}
	public static String stringify(JsObject json, JsObject replacer,int space) {
		return _stringify( 0,json, replacer,space);
	}
	public static String stringify(JsObject json, JsObject replacer,String space) {
		if(space.length()>10){
			space = space.substring(0,10);
		}
		return stringify( json, replacer, space.length());
	}
	public static String stringify(JsObject json, JsObject replacer) {
		return stringify(json, replacer, 0);
	}

	public static String stringify(JsObject json) {
		return stringify(json, null);
	}
}