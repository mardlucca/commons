/*
 * File: TypeReferenceTest.java
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

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.junit.Assert.*;

public class TypeReferenceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getType() {
        TypeReference<List<Integer>> integerList =
                new TypeReference<List<Integer>>() {};

        assertTrue(integerList.getType() instanceof ParameterizedType);
        ParameterizedType type = (ParameterizedType) integerList.getType();
        assertEquals(List.class, type.getRawType());
        assertEquals(1, type.getActualTypeArguments().length);
        assertEquals(Integer.class, type.getActualTypeArguments()[0]);
    }

    @Test
    public void getTypeNamedClass() {
        T1 lT1 = new T1();

        assertTrue(lT1.getType() instanceof ParameterizedType);
        ParameterizedType type = (ParameterizedType) lT1.getType();
        assertEquals(List.class, type.getRawType());
        assertEquals(1, type.getActualTypeArguments().length);
        assertEquals(String.class, type.getActualTypeArguments()[0]);
    }

    @Test
    public void testExtendingAnotherTypeReference() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(
                "A TypeReference cannot extend a concrete TypeReference");
        new T2();
    }

    static class T1 extends TypeReference<List<String>> {}

    static class T2 extends T1 {}
}