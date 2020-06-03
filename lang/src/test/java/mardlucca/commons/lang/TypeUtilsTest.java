/*
 * File: TypeUtilsTest.java
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test class for {@link TypeUtils}. In spite of this class containing
 * type variables, JUnit is able to run this without any problems.
 *
 * @param <E> a type variable. This is used in test cases
 * @param <S> a bounded type variable. Used in test cases
 */
public class TypeUtilsTest<E, S extends Comparable<E>> {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    // type variables
    private TypeReference<E> e = new TypeReference<E>() {};
    private TypeReference<S> s = new TypeReference<S>() {};

    // arrays
    private TypeReference<int[]> intArray = new TypeReference<int[]>() {};
    private TypeReference<Integer[]> integerArray =
            new TypeReference<Integer[]>() {};
    private TypeReference<E[]> eArray = new TypeReference<E[]>() {};
    private TypeReference<S[]> sArray = new TypeReference<S[]>() {};
    private TypeReference<List<E>[]> arrayOfListsOfE =
            new TypeReference<List<E>[]>() {};
    private TypeReference<List<Integer>[]> arrayOfListsOfInteger =
            new TypeReference<List<Integer>[]>() {};
    private TypeReference<List<?>[]> arrayOfListsOfWildcards =
            new TypeReference<List<?>[]>() {};
    private TypeReference<List<? extends Cloneable>[]>
            arrayOfListsOfBoundedWildcards =
            new TypeReference<List<? extends Cloneable>[]>() {};
    private TypeReference<int[][]> arrayOfArraysOfInts =
            new TypeReference<int[][]>() {};

    // lists
    private TypeReference<List> rawList = new TypeReference<List>() {};
    private TypeReference<List<Integer>> listOfInteger =
            new TypeReference<List<Integer>>() {};
    private TypeReference<List<E>> listOfE = new TypeReference<List<E>>() {};
    private TypeReference<List<S>> listOfS = new TypeReference<List<S>>() {};
    private TypeReference<List<S[]>> listOfSArrays =
            new TypeReference<List<S[]>>() {};
    private TypeReference<List<?>> listOfWildcards =
            new TypeReference<List<?>>() {};
    private TypeReference<List<? extends Cloneable>> listOfBoundedWildcards =
            new TypeReference<List<? extends Cloneable>>() {};
    private TypeReference<List<List<E>>> listOflistsOfE =
            new TypeReference<List<List<E>>>() {};
    private TypeReference<List<List<Integer>>> listOfListsOfInteger =
            new TypeReference<List<List<Integer>>>() {};
    private TypeReference<List<List<?>>> listOfListsOfWildcards =
            new TypeReference<List<List<?>>>() {};
    private TypeReference<List<int[]> >listOfIntArrays =
            new TypeReference<List<int[]>>() {};

    // maps
    private TypeReference<Map> rawMap = new TypeReference<Map>() {};
    private TypeReference<Map<String, Integer>> mapOfStringToInteger =
            new TypeReference<Map<String, Integer>>() {};
    private TypeReference<Map<Integer, E>> mapOfIntegerToE =
            new TypeReference<Map<Integer, E>>() {};
    private TypeReference<Map<Integer, S>> mapOfIntegerToS =
            new TypeReference<Map<Integer, S>>() {};
    private TypeReference<Map<String, S[]>> mapOfStringToSArrays =
            new TypeReference<Map<String, S[]>>() {};
    private TypeReference<Map<String, ?>> mapOfStringToWildcards =
            new TypeReference<Map<String, ?>>() {};
    private TypeReference<Map<String, ? extends Cloneable>>
            mapOfStringToBoundedWildcards =
            new TypeReference<Map<String, ? extends Cloneable>>() {};
    private TypeReference<Map<String, List<E>>> mapOfStringTolistsOfE =
            new TypeReference<Map<String, List<E>>>() {};
    private TypeReference<Map<String, List<Integer>>>
            mapOfStringToListsOfInteger =
            new TypeReference<Map<String, List<Integer>>>() {};
    private TypeReference<Map<String, List<?>>> mapOfStringToListsOfWildcards =
            new TypeReference<Map<String, List<?>>>() {};
    private TypeReference<Map<String, int[]>> mapOfStringToIntArray =
            new TypeReference<Map<String, int[]>>() {};

//    e, s, intArray, integerArray, eArray, sArray, arrayOfListsOfE,
//    arrayOfListsOfInteger, arrayOfListsOfWildcards,
//    arrayOfListsOfBoundedWildcards, arrayOfArraysOfInts, rawList,
//    listOfInteger, listOfE, listOfS, listOfSArrays, listOfWildcards,
//    listOfBoundedWildcards, listOflistsOfE, listOfListsOfInteger,
//    listOfListsOfWildcards, listOfIntArrays, rawMap, mapOfStringToInteger,
//    mapOfIntegerToE, mapOfIntegerToS, mapOfStringToSArrays,
//    mapOfStringToWildcards, mapOfStringToBoundedWildcards,
//    mapOfStringTolistsOfE, mapOfStringToListsOfInteger,
//    mapOfStringToListsOfWildcards, mapOfStringToIntArray


