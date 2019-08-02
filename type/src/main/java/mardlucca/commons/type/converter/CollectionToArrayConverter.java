/*
 * File: CollectionToArrayConverter.java
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
import java.util.Collection;

/**
 * Created by mlucca on 1/20/17.
 */
public class CollectionToArrayConverter<F, T>
        implements Converter<Collection<F>, T[]> {
    private Converter<F, T> elementConverter;

    private Class<T> arrayComponentType;

    CollectionToArrayConverter(
            Converter<F, T> aInElementConverter,
            Class<T> aInArrayComponentType) {
        elementConverter = aInElementConverter;
        arrayComponentType = aInArrayComponentType;
    }

    @Override
    public T[] convert(Collection<F> aInFrom) {
        if (aInFrom == null) {
            return null;
        }

        //noinspection unchecked
        T[] lReturn = (T[]) Array.newInstance(
                arrayComponentType, aInFrom.size());
        int i = 0;
        for (F lItem : aInFrom) {
            lReturn[i++] = elementConverter.convert(lItem);
        }

        return lReturn;
    }
}
