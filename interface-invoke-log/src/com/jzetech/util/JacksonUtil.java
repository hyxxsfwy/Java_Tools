package com.jzetech.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author 0703
 */
public class JacksonUtil {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    /**
     * JSON字符串转换成Object
     */
    public static <T> T toObject(String json, Class<T> clazz) {

        try {
            if (json == null || "".equals(json)) {
                return null;
            }
            return objectMapper.readValue(json, clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * JSON字符串转换成List
     */
    public static <T> T toList(String json, TypeReference<T> valueTypeRef) {

        try {
            if (json == null || "".equals(json)) {
                return null;
            }
            return objectMapper.readValue(json, valueTypeRef);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JavaBean转换成JSON字符串
     */
    public static String toJsonString(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String argsToString(Object[] args) {
        StringBuilder result = new StringBuilder();
        for (Object arg : args) {
            result.append(toJsonString(arg));
            result.append("; ");
        }
        return result.toString();
    }

}
