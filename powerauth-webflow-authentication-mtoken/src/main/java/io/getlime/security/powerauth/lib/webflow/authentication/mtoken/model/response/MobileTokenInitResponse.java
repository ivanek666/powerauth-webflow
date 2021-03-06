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
package io.getlime.security.powerauth.lib.webflow.authentication.mtoken.model.response;

import io.getlime.security.powerauth.lib.webflow.authentication.base.AuthStepResponse;

/**
 * Response to the init step of mobile token authentication.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class MobileTokenInitResponse extends AuthStepResponse {

    private String webSocketId;
    private boolean offlineModeAvailable;

    /**
     * Get Web Socket ID.
     * @return Web Socket ID.
     */
    public String getWebSocketId() {
        return webSocketId;
    }

    /**
     * Set Web Socket ID.
     * @param webSocketId Web Socket ID.
     */
    public void setWebSocketId(String webSocketId) {
        this.webSocketId = webSocketId;
    }

    /**
     * Whether offline mode is available.
     * @return True if offline mode is available.
     */
    public boolean isOfflineModeAvailable() {
        return offlineModeAvailable;
    }

    /**
     * Set whether offline mode is available.
     * @param offlineModeAvailable True if offline mode is available.
     */
    public void setOfflineModeAvailable(boolean offlineModeAvailable) {
        this.offlineModeAvailable = offlineModeAvailable;
    }
}
