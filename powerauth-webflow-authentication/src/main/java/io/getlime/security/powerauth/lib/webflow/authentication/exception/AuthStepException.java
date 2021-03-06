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

package io.getlime.security.powerauth.lib.webflow.authentication.exception;

/**
 * Exception during an authentication step.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
public class AuthStepException extends Exception {

    private Integer remainingAttempts;
    private String messageId;

    /**
     * Constructor with message and message ID.
     *
     * @param message Error message.
     * @param messageId Error message localization key.
     */
    public AuthStepException(String message, String messageId) {
        super(message);
        this.messageId = messageId;
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Error message.
     * @param cause   Error cause (original exception, if any).
     */
    public AuthStepException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Get number of remaining authentication attempts.
     * @return Number of remaining attempts.
     */
    public Integer getRemainingAttempts() {
        return remainingAttempts;
    }

    /**
     * Set number of remaining authentication attempts.
     * @param remainingAttempts Number of remaining attempts.
     */
    public void setRemainingAttempts(Integer remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    /**
     * Get error message localization key.
     * @return Error message localization key.
     */
    public String getMessageId() {
        return messageId;
    }
}
