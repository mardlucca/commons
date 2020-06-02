/*
 * File: ChainedConverterFactory.java
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

package mardlucca.commons.type;

import mardlucca.commons.lang.TypeReference;

import java.lang.reflect.Type;

/**
 * Created by mlucca on 1/20/17.
 */
public interface ConverterFactory {
    <F, T> Converter<F, T> getConverter(Type aInFrom, Type aInTo);

    default <F, T> Converter<F, T> getConverter(
            TypeReference<F> aInFrom, TypeReference<T> aInTo) {
        return getConverter(aInFrom.getType(), aInTo.getType());
    }
}
