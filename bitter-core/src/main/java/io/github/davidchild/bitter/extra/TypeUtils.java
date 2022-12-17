package io.github.davidchild.bitter.extra;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class TypeUtils {

    public static Class<?> getTypeVariableType(Class<?> subClass, TypeVariable<?> typeVariable) {
        Map<TypeVariable<?>, Type> subMap = new HashMap<>();
        Class<?> superClass;
        while ((superClass = subClass.getSuperclass()) != null) {

            Map<TypeVariable<?>, Type> superMap = new HashMap<>();
            Type superGeneric = subClass.getGenericSuperclass();
            if (superGeneric instanceof ParameterizedType) {

                TypeVariable<?>[] typeParams = superClass.getTypeParameters();
                Type[] actualTypeArgs = ((ParameterizedType) superGeneric).getActualTypeArguments();

                for (int i = 0; i < typeParams.length; i++) {
                    Type actualType = actualTypeArgs[i];
                    if (actualType instanceof TypeVariable) {
                        actualType = subMap.get(actualType);
                    }
                    if (typeVariable == typeParams[i])
                        return (Class<?>) actualType;
                    superMap.put(typeParams[i], actualType);
                }
            }
            subClass = superClass;
            subMap = superMap;
        }
        return null;
    }

    public static Class<?> getTypeParameterType(Class<?> subClass, Class<?> superClass, int typeParameterIndex) {
        return TypeUtils.getTypeVariableType(subClass, superClass.getTypeParameters()[typeParameterIndex]);
    }

    public static Class<?> getFieldType(Class<?> clazz, AccessibleObject element) {
        Class<?> type = null;
        Type genericType = null;

        if (element instanceof Field) {
            type = ((Field) element).getType();
            genericType = ((Field) element).getGenericType();
        } else if (element instanceof Method) {
            type = ((Method) element).getReturnType();
            genericType = ((Method) element).getGenericReturnType();
        }

        if (genericType instanceof TypeVariable) {
            Class<?> typeVariableType = TypeUtils.getTypeVariableType(clazz, (TypeVariable) genericType);
            if (typeVariableType != null) {
                type = typeVariableType;
            }
        }

        return type;
    }
}
