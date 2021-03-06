/*
 * Copyright 2017 Wultra s.r.o.
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
package io.getlime.security.powerauth.lib.mtoken.model.entity.attributes;

/**
 * Attribute representing a key-value item, where key and value are displayed
 * below each other, with value that can extend over multiple lines.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
public class NoteAttribute extends Attribute {

    private String note;

    /**
     * Default constructor.
     */
    public NoteAttribute() {
        super();
        this.setType(Type.NOTE);
    }

    /**
     * Constructor with all details.
     * @param id Attribute ID.
     * @param label Attribute label.
     * @param note Note.
     */
    public NoteAttribute(String id, String label, String note) {
        this();
        this.id = id;
        this.label = label;
        this.note = note;
    }

    /**
     * Get note.
     * @return Note.
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note.
     * @param note Note.
     */
    public void setNote(String note) {
        this.note = note;
    }
}
