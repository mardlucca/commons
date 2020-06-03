/*
 * File: ChainingConverterFactory.java
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

import java.lang.reflect.Type;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Created by mlucca on 1/17/17.
 */
public abstract class ChainingConverterFactory
        implements ConverterFactory {

    public abstract List<ChainedConverterFactory> getFactories();

    @Override
    public <F, T> Converter<F, T> getConverter(Type aInFrom, Type aInTo) {
        FactoryChain lChain = new ChainImpl();
        return lChain.invokeFirst(aInFrom, aInTo);
    }

    public static ConverterFactory singletonFactory(
            ChainedConverterFactory aInChainedConverterFactory) {
        return new ChainingConverterFactory() {
            @Override
            public List<ChainedConverterFactory> getFactories() {
                return singletonList(aInChainedConverterFactory);
            }
        };
    }

    public static ConverterFactory fromFactories(
            ChainedConverterFactory... aInChainedConverterFactory) {
        return new ChainingConverterFactory() {
            private List<ChainedConverterFactory> factories =
                    asList(aInChainedConverterFactory);
            @Override
            public List<ChainedConverterFactory> getFactories() {
                return factories;
            }
        };
    }

    public interface FactoryChain {
        <F, T> Converter<F, T> invokeNext(Type aInFrom, Type aInTo);
        <F, T> Converter<F, T> invokeFirst(Type aInFrom, Type aInTo);
    }

    public interface ChainedConverterFactory {
        <F, T> Converter<F, T> getConverter(
                Type aInFrom, Type aInTo, FactoryChain aInChain);
    }

    private class ChainImpl implements FactoryChain {
        private int currentFactory = 0;

        @Override
        public <F, T> Converter<F, T> invokeNext(Type aInFrom, Type aInTo) {
            currentFactory++;
            List<ChainedConverterFactory> lFactories = getFactories();
            if (currentFactory >= lFactories.size()) {
                return null;
            }
            return lFactories.get(currentFactory)
                    .getConverter(aInFrom, aInTo, this);
        }

        @Override
        public <F, T> Converter<F, T> invokeFirst(Type aInFrom, Type aInTo) {
            return getFactories().get(0).getConverter(aInFrom, aInTo, this);
        }
    }
}
