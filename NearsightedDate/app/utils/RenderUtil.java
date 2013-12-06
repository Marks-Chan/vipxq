package utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class RenderUtil {
    
    public static Map EmptyMap() {
        return new HashMap<String, Object>();
    }
    
    public static List EmptyMapList() {
        return new ArrayList<Map<String, Object>>();
    }
    
    public static Map SuccessMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("msg", "操作成功!");
        return map;
    }
    
    public static Map SuccessMap(Object data) {
        return SuccessMap(data, "");
    }
    
    public static Map SuccessMap(String msg) {
        return SuccessMap(null, msg);
    }
    
    public static Map SuccessMap(Object data, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("msg", StringUtils.isBlank(msg) ? "操作成功!" : msg);
        map.put("data", data);
        return map;
    }
    
    public static Map FailureMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("msg", "操作失败!");
        return map;
    }
    
    public static Map FailureMap(String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("msg", StringUtils.isBlank(msg) ? "操作失败!" : msg);
        return map;
    }
    
    public static Map DTO(Class clazz, Object obj) {
        List<Field> detailFields = getFields(clazz);
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        for (Field field : detailFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        return map;
    }
    
    /**
     * 
     * 功能描述：获取class的属性。
     * 
     * @param c
     * @return
     * @author <a href="mailto:chenwj@ucweb.com">陈玩杰 </a>
     * @version 1.0.0
     * @since 1.0.0 create on: 2013-6-28
     */
    public static List<Field> getFields(Class c) {
        List<Field> fields = new ArrayList<Field>();
        while (null != c) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
            c = c.getSuperclass();
        }
        return fields;
    }
    
}
