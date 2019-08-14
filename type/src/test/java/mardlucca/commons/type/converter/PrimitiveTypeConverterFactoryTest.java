/*
 * File: PrimitiveTypeConverterFactoryTest.java
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

package mardlucca.commons.type.converter;

import mardlucca.commons.type.ConverterFactory;
import org.junit.Test;

import static mardlucca.commons.type.converter.ChainingConverterFactory.singletonFactory;
import static org.junit.Assert.*;

public class PrimitiveTypeConverterFactoryTest {
    private ConverterFactory factory =
            singletonFactory(new PrimitiveTypeConverterFactory());

    @Test
    public void testConvertIntToInteger() {
        assertEquals(new Integer(10),
                factory.<Integer, Integer>getConverter(int.class, Integer.class)
                        .convert(10));
    }

    @Test
    public void testConvertCharToDouble() {
        char c = 'a';
        assertEquals(
                (double) c,
                factory.<Character, Double>getConverter(
                        char.class, double.class)
                            .convert(c),
                0.0);
    }
}