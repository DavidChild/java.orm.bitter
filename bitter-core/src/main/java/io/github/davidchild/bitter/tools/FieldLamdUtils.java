package io.github.davidchild.bitter.tools;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.FieldFunction;

public class FieldLamdUtils {

    private static Map<Class, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();

    /***
     * Convert method reference to property name
     *
     * @param fn
     * @return
     */
    public static <T extends BaseModel> String convertToFieldName(FieldFunction<T> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        // Get method name
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        } else if (methodName.startsWith("is")) {
            prefix = "is";
        }
        if (prefix == null) {
            System.out.println("无效的getter方法: " + methodName);
        }
        // Intercept the string after get/is and convert the first letter to lower case
        return toLowerCaseFirstOne(methodName.replace(prefix, ""));
    }

    /**
     * First letter to lower case
     *
     * @param s
     * @return
     */
    static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }

    /** The key is this method */
    static SerializedLambda getSerializedLambda(Serializable fn) {
        SerializedLambda lambda = CLASS_LAMBDA_CACHE.get(fn.getClass());
        // Check whether the cache already exists
        if (lambda == null) {
            try {
                // Extract SerializedLambda and cache
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda)method.invoke(fn);
                CLASS_LAMBDA_CACHE.put(fn.getClass(), lambda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lambda;
    }
}
