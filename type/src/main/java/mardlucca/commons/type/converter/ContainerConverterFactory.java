/*
 * File: ContainerConverterFactory.java
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
import mardlucca.commons.type.converter.ContainerConverter.ArrayContainerHandler;
import mardlucca.commons.type.converter.ContainerConverter.CollectionContainerHandler;
import mardlucca.commons.type.converter.ContainerConverter.ContainerFactory;
import mardlucca.commons.type.converter.ContainerConverter.ContainerHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mlucca on 1/21/17.
 */
public class ContainerConverterFactory
        implements ChainingConverterFactory.ConverterFactory {

    @Override
    public <F, T> Converter<F, T> getConverter(
            Type aInFrom, Type aInTo, FactoryChain aInChain) {
        ContainerHandler lFromHandler = null;
        ContainerHandler lToHander = null;
        Type lFromElementType = null;
        Type lToElementType = null;

        if (TypeUtils.isArrayType(aInFrom)) {
            lFromElementType = TypeUtils.getArrayComponentType(aInFrom);
            lFromHandler = new ArrayContainerHandler(
                    (Class<?>) lFromElementType);
        } else if (TypeUtils.isCollection(aInFrom)) {
            lFromElementType = TypeUtils.getCollectionElementType(aInFrom);
            // don't need a container factory in the "from" side as we are not
            // going to create a container using this handler
            lFromHandler = new CollectionContainerHandler(null);
        }

        if (lFromHandler != null) {
            if (TypeUtils.isArrayType(aInTo)) {
                lToElementType = TypeUtils.getArrayComponentType(aInTo);
                lToHander = new ArrayContainerHandler(
                        (Class<?>) lToElementType);
            } else if (TypeUtils.isCollection(aInFrom)) {
                lToElementType = TypeUtils.getCollectionElementType(aInTo);
                lToHander = new CollectionContainerHandler(getFactory(aInTo));
            }
        }

        if (lFromHandler != null && lToHander != null) {
            Converter<Object, Object> lElementConverter =
                    aInChain.invokeFirst(lFromElementType, lToElementType);
            if (lElementConverter != null) {
                return (Converter<F, T>) new ContainerConverter(
                        lFromHandler, lToHander, lElementConverter);
            }
        }

        return aInChain.invokeNext(aInFrom, aInTo);
    }

    private ContainerFactory getFactory(Type aInType) {
        ParameterizedType lType = (ParameterizedType) aInType;
        Class<?> lClass = (Class<?>) lType.getRawType();

        // TODO: In the future will need to allow for the customization of what
        // data structure implementation to use.
        if (Set.class.isAssignableFrom(lClass)) {
            return aInSize -> new HashSet<>();
        }
        if (Collection.class.isAssignableFrom(lClass)) {
            return ArrayList::new;
        }

        // This should never happen
        return null;
    }
}
