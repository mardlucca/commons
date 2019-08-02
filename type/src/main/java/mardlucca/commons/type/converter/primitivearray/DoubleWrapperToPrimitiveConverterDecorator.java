/*
 * File: DoubleWrapperToPrimitiveConverterDecorator.java
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

package mardlucca.commons.type.converter.primitivearray;

import mardlucca.commons.lang.ArrayUtils;
import mardlucca.commons.type.Converter;

/**
 * Created by mlucca on 1/24/17.
 */
public class DoubleWrapperToPrimitiveConverterDecorator<T>
        implements Converter<T, double[]> {
    private Converter<T, Double[]> delegateConverter;

    public DoubleWrapperToPrimitiveConverterDecorator(
            Converter<T, Double[]> aInDelegateConverter) {
        delegateConverter = aInDelegateConverter;
    }

    @Override
    public double[] convert(T aInFrom) {
        return ArrayUtils.toPrimitiveArray(delegateConverter.convert(aInFrom));
    }
}