    @Test
    public void isGeneric() {
        assertFalse(TypeUtils.isGeneric(int.class));
        assertTrue(TypeUtils.isGeneric(e.getType()));
        assertTrue(TypeUtils.isGeneric(s.getType()));
        assertFalse(TypeUtils.isGeneric(intArray.getType()));
        assertFalse(TypeUtils.isGeneric(integerArray.getType()));
        assertTrue(TypeUtils.isGeneric(eArray.getType()));
        assertTrue(TypeUtils.isGeneric(sArray.getType()));
        assertTrue(TypeUtils.isGeneric(arrayOfListsOfE.getType()));
        assertFalse(TypeUtils.isGeneric(arrayOfListsOfInteger.getType()));
        assertTrue(TypeUtils.isGeneric(arrayOfListsOfWildcards.getType()));
        assertTrue(TypeUtils.isGeneric(
                arrayOfListsOfBoundedWildcards.getType()));
        assertFalse(TypeUtils.isGeneric(arrayOfArraysOfInts.getType()));
        assertFalse(TypeUtils.isGeneric(rawList.getType()));
        assertFalse(TypeUtils.isGeneric(listOfInteger.getType()));
        assertTrue(TypeUtils.isGeneric(listOfE.getType()));
        assertTrue(TypeUtils.isGeneric(listOfS.getType()));
        assertTrue(TypeUtils.isGeneric(listOfSArrays.getType()));
        assertTrue(TypeUtils.isGeneric(listOfWildcards.getType()));
        assertTrue(TypeUtils.isGeneric(listOfBoundedWildcards.getType()));
        assertTrue(TypeUtils.isGeneric(listOflistsOfE.getType()));
        assertFalse(TypeUtils.isGeneric(listOfListsOfInteger.getType()));
        assertTrue(TypeUtils.isGeneric(listOfListsOfWildcards.getType()));
        assertFalse(TypeUtils.isGeneric(listOfIntArrays.getType()));
        assertFalse(TypeUtils.isGeneric(rawMap.getType()));
        assertFalse(TypeUtils.isGeneric(mapOfStringToInteger.getType()));
        assertTrue(TypeUtils.isGeneric(mapOfIntegerToE.getType()));
        assertTrue(TypeUtils.isGeneric(mapOfIntegerToS.getType()));
        assertTrue(TypeUtils.isGeneric(mapOfStringToSArrays.getType()));
        assertTrue(TypeUtils.isGeneric(mapOfStringToWildcards.getType()));
        assertTrue(TypeUtils.isGeneric(
                mapOfStringToBoundedWildcards.getType()));
        assertTrue(TypeUtils.isGeneric(mapOfStringTolistsOfE.getType()));
        assertFalse(TypeUtils.isGeneric(mapOfStringToListsOfInteger.getType()));
        assertTrue(TypeUtils.isGeneric(
                mapOfStringToListsOfWildcards.getType()));
        assertFalse(TypeUtils.isGeneric(mapOfStringToIntArray.getType()));
    }

