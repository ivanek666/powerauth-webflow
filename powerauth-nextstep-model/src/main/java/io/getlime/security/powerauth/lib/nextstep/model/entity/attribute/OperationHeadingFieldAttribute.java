/*
 * Copyright 2018 Lime - HighTech Solutions s.r.o.
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
package io.getlime.security.powerauth.lib.nextstep.model.entity.attribute;

import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.ValueFormatType;

/**
 * Class representing an operation form field attribute for a heading.
 *
 * @author Roman Strobl, roman.strobl@lime-company.eu
 */
public class OperationHeadingFieldAttribute extends OperationKeyValueFieldAttribute {

    /**
     * Default constructor.
     */
    public OperationHeadingFieldAttribute() {
        this.type = Type.HEADING;
        this.valueFormatType = ValueFormatType.TEXT;
    }

    /**
     * Constructor with value format type.
     * @param valueFormatType Value format type.
     */
    public OperationHeadingFieldAttribute(ValueFormatType valueFormatType) {
        this.type = Type.HEADING;
        this.valueFormatType = valueFormatType;
    }

    /**
     * Constructor with all details.
     * @param id Attribute ID.
     * @param label Label.
     * @param value Value.
     * @param valueFormatType Value format type.
     * @param formattedValue Formatted value.
     */
    public OperationHeadingFieldAttribute(String id, String label, String value, ValueFormatType valueFormatType, String formattedValue) {
        this.type = Type.HEADING;
        this.id = id;
        this.label = label;
        this.value = value;
        this.valueFormatType = valueFormatType;
        this.formattedValue = formattedValue;
    }

}