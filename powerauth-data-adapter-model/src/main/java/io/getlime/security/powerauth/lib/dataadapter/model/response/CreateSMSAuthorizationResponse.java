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
package io.getlime.security.powerauth.lib.dataadapter.model.response;

/**
 * Response after creating SMS OTP authorization message.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class CreateSMSAuthorizationResponse {

    /**
     * Message ID.
     */
    private String messageId;

    /**
     * Default constructor.
     */
    public CreateSMSAuthorizationResponse() {
    }

    /**
     * Constructor with message ID.
     * @param messageId Message ID.
     */
    public CreateSMSAuthorizationResponse(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Get message ID.
     * @return Message ID.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Set message ID.
     * @param messageId Message ID.
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