    @Test
    public void isArrayType() {
        assertFalse(TypeUtils.isArrayType(int.class));
        assertFalse(TypeUtils.isArrayType(e.getType()));
        assertFalse(TypeUtils.isArrayType(s.getType()));
        assertTrue(TypeUtils.isArrayType(intArray.getType()));
        assertTrue(TypeUtils.isArrayType(integerArray.getType()));
        assertTrue(TypeUtils.isArrayType(eArray.getType()));
        assertTrue(TypeUtils.isArrayType(sArray.getType()));
        assertTrue(TypeUtils.isArrayType(arrayOfListsOfE.getType()));
        assertTrue(TypeUtils.isArrayType(arrayOfListsOfInteger.getType()));
        assertTrue(TypeUtils.isArrayType(arrayOfListsOfWildcards.getType()));
        assertTrue(TypeUtils.isArrayType(
                arrayOfListsOfBoundedWildcards.getType()));
        assertTrue(TypeUtils.isArrayType(arrayOfArraysOfInts.getType()));
        assertFalse(TypeUtils.isArrayType(rawList.getType()));
        assertFalse(TypeUtils.isArrayType(listOfInteger.getType()));
        assertFalse(TypeUtils.isArrayType(listOfE.getType()));
        assertFalse(TypeUtils.isArrayType(listOfS.getType()));
        assertFalse(TypeUtils.isArrayType(listOfSArrays.getType()));
        assertFalse(TypeUtils.isArrayType(listOfWildcards.getType()));
        assertFalse(TypeUtils.isArrayType(listOfBoundedWildcards.getType()));
        assertFalse(TypeUtils.isArrayType(listOflistsOfE.getType()));
        assertFalse(TypeUtils.isArrayType(listOfListsOfInteger.getType()));
        assertFalse(TypeUtils.isArrayType(listOfListsOfWildcards.getType()));
        assertFalse(TypeUtils.isArrayType(listOfIntArrays.getType()));
        assertFalse(TypeUtils.isArrayType(rawMap.getType()));
        assertFalse(TypeUtils.isArrayType(mapOfStringToInteger.getType()));
        assertFalse(TypeUtils.isArrayType(mapOfIntegerToE.getType()));
        assertFalse(TypeUtils.isArrayType(mapOfIntegerToS.getType()));
        assertFalse(TypeUtils.isArrayType(mapOfStringToSArrays.getType()));
        assertFalse(TypeUtils.isArrayType(mapOfStringToWildcards.getType()));
        assertFalse(TypeUtils.isArrayType(
                mapOfStringToBoundedWildcards.getType()));
        assertFalse(TypeUtils.isArrayType(mapOfStringTolistsOfE.getType()));
        assertFalse(TypeUtils.isArrayType(
                mapOfStringToListsOfInteger.getType()));
        assertFalse(TypeUtils.isArrayType(
                mapOfStringToListsOfWildcards.getType()));
        assertFalse(TypeUtils.isArrayType(mapOfStringToIntArray.getType()));
    }

    @Test
    public void getArrayComponentType() {
        assertNull(TypeUtils.getArrayComponentType(int.class));
        assertNull(TypeUtils.getArrayComponentType(e.getType()));
        assertNull(TypeUtils.getArrayComponentType(s.getType()));
        assertTypeEquals(
                "int", TypeUtils.getArrayComponentType(intArray.getType()));
        assertTypeEquals(
                "class java.lang.Integer",
                TypeUtils.getArrayComponentType(integerArray.getType()));
        assertTypeEquals(
                "E", TypeUtils.getArrayComponentType(eArray.getType()));
        assertTypeEquals(
                "S", TypeUtils.getArrayComponentType(sArray.getType()));
        assertTypeEquals(
                "java.util.List<E>",
                TypeUtils.getArrayComponentType(arrayOfListsOfE.getType()));
        assertTypeEquals(
                "java.util.List<java.lang.Integer>",
                TypeUtils.getArrayComponentType(
                        arrayOfListsOfInteger.getType()));
        assertTypeEquals(
                "java.util.List<?>",
                TypeUtils.getArrayComponentType(
                        arrayOfListsOfWildcards.getType()));
        assertTypeEquals(
                "java.util.List<? extends java.lang.Cloneable>",
                TypeUtils.getArrayComponentType(
                        arrayOfListsOfBoundedWildcards.getType()));
        assertTypeEquals(
                "class [I",
                TypeUtils.getArrayComponentType(
                        arrayOfArraysOfInts.getType()));
        assertNull(TypeUtils.getArrayComponentType(rawList.getType()));
        assertNull(TypeUtils.getArrayComponentType(listOfInteger.getType()));
        assertNull(TypeUtils.getArrayComponentType(listOfE.getType()));
        assertNull(TypeUtils.getArrayComponentType(listOfS.getType()));
        assertNull(TypeUtils.getArrayComponentType(listOfSArrays.getType()));
        assertNull(TypeUtils.getArrayComponentType(listOfWildcards.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                listOfBoundedWildcards.getType()));
        assertNull(TypeUtils.getArrayComponentType(listOflistsOfE.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                listOfListsOfInteger.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                listOfListsOfWildcards.getType()));
        assertNull(TypeUtils.getArrayComponentType(listOfIntArrays.getType()));
        assertNull(TypeUtils.getArrayComponentType(rawMap.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringToInteger.getType()));
        assertNull(TypeUtils.getArrayComponentType(mapOfIntegerToE.getType()));
        assertNull(TypeUtils.getArrayComponentType(mapOfIntegerToS.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringToSArrays.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringToWildcards.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringToBoundedWildcards.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringTolistsOfE.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringToListsOfInteger.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringToListsOfWildcards.getType()));
        assertNull(TypeUtils.getArrayComponentType(
                mapOfStringToIntArray.getType()));
    }

