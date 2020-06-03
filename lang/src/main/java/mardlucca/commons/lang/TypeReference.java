/*
 * File: TypeReference.java
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

package mardlucca.commons.lang;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T> {
    private Type type;

    protected TypeReference() {
        Type lSuperType = this.getClass().getGenericSuperclass();
        if (!(lSuperType instanceof ParameterizedType)) {
            throw new RuntimeException(
                    "A TypeReference cannot extend a concrete TypeReference");
        }
        ParameterizedType lParameterizedSuperType =
                (ParameterizedType) lSuperType;
        type = lParameterizedSuperType.getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
