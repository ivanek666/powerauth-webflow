/*
 * Copyright 2016 Lime - HighTech Solutions s.r.o.
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
package io.getlime.security.powerauth.app.webauth.model.entity.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.getlime.security.powerauth.app.webauth.model.entity.WebSocketJsonMessage;

import java.util.Base64;

/**
 * @author Roman Strobl
 */
public class AuthenticationRequest extends WebSocketJsonMessage {

    @JsonProperty
    private String operationId;
    @JsonProperty
    private WebAuthMethod method;
    @JsonProperty
    private String credentials;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String operationId, WebAuthMethod method, String credentials) {
        this.action = WebAuthAction.LOGIN_CONFIRM;
        this.method = method;
        this.credentials = credentials;
    }

    @JsonIgnore
    public String getUsername() {
        if (credentials == null || method==null || operationId==null) {
            return null;
        }
        try {
            switch (method) {
                case BASIC_BASE64:
                    String decoded = new String(Base64.getDecoder().decode(credentials));
                    String[] parts = decoded.split(":");
                    if (parts.length != 2) {
                        return null;
                    }
                    return parts[0];
                default:
                    return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @JsonIgnore
    public char[] getPassword() {
        if (credentials == null) {
            return null;
        }
        try {
            switch (method) {
                case BASIC_BASE64:
                    // TODO - convert without String for security reasons
                    String decoded = new String(Base64.getDecoder().decode(credentials));
                    String[] parts = decoded.split(":");
                    if (parts.length != 2) {
                        return null;
                    }
                    return parts[1].toCharArray();
                default:
                    return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getOperationId() {
        return operationId;
    }

}