    @Test
    public void getCollectionElementType() {
        assertNull(TypeUtils.getCollectionElementType(int.class));
        assertNull(TypeUtils.getCollectionElementType(e.getType()));
        assertNull(TypeUtils.getCollectionElementType(s.getType()));
        assertNull(TypeUtils.getCollectionElementType(intArray.getType()));
        assertNull(TypeUtils.getCollectionElementType(integerArray.getType()));
        assertNull(TypeUtils.getCollectionElementType(eArray.getType()));
        assertNull(TypeUtils.getCollectionElementType(sArray.getType()));
        assertNull(TypeUtils.getCollectionElementType(arrayOfListsOfE.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                        arrayOfListsOfInteger.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                        arrayOfListsOfWildcards.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                        arrayOfListsOfBoundedWildcards.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                        arrayOfArraysOfInts.getType()));
        assertTypeEquals(
                "class java.lang.Object",
                TypeUtils.getCollectionElementType(rawList.getType()));
        assertTypeEquals(
                "class java.lang.Integer",
                TypeUtils.getCollectionElementType(listOfInteger.getType()));
        assertTypeEquals(
                "E", TypeUtils.getCollectionElementType(listOfE.getType()));
        assertTypeEquals(
                "S", TypeUtils.getCollectionElementType(listOfS.getType()));
        assertTypeEquals(
                "S[]",
                TypeUtils.getCollectionElementType(listOfSArrays.getType()));
        assertTypeEquals(
                "?",
                TypeUtils.getCollectionElementType(listOfWildcards.getType()));
        assertTypeEquals(
                "? extends java.lang.Cloneable",
                TypeUtils.getCollectionElementType(
                        listOfBoundedWildcards.getType()));
        assertTypeEquals(
                "java.util.List<E>",
                TypeUtils.getCollectionElementType(listOflistsOfE.getType()));
        assertTypeEquals(
                "java.util.List<java.lang.Integer>",
                TypeUtils.getCollectionElementType(
                listOfListsOfInteger.getType()));
        assertTypeEquals(
                "java.util.List<?>",
                TypeUtils.getCollectionElementType(
                listOfListsOfWildcards.getType()));
        assertTypeEquals(
                "class [I",
                TypeUtils.getCollectionElementType(listOfIntArrays.getType()));
        assertNull(TypeUtils.getCollectionElementType(rawMap.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringToInteger.getType()));
        assertNull(TypeUtils.getCollectionElementType(mapOfIntegerToE.getType()));
        assertNull(TypeUtils.getCollectionElementType(mapOfIntegerToS.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringToSArrays.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringToWildcards.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringToBoundedWildcards.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringTolistsOfE.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringToListsOfInteger.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringToListsOfWildcards.getType()));
        assertNull(TypeUtils.getCollectionElementType(
                mapOfStringToIntArray.getType()));
    }

