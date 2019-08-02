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
import java.lang.reflect.Type;

/**
 * Created by mlucca on 1/21/17.
 */
public class ContainerConverterFactory implements ChainingConverterFactory {

    @Override
    public <F, T> Converter<F, T> getConverter(
            Type aInFrom, Type aInTo, FactoryChain aInChain) {
        Type lFromElementType;
        Type lToElementType;
        Converter<F, T> lElementConverter;

        // are we converting from an array to something?
        if (TypeUtils.isArrayType(aInFrom)) {
            lFromElementType = TypeUtils.getArrayComponentType(aInFrom);
            if (TypeUtils.isArrayType(aInTo)) {
                // converting from array to array

                lToElementType = TypeUtils.getArrayComponentType(aInTo);
                lElementConverter = aInChain.invokeFirst(
                        lFromElementType, lToElementType);
                if (lElementConverter == null) {
                    // couldn't find converter for individual elements.
                    return aInChain.invokeNext(aInFrom, aInTo);
                }

                return new ArrayToArrayConverter(
                        lElementConverter,
                        TypeUtils.getArrayClass(aInTo));
            } else if (TypeUtils.isCollection(aInTo)) {
                lToElementType = TypeUtils.getCollectionElementType(aInTo);
                lElementConverter = aInChain.invokeFirst(
                        lFromElementType, lToElementType);
                if (lElementConverter == null) {
                    // can't convert element types. we keep looking down the
                    // chain for other potential converters
                    return aInChain.invokeNext(aInFrom, aInTo);
                }
                return new ArrayToCollectionConverter(lElementConverter,
                        new ConstructorBackedCollectionFactory(
                                TypeUtils.getCollectionClass(aInTo)));
            }
            // else we're converting to neither an array nor a collection, which
            // means this factory cannot produce a converter for this conversion

        } else if (TypeUtils.isCollection(aInFrom)) {
            // we are converting from a collection to something

            lFromElementType = TypeUtils.getCollectionElementType(aInFrom);
            if (TypeUtils.isArrayType(aInTo)) {
                lToElementType = TypeUtils.getArrayComponentType(aInTo);
                lElementConverter = aInChain.invokeFirst(
                        lFromElementType, lToElementType);
                if (lElementConverter == null) {
                    return aInChain.invokeNext(aInFrom, aInTo);
                }
                return new CollectionToArrayConverter(lElementConverter,
                        TypeUtils.getArrayClass(aInTo));
            } else if (TypeUtils.isCollection(aInTo)) {
                lToElementType = TypeUtils.getCollectionElementType(aInTo);
                lElementConverter = aInChain.invokeFirst(
                        lFromElementType, lToElementType);
                if (lElementConverter == null) {
                    return aInChain.invokeNext(aInFrom, aInTo);
                }
                return new CollectionToCollectionConverter(lElementConverter,
                        new ConstructorBackedCollectionFactory(
                                TypeUtils.getCollectionClass(aInTo)));
            }
            // else we're converting to neither an array nor a collection, which
            // means this factory cannot produce a converter for this conversion
        }
        return aInChain.invokeNext(aInFrom, aInTo);
    }
}
