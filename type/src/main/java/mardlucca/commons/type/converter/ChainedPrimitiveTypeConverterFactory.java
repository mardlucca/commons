/*
 * File: ChainedPrimitiveTypeConverterFactory.java
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
import mardlucca.commons.type.converter.ChainingConverterFactory.ChainedConverterFactory;
import mardlucca.commons.type.converter.ChainingConverterFactory.FactoryChain;

import java.lang.reflect.Type;

import static mardlucca.commons.type.Converter.nullConverter;

/**
 * Cannot convert form char to anything other than char. Cannot convert from
 * boolean to anything other than boolean.
 *
 * Created by mlucca on 1/20/17.
 */
public class ChainedPrimitiveTypeConverterFactory
        implements ChainedConverterFactory {
    @Override
    public <F, T> Converter<F, T> getConverter(
            Type aInFrom, Type aInTo, FactoryChain aInChain) {
        Converter<F, T> lNumericConverter =
                getNumericConverter(aInFrom, aInTo);
        return lNumericConverter == null
                ? (Converter<F, T>) aInChain.invokeNext(aInFrom, aInTo)
                : lNumericConverter;
    }

    private static <F, T> Converter<F, T> getNumericConverter(
            Type aInFrom, Type aInTo) {
        if (byte.class == aInFrom || Byte.class == aInFrom) {
            return (Converter<F, T>) getByteConverter(aInTo);
        }
        if (char.class == aInFrom || Character.class == aInFrom) {
            return (Converter<F, T>) getCharacterConverter(aInTo);
        }
        if (float.class == aInFrom || Float.class == aInFrom) {
            return (Converter<F, T>) getFloatConverter(aInTo);
        }
        if (int.class == aInFrom || Integer.class == aInFrom) {
            return (Converter<F, T>) getIntegerConverter(aInTo);
        }
        if (long.class == aInFrom || Long.class == aInFrom) {
            return (Converter<F, T>) getLongConverter(aInTo);
        }
        if (short.class == aInFrom || Short.class == aInFrom) {
            return (Converter<F, T>) getShortConverter(aInTo);
        }
        return null;
    }

    private static Converter<Byte, ?> getByteConverter(Type aInTo) {
        if (double.class == aInTo || Double.class == aInTo) {
            return nullConverter(Byte::doubleValue);
        }
        if (float.class == aInTo || Float.class == aInTo) {
            return nullConverter(Byte::floatValue);
        }
        if (int.class == aInTo || Integer.class == aInTo) {
            return nullConverter(Byte::intValue);
        }
        if (long.class == aInTo || Long.class == aInTo) {
            return nullConverter(Byte::longValue);
        }
        if (short.class == aInTo || Short.class == aInTo) {
            return nullConverter(Byte::shortValue);
        }
        return null;
    }

    private static Converter<Character, ?> getCharacterConverter(Type aInTo) {
        if (double.class == aInTo || Double.class == aInTo) {
            return nullConverter(aInFrom -> (double) aInFrom);
        }
        if (float.class == aInTo || Float.class == aInTo) {
            return nullConverter(aInFrom -> (float) aInFrom);
        }
        if (int.class == aInTo || Integer.class == aInTo) {
            return nullConverter(aInFrom -> (int) aInFrom);
        }
        return null;
    }

    private static Converter<Float, ?> getFloatConverter(Type aInTo) {
        if (double.class == aInTo || Double.class == aInTo) {
            return nullConverter(Float::doubleValue);
        }
        return null;
    }

    private static Converter<Integer, ?> getIntegerConverter(Type aInTo) {
        if (double.class == aInTo || Double.class == aInTo) {
            return nullConverter(Integer::doubleValue);
        }
        if (float.class == aInTo || Float.class == aInTo) {
            return nullConverter(Integer::floatValue);
        }
        if (long.class == aInTo || Long.class == aInTo) {
            return nullConverter(Integer::longValue);
        }
        return null;
    }

    private static Converter<Long, ?> getLongConverter(Type aInTo) {
        if (double.class == aInTo || Double.class == aInTo) {
            return nullConverter(Long::doubleValue);
        }
        if (float.class == aInTo || Float.class == aInTo) {
            return nullConverter(Long::floatValue);
        }
        return null;
    }

    private static Converter<Short, ?> getShortConverter(Type aInTo) {
        if (double.class == aInTo || Double.class == aInTo) {
            return nullConverter(Short::doubleValue);
        }
        if (float.class == aInTo || Float.class == aInTo) {
            return nullConverter(Short::floatValue);
        }
        if (int.class == aInTo || Integer.class == aInTo) {
            return nullConverter(Short::intValue);
        }
        if (long.class == aInTo || Long.class == aInTo) {
            return nullConverter(Short::longValue);
        }
        return null;
    }
}