    @Test
    public void getMapKeyValueTypes() {
        assertNull(TypeUtils.getMapKeyValueTypes(int.class));
        assertNull(TypeUtils.getMapKeyValueTypes(e.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(s.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(intArray.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(integerArray.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(eArray.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(sArray.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(arrayOfListsOfE.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(
                arrayOfListsOfInteger.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(
                arrayOfListsOfWildcards.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(
                arrayOfListsOfBoundedWildcards.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(
                arrayOfArraysOfInts.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(rawList.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(listOfInteger.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(listOfE.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(listOfS.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(listOfSArrays.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(listOfWildcards.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(
                listOfBoundedWildcards.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(listOflistsOfE.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(
                listOfListsOfInteger.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(
                listOfListsOfWildcards.getType()));
        assertNull(TypeUtils.getMapKeyValueTypes(listOfIntArrays.getType()));
        assertTypesEqual(
                "class java.lang.Object",
                "class java.lang.Object",
                TypeUtils.getMapKeyValueTypes(rawMap.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "class java.lang.Integer",
                TypeUtils.getMapKeyValueTypes(mapOfStringToInteger.getType()));
        assertTypesEqual(
                "class java.lang.Integer",
                "E",
                TypeUtils.getMapKeyValueTypes(mapOfIntegerToE.getType()));
        assertTypesEqual(
                "class java.lang.Integer",
                "S",
                TypeUtils.getMapKeyValueTypes(mapOfIntegerToS.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "S[]",
                TypeUtils.getMapKeyValueTypes(mapOfStringToSArrays.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "?",
                TypeUtils.getMapKeyValueTypes(mapOfStringToWildcards.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "? extends java.lang.Cloneable",
                TypeUtils.getMapKeyValueTypes(
                        mapOfStringToBoundedWildcards.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "java.util.List<E>",
                TypeUtils.getMapKeyValueTypes(
                        mapOfStringTolistsOfE.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "java.util.List<java.lang.Integer>",
                TypeUtils.getMapKeyValueTypes(
                        mapOfStringToListsOfInteger.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "java.util.List<?>",
                TypeUtils.getMapKeyValueTypes(
                        mapOfStringToListsOfWildcards.getType()));
        assertTypesEqual(
                "class java.lang.String",
                "class [I",
                TypeUtils.getMapKeyValueTypes(mapOfStringToIntArray.getType()));
    }

    @Test
    public void isCollection() {
        assertFalse(TypeUtils.isCollection(int.class));
        assertFalse(TypeUtils.isCollection(e.getType()));
        assertFalse(TypeUtils.isCollection(s.getType()));
        assertFalse(TypeUtils.isCollection(intArray.getType()));
        assertFalse(TypeUtils.isCollection(integerArray.getType()));
        assertFalse(TypeUtils.isCollection(eArray.getType()));
        assertFalse(TypeUtils.isCollection(sArray.getType()));
        assertFalse(TypeUtils.isCollection(arrayOfListsOfE.getType()));
        assertFalse(TypeUtils.isCollection(arrayOfListsOfInteger.getType()));
        assertFalse(TypeUtils.isCollection(arrayOfListsOfWildcards.getType()));
        assertFalse(TypeUtils.isCollection(
                arrayOfListsOfBoundedWildcards.getType()));
        assertFalse(TypeUtils.isCollection(arrayOfArraysOfInts.getType()));
        assertTrue(TypeUtils.isCollection(rawList.getType()));
        assertTrue(TypeUtils.isCollection(listOfInteger.getType()));
        assertTrue(TypeUtils.isCollection(listOfE.getType()));
        assertTrue(TypeUtils.isCollection(listOfS.getType()));
        assertTrue(TypeUtils.isCollection(listOfSArrays.getType()));
        assertTrue(TypeUtils.isCollection(listOfWildcards.getType()));
        assertTrue(TypeUtils.isCollection(listOfBoundedWildcards.getType()));
        assertTrue(TypeUtils.isCollection(listOflistsOfE.getType()));
        assertTrue(TypeUtils.isCollection(listOfListsOfInteger.getType()));
        assertTrue(TypeUtils.isCollection(listOfListsOfWildcards.getType()));
        assertTrue(TypeUtils.isCollection(listOfIntArrays.getType()));
        assertFalse(TypeUtils.isCollection(rawMap.getType()));
        assertFalse(TypeUtils.isCollection(mapOfStringToInteger.getType()));
        assertFalse(TypeUtils.isCollection(mapOfIntegerToE.getType()));
        assertFalse(TypeUtils.isCollection(mapOfIntegerToS.getType()));
        assertFalse(TypeUtils.isCollection(mapOfStringToSArrays.getType()));
        assertFalse(TypeUtils.isCollection(mapOfStringToWildcards.getType()));
        assertFalse(TypeUtils.isCollection(
                mapOfStringToBoundedWildcards.getType()));
        assertFalse(TypeUtils.isCollection(mapOfStringTolistsOfE.getType()));
        assertFalse(TypeUtils.isCollection(
                mapOfStringToListsOfInteger.getType()));
        assertFalse(TypeUtils.isCollection(
                mapOfStringToListsOfWildcards.getType()));
        assertFalse(TypeUtils.isCollection(mapOfStringToIntArray.getType()));
    }

    @Test
    public void isMap() {
        assertFalse(TypeUtils.isMap(int.class));
        assertFalse(TypeUtils.isMap(e.getType()));
        assertFalse(TypeUtils.isMap(s.getType()));
        assertFalse(TypeUtils.isMap(intArray.getType()));
        assertFalse(TypeUtils.isMap(integerArray.getType()));
        assertFalse(TypeUtils.isMap(eArray.getType()));
        assertFalse(TypeUtils.isMap(sArray.getType()));
        assertFalse(TypeUtils.isMap(arrayOfListsOfE.getType()));
        assertFalse(TypeUtils.isMap(arrayOfListsOfInteger.getType()));
        assertFalse(TypeUtils.isMap(arrayOfListsOfWildcards.getType()));
        assertFalse(TypeUtils.isMap(
                arrayOfListsOfBoundedWildcards.getType()));
        assertFalse(TypeUtils.isMap(arrayOfArraysOfInts.getType()));
        assertFalse(TypeUtils.isMap(rawList.getType()));
        assertFalse(TypeUtils.isMap(listOfInteger.getType()));
        assertFalse(TypeUtils.isMap(listOfE.getType()));
        assertFalse(TypeUtils.isMap(listOfS.getType()));
        assertFalse(TypeUtils.isMap(listOfSArrays.getType()));
        assertFalse(TypeUtils.isMap(listOfWildcards.getType()));
        assertFalse(TypeUtils.isMap(listOfBoundedWildcards.getType()));
        assertFalse(TypeUtils.isMap(listOflistsOfE.getType()));
        assertFalse(TypeUtils.isMap(listOfListsOfInteger.getType()));
        assertFalse(TypeUtils.isMap(listOfListsOfWildcards.getType()));
        assertFalse(TypeUtils.isMap(listOfIntArrays.getType()));
        assertTrue(TypeUtils.isMap(rawMap.getType()));
        assertTrue(TypeUtils.isMap(mapOfStringToInteger.getType()));
        assertTrue(TypeUtils.isMap(mapOfIntegerToE.getType()));
        assertTrue(TypeUtils.isMap(mapOfIntegerToS.getType()));
        assertTrue(TypeUtils.isMap(mapOfStringToSArrays.getType()));
        assertTrue(TypeUtils.isMap(mapOfStringToWildcards.getType()));
        assertTrue(TypeUtils.isMap(
                mapOfStringToBoundedWildcards.getType()));
        assertTrue(TypeUtils.isMap(mapOfStringTolistsOfE.getType()));
        assertTrue(TypeUtils.isMap(
                mapOfStringToListsOfInteger.getType()));
        assertTrue(TypeUtils.isMap(
                mapOfStringToListsOfWildcards.getType()));
        assertTrue(TypeUtils.isMap(mapOfStringToIntArray.getType()));
    }

    @Test
    public void boxingType() {
        assertEquals(Boolean.class, TypeUtils.boxingType(boolean.class));
        assertEquals(Byte.class, TypeUtils.boxingType(byte.class));
        assertEquals(Character.class, TypeUtils.boxingType(char.class));
        assertEquals(Double.class, TypeUtils.boxingType(double.class));
        assertEquals(Float.class, TypeUtils.boxingType(float.class));
        assertEquals(Integer.class, TypeUtils.boxingType(int.class));
        assertEquals(Long.class, TypeUtils.boxingType(long.class));
        assertEquals(Short.class, TypeUtils.boxingType(short.class));
        assertSame(eArray.getType(), TypeUtils.boxingType(eArray.getType()));
        assertSame(Integer.class, TypeUtils.boxingType(Integer.class));
        assertSame(Integer[].class, TypeUtils.boxingType(Integer[].class));
    }

    @Test
    public void isNumeric() {
        assertFalse(TypeUtils.isNumeric(boolean.class));
        assertTrue(TypeUtils.isNumeric(byte.class));
        assertFalse(TypeUtils.isNumeric(char.class));
        assertTrue(TypeUtils.isNumeric(double.class));
        assertTrue(TypeUtils.isNumeric(float.class));
        assertTrue(TypeUtils.isNumeric(int.class));
        assertTrue(TypeUtils.isNumeric(long.class));
        assertTrue(TypeUtils.isNumeric(short.class));
        assertFalse(TypeUtils.isNumeric(Boolean.class));
        assertTrue(TypeUtils.isNumeric(Byte.class));
        assertFalse(TypeUtils.isNumeric(Character.class));
        assertTrue(TypeUtils.isNumeric(Double.class));
        assertTrue(TypeUtils.isNumeric(Float.class));
        assertTrue(TypeUtils.isNumeric(Integer.class));
        assertTrue(TypeUtils.isNumeric(Long.class));
        assertTrue(TypeUtils.isNumeric(Short.class));

        assertTrue(TypeUtils.isNumeric(BigInteger.class));
        assertTrue(TypeUtils.isNumeric(BigDecimal.class));

        assertFalse(TypeUtils.isNumeric(eArray.getType()));
    }

    @Test
    public void isPrimitiveFor() {
        assertTrue(TypeUtils.isPrimitiveFor(boolean.class, Boolean.class));
        assertTrue(TypeUtils.isPrimitiveFor(byte.class, Byte.class));
        assertTrue(TypeUtils.isPrimitiveFor(char.class, Character.class));
        assertTrue(TypeUtils.isPrimitiveFor(double.class, Double.class));
        assertTrue(TypeUtils.isPrimitiveFor(float.class, Float.class));
        assertTrue(TypeUtils.isPrimitiveFor(int.class, Integer.class));
        assertTrue(TypeUtils.isPrimitiveFor(long.class, Long.class));
        assertTrue(TypeUtils.isPrimitiveFor(short.class, Short.class));

        assertFalse(TypeUtils.isPrimitiveFor(Boolean.class, boolean.class));
        assertFalse(TypeUtils.isPrimitiveFor(Byte.class, byte.class));
        assertFalse(TypeUtils.isPrimitiveFor(Character.class, char.class));
        assertFalse(TypeUtils.isPrimitiveFor(Double.class, double.class));
        assertFalse(TypeUtils.isPrimitiveFor(Float.class, float.class));
        assertFalse(TypeUtils.isPrimitiveFor(Integer.class, int.class));
        assertFalse(TypeUtils.isPrimitiveFor(Long.class, long.class));
        assertFalse(TypeUtils.isPrimitiveFor(Short.class, short.class));

        assertFalse(TypeUtils.isPrimitiveFor(boolean.class, boolean.class));
        assertFalse(TypeUtils.isPrimitiveFor(byte.class, byte.class));
        assertFalse(TypeUtils.isPrimitiveFor(char.class, char.class));
        assertFalse(TypeUtils.isPrimitiveFor(double.class, double.class));
        assertFalse(TypeUtils.isPrimitiveFor(float.class, float.class));
        assertFalse(TypeUtils.isPrimitiveFor(int.class, int.class));
        assertFalse(TypeUtils.isPrimitiveFor(long.class, long.class));
        assertFalse(TypeUtils.isPrimitiveFor(short.class, short.class));

        assertFalse(TypeUtils.isPrimitiveFor(Boolean.class, Boolean.class));
        assertFalse(TypeUtils.isPrimitiveFor(Byte.class, Byte.class));
        assertFalse(TypeUtils.isPrimitiveFor(Character.class, Character.class));
        assertFalse(TypeUtils.isPrimitiveFor(Double.class, Double.class));
        assertFalse(TypeUtils.isPrimitiveFor(Float.class, Float.class));
        assertFalse(TypeUtils.isPrimitiveFor(Integer.class, Integer.class));
        assertFalse(TypeUtils.isPrimitiveFor(Long.class, Long.class));
        assertFalse(TypeUtils.isPrimitiveFor(Short.class, Short.class));

        assertFalse(TypeUtils.isPrimitiveFor(eArray.getType(), int.class));
        assertFalse(TypeUtils.isPrimitiveFor(int.class, eArray.getType()));
    }

    @Test
    public void isWrapperFor() {
        assertTrue(TypeUtils.isWrapperFor(Boolean.class, boolean.class));
        assertTrue(TypeUtils.isWrapperFor(Byte.class, byte.class));
        assertTrue(TypeUtils.isWrapperFor(Character.class, char.class));
        assertTrue(TypeUtils.isWrapperFor(Double.class, double.class));
        assertTrue(TypeUtils.isWrapperFor(Float.class, float.class));
        assertTrue(TypeUtils.isWrapperFor(Integer.class, int.class));
        assertTrue(TypeUtils.isWrapperFor(Long.class, long.class));
        assertTrue(TypeUtils.isWrapperFor(Short.class, short.class));

        assertFalse(TypeUtils.isWrapperFor(boolean.class, Boolean.class));
        assertFalse(TypeUtils.isWrapperFor(byte.class, Byte.class));
        assertFalse(TypeUtils.isWrapperFor(char.class, Character.class));
        assertFalse(TypeUtils.isWrapperFor(double.class, Double.class));
        assertFalse(TypeUtils.isWrapperFor(float.class, Float.class));
        assertFalse(TypeUtils.isWrapperFor(int.class, Integer.class));
        assertFalse(TypeUtils.isWrapperFor(long.class, Long.class));
        assertFalse(TypeUtils.isWrapperFor(short.class, Short.class));

        assertFalse(TypeUtils.isWrapperFor(boolean.class, boolean.class));
        assertFalse(TypeUtils.isWrapperFor(byte.class, byte.class));
        assertFalse(TypeUtils.isWrapperFor(char.class, char.class));
        assertFalse(TypeUtils.isWrapperFor(double.class, double.class));
        assertFalse(TypeUtils.isWrapperFor(float.class, float.class));
        assertFalse(TypeUtils.isWrapperFor(int.class, int.class));
        assertFalse(TypeUtils.isWrapperFor(long.class, long.class));
        assertFalse(TypeUtils.isWrapperFor(short.class, short.class));

        assertFalse(TypeUtils.isWrapperFor(Boolean.class, Boolean.class));
        assertFalse(TypeUtils.isWrapperFor(Byte.class, Byte.class));
        assertFalse(TypeUtils.isWrapperFor(Character.class, Character.class));
        assertFalse(TypeUtils.isWrapperFor(Double.class, Double.class));
        assertFalse(TypeUtils.isWrapperFor(Float.class, Float.class));
        assertFalse(TypeUtils.isWrapperFor(Integer.class, Integer.class));
        assertFalse(TypeUtils.isWrapperFor(Long.class, Long.class));
        assertFalse(TypeUtils.isWrapperFor(Short.class, Short.class));

        assertFalse(TypeUtils.isWrapperFor(eArray.getType(), int.class));
        assertFalse(TypeUtils.isWrapperFor(int.class, eArray.getType()));
    }

    @Test
    public void compareNumericSize() {
        assertTrue(TypeUtils.compareNumericSize(byte.class, byte.class) == 0);
        assertTrue(TypeUtils.compareNumericSize(byte.class, short.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(byte.class, int.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(byte.class, long.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(byte.class, float.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(byte.class, double.class) < 0);

        assertTrue(TypeUtils.compareNumericSize(short.class, byte.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(short.class, short.class) == 0);
        assertTrue(TypeUtils.compareNumericSize(short.class, int.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(short.class, long.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(short.class, float.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(short.class, double.class) < 0);

        assertTrue(TypeUtils.compareNumericSize(int.class, byte.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(int.class, short.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(int.class, int.class) == 0);
        assertTrue(TypeUtils.compareNumericSize(int.class, long.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(int.class, float.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(int.class, double.class) < 0);

        assertTrue(TypeUtils.compareNumericSize(long.class, byte.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(long.class, short.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(long.class, int.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(long.class, long.class) == 0);
        assertTrue(TypeUtils.compareNumericSize(long.class, float.class) < 0);
        assertTrue(TypeUtils.compareNumericSize(long.class, double.class) < 0);

        assertTrue(TypeUtils.compareNumericSize(float.class, byte.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(float.class, short.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(float.class, int.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(float.class, long.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(float.class, float.class) == 0);
        assertTrue(TypeUtils.compareNumericSize(float.class, double.class) < 0);

        assertTrue(TypeUtils.compareNumericSize(double.class, byte.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(double.class, short.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(double.class, int.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(double.class, long.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(double.class, float.class) > 0);
        assertTrue(TypeUtils.compareNumericSize(double.class, double.class) == 0);
    }

    @Test
    public void compareNumericSizeNonNumeric1() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
                "class java.lang.String is not numeric");
        TypeUtils.compareNumericSize(String.class, int.class);
    }

    @Test
    public void compareNumericSizeNonNumeric2() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(
                "interface java.util.List is not numeric");
        TypeUtils.compareNumericSize(int.class, List.class);
    }

    @Test
    public void isAssignable() {
    }

    private static void assertTypeEquals(String aInString, Type aInType) {
        assertNotNull(aInType);
        assertEquals(aInString, aInType.toString());
    }

    private static void assertTypesEqual(
            String aInString1, String aInString2, Type[] aInTypes) {
        assertNotNull(aInTypes);
        assertEquals(2, aInTypes.length);
        assertEquals(aInString1, aInTypes[0].toString());
        assertEquals(aInString2, aInTypes[1].toString());
    }
}