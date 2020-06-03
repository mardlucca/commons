/*
 * File: TypeUtils.java
 *
 * Copyright 2019 Marcio D. Lucca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mardlucca.commons.lang;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mlucca on 1/23/17.
 */
public class TypeUtils {
    private static Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_MAP;

    private static Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_MAP;

    private static Map<Class<?>, Integer> NUMERIC_TYPE_SIZE_ORDER;

    static {
        PRIMITIVE_TO_WRAPPER_MAP = new HashMap<>();
        PRIMITIVE_TO_WRAPPER_MAP.put(char.class, Character.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(byte.class, Byte.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(short.class, Short.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(int.class, Integer.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(long.class, Long.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(float.class, Float.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(double.class, Double.class);

        WRAPPER_TO_PRIMITIVE_MAP = new HashMap<>();
        WRAPPER_TO_PRIMITIVE_MAP.put(Character.class, char.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Byte.class, byte.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Short.class, short.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Integer.class, int.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Long.class, long.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Float.class, float.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Double.class, double.class);

        NUMERIC_TYPE_SIZE_ORDER = new HashMap<>();
        NUMERIC_TYPE_SIZE_ORDER.put(byte.class, 0);
        NUMERIC_TYPE_SIZE_ORDER.put(Byte.class, 0);
        NUMERIC_TYPE_SIZE_ORDER.put(short.class, 1);
        NUMERIC_TYPE_SIZE_ORDER.put(Short.class, 1);
        NUMERIC_TYPE_SIZE_ORDER.put(int.class, 2);
        NUMERIC_TYPE_SIZE_ORDER.put(Integer.class, 2);
        NUMERIC_TYPE_SIZE_ORDER.put(long.class, 3);
        NUMERIC_TYPE_SIZE_ORDER.put(Long.class, 3);
        NUMERIC_TYPE_SIZE_ORDER.put(float.class, 4);
        NUMERIC_TYPE_SIZE_ORDER.put(Float.class, 4);
        NUMERIC_TYPE_SIZE_ORDER.put(double.class, 5);
        NUMERIC_TYPE_SIZE_ORDER.put(Double.class, 5);
    }

    /**
     * Private constructor. This is not meant to be instantiated.
     */
    private TypeUtils() {
    }

    public static boolean isGeneric(Type aInType) {
        if (aInType instanceof Class) {
            return false;
        }

        if (aInType instanceof TypeVariable
                || aInType instanceof WildcardType) {
            return true;
        }

        if (aInType instanceof ParameterizedType) {
            for (Type lTypeParameter :
                    ((ParameterizedType) aInType).getActualTypeArguments()) {
                if (isGeneric(lTypeParameter)) {
                    return true;
                }
            }

            return false;
        }

        // else aInType is a GenericArrayType
        GenericArrayType lArrayType = (GenericArrayType) aInType;
        return isGeneric(lArrayType.getGenericComponentType());
    }

    public static boolean hasTypeVariables(Type aInType) {
        if (aInType instanceof Class) {
            return false;
        }

        if (aInType instanceof TypeVariable) {
            return true;
        }

        if (aInType instanceof WildcardType) {
            WildcardType lWildcardType = (WildcardType) aInType;
            for (Type lUpperBound : lWildcardType.getUpperBounds()) {
                if (hasTypeVariables(lUpperBound)) {
                    return true;
                }
            }
            for (Type lLowerBound : lWildcardType.getLowerBounds()) {
                if (hasTypeVariables(lLowerBound)) {
                    return true;
                }
            }
            return false;
        }

        if (aInType instanceof ParameterizedType) {
            for (Type lTypeParameter :
                    ((ParameterizedType) aInType).getActualTypeArguments()) {
                if (hasTypeVariables(lTypeParameter)) {
                    return true;
                }
            }

            return false;
        }

        // else aInType is a GenericArrayType
        GenericArrayType lArrayType = (GenericArrayType) aInType;
        return hasTypeVariables(lArrayType.getGenericComponentType());
    }

    public static boolean isArrayType(Type aInType) {
        if (aInType instanceof GenericArrayType) {
            return true;
        }
        if (!(aInType instanceof Class)) {
            return false;
        }
        Class<?> lClass = (Class<?>) aInType;
        return lClass.isArray();
    }

    public static Type getArrayComponentType(Type aInArrayType) {
        if (aInArrayType instanceof GenericArrayType) {
            GenericArrayType lGenericArrayType =
                    (GenericArrayType) aInArrayType;
            return lGenericArrayType.getGenericComponentType();
        }
        if (!(aInArrayType instanceof Class)) {
            return null;
        }
        Class<?> lClass = (Class<?>) aInArrayType;
        return lClass.getComponentType();
    }

    public static Type getCollectionElementType(Type aInCollectionType) {
        if (!isCollection(aInCollectionType)) {
            return null;
        }

        if (aInCollectionType instanceof Class) {
            // raw collection
            return Object.class;
        }

        if (aInCollectionType instanceof ParameterizedType) {
            return ((ParameterizedType) aInCollectionType)
                    .getActualTypeArguments()[0];
        }

        // this should never happen
        return null;
    }

    public static Type[] getMapKeyValueTypes(Type aInMapType) {
        if (!isMap(aInMapType)) {
            return null;
        }
        if (aInMapType instanceof Class) {
            // raw map
            return new Type[]{Object.class, Object.class};
        }
        if (aInMapType instanceof ParameterizedType) {
            return ((ParameterizedType) aInMapType)
                    .getActualTypeArguments();
        }

        // this should never happen
        return null;
    }

    public static boolean isCollection(Type aInTo) {
        if (aInTo instanceof Class) {
            // raw collection, possibly
            return Collection.class.isAssignableFrom((Class<?>) aInTo);
        }
        if (aInTo instanceof ParameterizedType) {
            ParameterizedType lParameterizedType = (ParameterizedType) aInTo;
            Type lRawType = lParameterizedType.getRawType();
            return (lRawType instanceof Class
                    && Collection.class.isAssignableFrom((Class<?>) lRawType));
        }
        return false;
    }

    public static boolean isMap(Type aInTo) {
        if (aInTo instanceof Class) {
            // raw map?
            return Map.class.isAssignableFrom((Class<?>) aInTo);
        }
        if (aInTo instanceof ParameterizedType) {
            ParameterizedType lParameterizedType = (ParameterizedType) aInTo;
            Type lRawType = lParameterizedType.getRawType();
            return (lRawType instanceof Class
                    && Map.class.isAssignableFrom((Class<?>) lRawType));
        }
        return false;
    }

    public static Type boxingType(Type aInType) {
        if (!(aInType instanceof Class<?>)) {
            return aInType;
        }

        Class<?> lClass = (Class<?>) aInType;
        if (lClass.isPrimitive()) {
            return PRIMITIVE_TO_WRAPPER_MAP.get(lClass);
        }
        return lClass;
    }

    public static boolean isNumeric(Type aInType) {
        if (!(aInType instanceof Class<?>)) {
            return false;
        }
        Class<?> lClass = (Class<?>) aInType;

        if (Number.class.isAssignableFrom(lClass)
                || lClass == byte.class
                || lClass == short.class
                || lClass == int.class
                || lClass == long.class
                || lClass == float.class
                || lClass == double.class) {
            return true;
        }

        return false;
    }

    public static boolean isPrimitiveFor(Type aInPrimitiveType,
                                         Type aInWrapperType) {
        if (!(aInPrimitiveType instanceof Class<?>)
                || !(aInWrapperType instanceof Class<?>)) {
            return false;
        }
        Class<?> lPrimitiveClass = (Class<?>) aInPrimitiveType;
        Class<?> lWrapperClass = (Class<?>) aInWrapperType;

        return lWrapperClass.equals(
                PRIMITIVE_TO_WRAPPER_MAP.get(lPrimitiveClass));
    }

    public static boolean isWrapperFor(Type aInWrapperType,
                                       Type aInPrimitiveType) {
        if (!(aInPrimitiveType instanceof Class<?>)
                || !(aInWrapperType instanceof Class<?>)) {
            return false;
        }
        Class<?> lPrimitiveClass = (Class<?>) aInPrimitiveType;
        Class<?> lWrapperClass = (Class<?>) aInWrapperType;

        return lPrimitiveClass.isPrimitive() && lPrimitiveClass.equals(
                WRAPPER_TO_PRIMITIVE_MAP.get(lWrapperClass));
    }

    public static int compareNumericSize(Type aInType1, Type aInType2) {
        if (!isNumeric(aInType1)) {
            throw new IllegalArgumentException(aInType1 + " is not numeric");
        }
        if (!isNumeric(aInType2)) {
            throw new IllegalArgumentException(aInType2 + " is not numeric");
        }

        return NUMERIC_TYPE_SIZE_ORDER.get(aInType1)
                - NUMERIC_TYPE_SIZE_ORDER.get(aInType2);
    }

    public static boolean isAssignable(Type aInFrom, Type aInTo) {
        List l1 = null;
        List<Integer> l2 = null;
        
        l2 = l1;
        // TODO: Figure this out. Commons lang has this but I'm considering
        // implementing my own simplified version
        return false;
    }
}
