/*
 * File: PrimitiveArrayConverterDecoratorFactory.java
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

import mardlucca.commons.type.Converter;
import mardlucca.commons.type.ConverterFactory;
import mardlucca.commons.type.converter.primitivearray.BooleanPrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.BooleanWrapperToPrimitiveConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.BytePrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.ByteWrapperToPrimitiveConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.CharacterPrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.CharacterWrapperToPrimitiveConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.DoublePrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.DoubleWrapperToPrimitiveConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.FloatPrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.FloatWrapperToPrimitiveConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.IntPrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.IntWrapperToPrimitiveConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.LongPrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.LongWrapperToPrimitiveConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.ShortPrimitiveToWrapperConverterDecorator;
import mardlucca.commons.type.converter.primitivearray.ShortWrapperToPrimitiveConverterDecorator;

import java.lang.reflect.Type;

/**
 * Created by mlucca on 1/24/17.
 */
public class PrimitiveArrayConverterDecoratorFactory
        implements ChainingConverterFactory {

    @Override
    public <F, T> Converter<F, T> getConverter(
            Type aInFrom, Type aInTo, FactoryChain aInChain) {
        Converter<?, ?> lConverter = aInChain.invokeNext(aInFrom, aInTo);

        if (aInFrom instanceof Class) {
            Class<?> aInFromClass = (Class<?>) aInFrom;

            if (aInFromClass.isArray()
                    && aInFromClass.getComponentType().isPrimitive()) {
                lConverter = wrapFrom(aInFromClass, lConverter);
            }
        }
        if (aInTo instanceof Class) {
            Class<?> aInToClass = (Class<?>) aInTo;

            if (aInToClass.isArray()
                    && aInToClass.getComponentType().isPrimitive()) {
                lConverter = wrapTo(aInToClass, lConverter);
            }
        }

        return (Converter<F, T>) lConverter;
    }

    private static Converter<?, ?> wrapFrom(Class<?> aInFromClass,
                                            Converter<?, ?> aInConverter) {
        if (aInFromClass == char[].class) {
            return new CharacterPrimitiveToWrapperConverterDecorator<>(
                    (Converter<Character[], ?>) aInConverter);
        }
        if (aInFromClass == boolean[].class) {
            return new BooleanPrimitiveToWrapperConverterDecorator<>(
                    (Converter<Boolean[], ?>) aInConverter);
        }
        if (aInFromClass == byte[].class) {
            return new BytePrimitiveToWrapperConverterDecorator<>(
                    (Converter<Byte[], ?>) aInConverter);
        }
        if (aInFromClass == short[].class) {
            return new ShortPrimitiveToWrapperConverterDecorator<>(
                    (Converter<Short[], ?>) aInConverter);
        }
        if (aInFromClass == int[].class) {
            return new IntPrimitiveToWrapperConverterDecorator<>(
                    (Converter<Integer[], ?>) aInConverter);
        }
        if (aInFromClass == long[].class) {
            return new LongPrimitiveToWrapperConverterDecorator<>(
                    (Converter<Long[], ?>) aInConverter);
        }
        if (aInFromClass == float[].class) {
            return new FloatPrimitiveToWrapperConverterDecorator<>(
                    (Converter<Float[], ?>) aInConverter);
        }
        if (aInFromClass == double[].class) {
            return new DoublePrimitiveToWrapperConverterDecorator<>(
                    (Converter<Double[], ?>) aInConverter);
        }

        return aInConverter;
    }

    private static Converter<?, ?> wrapTo(Class<?> aInToClass,
                                          Converter<?, ?> aInConverter) {
        if (aInToClass == char[].class) {
            return new CharacterWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Character[]>) aInConverter);
        }
        if (aInToClass == boolean[].class) {
            return new BooleanWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Boolean[]>) aInConverter);
        }
        if (aInToClass == byte[].class) {
            return new ByteWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Byte[]>) aInConverter);
        }
        if (aInToClass == short[].class) {
            return new ShortWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Short[]>) aInConverter);
        }
        if (aInToClass == int[].class) {
            return new IntWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Integer[]>) aInConverter);
        }
        if (aInToClass == long[].class) {
            return new LongWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Long[]>) aInConverter);
        }
        if (aInToClass == float[].class) {
            return new FloatWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Float[]>) aInConverter);
        }
        if (aInToClass == double[].class) {
            return new DoubleWrapperToPrimitiveConverterDecorator<>(
                    (Converter<?, Double[]>) aInConverter);
        }

        return aInConverter;
    }

}
