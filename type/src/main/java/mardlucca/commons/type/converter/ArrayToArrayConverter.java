/*
 * File: ArrayToArrayConverter.java
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

import java.lang.reflect.Array;

/**
 * Created by mlucca on 1/20/17.
 */
public class ArrayToArrayConverter<F, T>
        implements Converter<F[], T[]> {
    private Converter<F, T> elementConverter;

    private Class<T> arrayComponentType;

    ArrayToArrayConverter(
            Converter<F, T> aInElementConverter,
            Class<T> aInArrayComponentType) {
        elementConverter = aInElementConverter;
        arrayComponentType = aInArrayComponentType;
        if (arrayComponentType.isPrimitive()) {
            throw new IllegalArgumentException(
                    "This class does not support arrays of primitive types");
        }
    }

    @Override
    public T[] convert(F[] aInFrom) {
        if (aInFrom == null) {
            return null;
        }

        //noinspection unchecked
        T[] lReturn = (T[]) Array.newInstance(
                arrayComponentType, aInFrom.length);
        for (int i = 0; i < aInFrom.length; i++) {
            lReturn[i++] = elementConverter.convert(aInFrom[i]);
        }

        return lReturn;
    }
}
