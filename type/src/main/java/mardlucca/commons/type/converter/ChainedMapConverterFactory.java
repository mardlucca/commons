/*
 * File: ChainedMapConverterFactory.java
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

import mardlucca.commons.lang.TypeUtils;
import mardlucca.commons.type.Converter;
import mardlucca.commons.type.converter.ChainingConverterFactory.ChainedConverterFactory;
import mardlucca.commons.type.converter.ChainingConverterFactory.FactoryChain;

import java.lang.reflect.Type;

/**
 * Created by mlucca on 1/24/17.
 */
public class ChainedMapConverterFactory
        implements ChainedConverterFactory {
    @Override
    public <F, T> Converter<F, T> getConverter(
            Type aInFrom, Type aInTo, FactoryChain aInChain) {

        if (TypeUtils.isMap(aInFrom) && TypeUtils.isMap(aInTo)) {
            Type[] aInFromTypes = TypeUtils.getMapKeyValueTypes(aInFrom);
            Type[] aInToTypes = TypeUtils.getMapKeyValueTypes(aInTo);

            Converter lKeyConverter = aInChain.invokeFirst(
                    aInFromTypes[0], aInToTypes[0]);
            Converter lValueConverter = aInChain.invokeFirst(
                    aInFromTypes[1], aInToTypes[1]);

            if (lKeyConverter != null && lValueConverter != null) {
                return new MapConverter<>(lKeyConverter, lValueConverter);
            }
        }

        return aInChain.invokeNext(aInFrom, aInTo);
    }
}
