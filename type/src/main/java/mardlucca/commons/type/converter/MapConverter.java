/*
 * File: MapConverter.java
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mlucca on 1/20/17.
 */
public class MapConverter<FK, TK, FV, TV>
        implements Converter<Map<FK, FV>, Map<TK, TV>> {
    private Converter<FK, TK> keyConverter;
    private Converter<FV, TV> valueConverter;

    public MapConverter(
            Converter<FK, TK> aInKeyConverter,
            Converter<FV, TV> aInValueConverter) {
        keyConverter = aInKeyConverter;
        valueConverter = aInValueConverter;
    }

    @Override
    public Map<TK, TV> convert(Map<FK, FV> aInFrom) {
        if (aInFrom == null) {
            return null;
        }

        Map<TK, TV> lConvertedMap = new HashMap<>(aInFrom.size());

        for (Map.Entry<FK, FV> lEntry : aInFrom.entrySet()) {
            lConvertedMap.put(keyConverter.convert(lEntry.getKey()),
                    valueConverter.convert(lEntry.getValue()));
        }

        return lConvertedMap;
    }
}
