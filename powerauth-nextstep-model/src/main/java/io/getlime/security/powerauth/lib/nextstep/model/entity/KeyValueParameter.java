/*
 * Copyright 2017 Lime - HighTech Solutions s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.getlime.security.powerauth.lib.nextstep.model.entity;

/**
 * Class representing a generic key-value attribute.
 *
 * @author Petr Dvorak, petr@lime-company.eu
 */
public class KeyValueParameter {

    private String key;
    private String value;

    public KeyValueParameter() {
    }

    public KeyValueParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get a key.
     * @return Key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set a key.
     * @param key Key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get a value.
     * @return Value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set a value.
     * @param value Value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}