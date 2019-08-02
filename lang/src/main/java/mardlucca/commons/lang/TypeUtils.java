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

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by mlucca on 1/23/17.
 */
public class TypeUtils
{
    private static Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_MAP;

    private static Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_MAP;

    private static Map<Class<?>, Integer> NUMERIC_TYPE_SIZE_ORDER;

    static
    {
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
    private TypeUtils() {}

    public static boolean isArrayType(Type aInType) {
        if (aInType instanceof GenericArrayType) { return true; }
        if (!(aInType instanceof Class)) { return false; }
        Class<?> lClass = (Class<?>) aInType;
        return lClass.isArray();
    }

    public static Type getArrayComponentType(Type aInArrayType) {
        if (aInArrayType instanceof GenericArrayType) {
            GenericArrayType lGenericArrayType =
                    (GenericArrayType) aInArrayType;
            return lGenericArrayType.getGenericComponentType();
        }
        if (!(aInArrayType instanceof Class)) { return null; }
        Class<?> lClass = (Class<?>) aInArrayType;
        return lClass.getComponentType();
    }

    public static Type getCollectionElementType(Type aInCollectionType)
    {
        if (!isCollection(aInCollectionType))
        {
            return null;
        }
        if (aInCollectionType instanceof Class)
        {
            // raw collection
            return Object.class;
        }
        if (aInCollectionType instanceof ParameterizedType)
        {
            return ((ParameterizedType) aInCollectionType)
                .getActualTypeArguments()[0];
        }

        return null;
    }

    public static Type[] getMapKeyValueTypes(Type aInMapType)
    {
        if (!isMap(aInMapType))
        {
            return null;
        }
        if (aInMapType instanceof Class)
        {
            // raw map
            return new Type[] { Object.class, Object.class };
        }
        if (aInMapType instanceof ParameterizedType)
        {
            return ((ParameterizedType) aInMapType)
                .getActualTypeArguments();
        }

        return null;
    }

    public static boolean isCollection(Type aInTo)
    {
        if (aInTo instanceof Class)
        {
            // raw collection, possibly
            return Collection.class.isAssignableFrom((Class<?>) aInTo);
        }
        if (aInTo instanceof ParameterizedType)
        {
            ParameterizedType lParameterizedType = (ParameterizedType) aInTo;
            Type lRawType = lParameterizedType.getRawType();
            return (lRawType instanceof Class
                && Collection.class.isAssignableFrom((Class<?>) lRawType));
        }
        return false;
    }

    public static <T> Class<T> getArrayClass(Type aInType)
    {
        if (!isArrayType(aInType))
        {
            return null;
        }

        if (aInType instanceof Class)
        {
            // this is a regular array
            Class<?> lArrayClass = (Class<?>) aInType;
            Class<?> lComponentType = lArrayClass.getComponentType();
            if (lComponentType.isPrimitive())
            {
                return (Class<T>) wrap(lComponentType);
            }
            else if (lComponentType.isArray())
            {
                // we need to convert multidimensional primitive arrays to
                // multidimensional wrapper arrays
                return (Class<T>) Array.newInstance(
                    getArrayClass(lComponentType), 0).getClass();
            }
            return (Class<T>) lComponentType;
        }

        if (aInType instanceof GenericArrayType)
        {
            GenericArrayType lArrayType = (GenericArrayType) aInType;
            return getGenericArrayComponentClass(
                lArrayType.getGenericComponentType());
        }

        return null;
    }

    private static <T> Class<T> getGenericArrayComponentClass(Type aInType)
    {
        if (aInType instanceof ParameterizedType)
        {
            // generic array of parameterized types
            ParameterizedType lParameterizedType = (ParameterizedType) aInType;
            return (Class<T>) lParameterizedType.getRawType();
        }
        if (aInType instanceof TypeVariable)
        {
            // generic array of type variables
            TypeVariable<?> lTypeVariable = (TypeVariable<?>) aInType;
            return (Class<T>) lTypeVariable.getBounds()[0];
        }
        if (aInType instanceof GenericArrayType)
        {
            // multi dimensional generic array type
            GenericArrayType lArrayType = (GenericArrayType) aInType;
            Class<?> lComponentClass = getGenericArrayComponentClass(
                lArrayType.getGenericComponentType());
            Object lObject = Array.newInstance(lComponentClass, 1);
            return (Class<T>) lObject.getClass();
        }

        return null;
    }

    public static Class<? extends Collection> getCollectionClass(
        Type aInType)
    {
        if (!isCollection(aInType))
        {
            return null;
        }

        Class<? extends Collection> lCollectionType;
        if (aInType instanceof Class)
        {
            // raw collection
            lCollectionType = (Class<? extends Collection>) aInType;
        }
        else
        {
            ParameterizedType lParameterizedType = (ParameterizedType) aInType;
            lCollectionType =
                (Class<? extends Collection>) lParameterizedType.getRawType();
        }

        if (Set.class.isAssignableFrom(lCollectionType))
        {
            return lCollectionType.isInterface() ?
                HashSet.class
                : lCollectionType;
        }

        return lCollectionType.isInterface()
            ?  ArrayList.class
            : lCollectionType;
    }

    public static boolean isMap(Type aInTo)
    {
        if (aInTo instanceof Class)
        {
            // raw map?
            return Map.class.isAssignableFrom((Class<?>) aInTo);
        }
        if (aInTo instanceof ParameterizedType)
        {
            ParameterizedType lParameterizedType = (ParameterizedType) aInTo;
            Type lRawType = lParameterizedType.getRawType();
            return (lRawType instanceof Class
                && Map.class.isAssignableFrom((Class<?>) lRawType));
        }
        return false;
    }

    public static Type wrap(Type aInType)
    {
        if (!(aInType instanceof Class<?>))
        {
            return aInType;
        }

        Class<?> lClass = (Class<?>) aInType;
        if (lClass.isPrimitive())
        {
            return PRIMITIVE_TO_WRAPPER_MAP.get(lClass);
        }
        return lClass;
    }

    public static boolean isNumeric(Type aInType)
    {
        if (!(aInType instanceof Class<?>))
        {
            return false;
        }
        Class<?> lClass = (Class<?>) aInType;

        if (Number.class.isAssignableFrom(lClass)
            || lClass == byte.class
            || lClass == short.class
            || lClass == int.class
            || lClass == long.class
            || lClass == float.class
            || lClass == double.class)
        {
            return true;
        }

        return false;
    }

    public static boolean isPrimitiveFor(Type aInPrimitiveType,
        Type aInWrapperType)
    {
        if (!(aInPrimitiveType instanceof Class<?>)
            || !(aInWrapperType instanceof Class<?>))
        {
            return false;
        }
        Class<?> lPrimitiveClass = (Class<?>) aInPrimitiveType;
        Class<?> lWrapperClass = (Class<?>) aInWrapperType;

        return lWrapperClass.equals(
            PRIMITIVE_TO_WRAPPER_MAP.get(lPrimitiveClass));
    }

    public static boolean isWrapperFor(Type aInWrapperType,
        Type aInPrimitiveType)
    {
        if (!(aInPrimitiveType instanceof Class<?>)
            || !(aInWrapperType instanceof Class<?>))
        {
            return false;
        }
        Class<?> lPrimitiveClass = (Class<?>) aInPrimitiveType;
        Class<?> lWrapperClass = (Class<?>) aInWrapperType;

        return lPrimitiveClass.isPrimitive() && lPrimitiveClass.equals(
            WRAPPER_TO_PRIMITIVE_MAP.get(lWrapperClass));
    }

    public static int compareNumericSize(Type aInType1, Type aInType2)
    {
        if (!isNumeric(aInType1))
        {
            throw new IllegalArgumentException(aInType1 + " is not numeric");
        }
        if (!isNumeric(aInType2))
        {
            throw new IllegalArgumentException(aInType2 + " is not numeric");
        }

        return NUMERIC_TYPE_SIZE_ORDER.get(aInType1)
            - NUMERIC_TYPE_SIZE_ORDER.get(aInType2);
    }

    public static boolean isContainerType(Type aInContainerType)
    {
        return isArrayType(aInContainerType)
            || isCollection(aInContainerType);
    }

    public static Type getElementType(Type aInContainerType)
    {
        if (isArrayType(aInContainerType))
        {
            return getArrayComponentType(aInContainerType);
        }
        if (isCollection(aInContainerType))
        {
            return getCollectionElementType(aInContainerType);
        }

        return null;
    }

    public static boolean isAssignable(Type aInFrom, Type aInTo) {
        // TODO: Figure this out. Commons lang has this but I'm considering
        // implementing my own simplified version
        return false;
    }
}
