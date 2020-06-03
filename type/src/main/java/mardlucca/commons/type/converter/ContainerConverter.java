/*
 * File: ContainerConverter.java
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
import java.util.Iterator;

public class ContainerConverter
        implements Converter<Object, Object> {
    private ContainerHandler fromHandler;
    private ContainerHandler toHandler;
    private Converter<Object, Object> elementConverter;

    public ContainerConverter(
            ContainerHandler aInFromHandler,
            ContainerHandler aInToHandler,
            Converter<Object, Object> aInElementConverter) {
        fromHandler = aInFromHandler;
        toHandler = aInToHandler;
        elementConverter = aInElementConverter;
    }

    @Override
    public Object convert(Object aInFrom) {
        Object lContainer = toHandler.newInstance(fromHandler.getSize(aInFrom));
        Appender lAppender = toHandler.getAppender(lContainer);
        for (Object lObject : fromHandler.asIterable(aInFrom)) {
            lAppender.add(elementConverter.convert(lObject));
        }
        return lContainer;
    }

    interface ContainerHandler {
        Object newInstance(int aInSize);
        int getSize(Object aInContainer);
        Iterable<Object> asIterable(Object aInContainer);
        Appender getAppender(Object aInContainer);
    }

    interface Appender {
        boolean add(Object aInValue);
    }

    static class ArrayContainerHandler implements ContainerHandler {
        private Class<?> componentType;

        public ArrayContainerHandler(Class<?> aInComponentType) {
            componentType = aInComponentType;
        }

        @Override
        public Object newInstance(int aInSize) {
            return Array.newInstance(componentType, aInSize);
        }

        @Override
        public int getSize(Object aInContainer) {
            return Array.getLength(aInContainer);
        }

        @Override
        public Iterable<Object> asIterable(Object aInContainer) {
            return () -> new Iterator<Object>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < Array.getLength(aInContainer);
                }

                @Override
                public Object next() {
                    return Array.get(aInContainer, index++);
                }
            };
        }

        @Override
        public Appender getAppender(Object aInContainer) {
            return new Appender() {
                private int index = 0;

                @Override
                public boolean add(Object aInValue) {
                    Array.set(aInContainer, index++, aInValue);
                    return true;
                }
            };
        }
    }

    @FunctionalInterface
    interface ContainerFactory {
        Object newInstance(int aInSize);
    }

    static class CollectionContainerHandler implements ContainerHandler {
        private ContainerFactory containerFactory;

        public CollectionContainerHandler(
                ContainerFactory aInContainerFactory) {
            containerFactory = aInContainerFactory;
        }

        @Override
        public Object newInstance(int aInSize) {
            return containerFactory.newInstance(aInSize);
        }

        @Override
        public int getSize(Object aInContainer) {
            return toCollection(aInContainer).size();
        }

        @Override
        public Iterable<Object> asIterable(Object aInContainer) {
            return toCollection(aInContainer);
        }

        @Override
        public Appender getAppender(Object aInContainer) {
            return toCollection(aInContainer)::add;
        }

        private Collection<Object> toCollection(Object aInObject) {
            //noinspection unchecked
            return (Collection<Object>) aInObject;
        }
    }
}
